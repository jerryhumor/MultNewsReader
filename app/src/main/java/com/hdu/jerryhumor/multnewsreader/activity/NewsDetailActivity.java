package com.hdu.jerryhumor.multnewsreader.activity;


import android.content.Intent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.hdu.jerryhumor.multnewsreader.R;
import com.hdu.jerryhumor.multnewsreader.constant.IntentExtra;
import com.hdu.jerryhumor.multnewsreader.constant.NewsApi;
import com.hdu.jerryhumor.multnewsreader.constant.NewsConstant;
import com.hdu.jerryhumor.multnewsreader.net.NetworkConnector;
import com.hdu.jerryhumor.multnewsreader.net.callback.BaseCallback;
import com.hdu.jerryhumor.multnewsreader.util.ToastUtil;

public class NewsDetailActivity extends BaseActivity {

    private WebView webView;
    private ProgressBar progressBar;

    private NetworkConnector mNetworkConnector;
    private int mNewsId;

    @Override
    protected int getResourceId() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void initView() {
        webView = findViewById(R.id.web_view);
        progressBar = findViewById(R.id.progress_bar);
    }

    @Override
    protected void initData() {
        mNetworkConnector = NetworkConnector.getInstance();
        getNewsDetailUrl();
    }

    @Override
    protected void initEvent() {
        initWebView();
        loadNews(mNewsId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
    }

    //获取前一个页面的url，之后可以还会再传新闻类型，来源等
    private void getNewsDetailUrl(){
        Intent newsInfoIntent = getIntent();
        mNewsId = newsInfoIntent.getIntExtra(IntentExtra.NEWS_ID, 0);
    }

    //初始化 WebView
    private void initWebView(){

    }

    //载入数据
    private void loadNews(int newsId){
        showProgressBar();
        mNetworkConnector.getNewsDetail(newsId, new BaseCallback<String>() {
            @Override
            public void onNetworkError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideProgressBar();
                        ToastUtil.showToast(NewsDetailActivity.this, "网络错误");
                    }
                });
            }

            @Override
            public void onFailed(String error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideProgressBar();
                        ToastUtil.showToast(NewsDetailActivity.this, "获取失败");
                    }
                });
            }

            @Override
            public void onSuccess(final String data) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideProgressBar();
                        webView.loadData(data, "text/html; charset=utf-8", "utf-8");
                    }
                });
            }
        });
    }

    private void showProgressBar(){
        webView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar(){
        webView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }
}
