package com.padcmyanmar.sfc.data.persistence.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.padcmyanmar.sfc.data.vo.NewsVO;
import com.padcmyanmar.sfc.data.vo.SentToVO;

import java.util.List;

@Dao
public interface SentToDao extends BaseDao<SentToVO> {

    @Query("DELETE FROM SentToActions")
    void deleteAll();

    @Query("SELECT * FROM SentToActions")
    List<SentToVO> getAll();
}
