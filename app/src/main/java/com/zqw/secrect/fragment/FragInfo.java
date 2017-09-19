package com.zqw.secrect.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.zqw.secrect.Config;
import com.zqw.secrect.R;
import com.zqw.secrect.activity.AtyMessageContent;
import com.zqw.secrect.adapter.AtyTimelineMessageListAdapter;
import com.zqw.secrect.adapter.IUninstall;
import com.zqw.secrect.net.Message;
import com.zqw.secrect.net.Timeline;

import java.util.List;




/**
 * Created by 启文 on 2017/9/7.
 */
public class FragInfo extends Fragment implements IUninstall, AdapterView.OnItemClickListener {

    private String user;
    private String token;
    private AtyTimelineMessageListAdapter adapter = null;
    private ListView list;

    //   CallBackValuel callBackValue;



    public FragInfo(String user, String token){
        this.user = user;
        this.token = token;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragments_infi, container, false);

        Bundle bundle = getArguments();
        if(bundle != null){
            user = bundle.getString(Config.KEY_USER);
            token = bundle.getString(Config.KEY_TOKEN);
        }
        Log.i("TEXT", user+"**user***token***"+token);

        list = (ListView) rootView.findViewById(R.id.list_timeline);
        adapter = new AtyTimelineMessageListAdapter(getActivity());

        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
        adapter.setiUninstall(this);

        loadMessage();

        return rootView;
    }

    /**
     * 加载消息
     */
    public void loadMessage(){

        //final ProgressDialog pd = ProgressDialog.show(getActivity(), "..." , "正在加载...");
        new Timeline(user, token, 1, 20, new Timeline.SuccessCallback() {
            @Override
            public void onSuccess(int pag, int perpag, List<Message> timeline) {
               // pd.dismiss();
                //向适配器添加数据
                adapter.clear();
                adapter.addAll(timeline);
            }
        }, new Timeline.FailCallback() {
            @Override
            public void onFail() {
               // pd.dismiss();
                Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Message msg = adapter.getItem(i);
        Intent k = new Intent(getActivity(), AtyMessageContent.class);
        k.putExtra(Config.KEY_MSG, msg.getMsg());
        k.putExtra(Config.KEY_MSG_ID, msg.getMsgID());
        k.putExtra(Config.KEY_USER,user);
        k.putExtra(Config.KEY_TOKEN, token);
        startActivity(k);

      //  callBackValue.SendMessageValue(bundle);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void OnClickView(int i, View v) {
        Message msg = adapter.getItem(i);

        switch (v.getId()){
            case R.id.tv_user_name:
                Log.i("TEXT", "点击了用户名"+msg.getUser());
                Toast.makeText(getActivity(), "点击了用户名"+msg.getUser(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_share:
                Log.i("TEXT", "点击了分享"+i);
                Toast.makeText(getActivity(), "点击了分享"+i, Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_collection:
                Log.i("TEXT", "点击了收藏"+i);
                Toast.makeText(getActivity(), "点击了收藏"+i, Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_fabulous:
                Log.i("TEXT", "点击了赞"+i);
                Toast.makeText(getActivity(), "点击了赞"+i, Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_comment:
                Log.i("TEXT", "点击了评论"+i);
                Toast.makeText(getActivity(), "点击了评论"+i, Toast.LENGTH_SHORT).show();
                break;

        }
    }




    /**
     * fragment与activity产生关联是 回调这个方法
     */
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(getActivity());
//        UpdateCallBack = (AtyTimeline.UpdateCallBack) getActivity();
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (resultCode){
//            case Config.ACITVITY_RESULT_NEED_REFRESH:
//                loadMessage();
//                Log.i("","这里重新加载了");
//                break;
//            default:
//                break;
//        }
//    }

    //定义一个回调接口
//    public interface CallBackValuel {
//        public void SendMessageValue(Bundle strValue);
//    }
}
