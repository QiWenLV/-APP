package com.zqw.secrect.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zqw.secrect.BaseActivity;
import com.zqw.secrect.Config;
import com.zqw.secrect.R;
import com.zqw.secrect.adapter.AtyMessageCommentListAdapter;
import com.zqw.secrect.net.ClickFabulous;
import com.zqw.secrect.net.Comment;
import com.zqw.secrect.net.GetComment;
import com.zqw.secrect.net.Message;
import com.zqw.secrect.net.PubComment;

import java.util.List;

/**
 * Created by 启文 on 2017/7/21.
 */
public class AtyMessageContent extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView list_Comment;
    private String user;    //当前用户
    private String msgId;   //当前消息Id
    private String token;
    private String type = Config.COMMENT_TYPE_COMMENT;    //评论内容      COMMENT_TYPE_COMMENT or COMMENT_TYPE_REPLY
    private String to_uid = null;  //回复的用户id


    private EditText etCommrnt;
    private Button btnSendComment;
    private AtyMessageCommentListAdapter adapter;
    private Bundle bundle;


    Message msg;
    private ImageView imgHead;          //头像
    private TextView tvUserName;        //用户名
    private TextView tvCellLabelTitle;  //消息标题
    private TextView tvMessage;       //消息内容
    private ImageButton imgShare;       //分享按钮
    private ImageButton imgCollection;  //收藏按钮
    private ImageButton imgFabulous;    //点赞按钮
    private TextView tvNumFaulous;      //点赞数
    private ImageButton imgComment;     //评论按钮
    private TextView tvNumComment;      //评论数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.aty_message);

        setTitle("评论");
        setBackArrow();



        initView();

        btnSendComment.setOnClickListener(this);
        imgShare.setOnClickListener(this);
        imgCollection.setOnClickListener(this);
        imgFabulous.setOnClickListener(this);

        adapter = new AtyMessageCommentListAdapter(this);
        list_Comment.setAdapter(adapter);
        list_Comment.setOnItemClickListener(this);
        GetComments();
    }


    private void initView(){


        imgHead = (ImageView) findViewById(R.id.img_head);         //头像
        tvUserName = (TextView) findViewById(R.id.tv_user_name);        //用户名
        tvCellLabelTitle = (TextView) findViewById(R.id.tvMessage_title);  //消息标题
        tvMessage = (TextView) findViewById(R.id.tvMessage);     //消息内容
        imgShare = (ImageButton) findViewById(R.id.img_share);       //分享按钮
        imgCollection = (ImageButton) findViewById(R.id.img_collection);  //收藏按钮
        imgFabulous = (ImageButton) findViewById(R.id.img_fabulous);    //点赞按钮
        tvNumFaulous = (TextView) findViewById(R.id.tv_item_num_fabilous);      //点赞数
        tvNumComment = (TextView) findViewById(R.id.tv_item_num_comment);      //评论数

        etCommrnt = (EditText) findViewById(R.id.etComment);        //评论输入框
        btnSendComment = (Button) findViewById(R.id.btnSendComment);    //发布评论按钮

        list_Comment = (ListView) findViewById(R.id.list_coment);   //评论列表

        /*
         * 获取上个页面传来的信息
         */
        Intent data = getIntent();
        user = data.getStringExtra(Config.KEY_USER);
        token = data.getStringExtra(Config.KEY_TOKEN);

        msg = data.getParcelableExtra("msg");

        tvUserName.setText(msg.getUser());
        tvCellLabelTitle.setText(msg.getMsg_title());
        tvMessage.setText(msg.getMsg_context());
        tvNumFaulous.setText(msg.getMsg_fabulous());
        tvNumComment.setText(msg.getMsg_comment());
        msgId = msg.getMsgID();
        imgHead.setImageBitmap(msg.getHeadImage());


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

        switch (view.getId()){

            case R.id.btnSendComment:   //发送
                pubCommentButton();
                break;
            case R.id.img_share:    //分享
                break;
            case R.id.img_collection:    //收藏
                break;
            case R.id.img_fabulous:    //赞

                String isE = null;
                if(msg.isFabulous()){
                    isE = Config.KEY_FA_YES;
                }else {
                    isE = Config.KEY_FA_NO;
                }
                new ClickFabulous(user, token, msgId, isE, new ClickFabulous.SuccessCallback() {
                    @Override
                    public void onSuccess(String newFabulous) {
                        tvNumFaulous.setText(newFabulous);
                        msg.setFabulous(!msg.isFabulous());

                    }
                }, new ClickFabulous.FailCallback() {
                    @Override
                    public void onFail() {
                        Toast.makeText(AtyMessageContent.this, "点赞失败，请稍后重试", Toast.LENGTH_SHORT).show();
                    }
                });

                break;

        }

    }



    private void pubCommentButton(){
        if(TextUtils.isEmpty(etCommrnt.getText())){
            Toast.makeText(AtyMessageContent.this, "评论内容不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        new PubComment(user, token, etCommrnt.getText().toString(), msgId, type, to_uid, new PubComment.SuccessCallback() {
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

    /**
     * 点击评论回复
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Comment comment = adapter.getItem(i);
        type = Config.COMMENT_TYPE_REPLY;
        to_uid = comment.getUser_id();
        etCommrnt.setHint("回复 "+comment.getUser()+":");

    }
//    @Override
//    public void SendMessageValue(Bundle strValue) {
//        bundle = strValue;
//    }



}
