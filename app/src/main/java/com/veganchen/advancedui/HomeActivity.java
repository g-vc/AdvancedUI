package com.veganchen.advancedui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.veganchen.advancedui.download.DownloadActivity;
import com.veganchen.advancedui.dragwidget.DragActivity;
import com.veganchen.advancedui.gridview.GridActivity;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvDrag;
    private TextView tvCarousel;
    private TextView tvDownload;
    private TextView tvGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
    }

    private void initView(){
        tvDrag = findViewById(R.id.tvDrag);
        tvCarousel = findViewById(R.id.tvCarousel);
        tvDownload = findViewById(R.id.tvDownload);
        tvGrid = findViewById(R.id.tvGrid);

        tvDrag.setOnClickListener(this);
        tvCarousel.setOnClickListener(this);
        tvDownload.setOnClickListener(this);
        tvGrid.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.tvDrag){
            DragActivity.start(this);
        } else if(id == R.id.tvCarousel){
            MainActivity.start(this);
        } else if(id == R.id.tvDownload){
            DownloadActivity.start(this);
        } else if(id == R.id.tvGrid){
            GridActivity.start(this);
        }
    }
}
