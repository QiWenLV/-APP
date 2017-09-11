package com.zqw.secrect.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zqw.secrect.Config;
import com.zqw.secrect.R;
import com.zqw.secrect.net.Publish;

/**
 * Created by 启文 on 2017/7/21.
 * 发送消息界面
 */
public class AtyPubMessage extends Activity{

    private Button btnPubMsg;
    private EditText etMsgContent;
    private String user;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_pub_message);

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
}
