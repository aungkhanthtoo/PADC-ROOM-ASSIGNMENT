package com.padcmyanmar.sfc.data.persistence.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteQuery;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RawQuery;
import android.arch.persistence.room.Transaction;

import com.padcmyanmar.sfc.data.persistence.queries.NewWithPublication;
import com.padcmyanmar.sfc.data.persistence.queries.NewWithRelation;
import com.padcmyanmar.sfc.data.vo.NewsVO;

import java.util.List;

@Dao
public interface NewsDao extends BaseDao<NewsVO> {

    @Query("DELETE FROM New")
    void deleteAll();

    @Query("SELECT * FROM New")
    List<NewsVO> getAll();

    @Query("SELECT New.*, Publications.logo, Publications.title FROM New INNER JOIN Publications ON New.`publication-id` = Publications.`publication-id`")
    List<NewWithPublication> getAllWithPublication();

    @Transaction
    @Query("SELECT * FROM New WHERE New.id = :id")
    List<NewWithRelation> getWithRelationId(int id);

    @Transaction
    @Query("SELECT * FROM New WHERE `news-id` = :newsId")
    NewWithRelation getRelationWithId(String newsId);

    @Query("SELECT * FROM New WHERE `publication-id` = :pubId")
    LiveData<NewsVO> getWithPublication(String pubId);

    @Query("SELECT * FROM New WHERE `news-id` = :newsId")
    LiveData<NewsVO> getWithNewsIdAsync(String newsId);

    @RawQuery
    NewsVO getWithId(SupportSQLiteQuery query);

    @Transaction
    @Query("SELECT * FROM New")
    List<NewWithRelation> getAllWithRelations();

}
