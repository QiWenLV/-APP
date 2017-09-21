package com.zqw.secrect.net;

import java.util.List;

/**
 * Created by 启文 on 2017/9/21.
 */
public class MyData {

    public MyData(String user){

    }


    public static interface SuccessCallback{
        void onSuccess(int pag, int perpage, List<Message> timeline);
    }

    public static interface FailCallback{
        void onFail();
    }



}
