package com.zqw.secrect.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zqw.secrect.Config;
import com.zqw.secrect.R;

/**
 * Created by 启文 on 2017/9/14.
 */
public class FragMyData extends Fragment {

    private String user;
    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragments_mydata, container, false);

        Bundle bundle = getArguments();
        if(bundle != null){
            user = bundle.getString(Config.KEY_USER);
            token = bundle.getString(Config.KEY_TOKEN);
        }


        return rootView;
    }
}
