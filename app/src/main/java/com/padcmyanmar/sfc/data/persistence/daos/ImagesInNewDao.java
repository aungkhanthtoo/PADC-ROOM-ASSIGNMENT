package com.padcmyanmar.sfc.data.persistence.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.padcmyanmar.sfc.data.vo.ImagesInNewVO;
import com.padcmyanmar.sfc.data.vo.NewsVO;

import java.util.List;

@Dao
public interface ImagesInNewDao extends BaseDao<ImagesInNewVO> {

    @Query("DELETE FROM ImagesInNew")
    void deleteAll();

    @Query("SELECT * FROM ImagesInNew")
    List<ImagesInNewVO> getAll();
}
