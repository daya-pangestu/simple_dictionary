package com.daya.dictio;

import android.app.Application;

import timber.log.Timber;

public class dictio extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
