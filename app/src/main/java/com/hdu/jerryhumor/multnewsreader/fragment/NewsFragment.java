package com.hdu.jerryhumor.multnewsreader.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.hdu.jerryhumor.multnewsreader.R;
import com.hdu.jerryhumor.multnewsreader.activity.NewsDetailActivity;
import com.hdu.jerryhumor.multnewsreader.activity.NewsListActivity;
import com.hdu.jerryhumor.multnewsreader.adapter.NewsListAdapter;
import com.hdu.jerryhumor.multnewsreader.constant.IntentExtra;
import com.hdu.jerryhumor.multnewsreader.constant.NewsApi;
import com.hdu.jerryhumor.multnewsreader.net.bean.NewsInfo;
import com.hdu.jerryhumor.multnewsreader.net.bean.NewsInfoResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jerryhumor on 2017/11/7.
 *
 * 新闻列表碎片
 */

public class NewsFragment extends BaseFragment{

    private RecyclerView rvNewsList;
    private SwipeRefreshLayout swipeRefreshLayout;

    private NewsListAdapter mAdapter;
    private String mUrl;
    private List<NewsInfo> mNewsInfoList;
    private NewsListActivity mActivity;
    private LinearLayoutManager mLinearLayoutManager;
    private int mCurrentPage = 1;
    private final int DEFAULT_PAGE_SIZE = 10;

    @Override
    int getResourceId() {
        return R.layout.fragment_news_pager;
    }

    @Override
    void initView(View view) {
        rvNewsList = view.findViewById(R.id.rv_news_list);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
    }

    @Override
    void initData() {
        mNewsInfoList = new ArrayList<>();
        generateTestData();
        mAdapter = new NewsListAdapter(mNewsInfoList);
        mActivity = (NewsListActivity) getActivity();
    }

    @Override
    void initEvent() {
        initRecyclerView();
        initSwipeRefreshLayout();
        solveRvSrlConflict();
    }

    //设置页面访问的地址
    public void setUrl(String url){
        mUrl = url;
    }

    private void initRecyclerView(){
        mLinearLayoutManager = new LinearLayoutManager(mActivity);
        mAdapter.setOnItemViewClickListener(new NewsListAdapter.OnItemClickListener() {
            @Override
            public void onClickItem(int position) {
                startNewsDetailActivity(mNewsInfoList.get(position).getUrl());
            }
        });
        rvNewsList.setLayoutManager(mLinearLayoutManager);
        rvNewsList.setAdapter(mAdapter);
        rvNewsList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = mLinearLayoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == mAdapter.getItemCount()) {
                    //todo 联网获取下一批数据
                }
            }
        });
    }

    private void generateTestData(){
        Gson gson = new Gson();
        NewsInfoResponse response = gson.fromJson(NewsApi.TEST_JSON_NEWS_INFO, NewsInfoResponse.class);
        mNewsInfoList = response.getNewsInfoList();
    }

    //用于解决 RecyclerView 和 SwipeRefreshLayout 滑动冲突
    private void solveRvSrlConflict(){
        rvNewsList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                swipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
            }
        });
    }

    //初始化下拉刷新控件
    private void initSwipeRefreshLayout(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //todo 下拉刷新数据
            }
        });
    }

    //联网获取数据
    private void loadDataFromNet(int pageNum, int pageSize, String token){

    }

    private void startNewsDetailActivity(String newsUrl){
        Intent intent = new Intent(mActivity, NewsDetailActivity.class);
        intent.putExtra(IntentExtra.URL, newsUrl);
        startActivity(intent);
    }

}
