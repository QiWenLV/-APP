package com.zqw.secrect.net;

/**
 * Created by 启文 on 2017/7/22.
 * 消息类
 */
public class Message {

    private String msgID = null;      //消息ID
    private String userID = null;     //用户ID
    private String headImage = null;  //用户头像
    private String msg_context = null;        //消息内容
    private String msg_title = null;    //消息标题
    private String user =null;          //发表消息的用户
    private String date = null;         //发表消息的时间
    private String msg_fabulous = null;     //点赞数
    private String msg_comment = null;  //评论数
    private String msg_image = null;    //消息图片



    public Message(String userID, String msgID, String headImage, String user, String msg_title, String msg_context, String msg_fabulous, String msg_comment, String date, String msg_image){
        this.userID = userID;
        this.msgID = msgID;
        this.headImage = "21";
        this.user = user;
        this.msg_title = msg_title;
        this.msg_context = msg_context;
        this.msg_fabulous = msg_fabulous;
        this.msg_comment = msg_comment;
        this.date = date;
        this.msg_image = "31";
    }

    public Message(String date, String msg_context, String user, String msgID){
        this.msg_context = msg_context;
        this.date = date;
        this.user = user;
        this.msgID = msgID;
        this.msg_fabulous = "0";
        this.msg_comment = "0";
    }
    public String getUserID() {
        return userID;
    }

    public String getHeadImage() {
        return headImage;
    }

    public String getMsg_context() {
        return msg_context;
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
