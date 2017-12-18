package com.hdu.jerryhumor.multnewsreader.news.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdu.jerryhumor.multnewsreader.R;
import com.hdu.jerryhumor.multnewsreader.base.BaseActivity;
import com.hdu.jerryhumor.multnewsreader.constant.IntentExtra;
import com.hdu.jerryhumor.multnewsreader.constant.NewsType;
import com.hdu.jerryhumor.multnewsreader.login.LoginActivity;
import com.hdu.jerryhumor.multnewsreader.login.UserInfo;
import com.hdu.jerryhumor.multnewsreader.news.fragment.NewsFragment;
import com.hdu.jerryhumor.multnewsreader.util.JLog;

import java.util.ArrayList;
import java.util.List;

public class NewsListActivity extends BaseActivity implements View.OnClickListener{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private ImageView ivUserImage;
    private TextView tvUserName;

    private List<NewsFragment> mNewsPagerList;
    private UserInfo mUserInfo;

    @Override
    protected int getResourceId() {
        return R.layout.activity_news_list;
    }

    @Override
    protected void initView() {
        tabLayout = findViewById(R.id.tab_layout_type);
        viewPager = findViewById(R.id.view_pager_news);
        toolbar = findViewById(R.id.toolbar);
        ivUserImage = findViewById(R.id.iv_user_image);
        tvUserName = findViewById(R.id.tv_user_name);
    }

    @Override
    protected void initData() {
        mNewsPagerList = new ArrayList<>();
        mNewsPagerList.add(new NewsFragment().setType(NewsType.CODE_LATEST));
        mNewsPagerList.add(new NewsFragment().setType(NewsType.CODE_TECH));
        mNewsPagerList.add(new NewsFragment().setType(NewsType.CODE_SPORT));
        mNewsPagerList.add(new NewsFragment().setType(NewsType.CODE_SOCIAL));
        mNewsPagerList.add(new NewsFragment().setType(NewsType.CODE_FUN));
        mNewsPagerList.add(new NewsFragment().setType(NewsType.CODE_CAR));
        mNewsPagerList.add(new NewsFragment().setType(NewsType.CODE_ART));
        mNewsPagerList.add(new NewsFragment().setType(NewsType.CODE_FINANCE));
        mNewsPagerList.add(new NewsFragment().setType(NewsType.CODE_EDUCATE));
        mNewsPagerList.add(new NewsFragment().setType(NewsType.CODE_WAR));
        mNewsPagerList.add(new NewsFragment().setType(NewsType.CODE_POLITICS));
        mNewsPagerList.add(new NewsFragment().setType(NewsType.CODE_UNKNOWN));
        mUserInfo = UserInfo.getInstance();
    }

    @Override
    protected void initEvent() {
        viewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        setSupportActionBar(toolbar);
        if (mUserInfo.isLogin()){
            ivUserImage.setOnClickListener(this);
            tvUserName.setText(mUserInfo.getUserName());
        }else{
            tvUserName.setText("请登录");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_user_image:
                startLoginActivity();
                break;
            default:break;
        }
    }

    /**
     * 启动登录界面
     */
    private void startLoginActivity(){
        Intent intent = new Intent(NewsListActivity.this, LoginActivity.class);
        startActivityForResult(intent, IntentExtra.LOGIN_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentExtra.LOGIN_ACTIVITY && resultCode == RESULT_OK){
            //todo 修改头像
            String userName = data.getStringExtra(IntentExtra.USER_NAME);
            if (!TextUtils.isEmpty(userName)){
                tvUserName.setText(userName);
            }else{
                JLog.w("登录成功，未返回用户名");
            }
        }
    }

    private class MyFragmentAdapter extends FragmentPagerAdapter{

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mNewsPagerList.get(position);
        }

        @Override
        public int getCount() {
            return mNewsPagerList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mNewsPagerList.get(position).getTitle();
        }
    }
}
