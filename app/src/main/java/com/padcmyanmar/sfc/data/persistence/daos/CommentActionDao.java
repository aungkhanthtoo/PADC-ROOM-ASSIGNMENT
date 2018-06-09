package com.padcmyanmar.sfc.data.persistence.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.padcmyanmar.sfc.data.vo.CommentActionVO;
import com.padcmyanmar.sfc.data.vo.NewsVO;

import java.util.List;

@Dao
public interface CommentActionDao extends BaseDao<CommentActionVO> {

    @Query("DELETE FROM CommentActions")
    void deleteAll();

    @Query("SELECT * FROM CommentActions")
    List<CommentActionVO> getAll();
}
