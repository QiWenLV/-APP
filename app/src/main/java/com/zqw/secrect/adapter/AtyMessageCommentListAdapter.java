package com.zqw.secrect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zqw.secrect.R;
import com.zqw.secrect.net.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 启文 on 2017/7/27.
 */
public class AtyMessageCommentListAdapter extends BaseAdapter {

    private Context context;
    private List<Comment> comments = new ArrayList<>();

    public Context getContext(){
        return context;
    }

    public AtyMessageCommentListAdapter(Context context){
        this.context = context;
    }


    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Comment getItem(int i) {
        return comments.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ListCell cell = null;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.aty_timeline_list_cell, null);
            cell = new ListCell();
            cell.tvCellLabel = (TextView) view.findViewById(R.id.tvCellLabel);

            view.setTag(cell);
        } else {
            cell = (ListCell) view.getTag();
        }

        Comment comment = getItem(i);

        cell.tvCellLabel.setText(comment.getContent());

        return view;
    }

    public void addAll(List<Comment> data){
        comments.addAll(data);
        notifyDataSetChanged();
    }

    public void clear(){
        comments.clear();
        notifyDataSetChanged();
    }

    private  class ListCell{
        TextView tvCellLabel;
    }
}
