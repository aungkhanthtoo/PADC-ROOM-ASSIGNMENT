package com.padcmyanmar.sfc.data.persistence.queries;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;
import android.support.annotation.NonNull;
import com.padcmyanmar.sfc.data.persistence.AppDatabase;
import com.padcmyanmar.sfc.data.vo.CommentActionVO;
import com.padcmyanmar.sfc.data.vo.FavoriteActionVO;
import com.padcmyanmar.sfc.data.vo.ImagesInNewVO;
import com.padcmyanmar.sfc.data.vo.NewsVO;
import com.padcmyanmar.sfc.data.vo.SentToVO;
import com.padcmyanmar.sfc.utils.Apply;
import com.padcmyanmar.sfc.utils.Function;
import java.util.ArrayList;
import java.util.List;

public class NewWithRelation {

    @Embedded
    public NewsVO newVO;

    @Relation(entity = ImagesInNewVO.class, parentColumn = "news-id", entityColumn = "news-id")
    public List<ImagesInNewVO> images;

    @Relation(entity = FavoriteActionVO.class, parentColumn = "news-id", entityColumn = "news-id")
    public List<FavoriteActionVO> favorites;

    @Relation(entity = CommentActionVO.class, parentColumn = "news-id", entityColumn = "news-id")
    public List<CommentActionVO> comments;

    @Relation(entity = SentToVO.class, parentColumn = "news-id", entityColumn = "news-id")
    public List<SentToVO> sentTos;

    public NewsVO parseToVO(@NonNull AppDatabase database) {
        newVO.setPublication(database.publicationDao().getWithId(newVO.getPublicationId()));
        newVO.setImages(map(images, ImagesInNewVO::getImageUrl));
        newVO.setFavoriteActions(mapWithId(favorites,
                favoriteVO -> favoriteVO.setActedUser(database.actedUserDao().getWithId(favoriteVO.getUserId()))));
        newVO.setCommentActions(mapWithId(comments,
                commentVO -> commentVO.setActedUser(database.actedUserDao().getWithId(commentVO.getUserId()))));
        newVO.setSentToActions(mapWithId(sentTos,
                sentToVO -> {
                    sentToVO.setSender(database.actedUserDao().getWithId(sentToVO.getSenderId()));
                    sentToVO.setReceiver(database.actedUserDao().getWithId(sentToVO.getReceiverId()));
                }));
        return newVO;
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

    private <X> List<X> mapWithId(List<X> list, Apply<X> block) {
        forEach(list, block);
        return list;
    }

    @Override
    public String toString() {
        return "NewWithRelation{" +
                "\nnewVO=" + newVO +
                ",\n images=" + images +
                ",\n favorites=" + favorites +
                ",\n comments=" + comments +
                ",\n sentTos=" + sentTos +
                '}';
    }

}
