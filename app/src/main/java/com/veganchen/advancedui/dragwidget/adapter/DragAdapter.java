package com.veganchen.advancedui.dragwidget.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.veganchen.advancedui.R;
import com.veganchen.advancedui.dragwidget.bean.DragBean;
import com.veganchen.advancedui.dragwidget.inter.OnDragItemListener;

import java.util.Collections;
import java.util.List;

public class DragAdapter extends RecyclerView.Adapter implements OnDragItemListener {

    private static int NORMAL_ITEM = 1;
    private static int FIX_ITEM = 2;

    private List<DragBean> mDataList;

    public DragAdapter(List<DragBean> dataList) {
        mDataList = dataList;
    }

    @Override
    public int getItemViewType(int position) {
        return position == getItemCount() - 1 ? FIX_ITEM : NORMAL_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == NORMAL_ITEM) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drag, parent, false);
            return new DragHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fix, parent, false);
            return new FixHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == NORMAL_ITEM) {
            DragBean bean = mDataList.get(position);
            DragHolder dragHolder = (DragHolder) holder;
            dragHolder.tvItem.setText(bean.getText());
        } else {
            FixHolder fixHolder = (FixHolder)holder;
            fixHolder.tvFixItem.setText("Fix");
        }
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 1 : mDataList.size() + 1;
    }

    @Override
    public void onMoveItem(int from, int to) {
        int fix = getItemCount() - 1;
        if(from == fix || to == fix){
            return;
        }
        Collections.swap(mDataList, from, to);
        notifyItemMoved(from, to);
    }

    static class DragHolder extends RecyclerView.ViewHolder {

        private TextView tvItem;

        public DragHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tvItem);
        }
    }

    static class FixHolder extends RecyclerView.ViewHolder {

        private TextView tvFixItem;

        public FixHolder(@NonNull View itemView) {
            super(itemView);
            tvFixItem = itemView.findViewById(R.id.tvFixItem);
        }
    }
}