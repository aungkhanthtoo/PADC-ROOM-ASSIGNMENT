package com.padcmyanmar.sfc.data.persistence.daos;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

public interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(T... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(T data);

}
