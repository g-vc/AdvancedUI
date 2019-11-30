package com.veganchen.advancedui.download;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

    private String url1 = "http://www.test.com/aaa";
    private String url2 = "http://www.test.com/bbb";
    private String filePath1 = CommonConfig.getContext().getExternalCacheDir().getAbsolutePath() + "/file1";
    private String filePath2 = CommonConfig.getContext().getExternalCacheDir().getAbsolutePath() + "/file2";

    private int progress1;
    private int progress2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        initView();
    }

    private void initView(){
        tvStart1 = findViewById(R.id.tvStart1);
        tvStop1 = findViewById(R.id.tvStop1);
        tvProgress1 = findViewById(R.id.tvProgress1);
        tvStart2 = findViewById(R.id.tvStart2);
        tvStop2 = findViewById(R.id.tvStop2);
        tvProgress2 = findViewById(R.id.tvProgress2);

        tvStart1.setOnClickListener(this);
        tvStart2.setOnClickListener(this);
        tvStop1.setOnClickListener(this);
        tvStop1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.tvStart1){
            DownloadManager.getInstance().download(url1, filePath1, new DownloadCallback() {
                @Override
                public void onSuccess() {
                    progress1 = 0;
                }

                @Override
                public void onFail() {

                }

                @Override
                public void onProgress(long progress, long total) {
                    progress1 += progress;
                    String percent = CandyUtil.getPercent(progress, total);
                    tvProgress1.setText(percent);
                }
            });
        } else if(id == R.id.tvStart2){
            DownloadManager.getInstance().download(url2, filePath2, new DownloadCallback() {
                @Override
                public void onSuccess() {
                    progress2 = 0;
                }

                @Override
                public void onFail() {

                }

                @Override
                public void onProgress(long progress, long total) {
                    progress2 += progress;
                    String percent = CandyUtil.getPercent(progress, total);
                    tvProgress2.setText(percent);
                }
            });
        } else if(id == R.id.tvStop1){
            DownloadManager.getInstance().stop(url1);
        } else if(id == R.id.tvStop2){
            DownloadManager.getInstance().stop(url2);
        }
    }
}