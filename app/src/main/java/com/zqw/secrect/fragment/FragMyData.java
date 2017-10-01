package com.zqw.secrect.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.zqw.secrect.activity.AtyLogin;
import com.zqw.secrect.net.HttpMethod;
import com.zqw.secrect.net.MyData;
import com.zqw.secrect.net.NetConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 启文 on 2017/9/14.
 */
public class FragMyData extends Fragment implements View.OnClickListener {

    private String user;
    private String token;

    static private String Num_Message = null;
    static private String Num_Comment = null;
    static private String Num_Collection = null;
    static private Bitmap Image;

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
        hear_image.setMaxWidth(70);
        hear_image.setMaxHeight(70);

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

        tvUser.setText(user);

        if(Num_Message == null){
            loadData();
        }else{
            tvNumMessage.setText(Num_Message);
            tvNumComment.setText(Num_Comment);
            tvNumCollection.setText(Num_Collection);
            hear_image.setImageBitmap(Image);
        }


    }


    private void  loadData(){
        final ProgressDialog pd = ProgressDialog.show(getActivity(), "..." , "正在加载...");
        new MyData(user, token, new MyData.SuccessCallback() {
            @Override
            public void onSuccess(String Num_message, String Num_comment, String Num_collection, String headImage) {
                pd.dismiss();
                Num_Message = Num_message;
                Num_Comment = Num_comment;
                Num_Collection = Num_collection;

                tvNumMessage.setText(Num_message);
                tvNumComment.setText(Num_comment);
                tvNumCollection.setText(Num_collection);

                Bitmap image = Config.convertStringToIcon(headImage);
                Image = image;

                hear_image.setImageBitmap(image);

            }
        }, new MyData.FailCallback() {
            @Override
            public void onFail() {
                pd.dismiss();
                Toast.makeText(getActivity(), "数据读取失败", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_head_portrait:
                PictureSelector.create(FragMyData.this)
                        .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                        .minSelectNum(1)        // 最大图片选择数量 int
                        .maxSelectNum(1)        // 最小选择数量 int
                        .selectionMode(PictureConfig.SINGLE)    // 多选 or 单选
                        .previewImage(true)     // 是否可预览图片
                        .isCamera(true)         // 是否显示拍照按钮
                        .enableCrop(true)       // 是否裁剪
                        //.compress(false)        // 是否压缩
                        //.compressMode(PictureConfig.LUBAN_COMPRESS_MODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                        .withAspectRatio(1, 1)      //int 裁剪比例
                        .cropWH(120, 120)// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                        .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示
                        .freeStyleCropEnabled(false)// 裁剪框是否可拖拽
                        .circleDimmedLayer(false)//是否圆形裁剪 true or false
                        .showCropFrame(true)     //是否显示裁剪矩形边框 圆形裁剪时建议设为false
                        .showCropGrid(true)      //是否显示裁剪矩形网格 圆形裁剪时建议设为false
                        .previewEggs(true)       //预览图片时 是否增强左右滑动图片体验
                        .rotateEnabled(true)     // 裁剪是否可旋转图片 true or false
                        .scaleEnabled(true)      // 裁剪是否可放大缩小图片 true or false
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
            case R.id.tv_user_name:
                Toast.makeText(getActivity(), "点击了用户名", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(), AtyLogin.class);
                startActivity(i);
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

                    for (LocalMedia a : selectList) {
                        Bitmap imageBitmap = BitmapFactory.decodeFile(a.getCutPath()); //取压缩后的图片

                        String headImage = Config.convertIconToString(imageBitmap);

                        new UqloadImage(user, token, headImage, new UqloadImage.SuccessCallback() {
                            @Override
                            public void onSuccess() {
                                loadData();
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
                                  //  String s = obj.getString(Config.KEY_HERD_IMAGE);
                                    successCallback.onSuccess();
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
            void onSuccess();
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





}
