package com.padcmyanmar.sfc;

import android.app.Application;
import android.support.annotation.NonNull;

import com.padcmyanmar.sfc.data.models.NewsModel;

/**
 * Created by aung on 11/4/17.
 */

public class SFCNewsApp extends Application {

    public static final String LOG_TAG = "SFCNewsApp";

    private static SFCNewsApp appContext;

    public static SFCNewsApp getAppContext() {
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        NewsModel.getInstance().startLoadingMMNews();
    }

}
