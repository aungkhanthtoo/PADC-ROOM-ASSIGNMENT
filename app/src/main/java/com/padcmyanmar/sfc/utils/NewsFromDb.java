package com.padcmyanmar.sfc.utils;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.os.AsyncTask;
import android.util.Log;

import com.padcmyanmar.sfc.SFCNewsApp;
import com.padcmyanmar.sfc.data.persistence.AppDatabase;
import com.padcmyanmar.sfc.data.persistence.queries.NewWithRelation;
import com.padcmyanmar.sfc.data.vo.NewsVO;

import java.util.ArrayList;
import java.util.List;

public class NewsFromDb extends AsyncTask<Void, Void, List<NewsVO>> implements LifecycleObserver {

    private Observer mObserver;

    private List<NewsVO> mCache;

    private boolean hasFetched;

    private AppDatabase mDb;

    public NewsFromDb(AppDatabase database) {
        mDb = database;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
        if (mObserver != null) {
            if (mCache != null) {
                mObserver.update(mCache);
            } else {
                hasFetched = false;
                execute();
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy() {
        if (mObserver != null) mObserver = null;
    }

    public NewsFromDb observe(Observer observer) {
        mObserver = observer;
        return this;
    }

    public void notifyFetched() {
        hasFetched = true;
    }

    @Override
    protected List<NewsVO> doInBackground(Void... voids) {
        List<NewWithRelation> newsWithRelation = AppDatabase.getInstance().newsDao().getAllWithRelations();
        Log.d(SFCNewsApp.LOG_TAG, "NewsWithRelations : " + newsWithRelation);

        List<NewsVO> newsListFromDb = map(newsWithRelation, newRelations -> newRelations.parseToVO(AppDatabase.getInstance()));
        Log.d(SFCNewsApp.LOG_TAG, "\nNewVOsConverted : " + newsListFromDb);

        return newsListFromDb;
    }

    @Override
    protected void onPostExecute(List<NewsVO> newsVOS) {
        super.onPostExecute(newsVOS);
        mCache = newsVOS;
        mObserver.update(mCache);
    }

    public interface Observer {

        void update(List<NewsVO> news);
    }

    private <T> void forEach(List<T> list, Apply<T> block) {
        for (T data :
                list) {
            block.apply(data);
        }
    }

    private <X, Y> List<Y> map(List<X> list, Function<X, Y> apply) {
        final List<Y> newList = new ArrayList<>();
        forEach(list, x -> newList.add(apply.map(x)));
        return newList;
    }
}
