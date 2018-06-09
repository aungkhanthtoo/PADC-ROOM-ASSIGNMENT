package com.padcmyanmar.sfc.data.persistence.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.padcmyanmar.sfc.data.vo.FavoriteActionVO;
import com.padcmyanmar.sfc.data.vo.NewsVO;

import java.util.List;

@Dao
public interface FavouriteActionDao extends BaseDao<FavoriteActionVO> {

    @Query("DELETE FROM FavouriteActions")
    void deleteAll();

    @Query("SELECT * FROM FavouriteActions")
    List<FavoriteActionVO> getAll();
}
