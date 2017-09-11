package com.zqw.secrect.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zqw.secrect.Config;
import com.zqw.secrect.R;
import com.zqw.secrect.adapter.AtyTimelineMessageListAdapter;
import com.zqw.secrect.fragment.FgtFriend;
import com.zqw.secrect.fragment.FgtSecrect;

/**
 * Created by 启文 on 2017/7/20.
 */
public class AtyTimeline extends AppCompatActivity implements View.OnClickListener {

    private String user;
    private String token;
    private AtyTimelineMessageListAdapter adapter = null;

    private LinearLayout mBottom;

    private Button mTabSecrect;
    private Button mTabFriend;

    private Fragment fSecrect;
    private Fragment fFriend;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_timeline);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("秘密");

        setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(onMenuItemClick);

        user = getIntent().getStringExtra(Config.KEY_USER);
        token = getIntent().getStringExtra(Config.KEY_TOKEN);

        init();

    }
    public void init(){
        /**
         * 拿到事务并开启事务
         */
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();

        /**
         * 初始化按钮
         */
        mBottom = (LinearLayout) findViewById(R.id.id_ly_bottombar);
        mTabSecrect = (Button) mBottom.findViewById(R.id.tab_bottom_friend);
        mTabFriend = (Button) mBottom.findViewById(R.id.tab_bottom_secrect);

        /**
         * 为按钮设置监听
         */
        mTabSecrect.setOnClickListener(this);
        mTabFriend.setOnClickListener(this);

        /**
         * 设置默认的Fragment
         */
        fSecrect = new FgtSecrect();
        transaction.replace(R.id.id_content, fSecrect);
        transaction.commit();
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

    /**
     * 标题栏菜单（发表消息的人口）
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_timeline, menu);

//        MenuItem search = menu.findItem(R.id.menuShowAddMessage);
//        MenuItemCompat.setOnActionExpandListener(search, new MenuItemCompat.OnActionExpandListener() {
//            @Override
//            public boolean onMenuItemActionExpand(MenuItem item) {
//                return false;
//            }
//
//            @Override
//            public boolean onMenuItemActionCollapse(MenuItem item) {
//                return false;
//            }
//        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View view) {
        Bundle mBundle = new Bundle();
        manager = getSupportFragmentManager();
        //开启Fragment事务
        transaction = manager.beginTransaction();
        switch (view.getId()){
            case R.id.tab_bottom_secrect:
                /**
                 * 防止重叠，点击前先移除其他Fragment
                 */
                hideFragment(transaction);
                if(fSecrect != null){
                    fSecrect = new FgtSecrect();
                    mBundle.putString(Config.KEY_USER, user);
                    mBundle.putString(Config.KEY_TOKEN, token);
                    fSecrect.setArguments(mBundle);
                }
                transaction.replace(R.id.id_content, fSecrect);
                break;
            case  R.id.tab_bottom_friend:
                hideFragment(transaction);
                fFriend = new FgtFriend();
                mBundle.putString(Config.KEY_USER, user);
                mBundle.putString(Config.KEY_TOKEN, token);
                fFriend.setArguments(mBundle);
                transaction.replace(R.id.id_content,fFriend);
                break;
        }
        //事务提交
        transaction.commit();
    }
//
//

    /**
     * 去除（隐藏）所有的Fragment
     * @param transaction
     */
    private void hideFragment(FragmentTransaction transaction) {
        if (fSecrect != null) {
            //transaction.hide(f1);隐藏方法也可以实现同样的效果，不过我一般使用去除
            transaction.remove(fSecrect);
        }
        if (fFriend != null) {
            //transaction.hide(f2);
            transaction.remove(fFriend);
        }
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (resultCode){
//            case Config.ACITVITY_RESULT_NEED_REFRESH:
//          //     loadMessage();
//               Log.i("TEST","这里重新加载了");
//               break;
//           default:
//               break;
//       }
//    }
}
