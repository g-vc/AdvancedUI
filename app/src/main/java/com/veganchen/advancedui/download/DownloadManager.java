package com.veganchen.advancedui.download;

import android.util.ArrayMap;

import com.veganchen.advancedui.download.inter.DownloadCallback;
import com.veganchen.advancedui.sdk.net.OkHttpManager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 下载管理类
 * created by chensihan on 2019/11/30
 */
public class DownloadManager {

    private static volatile DownloadManager instance;

    private Map<String, Call> callMap;

    public static DownloadManager getInstance(){
        if(instance == null){
            synchronized (DownloadManager.class){
                if(instance == null){
                    instance = new DownloadManager();
                }
            }
        }

        return instance;
    }

    private DownloadManager(){
        callMap = new ArrayMap<>();
    }

    private void addCall(String key, Call call){
        synchronized (callMap){
            callMap.put(key, call);
        }
    }

    private void removeCall(String key){
        synchronized (callMap){
            callMap.remove(key);
        }
    }

    public void stop(String url){
        synchronized (callMap){
            Call call = callMap.get(url);
            if(call != null){
                call.cancel();
            }
        }
    }


    public void clear(){
        synchronized (callMap){
            for(Call call : callMap.values()){
                call.cancel();
            }
            instance = null;
        }
    }

    public void download(final String url, int progress, long total, final String filePath, final DownloadCallback callback) {
        Single.create(new SingleOnSubscribe<Boolean>() {
            @Override
            public void subscribe(SingleEmitter<Boolean> emitter) throws Exception {
                OkHttpClient client = OkHttpManager.getInstance().getOkHttpClient();
                Request.Builder builder = new Request.Builder()
                        .url(url);
                if(progress > 0){
                    builder.addHeader("Range", "bytes=" + progress + "-" + total);
                }
                Request request = builder.build();
                Call call = client.newCall(request);
                addCall(url, call);
                Response response = call.execute();

                if(response.code() >= 400){
                    callback.onFail();
                    return;
                }
                File file = new File(filePath);
                File parent = file.getParentFile();
                if (!parent.exists()) {
                    parent.mkdirs();
                }
                file.createNewFile();
                FileOutputStream fos = null;
                InputStream is = response.body().byteStream();
                long total = response.body().contentLength();
                BufferedInputStream bis = new BufferedInputStream(is);
                byte[] buffer = new byte[1024];
                int len = 0;
                try {
                    fos = new FileOutputStream(file);
                    while ((len = bis.read(buffer)) != -1){
                        fos.write(buffer, 0, len);
                        callback.onProgress(len, total);
                    }
                    emitter.onSuccess(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    emitter.onError(e);
                } finally {
                    if(fos != null){
                        fos.close();
                        fos = null;
                    }
                    if(is != null){
                        is.close();
                        is = null;
                    }
                    if(bis != null){
                        bis.close();
                        bis = null;
                    }
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean o) throws Exception {
                        callback.onSuccess();
                        removeCall(url);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callback.onFail();
                        removeCall(url);
                    }
                });
    }
}