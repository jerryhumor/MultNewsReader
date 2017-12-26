package com.hdu.jerryhumor.multnewsreader.news.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.hdu.jerryhumor.multnewsreader.R;
import com.hdu.jerryhumor.multnewsreader.base.BaseFragment;
import com.hdu.jerryhumor.multnewsreader.news.activity.NewsDetailActivity;
import com.hdu.jerryhumor.multnewsreader.news.activity.NewsListActivity;
import com.hdu.jerryhumor.multnewsreader.news.adapter.NewsListAdapter;
import com.hdu.jerryhumor.multnewsreader.constant.IntentExtra;
import com.hdu.jerryhumor.multnewsreader.constant.NewsApi;
import com.hdu.jerryhumor.multnewsreader.constant.NewsType;
import com.hdu.jerryhumor.multnewsreader.exchange.Transformer;
import com.hdu.jerryhumor.multnewsreader.net.NetworkConnector;
import com.hdu.jerryhumor.multnewsreader.net.bean.NewsInfo;
import com.hdu.jerryhumor.multnewsreader.net.bean.NewsInfoResponse;
import com.hdu.jerryhumor.multnewsreader.net.callback.NewsCallback;
import com.hdu.jerryhumor.multnewsreader.recommend.UserProperties;
import com.hdu.jerryhumor.multnewsreader.util.JLog;
import com.hdu.jerryhumor.multnewsreader.util.ToastUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jerryhumor on 2017/11/7.
 *
 * 新闻列表碎片
 */

public class NewsFragment extends BaseFragment {

    private final int DEFAULT_PAGE_SIZE = 10;

    private RecyclerView rvNewsList;
    private SwipeRefreshLayout swipeRefreshLayout;

    private int mType;
    private String mUrl;
    private String mFragmentTitle;
    private long mLastUpdateTime = new Date().getTime() / 1000;                                   //上一次刷新新闻列表时间戳（不是上拉加载 单位秒）

