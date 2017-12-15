package com.hdu.jerryhumor.multnewsreader.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.hdu.jerryhumor.multnewsreader.R;
import com.hdu.jerryhumor.multnewsreader.constant.NewsType;
import com.hdu.jerryhumor.multnewsreader.fragment.NewsFragment;

import java.util.ArrayList;
import java.util.List;

public class NewsListActivity extends BaseActivity{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;

    private List<NewsFragment> mNewsPagerList;

    @Override
    protected int getResourceId() {
        return R.layout.activity_news_list;
    }

    @Override
    protected void initView() {
        tabLayout = findViewById(R.id.tab_layout_type);
        viewPager = findViewById(R.id.view_pager_news);
        toolbar = findViewById(R.id.toolbar);
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
    }

    @Override
    protected void initEvent() {
        viewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        setSupportActionBar(toolbar);
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
