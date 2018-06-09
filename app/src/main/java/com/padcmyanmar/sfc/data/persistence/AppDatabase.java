package com.padcmyanmar.sfc.data.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.padcmyanmar.sfc.SFCNewsApp;
import com.padcmyanmar.sfc.data.persistence.daos.ActedUserDao;
import com.padcmyanmar.sfc.data.persistence.daos.CommentActionDao;
import com.padcmyanmar.sfc.data.persistence.daos.FavouriteActionDao;
import com.padcmyanmar.sfc.data.persistence.daos.ImagesInNewDao;
import com.padcmyanmar.sfc.data.persistence.daos.NewsDao;
import com.padcmyanmar.sfc.data.persistence.daos.PublicationDao;
import com.padcmyanmar.sfc.data.persistence.daos.SentToDao;
import com.padcmyanmar.sfc.data.vo.ActedUserVO;
import com.padcmyanmar.sfc.data.vo.CommentActionVO;
import com.padcmyanmar.sfc.data.vo.FavoriteActionVO;
import com.padcmyanmar.sfc.data.vo.ImagesInNewVO;
import com.padcmyanmar.sfc.data.vo.NewsVO;
import com.padcmyanmar.sfc.data.vo.PublicationVO;
import com.padcmyanmar.sfc.data.vo.SentToVO;

@Database(entities = {NewsVO.class, PublicationVO.class, ActedUserVO.class,
        SentToVO.class, CommentActionVO.class, FavoriteActionVO.class,
        ImagesInNewVO.class},
        version = 1, exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "NewsDB";

    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance() {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(SFCNewsApp.getAppContext(), AppDatabase.class, DB_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public abstract ActedUserDao actedUserDao();

    public abstract CommentActionDao commentActionDao();

    public abstract FavouriteActionDao favouriteActionDao();

    public abstract ImagesInNewDao imagesInNewDao();

    public abstract NewsDao newsDao();

    public abstract PublicationDao publicationDao();

    public abstract SentToDao sentToDao();

}
