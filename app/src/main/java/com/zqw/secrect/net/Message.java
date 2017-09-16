package com.zqw.secrect.net;

/**
 * Created by 启文 on 2017/7/22.
 * 消息类
 */
public class Message {

    private String msgID = null;      //消息ID
    private String msg = null;        //消息内容
    private String user =null;          //发表消息的用户
    private String date = null;         //发表消息的时间
    private String msg_fabulous = null;     //点赞数
    private String msg_comment = null;  //评论数



    public Message(String date, String msg, String user, String msgID, String msg_fabulous, String msg_comment){
        this.msg = msg;
        this.date = date;
        this.user = user;
        this.msgID = msgID;
        this.msg_fabulous = msg_fabulous;
        this.msg_comment = msg_comment;

    }

    public Message(String date, String msg, String user, String msgID){
        this.msg = msg;
        this.date = date;
        this.user = user;
        this.msgID = msgID;
        this.msg_fabulous = "0";
        this.msg_comment = "0";
    }
    public String getMsg() {
        return msg;
    }

    public String getMsgID() {
        return msgID;
    }

    public String getDate() {
        return date;
    }

    public String getMsg_fabulous() {
        return msg_fabulous;
    }

    public String getMsg_comment() {
        return msg_comment;
    }

    public String getUser() {
        return user;
    }


    public void setMsg_fabulous(String msg_fabulous) {
        this.msg_fabulous = msg_fabulous;
    }

    public void setMsg_comment(String msg_comment) {
        this.msg_comment = msg_comment;
    }
}
