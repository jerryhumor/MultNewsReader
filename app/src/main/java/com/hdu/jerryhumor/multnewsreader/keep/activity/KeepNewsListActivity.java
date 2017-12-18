package com.hdu.jerryhumor.multnewsreader.keep.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.hdu.jerryhumor.multnewsreader.R;
import com.hdu.jerryhumor.multnewsreader.adapter.KeepListAdapter;
import com.hdu.jerryhumor.multnewsreader.base.BaseActivity;
import com.hdu.jerryhumor.multnewsreader.keep.bean.KeepItem;
import com.hdu.jerryhumor.multnewsreader.keep.database.DBHelper;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 收藏列表展示的活动
 */
//todo 没有数据提示空
//todo AsyncTask做数据存取操作
public class KeepNewsListActivity extends BaseActivity{

    private Toolbar toolbar;
    private ProgressBar progressBar;
    private SwipeMenuRecyclerView swipeMenuRecyclerView;

    private List<KeepItem> mKeepList;
    private KeepListAdapter mAdapter;
    private DBHelper mDBHelper;
    private SwipeMenuCreator mSwipeMenuCreator;


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
        mKeepList = new ArrayList<>();
        mDBHelper = new DBHelper(this, null);
        mAdapter = new KeepListAdapter(mKeepList);
        mSwipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(KeepNewsListActivity.this)
                        .setBackground(R.color.colorPrimary)
                        .setText(R.string.cancel_keep_news)
                        .setHeight(ViewGroup.LayoutParams.MATCH_PARENT)
                        .setWidth(getResources().getDimensionPixelSize(R.dimen.swipe_recycler_view_item_width));
                swipeRightMenu.addMenuItem(deleteItem);
            }
        };
    }

    @Override
    protected void initEvent() {
        initRecyclerView();
        List<KeepItem> items = mDBHelper.getKeepListBy(null, null, null);
        mKeepList.addAll(items);
        mAdapter.notifyDataSetChanged();
    }

    private void initRecyclerView(){
        swipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeMenuRecyclerView.setSwipeMenuCreator(mSwipeMenuCreator);
        swipeMenuRecyclerView.setAdapter(mAdapter);
        swipeMenuRecyclerView.setSwipeItemClickListener(new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {

            }
        });
    }
}
