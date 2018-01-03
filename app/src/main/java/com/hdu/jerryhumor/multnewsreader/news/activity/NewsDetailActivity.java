package com.hdu.jerryhumor.multnewsreader.news.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.hdu.jerryhumor.multnewsreader.R;
import com.hdu.jerryhumor.multnewsreader.base.BaseActivity;
import com.hdu.jerryhumor.multnewsreader.constant.IntentExtra;
import com.hdu.jerryhumor.multnewsreader.constant.NewsApi;
import com.hdu.jerryhumor.multnewsreader.keep.database.DBHelper;
import com.hdu.jerryhumor.multnewsreader.net.NetworkConnector;
import com.hdu.jerryhumor.multnewsreader.base.BaseCallback;
import com.hdu.jerryhumor.multnewsreader.user.UserInfo;
import com.hdu.jerryhumor.multnewsreader.util.JLog;
import com.hdu.jerryhumor.multnewsreader.util.ToastUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.List;

public class NewsDetailActivity extends BaseActivity {

    private WebView webView;
    private ProgressBar progressBar;
    private Toolbar toolbar;

    private NetworkConnector mNetworkConnector;
    private DBHelper mDBHelper;
    private int mNewsId, mNewsType;
    private String mTitle;
    private int mSource;

    @Override
    protected int getResourceId() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void initView() {
        webView = findViewById(R.id.web_view);
        progressBar = findViewById(R.id.progress_bar);
        toolbar = findViewById(R.id.toolbar);
    }

    @Override
    protected void initData() {
        mNetworkConnector = NetworkConnector.getInstance();
        mDBHelper = new DBHelper(this, null);
        getNewsDetailUrl();
    }

    @Override
    protected void initEvent() {
        initWebView();
        initToolbar();
        loadNews(mNewsId, mNewsType, UserInfo.getInstance().getUserAccount());
//        loadFromLocal();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_keep:
                keepArticle();
                break;
            default:
                break;
        }
        return true;
    }

    //获取前一个页面的url，之后可以还会再传新闻类型，来源等
    private void getNewsDetailUrl(){
        Intent newsInfoIntent = getIntent();
        mNewsId = newsInfoIntent.getIntExtra(IntentExtra.NEWS_ID, 0);
        mTitle = newsInfoIntent.getStringExtra(IntentExtra.NEWS_TITLE);
        mSource = newsInfoIntent.getIntExtra(IntentExtra.NEWS_SOURCE, 0);
        mNewsType = newsInfoIntent.getIntExtra(IntentExtra.NEWS_TYPE, 1);

        JLog.i("news detail, news id: " + mNewsId + ", title: " + mTitle + ", source: " + mSource);
    }

    //初始化 WebView
    private void initWebView(){

    }

    //初始化Toolbar
    private void initToolbar(){
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //载入数据
    private void loadNews(int newsId, int newsType, String userAccount){
        showProgressBar();
        mNetworkConnector.getNewsDetail(newsId, newsType, userAccount, new BaseCallback<String>() {
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
                        loadHtml(data);
                    }
                });
            }
        });
    }

    //导入服务器获取的html文档
    private void loadHtml(final String data) {
        String html = processData(data);
        webView.loadData(html, "text/html; charset=utf-8", "utf-8");
    }

    private String processData(final String data) {
        String processedData = data.replace("\\\"", "\"").replace("//", "http:\\\\").replace("data-src", "src");
//        Document document = Jsoup.parse(processedData);
//        List<Element> imgElements = document.getElementsByTag("img");
//        if (imgElements != null && imgElements.size() > 0){
//            for (Element imgElement : imgElements){
//            }
//        }
//        JLog.i(document.body().toString());
        return processedData;
    }

    private void keepArticle(){
        if (mDBHelper.insertKeepItem(mTitle, mSource, mNewsId) < 1){
            showToast("已收藏");
        }else{
            showToast("收藏成功");
        }
    }

    private void loadFromLocal(){
        hideProgressBar();
        webView.loadData(NewsApi.TEST_JSON_NEWS_DETAIL, "text/html; charset=utf-8", "utf-8");
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
