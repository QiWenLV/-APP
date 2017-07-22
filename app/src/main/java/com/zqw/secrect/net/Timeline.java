package com.zqw.secrect.net;

import android.util.Log;

import com.zqw.secrect.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 启文 on 2017/7/22.
 */
public class Timeline {

    public Timeline(String User, String token, int page, int perpage, final SuccessCallback successCallback, final FailCallback failCallback){
        new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {

                try {
                    JSONObject obj = new JSONObject(result);

                    switch (obj.getInt(Config.KEY_STATUS)){
                        case 1:
                            if(successCallback != null){
                                List<Message> msgs = new ArrayList<>();
                                JSONArray msgsJsonArray = obj.getJSONArray(Config.KEY_TIMELINE);
                                JSONObject msgObj;
                                for(int i=0; i<msgsJsonArray.length(); i++){
                                    msgObj = msgsJsonArray.getJSONObject(i);
                                    msgs.add(new Message(msgObj.getString(Config.KEY_MSG_ID), msgObj.getString(Config.KEY_MSG), msgObj.getString(Config.KEY_USER)));
                                }
                                Log.i("TEST", "msgs");
                                successCallback.onSuccess(obj.getInt(Config.KEY_PAGE), obj.getInt(Config.KEY_PERPAGE), msgs);
                                Log.i("TEST", "msgs");
                            }
                            break;
                        default:
                            if(failCallback != null){
                                failCallback.onFail();
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
                    failCallback.onFail();
                }
            }
        }, Config.KEY_ACTION, Config.ACTION_TIMELINE,
                Config.KEY_USER, User,
                Config.KEY_TOKEN, token,
                Config.KEY_PAGE, page+"",
                Config.KEY_PERPAGE, perpage+"");
    }

    public static interface SuccessCallback{
        void onSuccess(int pag, int perpage, List<Message> timeline);
    }

    public static interface FailCallback{
        void onFail();
    }
}
