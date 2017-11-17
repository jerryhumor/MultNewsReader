package com.hdu.jerryhumor.multnewsreader.activity;


import android.content.Intent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.hdu.jerryhumor.multnewsreader.R;
import com.hdu.jerryhumor.multnewsreader.constant.IntentExtra;
import com.hdu.jerryhumor.multnewsreader.constant.NewsApi;
import com.hdu.jerryhumor.multnewsreader.constant.NewsConstant;

public class NewsDetailActivity extends BaseActivity {

    private WebView webView;
    private ProgressBar progressBar;

    private String mUrl;

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
        getNewsDetailUrl();
    }

    @Override
    protected void initEvent() {
        initWebView();
        loadNews(mUrl);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        webView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        webView.onPause();
    }

    //获取前一个页面的url，之后可以还会再传新闻类型，来源等
    private void getNewsDetailUrl(){
        Intent newsInfoIntent = getIntent();
        mUrl = newsInfoIntent.getStringExtra(IntentExtra.URL);
    }

    //初始化 WebView
    private void initWebView(){

    }

    //载入数据
    private void loadNews(String url){
        //todo 子线程获取数据 主线程更新
        showProgressBar();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(1000);

                }catch (Exception e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideProgressBar();
                        String content = NewsApi.TEST_JSON_NEWS_DETAIL;
                        webView.loadData(content, "text/html; charset=utf-8", "utf-8");
                    }
                });
            }
        }).start();

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
