package com.veganchen.advancedui.download;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.veganchen.advancedui.R;
import com.veganchen.advancedui.download.inter.DownloadCallback;
import com.veganchen.advancedui.util.CandyUtil;
import com.veganchen.advancedui.util.CommonConfig;

public class DownloadActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvStart1;
    private TextView tvStop1;
    private TextView tvProgress1;
    private TextView tvStart2;
    private TextView tvStop2;
    private TextView tvProgress2;

    private String url1 = "http://192.168.0.109/charles-proxy-4.5.4-win64.msi";
    private String url2 = "http://192.168.0.109/zxing-master.zip";
    private String filePath1 = CommonConfig.getContext().getExternalCacheDir() + "/file1";
    private String filePath2 = CommonConfig.getContext().getExternalCacheDir() + "/file2";

    private Handler handler = new Handler();

    private int progress1;
    private int progress2;
    private long total1;
    private long total2;

    public static void start(Context context) {
        Intent intent = new Intent(context, DownloadActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        initView();
    }

    private void initView() {
        tvStart1 = findViewById(R.id.tvStart1);
        tvStop1 = findViewById(R.id.tvStop1);
        tvProgress1 = findViewById(R.id.tvProgress1);
        tvStart2 = findViewById(R.id.tvStart2);
        tvStop2 = findViewById(R.id.tvStop2);
        tvProgress2 = findViewById(R.id.tvProgress2);

        tvStart1.setOnClickListener(this);
        tvStart2.setOnClickListener(this);
        tvStop1.setOnClickListener(this);
        tvStop2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tvStart1) {
            DownloadManager.getInstance().download(url1, progress1, total1, filePath1, new DownloadCallback() {
                @Override
                public void onSuccess() {
                    progress1 = 0;
                }

                @Override
                public void onFail() {
                    Log.i("DownloadActivity", "onFail");
                }

                @Override
                public void onProgress(long progress, long total) {
                    total1 = total;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progress1 += progress;
                            String percent = CandyUtil.getPercent(progress1, total);
                            tvProgress1.setText(percent);
                        }
                    });
                }
            });
        } else if (id == R.id.tvStart2) {
            DownloadManager.getInstance().download(url2, progress2, total2, filePath2, new DownloadCallback() {
                @Override
                public void onSuccess() {
                    progress2 = 0;
                }

                @Override
                public void onFail() {
                    Log.i("DownloadActivity", "onFail");
                }

                @Override
                public void onProgress(long progress, long total) {
                    total2 = total;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progress2 += progress;
                            String percent = CandyUtil.getPercent(progress2, total);
                            tvProgress2.setText(percent);
                        }
                    });
                }
            });
        } else if (id == R.id.tvStop1) {
            DownloadManager.getInstance().stop(url1);
        } else if (id == R.id.tvStop2) {
            DownloadManager.getInstance().stop(url2);
        }
    }
}