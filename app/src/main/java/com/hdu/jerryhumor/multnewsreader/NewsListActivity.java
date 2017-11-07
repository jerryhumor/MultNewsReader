package com.hdu.jerryhumor.multnewsreader;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;

import com.hdu.jerryhumor.multnewsreader.activity.BaseActivity;
import com.hdu.jerryhumor.multnewsreader.fragment.NewsFragment;

import java.util.ArrayList;
import java.util.List;

public class NewsListActivity extends BaseActivity{

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private List<Fragment> mNewsPagerList;
    private List<String> mNewsTitleList;

    @Override
    protected int getResourceId() {
        return R.layout.activity_news_list;
    }

    @Override
    protected void initView() {
        tabLayout = findViewById(R.id.tab_layout_type);
        viewPager = findViewById(R.id.view_pager_news);
    }

    @Override
    protected void initData() {
        mNewsPagerList = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            mNewsPagerList.add(new NewsFragment());
        }
    }

    @Override
    protected void initEvent() {
        viewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initNewsTitle(){
        mNewsTitleList = new ArrayList<>();


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
            return super.getPageTitle(position);
        }
    }
}
