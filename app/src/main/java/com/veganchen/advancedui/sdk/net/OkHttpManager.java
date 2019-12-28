package com.veganchen.advancedui.sdk.net;

import okhttp3.OkHttpClient;

/**
 * okHttp管理类
 * created by chensihan on 2019/11/30
 */
public class OkHttpManager {

    private static volatile OkHttpManager instance;
    private OkHttpClient okHttpClient;

    public static OkHttpManager getInstance(){
        if(instance == null){
            synchronized (OkHttpManager.class){
                if(instance == null){
                    instance = new OkHttpManager();
                }
            }
        }

        return instance;
    }

    private OkHttpManager(){
        okHttpClient = new OkHttpClient();
    }

    public OkHttpClient getOkHttpClient(){
        return okHttpClient;
    }
}