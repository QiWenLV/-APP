package com.zqw.secrect.net;

/**
 * Created by 启文 on 2017/7/27.
 */
public class Comment {

    private String msg_id;
    private String content;
    private String user_id;
    private String user;
    private String type;
    private String to_uid;
    private String date;

    public Comment(String msg_id, String content, String user_id, String user, String type, String to_uid, String date){
        this.msg_id = msg_id;
        this.content = content;
        this.user_id = user_id;
        this.user = user;
        this.type = type;
        this.to_uid = to_uid;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getType() {
        return type;
    }

    public String getTo_uid() {
        return to_uid;
    }

    public String getUser() {
        return user;
    }

    public String getContent() {
        return content;
    }
}
