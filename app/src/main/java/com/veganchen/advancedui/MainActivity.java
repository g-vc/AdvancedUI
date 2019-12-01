package com.veganchen.advancedui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();
    private ViewPager vp;
    private List<View> viewList = new ArrayList<>();

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        vp = findViewById(R.id.vp);
        View view0 = LayoutInflater.from(this).inflate(R.layout.view_test3, null);
        View view1 = LayoutInflater.from(this).inflate(R.layout.view_test1, null);
        View view2 = LayoutInflater.from(this).inflate(R.layout.view_test2, null);
        View view3 = LayoutInflater.from(this).inflate(R.layout.view_test3, null);
        View view4 = LayoutInflater.from(this).inflate(R.layout.view_test1, null);
        viewList.add(view0);
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);
        CarouselAdapter adapter = new CarouselAdapter(viewList);
        vp.setAdapter(adapter);
        vp.addOnPageChangeListener(new CarouselPageChangeListener(viewList.size()));
        vp.setCurrentItem(2);
        vp.setOffscreenPageLimit(5);
        vp.setPageMargin(20);
        vp.setPageTransformer(false, new CarouselTransformer());
    }

    private class CarouselPageChangeListener implements ViewPager.OnPageChangeListener {

        int mPosition;
        private int mSize;

        CarouselPageChangeListener(int size) {
            mSize = size;
        }

        /**
         * 页面滑动状态停止前一直调用
         *
         * @param position             当前点击滑动页面的位置
         * @param positionOffset       当前页面偏移的百分比
         * @param positionOffsetPixels 当前页面偏移的像素位置
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            Log.i(TAG, "onPageScrolled position:" + position
                    + " positionOffset:" + positionOffset + " positionOffsetPixels:" + positionOffsetPixels);
        }

        /**
         * 滑动后显示的页面和滑动前不同，调用
         *
         * @param position 滑动后最终显示的view的位置
         */
        @Override
        public void onPageSelected(int position) {
            Log.i(TAG, "onPageSelected position:" + position);
            mPosition = position;
        }

        /**
         * 页面状态改变时调用
         *
         * @param state 当前页面的状态
         *              <p>
         *              SCROLL_STATE_IDLE：空闲状态
         *              SCROLL_STATE_DRAGGING：滑动状态
         *              SCROLL_STATE_SETTLING：滑动后滑翔的状态
         */
        @Override
        public void onPageScrollStateChanged(int state) {
            Log.i(TAG, "onPageScrollStateChanged state:" + state);
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                if (mPosition == 0) {
                    vp.setCurrentItem(mSize - 2, false);
                } else if (mPosition == mSize - 1) {
                    vp.setCurrentItem(1, false);
                }
            }
        }
    }

    private class CarouselAdapter extends PagerAdapter {

        private List<View> mViewList;

        CarouselAdapter(List<View> viewList) {
            mViewList = viewList;
        }

        @Override
        public int getCount() {
            return mViewList == null ? 0 : mViewList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = mViewList.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(mViewList.get(position));
        }
    }

    /**
     * 翻页动画
     */
    private class CarouselTransformer implements ViewPager.PageTransformer {

        private float scale = 0.8f;

        @Override
        public void transformPage(@NonNull View page, float position) {
            if (position <= -1 || position >= 1) {
                page.setScaleY(scale);
            } else {
                if (position == 0) {
                    page.setScaleY(1f);
                } else {
                    float finalScale = 1f - 0.2f * Math.abs(position);
                    page.setScaleY(finalScale);
                }
            }
        }
    }
}