    private NewsListAdapter mAdapter;
    private List<NewsInfo> mNewsInfoList;
    private NewsListActivity mActivity;
    private LinearLayoutManager mLinearLayoutManager;
    private NetworkConnector mNetworkConnector;
    private UserProperties mUserProperties;
    private int mCurrentPage = 1;
    private boolean mIsLastPage = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewsInfoList = new ArrayList<>();
        mNetworkConnector = NetworkConnector.getInstance();
    }

    @Override
    protected int getResourceId() {
        return R.layout.fragment_news_pager;
    }

    @Override
    protected void initView(View view) {
        rvNewsList = view.findViewById(R.id.rv_news_list);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
    }

    @Override
    protected void initData() {
        mAdapter = new NewsListAdapter(mNewsInfoList);
        mActivity = (NewsListActivity) getActivity();
        mUserProperties = new UserProperties(mActivity);
//        generateTestData();
    }

    @Override
    protected void initEvent() {
        initRecyclerView();
        initSwipeRefreshLayout();
        solveRvSrlConflict();
    }

    @Override
    public void onResume() {
        super.onResume();
        JLog.i("视图可见");
        if (!hasData()){
            JLog.i("没有数据，自动加载");
            swipeRefreshLayout.setRefreshing(true);
            loadDataFromNet(mCurrentPage, DEFAULT_PAGE_SIZE, mLastUpdateTime);
        }else{
            JLog.i("有缓存数据，不加载新闻");
        }
    }

    private boolean hasData() {
        boolean hasData = true;
        if (mNewsInfoList == null){
            JLog.i("新闻列表为空指针");
            hasData = false;
        }else{
            if (mNewsInfoList.size() == 0){
                hasData = false;
                JLog.i("新闻列表数量为0");
            }
        }
        return hasData;
    }

    /**
     * 设置页面的类型，同时设置访问地址等
     * @param fragmentType
     * @return
     */
    public NewsFragment setType(int fragmentType){
        this.mType = fragmentType;
        this.mUrl = Transformer.getUrl(fragmentType);
        this.mFragmentTitle = Transformer.getTypeName(fragmentType);
        return this;
    }

    private void initRecyclerView(){
        mLinearLayoutManager = new LinearLayoutManager(mActivity);
        mAdapter.setOnItemViewClickListener(new NewsListAdapter.OnItemClickListener() {
            @Override
            public void onClickItem(int position) {
                NewsInfo newsInfo = mNewsInfoList.get(position);
                mUserProperties.addProperties(newsInfo.getType());
                startNewsDetailActivity(newsInfo.getNewsId(), newsInfo.getTitle(), newsInfo.getType());
            }
        });
        rvNewsList.setLayoutManager(mLinearLayoutManager);
        rvNewsList.setAdapter(mAdapter);
        //添加上拉加载逻辑
        rvNewsList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = mLinearLayoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == mAdapter.getItemCount()) {
                    loadDataFromNet(++mCurrentPage, DEFAULT_PAGE_SIZE, mLastUpdateTime);
                }
            }
        });
    }

    private void generateTestData(){
        Gson gson = new Gson();
        NewsInfoResponse response = gson.fromJson(NewsApi.TEST_JSON_NEWS_INFO, NewsInfoResponse.class);
        mNewsInfoList.addAll(response.getNewsInfoList());
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
                resetPage();
                loadDataFromNet(mCurrentPage, DEFAULT_PAGE_SIZE, mLastUpdateTime);
            }
        });
    }

    /**
     * 联网获取数据
     * @param pageNum       新闻页数
     * @param pageSize      一次加载的新闻数量
     */
    private void loadDataFromNet(int pageNum, int pageSize, long lastUpdateTime){
        JLog.i("联网获取数据");
        if (mIsLastPage){
            ToastUtil.showToast(mActivity, "没有更多信息");
            swipeRefreshLayout.setRefreshing(false);
        }else{
            mNetworkConnector.getNews(pageNum, pageSize, lastUpdateTime, mType, new NewsCallback() {
                @Override
                public void onNetworkError() {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                            ToastUtil.showToast(mActivity, "网络错误");
                            JLog.w("net work error");
                        }
                    });
                }

                @Override
                public void onFailed(String error) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                            ToastUtil.showToast(mActivity, "访问失败");
                            JLog.w("get data error");
                        }
                    });
                }

                @Override
                public void onSuccess(List<NewsInfo> newsInfoList, boolean isFirstPage, boolean isLastPage) {
                    mIsLastPage = isLastPage;

                    //下拉刷新 数据重置
                    if (isFirstPage)
                        mNewsInfoList.clear();

                    //按照用户喜好排序
//                    List<NewsInfo> sortedNewsInfo = null;
//                    if (isRecommendFragment()){
//                        sortedNewsInfo = sortByUserProperties(newsInfoList);
//                    }else{
//                        sortedNewsInfo = newsInfoList;
//                    }

                    //添加数据
//                    for (NewsInfo info : sortedNewsInfo){
//                        mNewsInfoList.add(info);
//                    }
                    mNewsInfoList.addAll(newsInfoList);

                    //主线程更新数据
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                }
            });
        }
    }

    /**
     * 根据用户喜好排序新闻
     * @param newsInfoList
     * @return
     */
    private List<NewsInfo> sortByUserProperties(List<NewsInfo> newsInfoList){
        List<Integer> userPropertiesList = mUserProperties.getUserPropertiesListSorted();
        List<NewsInfo> sortedNewsList = new ArrayList<>();
        for (int property : userPropertiesList){
            for (NewsInfo info : newsInfoList){
                //todo 优化
                if (property == info.getType()){
                    sortedNewsList.add(info);
                }
            }
        }
        return sortedNewsList;
    }

    /**
     * 判断是否是推荐新闻页面
     * @return
     */
    private boolean isRecommendFragment(){
        return mType == NewsType.CODE_LATEST;
    }

    /**
     * 跳转到详细新闻页
     * @param newsId
     */
    private void startNewsDetailActivity(final int newsId, final String title, final int type){
        Intent intent = new Intent(mActivity, NewsDetailActivity.class);
        intent.putExtra(IntentExtra.NEWS_ID, newsId);
        intent.putExtra(IntentExtra.NEWS_TITLE, title);
        intent.putExtra(IntentExtra.NEWS_TYPE, type);
        startActivity(intent);
    }

    private void resetPage(){
        mIsLastPage = false;
        mCurrentPage = 1;
        mLastUpdateTime = new Date().getTime() / 1000;
    }

    public String getTitle(){
        return mFragmentTitle;
    }

}
