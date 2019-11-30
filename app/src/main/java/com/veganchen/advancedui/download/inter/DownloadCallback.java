package com.veganchen.advancedui.download.inter;

/**
 * 下载回调
 * created by chensihan on 2019/11/30
 */
public interface DownloadCallback {

    void onSuccess();

    void onFail();

    void onProgress(long progress, long total);
}