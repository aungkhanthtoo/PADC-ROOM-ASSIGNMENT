package com.padcmyanmar.sfc.data.models;

import android.arch.persistence.db.SimpleSQLiteQuery;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.google.gson.Gson;
import com.padcmyanmar.sfc.SFCNewsApp;
import com.padcmyanmar.sfc.data.persistence.AppDatabase;
import com.padcmyanmar.sfc.data.persistence.queries.NewWithRelation;
import com.padcmyanmar.sfc.data.vo.ActedUserVO;
import com.padcmyanmar.sfc.data.vo.CommentActionVO;
import com.padcmyanmar.sfc.data.vo.FavoriteActionVO;
import com.padcmyanmar.sfc.data.vo.ImagesInNewVO;
import com.padcmyanmar.sfc.data.vo.NewsVO;
import com.padcmyanmar.sfc.data.vo.PublicationVO;
import com.padcmyanmar.sfc.data.vo.SentToVO;
import com.padcmyanmar.sfc.events.RestApiEvents;
import com.padcmyanmar.sfc.network.MMNewsAPI;
import com.padcmyanmar.sfc.network.MMNewsDataAgentImpl;
import com.padcmyanmar.sfc.network.reponses.GetNewsResponse;
import com.padcmyanmar.sfc.utils.AppConstants;
import com.padcmyanmar.sfc.utils.Apply;
import com.padcmyanmar.sfc.utils.NewsFromDb;
import com.padcmyanmar.sfc.utils.RoomQuery;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by aung on 12/3/17.
 */

public class NewsModel {

    private static NewsModel objInstance;

    private List<NewsVO> mNews;
    private int mmNewsPageIndex = 1;
    private AppDatabase mDb;
    private NewsFromDb mAllNews;
    private MMNewsAPI theAPI;
    private PublishSubject<List<NewsVO>> mSubject;

