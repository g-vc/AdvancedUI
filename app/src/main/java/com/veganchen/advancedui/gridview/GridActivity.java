package com.veganchen.advancedui.gridview;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.veganchen.advancedui.R;
import com.veganchen.advancedui.gridview.adapter.GridAdapter;
import com.veganchen.advancedui.gridview.adapter.GridAdapter2;

import java.util.ArrayList;
import java.util.List;

public class GridActivity extends AppCompatActivity implements View.OnClickListener {

    private GridView gv;
    private TextView tvChange;
    private GridAdapter gridAdapter;
    private GridAdapter2 gridAdapter2;

    private LayoutTransition mTransitioner;

    public static void start(Context context) {
        Intent intent = new Intent(context, GridActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        initLayoutTransition();
        initView();
    }

    private void initLayoutTransition(){
        mTransitioner = new LayoutTransition();
//        /**
//         * 添加View时过渡动画效果
//         */
//        ObjectAnimator addAnimator = ObjectAnimator.ofFloat(null, "rotationY", 0, 90,0).
//                setDuration(mTransitioner.getDuration(LayoutTransition.APPEARING));
//        mTransitioner.setAnimator(LayoutTransition.APPEARING, addAnimator);
//
//        /**
//         * 移除View时过渡动画效果
//         */
//        ObjectAnimator removeAnimator = ObjectAnimator.ofFloat(null, "rotationX", 0, -90, 0).
//                setDuration(mTransitioner.getDuration(LayoutTransition.DISAPPEARING));
//        mTransitioner.setAnimator(LayoutTransition.DISAPPEARING, removeAnimator);
//
//        /**
//         * view 动画改变时，布局中的每个子view动画的时间间隔
//         */
//        mTransitioner.setStagger(LayoutTransition.CHANGE_APPEARING, 30);
//        mTransitioner.setStagger(LayoutTransition.CHANGE_DISAPPEARING, 30);


        /**
         *LayoutTransition.CHANGE_APPEARING和LayoutTransition.CHANGE_DISAPPEARING的过渡动画效果
         * 必须使用PropertyValuesHolder所构造的动画才会有效果，不然无效！使用ObjectAnimator是行不通的,
         * 发现这点时真特么恶心,但没想到更恶心的在后面,在测试效果时发现在构造动画时，”left”、”top”、”bottom”、”right”属性的
         * 变动是必须设置的,至少设置两个,不然动画无效,问题是我们即使这些属性不想变动!!!也得设置!!!
         * 我就问您恶不恶心!,因为这里不想变动,所以设置为(0,0)
         *
         */
        PropertyValuesHolder pvhLeft =
                PropertyValuesHolder.ofInt("left", 0, 0);
        PropertyValuesHolder pvhTop =
                PropertyValuesHolder.ofInt("top", 0, 0);
        PropertyValuesHolder pvhRight =
                PropertyValuesHolder.ofInt("right", 0, 0);
        PropertyValuesHolder pvhBottom =
                PropertyValuesHolder.ofInt("bottom", 0, 0);


//        /**
//         * view被添加时,其他子View的过渡动画效果
//         */
//        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("alpha", 0, 1);
//        final ObjectAnimator changeIn = ObjectAnimator.ofPropertyValuesHolder(
//                this, pvhLeft,  pvhBottom, animator).
//                setDuration(mTransitioner.getDuration(LayoutTransition.CHANGE_APPEARING));
//
//        mTransitioner.setAnimator(LayoutTransition.CHANGE_APPEARING, changeIn);
//
//
        /**
         * view移除时，其他子View的过渡动画
         */
//        PropertyValuesHolder pvhRotation =
//                PropertyValuesHolder.ofFloat("alpha", 0, 1);
//        final ObjectAnimator changeOut = ObjectAnimator.ofPropertyValuesHolder(
//                this, pvhLeft, pvhBottom, pvhRotation).
//                setDuration(mTransitioner.getDuration(LayoutTransition.CHANGE_DISAPPEARING));
//
//        mTransitioner.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, changeOut);

//        mTransitioner.setAnimator(LayoutTransition.CHANGING, changeOut);
    }

    private void initView() {
        tvChange = findViewById(R.id.tvChange);
        tvChange.setOnClickListener(this);
        gv = findViewById(R.id.gv);
        gv.setNumColumns(2);
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            dataList.add("text" + i);
        }
        gridAdapter = new GridAdapter(dataList);
        gridAdapter2 = new GridAdapter2(dataList);
        gv.setLayoutTransition(mTransitioner);
        gv.setAdapter(gridAdapter);
    }

    @Override
    public void onClick(View view) {
        if (gv.getAdapter() instanceof GridAdapter) {
            gv.setNumColumns(4);
            gv.setAdapter(gridAdapter2);
        } else {
            gv.setNumColumns(2);
            gv.setAdapter(gridAdapter);
        }
    }
}