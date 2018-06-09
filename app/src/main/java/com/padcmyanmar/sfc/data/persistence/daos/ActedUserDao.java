package com.padcmyanmar.sfc.data.persistence.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.padcmyanmar.sfc.data.vo.ActedUserVO;
import com.padcmyanmar.sfc.data.vo.NewsVO;

import java.util.List;

@Dao
public interface ActedUserDao extends BaseDao<ActedUserVO> {

    @Query("DELETE FROM ActedUsers")
    void deleteAll();

    @Query("SELECT * FROM ActedUsers")
    List<ActedUserVO> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(ActedUserVO data);

    @Query("SELECT * FROM ActedUsers WHERE `user-id` = :id")
    ActedUserVO getWithId(String id);
}
