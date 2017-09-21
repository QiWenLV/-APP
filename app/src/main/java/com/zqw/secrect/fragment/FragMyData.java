package com.zqw.secrect.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
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
import com.zqw.secrect.net.HttpMethod;
import com.zqw.secrect.net.NetConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
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

    public FragMyData(String user, String token){
        this.user = user;
        this.token = token;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragments_mydata, container, false);

        Log.i("TEXTC", "**&&**&&**&"+user);
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
                    for (LocalMedia a : selectList) {
                        Bitmap imageBitmap = BitmapFactory.decodeFile(a.getCutPath());

                        String headImage = convertIconToString(imageBitmap);
                       // Log.i("TEXTC", "这是原版"+headImage);

                        new UqloadImage(user, token, headImage, new UqloadImage.SuccessCallback() {
                            @Override
                            public void onSuccess(String s) {
                                Bitmap image = convertStringToIcon(s);
                                hear_image.setImageBitmap(image);
                            }
                        }, new UqloadImage.FailCallback() {
                            @Override
                            public void onFail() {
                                Toast.makeText(getActivity(), "头像上传失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    //    Log.i("TEXTC", a.getCutPath());
                    }
                    break;
            }
        }
    }


    public static class UqloadImage{

        public UqloadImage(String user, String token, String headImage, final SuccessCallback successCallback, final FailCallback failCallback){
            new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
                @Override
                public void onSuccess(String result) {
                    try {
                        JSONObject obj = new JSONObject(result);
                        switch (obj.getInt(Config.KEY_STATUS)) {
                            case 1:
                                if (successCallback != null) {
                                    String s = obj.getString(Config.KEY_HERD_IMAGE);
                                    successCallback.onSuccess(s);
                                }
                                break;
                            default:
                                if (failCallback != null) {
                                    failCallback.onFail();
                                }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new NetConnection.FailCallback() {
                @Override
                public void onFail() {
                    if(failCallback != null){
                        failCallback.onFail();
                    }
                }
            }, Config.KEY_ACTION, Config.ACTION_UPLOAD_HEAD,
                    Config.KEY_USER, user,
                    Config.KEY_TOKEN, token,
                    Config.KEY_HERD_IMAGE, headImage);

        }


        public interface SuccessCallback{
            void onSuccess(String s);
        }

        public static interface FailCallback{
            void onFail();
        }
    }



//    public class UqloadImage extends AsyncTask<Bitmap, Void, Bitmap> {
//
//
//        @Override
//        protected Bitmap doInBackground(Bitmap... bitmaps) {
//            StringBuilder paramsStr = new StringBuilder();
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//
//            bitmaps[0].compress(Bitmap.CompressFormat.PNG, 100, baos);
//            InputStream isBm = new ByteArrayInputStream(baos.toByteArray());
//
//
//            URLConnection uc;
//            try {
//                //上传头像
//                uc = new URL(Config.SERVER_URL).openConnection();
//                uc.setDoOutput(true);
//
//                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(uc.getOutputStream(), Config.CHARSET));
//                bw.write(isBm.toString());
//                bw.flush();
//
//
//                //服务器返回图片数据
//                BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream(), Config.CHARSET));
//                String line = null;
//                StringBuffer result = new StringBuffer();
//
//                while((line = br.readLine()) != null){
//                    result.append(line);
//                }
//
//                JSONObject obj = new JSONObject(result.toString());
//
//                switch (obj.getInt(Config.KEY_STATUS)){
//                    case 1:
//
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//
//            return null;
//        }
//    }


    /**
     * 图片转成string
     *
     * @param bitmap
     * @return
     */
    public static String convertIconToString(Bitmap bitmap)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] appicon = baos.toByteArray();// 转为byte数组
        return Base64.encodeToString(appicon, Base64.DEFAULT);

    }


    /**
     * 将字符串转化为bitmap
     * @param st
     * @return
     */
    public static Bitmap convertStringToIcon(String st)
    {
        // OutputStream out;
        Bitmap bitmap = null;
        try
        {
            // out = new FileOutputStream("/sdcard/aa.jpg");
            byte[] bitmapArray;
            bitmapArray = Base64.decode(st, Base64.DEFAULT);
            bitmap =
                    BitmapFactory.decodeByteArray(bitmapArray, 0,
                            bitmapArray.length);
            // bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            return bitmap;
        }
        catch (Exception e)
        {
            return null;
        }
    }


}
