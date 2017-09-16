package com.zqw.secrect.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zqw.secrect.BaseActivity;
import com.zqw.secrect.Config;
import com.zqw.secrect.R;
import com.zqw.secrect.net.Publish;

/**
 * Created by 启文 on 2017/7/21.
 * 发送消息界面
 */
public class AtyPubMessage extends BaseActivity{

    private Button btnPubMsg;
    private EditText etMsgContent;
    private String user;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.aty_pub_message);

        setToolbar(R.id.btnPubMsg_tb);
        setToolBarMenuOnclick(new PubMessageMenuClickListener());
        setBackArrow();

        user = getIntent().getStringExtra(Config.KEY_USER);
        token = getIntent().getStringExtra(Config.KEY_TOKEN);

        btnPubMsg = (Button) findViewById(R.id.btnPubMsg);
        etMsgContent = (EditText) findViewById(R.id.etMessage);

        btnPubMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(etMsgContent.getText())){
                    Toast.makeText(AtyPubMessage.this, "发布内容不能为空", Toast.LENGTH_LONG).show();
                    return;
                }

                new Publish(user, token, etMsgContent.getText().toString(), new Publish.SuccessCallback() {
                    @Override
                    public void onSuccess() {
                        setResult(Config.ACITVITY_RESULT_NEED_REFRESH); //回调

                        Toast.makeText(AtyPubMessage.this, "发表消息成功", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }, new Publish.FailCallback() {
                    @Override
                    public void onFail(int errorCode) {
                        if(errorCode == 2){
                            startActivity(new Intent(AtyPubMessage.this, AtyLogin.class));
                            finish();
                        }else{
                            Toast.makeText(AtyPubMessage.this, "发表失败", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }


    class PubMessageMenuClickListener implements android.support.v7.widget.Toolbar.OnMenuItemClickListener{

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menuShowMessage:
                    Toast.makeText(AtyPubMessage.this, "clicked me", Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        }
    }

    /**
     * 创建菜单栏
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pub_message, menu);
        return true;
    }
}
