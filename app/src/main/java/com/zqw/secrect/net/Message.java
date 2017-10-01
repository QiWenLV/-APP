package com.zqw.secrect.net;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 启文 on 2017/7/22.
 * 消息类
 */
public class Message implements Parcelable {

    private String msgID = null;      //消息ID
    private String userID = null;     //用户ID
    private Bitmap headImage = null;  //用户头像
    private String msg_context = null;        //消息内容
    private String msg_title = null;    //消息标题


    private String user =null;          //发表消息的用户
    private String date = null;         //发表消息的时间
    private String msg_fabulous = null;     //点赞数
    private String msg_comment = null;  //评论数
    private String msg_image = null;    //消息图片

    private boolean isFabulous = true;

    public Message(String userID, String msgID, Bitmap headImage, String user, String msg_title, String msg_context, String msg_fabulous, String msg_comment, String date, String msg_image){
        this.userID = userID;
        this.msgID = msgID;
        this.headImage = headImage;
        this.user = user;
        this.msg_title = msg_title;
        this.msg_context = msg_context;
        this.msg_fabulous = msg_fabulous;
        this.msg_comment = msg_comment;
        this.date = date;
        this.msg_image = "31";
    }

    public Message(){}

    public String getUserID() {
        return userID;
    }

    public Bitmap getHeadImage() {
        return headImage;
    }


    public String getMsg_title() {
        return msg_title;
    }

    public String getMsg_image() {
        return msg_image;
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


    public void setFabulous(boolean fabulous) {
        isFabulous = fabulous;
    }

    public boolean isFabulous() {

        return isFabulous;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(msgID);
        parcel.writeString(userID);
        headImage.writeToParcel(parcel, i);
        parcel.writeString(msg_context);
        parcel.writeString(msg_title);

        parcel.writeString(user);
        parcel.writeString(date);
        parcel.writeString(msg_fabulous);
        parcel.writeString(msg_comment);
        parcel.writeString(msg_image);
        parcel.writeByte((byte) (isFabulous ?1:0));

    }
    public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>(){

        @Override
        public Message createFromParcel(Parcel parcel) {
            Message msg = new Message();
            msg.msgID = parcel.readString();
            msg.userID = parcel.readString();
            msg.headImage = Bitmap.CREATOR.createFromParcel(parcel);
            msg.msg_context = parcel.readString();
            msg.msg_title = parcel.readString();

            msg.user = parcel.readString();
            msg.date = parcel.readString();
            msg.msg_fabulous = parcel.readString();
            msg.msg_comment = parcel.readString();
            msg.msg_image = parcel.readString();
            msg.isFabulous = (parcel.readByte()==0?false:true);
           // msg.isFabulous = (parcel.readByte() != 0);

            return msg;
        }

        @Override
        public Message[] newArray(int i) {
            return new Message[i];
        }
    };
}
