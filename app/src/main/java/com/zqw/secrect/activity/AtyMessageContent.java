package com.zqw.secrect.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zqw.secrect.BaseActivity;
import com.zqw.secrect.Config;
import com.zqw.secrect.R;
import com.zqw.secrect.adapter.AtyMessageCommentListAdapter;
import com.zqw.secrect.net.Comment;
import com.zqw.secrect.net.GetComment;
import com.zqw.secrect.net.PubComment;

import java.util.List;

/**
 * Created by 启文 on 2017/7/21.
 */
public class AtyMessageContent extends BaseActivity implements View.OnClickListener {

    private ListView list_Comment;
    private String user;
    private String msg;
    private String msgId;
    private String token;
    private TextView tvMessage;
    private EditText etCommrnt;
    private Button btnSendComment;
    private AtyMessageCommentListAdapter adapter;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.aty_message);

        setTitle("评论");
        setBackArrow();

        Intent data = getIntent();
        user = data.getStringExtra(Config.KEY_USER);
        msg = data.getStringExtra(Config.KEY_MSG);
        msgId = data.getStringExtra(Config.KEY_MSG_ID);
        token = data.getStringExtra(Config.KEY_TOKEN);

        initView();

       // final ProgressDialog pd = ProgressDialog.show(this, "..." , "正。在。加载...");
        btnSendComment.setOnClickListener(this);

    }


    private void initView(){

        etCommrnt = (EditText) findViewById(R.id.etComment);
        btnSendComment = (Button) findViewById(R.id.btnSendComment);
        tvMessage = (TextView) findViewById(R.id.tvMessage);
        list_Comment = (ListView) findViewById(R.id.list_coment);
        tvMessage.setText(msg);

        adapter = new AtyMessageCommentListAdapter(this);

        list_Comment.setAdapter(adapter);
        GetComments();
    }

    public void GetComments(){
        final ProgressDialog pd = ProgressDialog.show(this, "。。。","请稍等。。");

        new GetComment(user, token, msgId, 1, 20, new GetComment.SuccessCallback() {
            @Override
            public void onSuccess(String msgId, int page, int perpage, List<Comment> comment) {
                pd.dismiss();
                adapter.clear();
                adapter.addAll(comment);
            }
        }, new GetComment.FailCallback() {
            @Override
            public void onFail(int errorCode) {
                pd.dismiss();
                if(errorCode == 2){
                    startActivity(new Intent(AtyMessageContent.this, AtyLogin.class));
                    finish();
                }else if(errorCode == 3){
                    pd.dismiss();
                    Toast.makeText(AtyMessageContent.this, "这条消息没有评论", Toast.LENGTH_LONG).show();
                }else{
                    pd.dismiss();
                    Toast.makeText(AtyMessageContent.this, "加载失败", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(TextUtils.isEmpty(etCommrnt.getText())){
            Toast.makeText(AtyMessageContent.this, "评论内容不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        new PubComment(user, token, etCommrnt.getText().toString(), msgId, new PubComment.SuccessCallback() {
            @Override
            public void onSuccess() {
                //    pd.dismiss();
                etCommrnt.setText("");
                Log.i("TEST", "发布成功");
                GetComments();
            }
        }, new PubComment.FailCallback() {
            @Override
            public void onFail(int errorCode) {
                Log.i("TEST", "发布失败");
                //     pd.dismiss();
                if(errorCode == 2){
                    startActivity(new Intent(AtyMessageContent.this, AtyLogin.class));
                    finish();
                }else{
                    Toast.makeText(AtyMessageContent.this, "发表失败", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

//    @Override
//    public void SendMessageValue(Bundle strValue) {
//        bundle = strValue;
//    }



}
