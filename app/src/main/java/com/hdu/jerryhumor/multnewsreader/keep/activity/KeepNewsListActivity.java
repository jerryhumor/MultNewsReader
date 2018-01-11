package com.hdu.jerryhumor.multnewsreader.keep.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.hdu.jerryhumor.multnewsreader.R;
import com.hdu.jerryhumor.multnewsreader.constant.IntentExtra;
import com.hdu.jerryhumor.multnewsreader.news.activity.NewsDetailActivity;
import com.hdu.jerryhumor.multnewsreader.news.adapter.KeepListAdapter;
import com.hdu.jerryhumor.multnewsreader.base.BaseActivity;
import com.hdu.jerryhumor.multnewsreader.keep.bean.KeepItem;
import com.hdu.jerryhumor.multnewsreader.keep.database.DBHelper;
import com.hdu.jerryhumor.multnewsreader.util.JLog;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
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
                        .setTextColor(Color.parseColor("#ffffff"))
                        .setHeight(ViewGroup.LayoutParams.MATCH_PARENT)
                        .setWidth(getResources().getDimensionPixelSize(R.dimen.swipe_recycler_view_item_width));
                swipeRightMenu.addMenuItem(deleteItem);
            }
        };
    }

    @Override
    protected void initEvent() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initRecyclerView();
        List<KeepItem> items = mDBHelper.getKeepListBy(null, null, null);
        mKeepList.addAll(items);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initRecyclerView(){
        swipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeMenuRecyclerView.setSwipeMenuCreator(mSwipeMenuCreator);
        swipeMenuRecyclerView.setSwipeItemClickListener(new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                KeepItem item = mKeepList.get(position);
                startNewsDetailActivity(item.getNewsId(), item.getTitle(), item.getSource());
            }
        });
        swipeMenuRecyclerView.setSwipeMenuItemClickListener(new SwipeMenuItemClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge) {
                //menuBridge.getPosition() 获取菜单位置
                //menuBridge.getAdapterPosition() 获取点击条目位置
                menuBridge.closeMenu();
                KeepItem item = mKeepList.get(menuBridge.getAdapterPosition());
                removeKeepItem(item.getId());
                mKeepList.remove(menuBridge.getAdapterPosition());
                mAdapter.notifyDataSetChanged();
            }
        });
        swipeMenuRecyclerView.setAdapter(mAdapter);
    }

    private void startNewsDetailActivity(final int newsId, final String title, final int source){
        Intent intent = new Intent(KeepNewsListActivity.this, NewsDetailActivity.class);
        intent.putExtra(IntentExtra.NEWS_ID, newsId);
        intent.putExtra(IntentExtra.NEWS_TITLE, title);
        intent.putExtra(IntentExtra.NEWS_SOURCE, source);
        startActivity(intent);
    }

    private void removeKeepItem(final int id){
        mDBHelper.deleteKeepItem(id);
    }

}
