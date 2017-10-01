package com.zqw.secrect.net;

import android.util.Log;

import com.zqw.secrect.Config;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 启文 on 2017/9/21.
 */
public class MyData {

    public MyData(String user, String token, final SuccessCallback successCallback, final FailCallback failCallback){
        new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    switch (obj.getInt(Config.KEY_STATUS)){
                        case 1:
                            if(successCallback != null){
                                Log.i("TEXTC", "服务器返回成功");
                                successCallback.onSuccess(obj.getString(Config.KEY_NUM_MESSAGE), obj.getString(Config.KEY_NUM_COMMENT), obj.getString(Config.KEY_NUM_COLLECTION),obj. getString(Config.KEY_HERD_IMAGE));

                            }
                            break;
                        default:
                            if(failCallback != null){
                                Log.i("TEXTC", "服务器返回失败");
                                failCallback.onFail();
                            }
                            break;
                    }

                } catch (JSONException e) {
                    if(failCallback != null){
                        failCallback.onFail();
                    }
                    e.printStackTrace();
                }
            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {
                if(failCallback != null){
                    failCallback.onFail();
                }
            }
        }, Config.KEY_ACTION, Config.ACTION_MYDATA,
                Config.KEY_USER, user,
                Config.KEY_TOKEN, token);

    }


    public static interface SuccessCallback{
        void onSuccess(String Num_message, String Num_comment, String Num_collection, String headImage);
    }

    public static interface FailCallback{
        void onFail();
    }



}
