package com.zqw.secrect;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 启文 on 2017/7/20.
 * 数据处理
 */
public class Config {

    public static final String CHARSET = "utf-8";

    public static final String APP_ID = "com.zqw.secret";
   // public static final String SERVER_URL = "http://123.207.11.223:8080/Secrect/secrect";
    public static final String SERVER_URL = "http://10.0.2.2:8080/Secrect/secrect";

    public static final String KEY_TOKEN = "token";
    public static final String KEY_ACTION = "action";
    public static final String KEY_TIMELINE = "timeline";
    public static final String KEY_COMMENTS = "comments";

    /*
    * 用户
    */
    public static final String KEY_USER_ID = "Uid";
    public static final String KEY_USER = "user";
    public static final String KEY_PASS = "pass";
    public static final String KEY_PHONENUMBER = "phoneNumber";
    public static final String KEY_EAMIL = "Email";
    public static final String KEY_HERD_IMAGE = "herdImage";

    /*
	 * 消息
	 */

    public static final String KEY_MSG_ID = "msgId";
    public static final String KEY_MSG_CONTEXT = "msgContext";
    public static final String KEY_MSG_TITLE = "msgTitle";
    public static final String KEY_MSG_NUM_STARS = "msgNumStars";
    public static final String KEY_MSG_NUM_COMMENT = "msgNumComment";
    public static final String KEY_MSG_TIME = "msgTime";
    public static final String KEY_MSG_IMAGE = "msgImage";

    /*
     * 评论
     */
    public static final String KEY_CONTENT = "content";


    public static final String KEY_PAGE = "page";
    public static final String KEY_PERPAGE = "perpage";

    /*

     */

    public static final String KEY_DATE = "date";

    public static final String KEY_STATUS = "status";


    /*
	 * 上传标签
	 */
    public static final String ACTION_LOGIN = "login";               //登陆
    public static final String ACTION_TIMELINE = "timeline";         //消息列表
    public static final String ACTION_REGISTERED = "registered";     //注册
    public static final String ACTION_GET_COMMENT = "get_comment";   //评论列表
    public static final String ACTION_PUB_COMMENT = "pub_comment";   //发表评论
    public static final String ACTION_PUBLISH = "publish";           //发表消息
    public static final String ACTION_MYDATA = "mydata";             //我的信息
    public static final String ACTION_UPLOAD_HEAD = "upload_head";   //上传头像

    public static final int ACITVITY_RESULT_NEED_REFRESH = 10000;


    /**
     * Token用户登录免登陆标签，标签保存7天，如果Token过期，则需要重新登录
     * 获取本地token
     */
    public static String getCachedToken(Context context) {
        //从本地取出token
        return context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).getString(KEY_TOKEN, null);
    }

    /**
     * 将token保存在本地，键是KEY_TOKEN
     * @param context
     * @param token
     */
    public static void cacheToken(Context context, String token){
        SharedPreferences.Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putString(KEY_TOKEN, token);
        e.commit();
    }


    /**
     * 获取缓存的用户名
     */
    public static String getCachedUser(Context context) {
        return context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).getString(KEY_USER, null);
    }

    /**
     * 缓存用户名
     */
    public static void cacheUser(Context context, String user){
        SharedPreferences.Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putString(KEY_USER, user);
        e.commit();
    }


}
