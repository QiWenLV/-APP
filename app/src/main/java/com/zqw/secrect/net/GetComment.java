package com.zqw.secrect.net;

import android.util.Log;

import com.zqw.secrect.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 启文 on 2017/7/27.
 * 获取评论的网络类
 */
public class GetComment {
    public GetComment(String user, String token, String msgId, int page, int perpage, final SuccessCallback successCallback, final FailCallback failCallback){

        new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result){

                try {
                    JSONObject jsonObject = new JSONObject((result));

                    switch (jsonObject.getInt(Config.KEY_STATUS)){
                        case 1:
                            Log.i("TEST", "1112222");
                            if(successCallback != null){
                                List<Comment> comments = new ArrayList<>();
                                JSONArray commentsJsonArray = jsonObject.getJSONArray(Config.KEY_COMMENTS);
                                JSONObject commentObj = null;
                                for(int i = 0; i<commentsJsonArray.length(); i++){
                                    commentObj = commentsJsonArray.getJSONObject(i);
                                    comments.add(new Comment(commentObj.getString(Config.KEY_CONTENT), commentObj.getString(Config.KEY_USER), commentObj.getString(Config.KEY_DATE)));
                                }

                                successCallback.onSuccess(jsonObject.getString(Config.KEY_MSG_ID), jsonObject.getInt(Config.KEY_PAGE), jsonObject.getInt(Config.KEY_PERPAGE), comments);
                            }
                            break;
                        case 0:
                            if(failCallback != null){
                                failCallback.onFail(0);
                            }
                            break;
                        case 2:
                            break;
                        case 3:
                            if(failCallback != null){
                                failCallback.onFail(3);
                            }
                            break;
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
                    failCallback.onFail(0);
                }
            }
        }, Config.KEY_ACTION, Config.ACTION_GET_COMMENT,
                Config.KEY_USER, user,
                Config.KEY_TOKEN, token,
                Config.KEY_MSG_ID, msgId,
                Config.KEY_PAGE, page+"",
                Config.KEY_PERPAGE, perpage+"");

    }


    public static interface SuccessCallback{
        void onSuccess(String msgId, int page, int perpage, List<Comment> comment);
    }

    public static interface FailCallback{
        void onFail(int errorCode);
    }
}
