package com.zqw.secrect.net;

/**
 * Created by 启文 on 2017/7/27.
 */
public class Comment {

    private String content;
    private String user;
    private String date;

    public Comment(String content, String user, String date){
        this.content = content;
        this.user = user;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getUser() {
        return user;
    }

    public String getContent() {
        return content;
    }
}
