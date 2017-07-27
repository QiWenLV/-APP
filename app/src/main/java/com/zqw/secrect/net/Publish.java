package com.zqw.secrect.net;

import com.zqw.secrect.Config;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 启文 on 2017/7/27.
 * 发表消息的类
 */
public class Publish {

    public Publish(String user, String token, String msg, final SuccessCallback successCallback, final FailCallback failCallback){
        new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {

                try {
                    JSONObject obj = new JSONObject(result);

                    switch (obj.getInt(Config.KEY_STATUS)){
                        case 1:
                            if(successCallback != null){
                                successCallback.onSuccess();
                            }
                            break;
                        case 2:
                            if(failCallback != null){
                                failCallback.onFail(2);
                            }
                            break;
                        default:
                            if(failCallback != null){
                                failCallback.onFail(0);
                            }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if(failCallback != null){
                        failCallback.onFail(0);
                    }
                }

            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {
                if(failCallback != null){
                    failCallback.onFail(2);
                }

            }
        }, Config.KEY_ACTION, Config.ACTION_PUBLISH,
                Config.KEY_USER, user,
                Config.KEY_TOKEN, token,
                Config.KEY_MSG, msg);
    }


    public static interface SuccessCallback{
        void onSuccess();
    }

    public static interface FailCallback{
        void onFail(int errorCode);
    }
}
