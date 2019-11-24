package com.veganchen.advancedui.util;

public class ScreenUtil {

    public static int dp2px(float dp){
        float scale = CommonConfig.getContext().getResources().getDisplayMetrics().density;

        return (int)(dp * scale + 0.5f);
    }

    public static int px2dp(float px){
        float scale = CommonConfig.getContext().getResources().getDisplayMetrics().density;

        return (int)(px / scale + 0.5f);
    }
}
