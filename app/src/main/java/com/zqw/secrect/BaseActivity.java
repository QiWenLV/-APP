package com.zqw.secrect;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by 启文 on 2017/9/14.
 */
public class BaseActivity extends AppCompatActivity {

    private TextView commonTitleTv;     //通用的ToolBar标题
    private Toolbar commonTitleTb;      //通用的ToolBar

    private RelativeLayout content;     //内容区域


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        initView();

        setSupportActionBar(commonTitleTb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    /**
     * 初始化
     */
    private void initView() {

        commonTitleTv = (TextView) findViewById(R.id.common_title_tv);
        commonTitleTb = (Toolbar) findViewById(R.id.common_title_tb);
        content = (RelativeLayout) findViewById(R.id.content);
    }

    /**
     * 子类调用，重新设置Toolbar
     */
    public void setToolbar(int layout){
        hidetoolBar();
        commonTitleTb = (Toolbar) content.findViewById(layout);
        setSupportActionBar(commonTitleTb);
        //设置actionBar的标题是否显示，对应ActionBar.DISPLAY_SHOW_TITLE
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    /**
     * 隐藏ToolBar，通过setToolBar重新定制ToolBar
     */
    public void hidetoolBar(){
        commonTitleTb.setVisibility(View.GONE);
    }

    /**
     * menu的点击事件
     * @param onclick
     */
    public void setToolBarMenuOnclick(Toolbar.OnMenuItemClickListener onclick) {
        commonTitleTb.setOnMenuItemClickListener(onclick);
    }

    /**
     * 设置左上角back按钮
     */
    public void setBackArrow(){
        //给ToolBar设置左侧的图标
        final Drawable upArrow = getResources().getDrawable(R.drawable.menu_back2);

        // 给左上角图标的左边加上一个返回的图标 。对应ActionBar.DISPLAY_HOME_AS_UP
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        //设置返回按钮的点击事件
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        commonTitleTb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 设置toolbar下面的内容区域的内容
     * @param layoutId
     */
    public void setContentLayout(int layoutId){
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(layoutId, null);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        content.addView(contentView, params);
    }

    /**
     * 设置标题
     * @param title
     */
    public void setTitle(String title){
        if(!TextUtils.isEmpty(title)){
            commonTitleTv.setText(title);
        }
    }

    /**
     * 设置标题
     *
     * @param resId
     */
    public void setTitle(int resId) {
        commonTitleTv.setText(resId);
    }

}
