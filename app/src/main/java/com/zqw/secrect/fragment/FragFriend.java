package com.zqw.secrect.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zqw.secrect.R;

/**
 * Created by 启文 on 2017/9/7.
 */
public class FragFriend extends Fragment {

    private String user;
    private String token;


    public FragFriend(String user, String token){
        this.user = user;
        this.token = token;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragments_friends, container, false);




        return rootView;
    }
}
