package com.zqw.secrect.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.zqw.secrect.BaseActivity;
import com.zqw.secrect.Config;
import com.zqw.secrect.R;
import com.zqw.secrect.net.Publish;

/**
 * Created by 启文 on 2017/7/21.
 * 发送消息界面
 */
public class AtyPubMessage extends BaseActivity{

    private ImageButton btnPicture;
    private EditText etMsgTitle;
    private EditText etMsgContent;
    private String user;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.aty_pub_message);

        setToolbar(R.id.btnPubMsg_tb);
        setToolBarMenuOnclick(new PubMessageMenuClickListener());
        setBackArrow();

        user = getIntent().getStringExtra(Config.KEY_USER);
        token = getIntent().getStringExtra(Config.KEY_TOKEN);

        btnPicture = (ImageButton) findViewById(R.id.btn_picture);
        etMsgTitle = (EditText) findViewById(R.id.etMessage_title);
        etMsgContent = (EditText) findViewById(R.id.etMessage_content);


        btnPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PictureSelector.create(AtyPubMessage.this)
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
            }
        });
    }


    class PubMessageMenuClickListener implements android.support.v7.widget.Toolbar.OnMenuItemClickListener{

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menuShowMessage:
                    if(TextUtils.isEmpty(etMsgContent.getText())){
                        Toast.makeText(AtyPubMessage.this, "发布内容不能为空", Toast.LENGTH_LONG).show();
                        break;
                    }

                    new Publish(user, token, etMsgContent.getText().toString(), new Publish.SuccessCallback() {
                        @Override
                        public void onSuccess() {
                            setResult(Config.ACITVITY_RESULT_NEED_REFRESH); //回调

                            Toast.makeText(AtyPubMessage.this, "发表消息成功", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }, new Publish.FailCallback() {
                        @Override
                        public void onFail(int errorCode) {
                            if(errorCode == 2){
                                startActivity(new Intent(AtyPubMessage.this, AtyLogin.class));
                                finish();
                            }else{
                                Toast.makeText(AtyPubMessage.this, "发表失败", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    break;
            }
            return false;
        }
    }

    /**
     * 创建菜单栏
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pub_message, menu);
        return true;
    }
}
