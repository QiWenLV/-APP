package com.zqw.secrect;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zqw.secrect.activity.AtyLogin;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //获取本地的token
        String token = Config.getCachedToken(this);
        String user = Config.getCachedUser(this);

//        Intent x = new Intent(MainActivity.this, AtyLogin.class);
//        startActivity(x);

        //Mycontacts.readContact(this);

        if(token != null && user != null){
//            //如果token不为空，直接跳过登录界面
//            Intent i = new Intent(MainActivity.this, AtyTimeline.class);
//            i.putExtra(Config.KEY_TOKEN, token);
//            i.putExtra(Config.KEY_USER, user);
//            startActivity(i);
//        }else{
            Intent x = new Intent(MainActivity.this, AtyLogin.class);
            startActivity(x);
        }
        //不管是哪种情况，都关闭主界面。
        finish();

    }
}