    private void initAPI() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://padcmyanmar.com/padc-3/mm-news/apis/")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        theAPI = retrofit.create(MMNewsAPI.class);
        mSubject = PublishSubject.create();

    }

    private NewsModel() {
        //EventBus.getDefault().register(this);
        mNews = new ArrayList<>();
        mDb = AppDatabase.getInstance();
        initAPI();
    }

    public static NewsModel getInstance() {
        if (objInstance == null) {
            objInstance = new NewsModel();
        }
        return objInstance;
    }

    public void startLoadingMMNews() {
       // MMNewsDataAgentImpl.getInstance().loadMMNews(AppConstants.ACCESS_TOKEN, mmNewsPageIndex);

        Observable<GetNewsResponse> newsResponseObservable = theAPI.loadMMNews(mmNewsPageIndex, AppConstants.ACCESS_TOKEN);
        newsResponseObservable
                .subscribeOn(Schedulers.io())
                .map(new Function<GetNewsResponse, List<NewsVO>>() {
                    @Override
                    public List<NewsVO> apply(GetNewsResponse getNewsResponse) throws Exception {
                        return getNewsResponse.getNewsList();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<NewsVO>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mSubject.onSubscribe(d);
                    }

                    @Override
                    public void onNext(List<NewsVO> newsVOS) {
                        mSubject.onNext(newsVOS);
                        mNews.addAll(newsVOS);
                        mmNewsPageIndex++;
//                        clearAll();
//                        saveBulkData(newsVOS);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mSubject.onError(e);
                        Log.e("NewsModel", "onError: "+ e.getMessage() );
                    }

                    @Override
                    public void onComplete() {
                        mSubject.onComplete();
                        Log.d("NewsModel", "onComplete: ");
                    }
                });

    }

    public NewsFromDb newsFromRoom() {
        if (mAllNews == null) {
            mAllNews = new NewsFromDb();
        }
        return mAllNews;
    }

    public RoomQuery<NewsVO> newWithId(String id) {
        return  new RoomQuery<>(this::querySingleNew, id);
    }

    @WorkerThread
    private List<NewsVO> querySingleNew(String id) {

        final List<NewsVO> newsVOS = new ArrayList<>();
        Log.d(SFCNewsApp.LOG_TAG, "Single New Id : " + id);

        NewWithRelation single = mDb.newsDao().getRelationWithId(id);
        Log.d(SFCNewsApp.LOG_TAG, "Single New Relation: "+single);

        NewsVO newsVO = single.parseToVO(mDb);
        newsVOS.add(newsVO);
        return newsVOS;
    }

    /*
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onNewsDataLoaded(RestApiEvents.NewsDataLoadedEvent event) {
        mNews.addAll(event.getLoadNews());
        mmNewsPageIndex = event.getLoadedPageIndex() + 1;
        clearAll();
        saveBulkData(event.getLoadNews());
    }
    */

    private void clearAll() {
        mDb.actedUserDao().deleteAll();
        mDb.favouriteActionDao().deleteAll();
        mDb.commentActionDao().deleteAll();
        mDb.sentToDao().deleteAll();
        mDb.imagesInNewDao().deleteAll();
        mDb.newsDao().deleteAll();
        mDb.publicationDao().deleteAll();
    }

    private void saveBulkData(@NonNull List<NewsVO> newList) {
        List<PublicationVO> publicationList = new ArrayList<>();
        List<ImagesInNewVO> imageList = new ArrayList<>();
        List<FavoriteActionVO> favoriteList = new ArrayList<>();
        List<CommentActionVO> commentList = new ArrayList<>();
        List<SentToVO> sentToList = new ArrayList<>();
        List<ActedUserVO> userList = new ArrayList<>();

        forEach(newList, newVO -> {
            newVO.setPublicationId(newVO.getPublication().getPublicationId());
            publicationList.add(newVO.getPublication());

            forEach(newVO.getImages(),
                    imageUrl -> imageList.add(new ImagesInNewVO(imageUrl, (newVO.getNewsId()))));

            forEach(newVO.getFavoriteActions(),
                    favouriteVO -> {
                        userList.add(favouriteVO.getActedUser());
                        favouriteVO.setNewsId(newVO.getNewsId());
                        favouriteVO.setUserId(favouriteVO.getActedUser().getUserId());
                        favoriteList.add(favouriteVO);
                    });
            forEach(newVO.getSentToActions(),
                    sentToVO -> {
                        userList.add(sentToVO.getSender());
                        userList.add(sentToVO.getReceiver());
                        sentToVO.setNewsId(newVO.getNewsId());
                        sentToVO.setSenderId(sentToVO.getSender().getUserId());
                        sentToVO.setReceiverId(sentToVO.getReceiver().getUserId());
                        sentToList.add(sentToVO);
                    });
            forEach(newVO.getCommentActions(),
                    commentActionVO -> {
                        userList.add(commentActionVO.getActedUser());
                        commentActionVO.setNewsId(newVO.getNewsId());
                        commentActionVO.setUserId(commentActionVO.getActedUser().getUserId());
                        commentList.add(commentActionVO);
                    });
        });


        long[] publications = mDb.publicationDao().insertAll(publicationList.toArray(new PublicationVO[0]));
        Log.d("INSERTED", "publications : " + publications.length);

        long[] news = mDb.newsDao().insertAll(newList.toArray(new NewsVO[0]));
        Log.d("INSERTED", "news : " +news.length);

        long[] images = mDb.imagesInNewDao().insertAll(imageList.toArray(new ImagesInNewVO[0]));
        Log.d("INSERTED", "images : " + images.length);

        long[] actedUsers = mDb.actedUserDao().insertAll(userList.toArray(new ActedUserVO[0]));
        Log.d("INSERTED", "actedUsers : " + actedUsers.length);

        long[] favorites = mDb.favouriteActionDao().insertAll(favoriteList.toArray(new FavoriteActionVO[0]));
        Log.d("INSERTED", "favoritesRoom : " + favorites.length);

        long[] comments = mDb.commentActionDao().insertAll(commentList.toArray(new CommentActionVO[0]));
        Log.d("INSERTED", "comments : " + comments.length);

        long[] sentTos = mDb.sentToDao().insertAll(sentToList.toArray(new SentToVO[0]));
        Log.d("INSERTED", "sentTos : " + sentTos.length);
    }

    private <T> void forEach(List<T> list, Apply<T> block) {
        for (T data :
                list) {
            block.apply(data);
        }
    }

    public PublishSubject<List<NewsVO>> getNewsFromNetwork() {
       return mSubject;
    }
}
