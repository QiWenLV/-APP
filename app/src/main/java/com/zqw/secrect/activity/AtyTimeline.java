package com.zqw.secrect.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.zqw.secrect.Config;
import com.zqw.secrect.R;
import com.zqw.secrect.adapter.AtyTimelineMessageListAdapter;
import com.zqw.secrect.adapter.IUninstall;
import com.zqw.secrect.net.Message;
import com.zqw.secrect.net.Timeline;

import java.util.List;

/**
 * Created by 启文 on 2017/7/20.
 */
public class AtyTimeline extends AppCompatActivity implements IUninstall, AdapterView.OnItemClickListener {

    private String user;
    private String token;
    private AtyTimelineMessageListAdapter adapter = null;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_timeline);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("秘密");

        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(onMenuItemClick);

        user = getIntent().getStringExtra(Config.KEY_USER);
        token = getIntent().getStringExtra(Config.KEY_TOKEN);

        list = (ListView) findViewById(R.id.list_timeline);
        adapter = new AtyTimelineMessageListAdapter(this);

        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
        adapter.setiUninstall(this);

        loadMessage();

    }


    private  Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener(){

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            String msg = "";
            switch (item.getItemId()) {
                case R.id.menuShowAddMessage:
                    Intent i = new Intent(AtyTimeline.this, AtyPubMessage.class);
                    i.putExtra(Config.KEY_USER, user);
                    i.putExtra(Config.KEY_TOKEN, token);
                    startActivityForResult(i, 1);

                    break;
            }
            if(!msg.equals("")) {
                Toast.makeText(AtyTimeline.this, msg, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };
    /**
     * 加载消息
     */
    private void loadMessage(){

        final ProgressDialog pd = ProgressDialog.show(this, "..." , "正在加载...");
        new Timeline(user, token, 1, 20, new Timeline.SuccessCallback() {
            @Override
            public void onSuccess(int pag, int perpag, List<Message> timeline) {
                pd.dismiss();
                //向适配器添加数据
                adapter.clear();
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Message msg = adapter.getItem(i);
        Intent k = new Intent(AtyTimeline.this, AtyMessage.class);
        k.putExtra(Config.KEY_MSG, msg.getMsg());
        k.putExtra(Config.KEY_MSG_ID, msg.getMsgID());
        k.putExtra(Config.KEY_USER,user);
        k.putExtra(Config.KEY_TOKEN, token);
        startActivity(k);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_timeline, menu);

//        MenuItem search = menu.findItem(R.id.menuShowAddMessage);
//        MenuItemCompat.setOnActionExpandListener(search, new MenuItemCompat.OnActionExpandListener() {
//            @Override
//            public boolean onMenuItemActionExpand(MenuItem item) {
//                return false;
//            }
//
//            @Override
//            public boolean onMenuItemActionCollapse(MenuItem item) {
//                return false;
//            }
//        });
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       switch (resultCode){
           case Config.ACITVITY_RESULT_NEED_REFRESH:
               loadMessage();
               Log.i("","这里重新加载了");
               break;
           default:
               break;
       }
    }
}
