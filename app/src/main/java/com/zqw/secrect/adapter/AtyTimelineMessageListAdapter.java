package com.zqw.secrect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    private int pos = 0;

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
            cell.imgHead = (ImageView) view.findViewById(R.id.img_head);
            cell.tvUserName = (TextView) view.findViewById(R.id.tv_user_name);
            cell.tvCellLabel = (TextView) view.findViewById(R.id.tvCellLabel);
            cell.imgShare = (ImageButton) view.findViewById(R.id.img_share);
            cell.imgCollection = (ImageButton) view.findViewById(R.id.img_collection);
            cell.imgFabulous = (ImageButton) view.findViewById(R.id.img_fabulous);
            cell.tvNumFaulous = (TextView) view.findViewById(R.id.tv_item_num_fabilous);
            cell.imgComment = (ImageButton) view.findViewById(R.id.img_comment);
            cell.tvNumComment = (TextView) view.findViewById(R.id.tv_item_num_comment);


            view.setTag(cell);
        }else {
            cell = (ListCell) view.getTag();
        }

        Message msg = getItem(i);
    //头像
        cell.tvUserName.setText(msg.getUser());
        cell.tvCellLabel.setText(msg.getMsg_context());
        cell.tvNumFaulous.setText(msg.getMsg_fabulous());
        cell.tvNumComment.setText(msg.getMsg_comment());


        cell.tvUserName.setOnClickListener(new ItemViewOnClick(i));
        cell.imgShare.setOnClickListener(new ItemViewOnClick(i));
        cell.imgCollection.setOnClickListener(new ItemViewOnClick(i));
        cell.imgFabulous.setOnClickListener(new ItemViewOnClick(i));
        cell.imgComment.setOnClickListener(new ItemViewOnClick(i));

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

    IUninstall iUninstall;

    public void setiUninstall(IUninstall iUninstall){
        this.iUninstall = iUninstall;
    }

    public Context getContext() {
        return context;
    }


    private class ItemViewOnClick implements View.OnClickListener {
        int i = 0;
        public ItemViewOnClick(int i) {
            this.i = i;
        }
        @Override
        public void onClick(View view) {
           iUninstall.OnClickView(i,view);
        }
    }

    private  class ListCell{
        ImageView imgHead;          //头像
        TextView tvUserName;        //用户名
        TextView tvCellLabel;       //消息内容
        ImageButton imgShare;       //分享按钮
        ImageButton imgCollection;  //收藏按钮
        ImageButton imgFabulous;    //点赞按钮
        TextView tvNumFaulous;      //点赞数
        ImageButton imgComment;     //评论按钮
        TextView tvNumComment;      //评论数


    }
}
