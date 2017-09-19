package com.zqw.secrect.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.zqw.secrect.Config;
import com.zqw.secrect.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 启文 on 2017/9/14.
 */
public class FragMyData extends Fragment implements View.OnClickListener {

    private String user;
    private String token;

    private List<LocalMedia> selectList = new ArrayList<>();

    private ImageButton hear_image;
    private TextView tvUser;
    private TextView tvNumMessage;
    private TextView tvNumComment;
    private TextView tvNumCollection;

    private LinearLayout layoutMassage;
    private LinearLayout layoutComment;
    private LinearLayout layoutCollection;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragments_mydata, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            user = bundle.getString(Config.KEY_USER);
            token = bundle.getString(Config.KEY_TOKEN);
        }
        initView(rootView);

        return rootView;
    }

    /**
     * 初始化
     *
     * @param v
     */
    private void initView(View v) {
        hear_image = (ImageButton) v.findViewById(R.id.iv_head_portrait);
        tvUser = (TextView) v.findViewById(R.id.tv_user_name);
        tvNumMessage = (TextView) v.findViewById(R.id.tv_num_message);
        tvNumComment = (TextView) v.findViewById(R.id.tv_num_comment);
        tvNumCollection = (TextView) v.findViewById(R.id.tv_num_collection);
        layoutMassage = (LinearLayout) v.findViewById(R.id.lin_num_message);
        layoutComment = (LinearLayout) v.findViewById(R.id.lin_num_comment);
        layoutCollection = (LinearLayout) v.findViewById(R.id.lin_num_collection);

        hear_image.setOnClickListener(this);
        tvUser.setOnClickListener(this);
        layoutMassage.setOnClickListener(this);
        layoutComment.setOnClickListener(this);
        layoutCollection.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_head_portrait:
                PictureSelector.create(FragMyData.this)
                        .openGallery(PictureMimeType.ofImage())
                        .minSelectNum(1)
                        .maxSelectNum(1)
                        .selectionMode(PictureConfig.SINGLE)
                        .previewImage(true)
                        .isCamera(true)
                        .enableCrop(true)
                        .compress(false)
                        .circleDimmedLayer(true)
                        .showCropFrame(false)
                        .showCropGrid(false)
                        .previewEggs(true)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
            case R.id.tv_user_name:
                Toast.makeText(getActivity(), "点击了用户名", Toast.LENGTH_SHORT).show();
                break;
            case R.id.lin_num_message:
                Toast.makeText(getActivity(), "点击了消息数量", Toast.LENGTH_SHORT).show();
                break;
            case R.id.lin_num_comment:
                Toast.makeText(getActivity(), "点击了评论数量", Toast.LENGTH_SHORT).show();
                break;
            case R.id.lin_num_collection:
                Toast.makeText(getActivity(), "点击了收藏数量", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择
                    selectList = PictureSelector.obtainMultipleResult(data);
                   // adapter.setList(selectList);
                   // adapter.notifyDataSetChanged();
                   // DebugUtil.i(TAG, "onActivityResult:" + selectList.size());
                    for(LocalMedia a : selectList){
                        Log.i("TEXTC", a.getCutPath());
                    }
                    break;
            }
        }
    }
}
