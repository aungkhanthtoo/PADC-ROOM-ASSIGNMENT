package com.padcmyanmar.sfc.data.persistence.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.padcmyanmar.sfc.data.vo.PublicationVO;

import java.util.List;

@Dao
public interface PublicationDao extends BaseDao<PublicationVO> {

    @Query("DELETE FROM Publications")
    void deleteAll();

    @Query("SELECT * FROM Publications")
    List<PublicationVO> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(PublicationVO data);

    @Query("SELECT * FROM Publications WHERE `publication-id` = :id")
    PublicationVO getWithId(String id);

}
