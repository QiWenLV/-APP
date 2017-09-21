package com.zqw.secrect.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.luck.picture.lib.entity.LocalMedia;
import com.zqw.secrect.BaseActivity;
import com.zqw.secrect.Config;
import com.zqw.secrect.R;
import com.zqw.secrect.fragment.FragFriend;
import com.zqw.secrect.fragment.FragInfo;
import com.zqw.secrect.fragment.FragMyData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 启文 on 2017/7/20.
 */
public class AtyTimeline extends BaseActivity {

    private String user;
    private String token;

    private ViewPager viewPager;    //页面内容
    private ImageView imageView;    // 滑动的图片

    private TextView voiceAnswer, healthPedia, pDected; //选项名称

    private List<Fragment> fragments;   //标题列表

    private int offset = 0;         //动画图片偏移量
    private int currIndex = 0;      //当前页卡编号
    private int bmpW;               //动画图片宽度
    private int selectedColor, unSelectedColor;

    private static final int pageSize = 3;  //页面总数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.aty_timeline);

        setTitle("好友秘密");
     //   setBackArrow();
        setToolBarMenuOnclick(new mainToolBarMenuClick());

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("秘密");
//
//        setSupportActionBar(toolbar);
//
//        toolbar.setOnMenuItemClickListener(onMenuItemClick);
//
        user = getIntent().getStringExtra(Config.KEY_USER);
        token = getIntent().getStringExtra(Config.KEY_TOKEN);


