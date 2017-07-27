package com.zqw.secrect.net;

/**
 * Created by 启文 on 2017/7/27.
 */
public class Comment {

    private String content;
    private String user;

    public Comment(String content, String user){
        this.content = content;
        this.user = user;
    }

    public String getContent() {
        return content;
    }
}
