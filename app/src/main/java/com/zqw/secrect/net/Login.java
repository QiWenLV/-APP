package com.zqw.secrect.net;

import android.util.Log;

import com.zqw.secrect.Config;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 启文 on 2017/7/21.
 */
public class Login {

    public Login(String user, String pass, final SuccessCallback successCallback, final FailCallback failCallback){

        new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    if (result != null && result.startsWith("\ufeff")) {
                        result = result.substring(1);
                    }

                    JSONObject obj = new JSONObject(result);
                    Log.i("TEXT", "login:"+obj.getInt(Config.KEY_STATUS));
                    switch (obj.getInt(Config.KEY_STATUS)){
                        case 1:
                            if(successCallback != null){
                                successCallback.onSuccess(obj.getString(Config.KEY_TOKEN));
                            }
                            break;
                        default:
                            if(failCallback != null){
                                failCallback.onFail(obj.getInt(Config.KEY_STATUS));
                            }
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {
                if(failCallback != null){
                    failCallback.onFail(0);
                }
            }
        }, Config.KEY_ACTION, Config.ACTION_LOGIN, Config.KEY_USER, user, Config.KEY_PASS, pass);

    }

    public static interface SuccessCallback{
        void onSuccess(String token);
    }

    public static interface FailCallback{
        void onFail(int t);
    }
}
