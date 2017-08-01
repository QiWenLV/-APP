package com.zqw.secrect.net;

/**
 * Created by 启文 on 2017/7/22.
 * 消息类
 */
public class Message {

    private String msgID = null;
    private String msg = null;
    private String user =null;
    private String date = null;

    public Message(String date, String msg, String user, String msgID){
        this.msg = msg;
        this.date = date;
        this.user = user;
        this.msgID = msgID;
    }

    public String getMsg() {
        return msg;
    }

    public String getUsre() {
        return user;
    }

    public String getMsgID() {
        return msgID;
    }

    public String getDate() {
        return date;
    }
}
