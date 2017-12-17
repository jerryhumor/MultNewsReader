package com.hdu.jerryhumor.multnewsreader.keep.activity;

import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;

import com.hdu.jerryhumor.multnewsreader.R;
import com.hdu.jerryhumor.multnewsreader.base.BaseActivity;

/**
 * 收藏列表展示的活动
 */
public class KeepNewsListActivity extends BaseActivity{

    private Toolbar toolbar;
    private ProgressBar progressBar;



    @Override
    protected int getResourceId() {
        return R.layout.activity_keep_news_list;
    }

    @Override
    protected void initView() {
        toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progress_bar);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }
}
