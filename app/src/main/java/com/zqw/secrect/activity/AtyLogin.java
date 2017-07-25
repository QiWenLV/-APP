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
import com.zqw.secrect.net.Login;

/**
 * Created by 启文 on 2017/7/20.
 */
public class AtyLogin extends Activity {

    private EditText etUser = null;
    private EditText etPass = null;
    private Button btnLogin = null;
    private Button btnRegistered = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.aty_login);
        etUser = (EditText) findViewById(R.id.etUser);
        etPass = (EditText) findViewById(R.id.etPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegistered = (Button) findViewById(R.id.btnRegistered);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(etUser.getText())){
                    Toast.makeText(AtyLogin.this, "用户名不能为空！", Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(etPass.getText())){
                    Toast.makeText(AtyLogin.this, "密码不能为空！", Toast.LENGTH_LONG).show();
                    return;
                }


                new Login(etUser.getText().toString(), etPass.getText().toString(), new Login.SuccessCallback(){

                    @Override
                    public void onSuccess(String token) {
                        Config.cacheToken(AtyLogin.this, token);
                        Config.cacheUser(AtyLogin.this, etUser.getText().toString());
                        Toast.makeText(AtyLogin.this, "成功！", Toast.LENGTH_LONG).show();

                        Intent i = new Intent(AtyLogin.this, AtyTimeline.class);
                        i.putExtra(Config.KEY_TOKEN, token);
                        i.putExtra(Config.KEY_USER, etUser.getText().toString());
                        startActivity(i);

                        finish();
                    }
                }, new Login.FailCallback() {
                    @Override
                    public void onFail(int t) {
                        if(t == 0){
                            Toast.makeText(AtyLogin.this, "密码不正确！", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(AtyLogin.this, "用户名未注册", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });


        btnRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(AtyLogin.this, AtyRegistered.class);
//                if(etUser.getText().toString() != null){
//                    i.putExtra(Config.KEY_USER, etUser.getText().toString());
//                }
                startActivity(a);
            }
        });

    }
}
