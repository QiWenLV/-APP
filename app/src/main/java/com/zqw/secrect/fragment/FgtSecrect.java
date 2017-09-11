package com.zqw.secrect.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
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
import com.zqw.secrect.adapter.AtyTimelineMessageListAdapter;
import com.zqw.secrect.adapter.IUninstall;
import com.zqw.secrect.net.Message;
import com.zqw.secrect.net.Timeline;

import java.util.List;




/**
 * Created by 启文 on 2017/9/7.
 */
public class FgtSecrect extends Fragment implements IUninstall, AdapterView.OnItemClickListener{

    private String user;
    private String token;
    private AtyTimelineMessageListAdapter adapter = null;
    private ListView list;
 //   CallBackValuel callBackValue;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragments_secrect, container, false);

        Bundle bundle = getArguments();
        if(bundle != null){
            user = bundle.getString(Config.KEY_USER);
            token = bundle.getString(Config.KEY_TOKEN);
        }
        Log.i("TEXT", user+"********"+token);

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
    private void loadMessage(){

        final ProgressDialog pd = ProgressDialog.show(getActivity(), "..." , "正在加载...");
        new Timeline(user, token, 1, 20, new Timeline.SuccessCallback() {
            @Override
            public void onSuccess(int pag, int perpag, List<Message> timeline) {
                pd.dismiss();
                //向适配器添加数据
                adapter.clear();
                adapter.addAll(timeline);
            }
        }, new Timeline.FailCallback() {
            @Override
            public void onFail() {
                pd.dismiss();
                Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Message msg = adapter.getItem(i);
//        Intent k = new Intent(getActivity(), AtyMessage.class);
//        k.putExtra(Config.KEY_MSG, msg.getMsg());
//        k.putExtra(Config.KEY_MSG_ID, msg.getMsgID());
//        k.putExtra(Config.KEY_USER,user);
//        k.putExtra(Config.KEY_TOKEN, token);
//        this.startActivityForResult(k);
        Bundle bundle = new Bundle();
        bundle.putString(Config.KEY_MSG, msg.getMsg());
        bundle.putString(Config.KEY_MSG_ID, msg.getMsgID());
        bundle.putString(Config.KEY_USER,user);
        bundle.putString(Config.KEY_TOKEN, token);
      //  callBackValue.SendMessageValue(bundle);
    }


    /**
     * fragment与activity产生关联是 回调这个方法
     */
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(getActivity());
//        callBackValue = (CallBackValuel) getActivity();
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