        initView();

    }

    /**
     * 初始化当前标题和未被选择的字体的颜色
     */
    public void initView(){
        selectedColor = getResources().getColor(R.color.tv_pre);    //选中时的颜色
        unSelectedColor = getResources().getColor(R.color.tv_normal);   //未被选中时的颜色

        InitViewPager();
        InitTextView();
        InitImageView();
    }

    /**
     * 初始化viewPager页
     */
    private void InitViewPager(){
        viewPager = (ViewPager) findViewById(R.id.id_viewwpager);
        fragments = new ArrayList<Fragment>();
        fragments.add(new FragInfo(user, token));
        fragments.add(new FragFriend(user, token));
        fragments.add(new FragMyData(user, token));
        viewPager.setAdapter(new myPagerAdapter(getSupportFragmentManager(), fragments));
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    /**
     * 初始化顶部三个字体按钮
     */
    private void InitTextView(){
        voiceAnswer = (TextView) findViewById(R.id.tv_Info);     //消息
        healthPedia = (TextView) findViewById(R.id.tv_friend);   //朋友
        pDected = (TextView) findViewById(R.id.tv_myData);       //我的信息

        voiceAnswer.setTextColor(selectedColor);
        healthPedia.setTextColor(unSelectedColor);
        pDected.setTextColor(unSelectedColor);

        voiceAnswer.setText("消息");
        healthPedia.setText("朋友");
        pDected.setText("我的");

        //设置文字监听
        voiceAnswer.setOnClickListener(new MyOnClickListener(0));
        healthPedia.setOnClickListener(new MyOnClickListener(1));
        pDected.setOnClickListener(new MyOnClickListener(2));
    }

    /**
     * 初始化动画，这个就是页面滑动时，下面的横线也滑动的效果，在这里需要计算一些数据
     */
    private void InitImageView(){
        imageView = (ImageView) findViewById(R.id.iv_cursor);
        bmpW = BitmapFactory.decodeResource(getResources(), R.mipmap.greenline).getWidth();   // 获取图片宽度

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        offset = (screenW / pageSize - bmpW) / 2;// 计算偏移量--(屏幕宽度/页卡总数-图片实际宽度)/2 = 偏移量

        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        imageView.setImageMatrix(matrix);// 设置动画初始位置
    }

    /**
     * 定义内部类三个文字点击按钮监听
     */
    private class MyOnClickListener implements View.OnClickListener{

        private int index = 0;  //表示当前页面

        public MyOnClickListener(int i){
            index = i;
        }

        @Override
        public void onClick(View view) {

            switch (index){
                case 0:
                    voiceAnswer.setTextColor(selectedColor);
                    healthPedia.setTextColor(unSelectedColor);
                    pDected.setTextColor(unSelectedColor);
                    break;
                case 1:
                    healthPedia.setTextColor(selectedColor);
                    voiceAnswer.setTextColor(unSelectedColor);
                    pDected.setTextColor(unSelectedColor);
                    break;
                case 2:
                    pDected.setTextColor(selectedColor);
                    voiceAnswer.setTextColor(unSelectedColor);
                    healthPedia.setTextColor(unSelectedColor);
                    break;
            }
            viewPager.setCurrentItem(index);    //切换到指定页面
        }
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{


        /**
         * This method will be invoked when the current page is scrolled, either as part
         * of a programmatically initiated smooth scroll or a user initiated touch scroll.
         *
         * @param position             Position index of the first page currently being displayed.
         *                             Page position+1 will be visible if positionOffset is nonzero.
         * @param positionOffset       Value from [0, 1) indicating the offset from the page at position.
         * @param positionOffsetPixels Value in pixels indicating the offset from position.
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        /**
         * Called when the scroll state changes. Useful for discovering when the user
         * begins dragging, when the pager is automatically settling to the current page,
         * or when it is fully stopped/idle.
         *
         * @param state The new scroll state.
         * @see ViewPager#SCROLL_STATE_IDLE
         * @see ViewPager#SCROLL_STATE_DRAGGING
         * @see ViewPager#SCROLL_STATE_SETTLING
         */
        @Override
        public void onPageScrollStateChanged(int state) {

        }

        /**
         * This method will be invoked when a new page becomes selected. Animation is not
         * necessarily complete.
         *
         * @param position Position index of the new selected page.
         */
        @Override
        public void onPageSelected(int position) {
            //Animation animation = new TranslateAnimation(one * currIndex, one * position, 0, 0);
            int one = offset * 2 + bmpW; //页面1 -> 页面2 偏移量
            int two = one * 2; //页面1 -> 页面3 偏移量
            Animation animation = null;


            switch (position){
                case 0:
                    voiceAnswer.setTextColor(selectedColor);
                    healthPedia.setTextColor(unSelectedColor);
                    pDected.setTextColor(unSelectedColor);

                    if(currIndex == 1){
                        animation = new TranslateAnimation(one, 0, 0, 0);
                    }else if(currIndex == 2){
                        animation = new TranslateAnimation(two, 0, 0, 0);
                    }

                    break;
                case 1:
                    healthPedia.setTextColor(selectedColor);
                    voiceAnswer.setTextColor(unSelectedColor);
                    pDected.setTextColor(unSelectedColor);

                    if(currIndex == 0){
                        animation = new TranslateAnimation(offset, one, 0, 0);
                    }else if(currIndex == 2){
                        animation = new TranslateAnimation(two, one, 0, 0);
                    }
                    break;
                case 2:
                    pDected.setTextColor(selectedColor);
                    voiceAnswer.setTextColor(unSelectedColor);
                    healthPedia.setTextColor(unSelectedColor);

                    if(currIndex == 0){
                        animation = new TranslateAnimation(offset, two, 0, 0);
                    }else if(currIndex == 1){
                        animation = new TranslateAnimation(one, two, 0, 0);
                    }
                    break;
            }

            currIndex = position;
            animation.setFillAfter(true);   //图片停在动画结束的位置
            animation.setDuration(300);
            imageView.startAnimation(animation);
        }
    }

    /**
     * 定义内部类适配器
     */
    private class myPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList;

        public myPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        /**
         * 得到每个页面
         *
         * @param position
         */
        @Override
        public Fragment getItem(int position) {
            return (fragmentList == null || fragmentList.size() == 0) ? null : fragmentList.get(position);
        }

        /**
         * 页面的总个数
         */
        @Override
        public int getCount() {
            return fragmentList == null ? 0 : fragmentList.size();
        }

        /**
         * 每个页面的title
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return super.getPageTitle(position);
        }
    }


    /**
     * 顶部菜单（标题栏）
     */
    private  Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener(){

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            String msg = "";
            switch (item.getItemId()) {
                case R.id.menuShowAddMessage:
                    Intent i = new Intent(AtyTimeline.this, AtyPubMessage.class);
                    i.putExtra(Config.KEY_USER, user);
                    i.putExtra(Config.KEY_TOKEN, token);
                    startActivityForResult(i, 1);

                    break;
            }
            if(!msg.equals("")) {
                Toast.makeText(AtyTimeline.this, msg, Toast.LENGTH_SHORT).show();

            }
            return true;
        }
    };

    class mainToolBarMenuClick implements Toolbar.OnMenuItemClickListener {

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            String msg = "";
            switch (item.getItemId()) {
                case R.id.menuShowAddMessage:
                 //  if(user != null){
                       Intent i = new Intent(AtyTimeline.this, AtyPubMessage.class);
                       i.putExtra(Config.KEY_USER, user);
                       i.putExtra(Config.KEY_TOKEN, token);
                       startActivityForResult(i, 1);
               //    }else{
              //         Intent a = new Intent(AtyTimeline.this, AtyLogin.class);
               //        startActivity(a);
                //   }

                    break;
//                case R.id.action_message:
//                    Toast.makeText(context, "message", Toast.LENGTH_SHORT).show();
//                    break;
            }
            if(!msg.equals("")) {
                Toast.makeText(AtyTimeline.this, msg, Toast.LENGTH_SHORT).show();

            }
            return false;
        }
    }

    /**
     * 标题栏菜单（发表消息的人口）
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_timeline, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case Config.ACITVITY_RESULT_NEED_REFRESH:
//                FragmentManager manager = getSupportFragmentManager();
//                FragInfo fragInfo = (FragInfo) manager.findFragmentById(R.id.id_viewwpager);
//               // FragInfo fragInfo = new FragInfo(user, token);
//                Log.i("TEXT","这里重新加载了");
//                fragInfo.loadMessage();

                FragInfo fragment = (FragInfo) getSupportFragmentManager().findFragmentByTag(
                        "android:switcher:"+R.id.id_viewwpager+":0");
                if(fragment != null)
                {
                    if(fragment.getView() != null)
                    {
                        fragment.loadMessage();
                    }
                }
                break;
           default:
               break;
       }
    }

    private List<LocalMedia> selectList = new ArrayList<>();
}
