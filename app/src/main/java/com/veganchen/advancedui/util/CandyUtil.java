package com.veganchen.advancedui.util;

public class CandyUtil {

    public static String getPercent(long progress, long total){
        int temp = (int)(progress * 100 / total);
        return temp + "%";
    }
}
