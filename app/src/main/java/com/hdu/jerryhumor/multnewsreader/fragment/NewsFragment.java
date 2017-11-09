package com.hdu.jerryhumor.multnewsreader.fragment;

import android.view.View;

import com.hdu.jerryhumor.multnewsreader.R;

/**
 * Created by jerryhumor on 2017/11/7.
 *
 * 新闻列表碎片
 */

public class NewsFragment extends BaseFragment{

    private String mUrl;

    @Override
    int getResourceId() {
        return R.layout.fragment_news_pager;
    }

    @Override
    void initData() {

    }

    @Override
    void initView(View view) {

    }

    @Override
    void initEvent() {

    }

    public void setUrl(String url){
        mUrl = url;
    }
}
