package com.hdu.jerryhumor.multnewsreader.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.hdu.jerryhumor.multnewsreader.R;
import com.hdu.jerryhumor.multnewsreader.activity.NewsListActivity;
import com.hdu.jerryhumor.multnewsreader.adapter.NewsListAdapter;
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
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
    }

    @Override
    void initEvent() {
        initRecyclerView();
    }

    public void setUrl(String url){
        mUrl = url;
    }

    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        rvNewsList.setLayoutManager(linearLayoutManager);
        rvNewsList.setAdapter(mAdapter);
    }

    private void generateTestData(){
        Gson gson = new Gson();
        NewsInfoResponse response = gson.fromJson(NewsApi.TEST_JSON_NEWS_INFO, NewsInfoResponse.class);
        mNewsInfoList = response.getNewsInfoList();
    }
}
