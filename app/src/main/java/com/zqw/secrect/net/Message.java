package com.zqw.secrect.net;

/**
 * Created by 启文 on 2017/7/22.
 * 消息类
 */
public class Message {

    private String msgID = null;
    private String msg = null;
    private String user =null;

    public Message(String msgID, String msg, String user){
        this.msg = msg;
        this.msgID = msgID;
        this.user = user;
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
}
