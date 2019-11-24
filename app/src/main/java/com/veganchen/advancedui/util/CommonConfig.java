package com.veganchen.advancedui.util;

import android.content.Context;

public class CommonConfig {

    private static Context mContext;

    public static Context getContext(){
        return mContext;
    }

    public static void setContext(Context context){
        mContext = context;
    }
}
