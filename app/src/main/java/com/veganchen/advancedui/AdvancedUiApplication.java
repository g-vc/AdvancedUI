package com.veganchen.advancedui;

import android.app.Application;

import com.veganchen.advancedui.util.CommonConfig;

public class AdvancedUiApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CommonConfig.setContext(this);
    }
}