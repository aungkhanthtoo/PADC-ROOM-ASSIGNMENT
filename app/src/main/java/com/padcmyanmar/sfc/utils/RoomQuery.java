package com.padcmyanmar.sfc.utils;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.OnLifecycleEvent;
import android.os.AsyncTask;

import java.util.List;

public class RoomQuery<T> implements LifecycleObserver {

    private Task<T> mTask;

    private Observer<T> mObserver;

    private QueryMapper<T> mQuery;

    private String mID;

    public RoomQuery(QueryMapper<T> mQuery) {
        this(mQuery, "");
    }

    public RoomQuery(QueryMapper<T> mQuery, String id) {
        this.mQuery = mQuery;
        mID = id;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onStop() {
        if (mTask != null) mTask = null;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy() {
        if (mObserver != null) mObserver = null;
    }

    public RoomQuery<T> observe(Observer<T> observer) {
        mObserver = observer;
        mTask = new Task<>(observer, mQuery, mID);
        mTask.execute();
        return this;
    }

    public interface Observer<D> {
        void update(List<D> news);
    }

    static class Task<E> extends AsyncTask<Void, Void, List<E>> {

        private Observer<E> observer;

        private QueryMapper<E> mapper;

        private String mId;

        Task(Observer<E> observer, QueryMapper<E> queryMapper, String id) {
            this.observer = observer;
            this.mapper = queryMapper;
            this.mId = id;
        }

        @Override
        protected List<E> doInBackground(Void... voids) {
            return mapper.query(mId);
        }

        @Override
        protected void onPostExecute(List<E> newsVOS) {
            super.onPostExecute(newsVOS);
            observer.update(newsVOS);
        }
    }

}
