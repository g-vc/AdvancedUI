package com.veganchen.advancedui.gridview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.veganchen.advancedui.R;

import java.util.List;

public class GridAdapter2 extends BaseAdapter {

    private List<String> mDataList;

    public GridAdapter2(List<String> dataList){
        mDataList = dataList;
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public Object getItem(int i) {
        return mDataList == null ? null : mDataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder = null;
        if(view == null){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_grid2, viewGroup, false);
            holder = new Holder(view);
            view.setTag(holder);
        } else {
            holder = (Holder)view.getTag();
        }
        String data = mDataList.get(i);
        holder.tv.setText(data);

        return view;
    }

    class Holder{
        TextView tv;

        Holder(View v){
            tv = v.findViewById(R.id.tvItem2);
        }

    }
}