package com.hdu.jerryhumor.multnewsreader.keep.activity;

import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;

import com.hdu.jerryhumor.multnewsreader.R;
import com.hdu.jerryhumor.multnewsreader.base.BaseActivity;
import com.hdu.jerryhumor.multnewsreader.keep.bean.KeepItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.List;

/**
 * 收藏列表展示的活动
 */
public class KeepNewsListActivity extends BaseActivity{

    private Toolbar toolbar;
    private ProgressBar progressBar;
    private SwipeMenuRecyclerView swipeMenuRecyclerView;

    private List<KeepItem> mKeepList;


    @Override
    protected int getResourceId() {
        return R.layout.activity_keep_news_list;
    }

    @Override
    protected void initView() {
        toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progress_bar);
        swipeMenuRecyclerView = findViewById(R.id.smrv_keep_list);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    
}
