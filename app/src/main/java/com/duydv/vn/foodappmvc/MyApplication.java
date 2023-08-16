package com.duydv.vn.foodappmvc;

import android.app.Application;

import com.duydv.vn.foodappmvc.pref.DataStoreManager;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DataStoreManager.init(getApplicationContext());
    }
}
