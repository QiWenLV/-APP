package com.zqw.secrect.net;

import com.zqw.secrect.Config;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 点赞
 * Created by 启文 on 2017/9/30.
 */
public class ClickFabulous{
    public ClickFabulous(String user, String token, String MsgId, String isE, final SuccessCallback successCallback, final FailCallback failCallback){
        new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    switch (obj.getInt(Config.KEY_STATUS)) {
                        case 1:
                            if (successCallback != null) {
                                //  String s = obj.getString(Config.KEY_HERD_IMAGE);
                                successCallback.onSuccess(obj.getString(Config.KEY_FA_NEW));
                            }
                            break;
                        default:
                            if (failCallback != null) {
                                failCallback.onFail();
                            }
                    }
                } catch (JSONException e) {
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
        }, Config.KEY_ACTION, Config.ACTION_CLICKFABULOUS,
                Config.KEY_USER, user,
                Config.KEY_TOKEN, token,
                Config.KEY_MSG_ID, MsgId,
                Config.KEY_FA_CLICK, isE);

    }


    public interface SuccessCallback{
        void onSuccess(String newFabulous);
    }

    public static interface FailCallback{
        void onFail();
    }
}
