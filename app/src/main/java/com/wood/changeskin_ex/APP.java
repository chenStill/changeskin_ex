package com.wood.changeskin_ex;

import android.app.Application;

import com.zhy.changeskin.SkinManager;


public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.getInstance().init(this);
    }
}
