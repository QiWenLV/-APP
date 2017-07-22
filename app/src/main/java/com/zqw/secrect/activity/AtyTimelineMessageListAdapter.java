package com.zqw.secrect.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zqw.secrect.R;
import com.zqw.secrect.net.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 启文 on 2017/7/22.
 */
public class AtyTimelineMessageListAdapter extends BaseAdapter {

    Context context = null;

    public AtyTimelineMessageListAdapter(Context context){

        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Message getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ListCell cell = null;
        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.aty_timeline_list_cell, null);
            cell = new ListCell();
            cell.tvCellLabel = (TextView) view.findViewById(R.id.tvCellLabel);

            view.setTag(cell);
        }else {
            cell = (ListCell) view.getTag();
        }

        Message msg = getItem(i);

        cell.tvCellLabel.setText(msg.getMsg());

        return view;
    }

    public void addAll(List<Message> data){
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void clear(){
        data.clear();
        notifyDataSetChanged();
    }

    private List<Message> data = new ArrayList<>();

    public Context getContext() {
        return context;
    }

    private  class ListCell{
        TextView tvCellLabel;
    }
}
