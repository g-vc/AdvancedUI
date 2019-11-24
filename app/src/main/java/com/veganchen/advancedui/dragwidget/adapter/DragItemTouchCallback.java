package com.veganchen.advancedui.dragwidget.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.veganchen.advancedui.dragwidget.inter.OnDragItemListener;

public class DragItemTouchCallback extends ItemTouchHelper.Callback {

    private OnDragItemListener mListener;

    public DragItemTouchCallback(OnDragItemListener listener){
        mListener = listener;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        //定义 item 的可以拖拽和滑动的方向
        int dragFlags = ItemTouchHelper.START | ItemTouchHelper.END |ItemTouchHelper.DOWN | ItemTouchHelper.UP;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, 0);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        //当 item 想要上下拖拽时会调用此方法
        int from = recyclerView.getChildAdapterPosition(viewHolder.itemView);
        int to = recyclerView.getChildAdapterPosition(target.itemView);
        mListener.onMoveItem(from, to);
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        //当 item 想要左右侧滑时会调用此方法
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }
}