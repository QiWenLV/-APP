package com.zqw.secrect.net;

import com.zqw.secrect.Config;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 启文 on 2017/7/27.
 * 发表评论的通讯类
 */
public class PubComment {

    public PubComment(String user, String token, String content, String msgId, final SuccessCallback successCallback, final FailCallback failCallback){

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

            }
        }, Config.KEY_ACTION, Config.ACTION_PUB_COMMENT,
                Config.KEY_USER, user,
                Config.KEY_TOKEN, token,
                Config.KEY_CONTENT, content,
                Config.KEY_MSG_ID, msgId);
    }


    public static interface SuccessCallback{
        void onSuccess();
}

    public static interface FailCallback{
        void onFail(int errorCode);
    }
}
