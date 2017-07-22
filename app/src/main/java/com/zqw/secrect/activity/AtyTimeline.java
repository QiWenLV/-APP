package com.zqw.secrect.activity;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.zqw.secrect.Config;
import com.zqw.secrect.R;
import com.zqw.secrect.net.Message;
import com.zqw.secrect.net.Timeline;

import java.util.List;

/**
 * Created by 启文 on 2017/7/20.
 */
public class AtyTimeline extends ListActivity {

    String user;
    String token;
    private AtyTimelineMessageListAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_timeline);

        user = getIntent().getStringExtra(Config.KEY_USER);
        token = getIntent().getStringExtra(Config.KEY_TOKEN);

        adapter = new AtyTimelineMessageListAdapter(this);
        setListAdapter(adapter);
        loadMessage();
    }

    private void loadMessage(){

        final ProgressDialog pd = ProgressDialog.show(this, "..." , "正在加载...");
        new Timeline(user, token, 1, 20, new Timeline.SuccessCallback() {
            @Override
            public void onSuccess(int pag, int perpag, List<Message> timeline) {
                pd.dismiss();
                //向适配器添加数据
                adapter.addAll(timeline);
            }
        }, new Timeline.FailCallback() {
            @Override
            public void onFail() {
                pd.dismiss();
                Toast.makeText(AtyTimeline.this, "加载失败", Toast.LENGTH_LONG).show();
            }
        });
    }
}
