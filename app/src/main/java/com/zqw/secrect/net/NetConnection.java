package com.zqw.secrect.net;

import android.os.AsyncTask;
import android.util.Log;

import com.zqw.secrect.Config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by 启文 on 2017/7/20.
 * 网络通信的基类
 */
public class NetConnection {

    public NetConnection(final String url, final HttpMethod method, final SuccessCallback successCallback, final FailCallback failCallback, final String ... kvs){

        //异步任务，传入参数无，过程无，返回String
        new AsyncTask<Void, Void, String>(){

            @Override
            protected String doInBackground(Void... voids) {

                StringBuilder paramsStr = new StringBuilder();
                for(int i=0; i<kvs.length; i+=2){
                    paramsStr.append(kvs[i]);
                    paramsStr.append("=");
                    paramsStr.append(kvs[i+1]).append("&");
                }

                //执行网络链接
                try {
                    URLConnection uc;
                    switch (method){
                        case POST:
                            uc = new URL(url).openConnection();
                            uc.setDoOutput(true);
                            //以流的形式直接写到服务器。
                            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(uc.getOutputStream(), Config.CHARSET));
                            bw.write(paramsStr.toString());
                            bw.flush();
                            break;
                        default:
                            //将键值对添加岛url的后面
                            uc = new URL(url + "?" + paramsStr.toString()).openConnection();
                            break;
                    }
                    Log.i("TEST",">"+url);
                    //按行来读取
                    BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream(), Config.CHARSET));
                    String line = null;
                    Log.i("TEST",">"+br.toString());
                    StringBuffer result = new StringBuffer();
                    Log.i("TEST",">>>"+url);
                    while ((line = br.readLine()) != null){
                        result.append(line);
                    }
                    Log.i("TEST",">!"+result.toString());
                    return result.toString();


                } catch (IOException e) {
                    e.printStackTrace();
                }


                return null;
            }

            /**
             * 在doInBackground方法执行完之后立刻执行这个方法，这个方法里面可以直接操作主线程
             * @param result    //doInBackground方法的返回值
             */
            @Override
            protected void onPostExecute(String result) {

                //进行回调
                if(result != null){
                    if(successCallback != null){
                        successCallback.onSuccess(result);
                    }
                }else {
                   if(failCallback != null){
                        failCallback.onFail();
                   }
                }

                super.onPostExecute(result);
            }
        }.execute();
    }


    /**
     * 网络访问成功时，回调接口，返回服务器响应信息
     */
    public static interface SuccessCallback{
        void onSuccess(String result);
    }


    /**
     * 网络访问失败时，回调接口，通知失败
     */
    public static interface FailCallback{
        void onFail();
    }
}
