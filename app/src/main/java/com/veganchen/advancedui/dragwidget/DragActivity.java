package com.veganchen.advancedui.dragwidget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.veganchen.advancedui.R;
import com.veganchen.advancedui.dragwidget.adapter.DragAdapter;
import com.veganchen.advancedui.dragwidget.adapter.DragItemTouchCallback;
import com.veganchen.advancedui.dragwidget.adapter.RecyclerViewItemClickListener;
import com.veganchen.advancedui.dragwidget.bean.DragBean;
import com.veganchen.advancedui.util.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

public class DragActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rv;
    private DragAdapter mDragAdapter;
    private ItemTouchHelper itemTouchHelper;
    private TextView tvChange;
    private GridLayoutManager manager;

    public static void start(Context context){
        Intent intent = new Intent(context, DragActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag);
        init();
    }

    private void init(){
        tvChange = findViewById(R.id.tvChange);
        tvChange.setOnClickListener(this);
        rv = findViewById(R.id.rv);
        manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);

        rv.setLayoutManager(manager);
        List<DragBean> dataList = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            DragBean bean = new DragBean();
            bean.setText("text" + i);
            dataList.add(bean);
        }
        rv.setItemAnimator(new DefaultItemAnimator());
        mDragAdapter = new DragAdapter(dataList);
        rv.setAdapter(mDragAdapter);
        itemTouchHelper = new ItemTouchHelper(new DragItemTouchCallback(mDragAdapter));
        itemTouchHelper.attachToRecyclerView(rv);
        DragItemDecoration decoration = new DragItemDecoration(ScreenUtil.dp2px(15), ScreenUtil.dp2px(20), 2);
        rv.addItemDecoration(decoration);
        rv.addOnItemTouchListener(new RecyclerViewItemClickListener(rv) {
            @Override
            public void onLongClick(int position, RecyclerView.ViewHolder vh) {
                if(position != mDragAdapter.getItemCount() - 1){
                    itemTouchHelper.startDrag(vh);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(manager.getSpanCount() == 2){
            manager.setSpanCount(4);
        } else {
            manager.setSpanCount(2);
        }
        mDragAdapter.notifyDataSetChanged();
    }

    private static class DragItemDecoration extends RecyclerView.ItemDecoration{
        private int hSpacing;
        private int vSpacing;
        private int columnCount;

        public DragItemDecoration(int hSpacing, int vSpacing, int columnCount){
            this.hSpacing = hSpacing;
            this.vSpacing = vSpacing;
            this.columnCount = columnCount;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            //获取item所在的位置
            int position = parent.getChildAdapterPosition(view);
            //获取item在一行中的位置
            int column = position % columnCount;

            outRect.left = column * hSpacing / columnCount;
            outRect.right = hSpacing - (column + 1) * hSpacing / columnCount;
            outRect.bottom = vSpacing;
        }
    }
}