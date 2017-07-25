package com.zqw.secrect.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zqw.secrect.Config;
import com.zqw.secrect.R;
import com.zqw.secrect.net.Registered;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by 启文 on 2017/7/25.
 */
public class AtyRegistered extends Activity{

    private EditText etUser;
    private EditText etPass;
    private EditText etRePass;
    private EditText etPhoneNumber;
    private EditText etEmail;
    private Button btnRegistered;

    private String user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_registered);

        init();

        btnRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(etUser.getText())){
                    Toast.makeText(AtyRegistered.this, "用户名不能为空！", Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(etPass.getText())){
                    Toast.makeText(AtyRegistered.this, "密码不能为空！", Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(etPhoneNumber.getText())){
                    Toast.makeText(AtyRegistered.this, "邮箱不能为空！", Toast.LENGTH_LONG).show();
                    return;
                }
                if(!etPass.getText().toString().equals(etRePass.getText().toString())){
                    Toast.makeText(AtyRegistered.this, "两次密码不一致", Toast.LENGTH_LONG).show();
                    return;
                }

                try {
                    user = URLEncoder.encode(etUser.getText().toString(), "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.i("TEST", user);
                new Registered(user, etPass.getText().toString(), etPhoneNumber.getText().toString(), etEmail.getText().toString(), new Registered.SuccessCallback() {
                    @Override
                    public void onSuccess(String token) {
                        Intent i = new Intent(AtyRegistered.this, AtyTimeline.class);
                        i.putExtra(Config.KEY_TOKEN, token);
                        i.putExtra(Config.KEY_USER, etUser.getText().toString());
                        startActivity(i);
                    }
                }, new Registered.FailCallback() {
                    @Override
                    public void onFail(int t) {
                        if(t == 0){
                            Toast.makeText(AtyRegistered.this, "手机号被注册了！", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(AtyRegistered.this, "用户名已经存在", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
    }


    public void init(){
        etUser = (EditText) findViewById(R.id.etUser);
        etPass = (EditText) findViewById(R.id.etPass);
        etRePass = (EditText) findViewById(R.id.etRePass);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        etEmail = (EditText) findViewById(R.id.etEmail);
        btnRegistered = (Button) findViewById(R.id.btnRegistered);
    }
}
