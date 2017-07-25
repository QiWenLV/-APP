package com.zqw.secrect.net;

import com.zqw.secrect.Config;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 启文 on 2017/7/25.
 */
public class Registered {

    public Registered(String user, String pass, String phoneNumber, String Email, final SuccessCallback successCallback, final FailCallback failCallback){
        new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);

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
                    failCallback.onFail(2);
                }
            }
        },Config.KEY_ACTION, Config.ACTION_REGISTERED,
                Config.KEY_USER, user,
                Config.KEY_PASS, pass,
                Config.KEY_PHONENUMBER, phoneNumber,
                Config.KEY_EAMIL,Email);
    }


    public static interface SuccessCallback{
        void onSuccess(String token);
    }

    public static interface FailCallback{
        void onFail(int t);
    }
}
