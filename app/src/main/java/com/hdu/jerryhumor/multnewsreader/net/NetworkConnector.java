package com.hdu.jerryhumor.multnewsreader.net;


import android.util.Log;

import com.google.gson.Gson;
import com.hdu.jerryhumor.multnewsreader.constant.NewsApi;
import com.hdu.jerryhumor.multnewsreader.net.bean.ArticleResponse;
import com.hdu.jerryhumor.multnewsreader.net.bean.NewsInfoResponse;
import com.hdu.jerryhumor.multnewsreader.net.callback.BaseCallback;
import com.hdu.jerryhumor.multnewsreader.net.callback.NewsCallback;
import com.hdu.jerryhumor.multnewsreader.util.JLog;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jerryhumor on 2017/11/9.
 *
 * 网络访问库
 */

public class NetworkConnector {

    private static final String TAG = "NETWORK CONNECTOR";

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static NetworkConnector mNetworkConnector;

    private OkHttpClient mOkHttpClient;
    private Gson mGson;

    private NetworkConnector(){
        mOkHttpClient = new OkHttpClient();
        mGson = new Gson();
    }

    public static NetworkConnector getInstance(){
        if (mNetworkConnector == null){
            mNetworkConnector = new NetworkConnector();
        }
        return mNetworkConnector;
    }

    //获取新闻列表
    public void getNews(int pageNum, int pageSize, long lastUpdateTime, final NewsCallback callback){
        String url = NewsApi.URL_GET_NEWS_LIST + pageNum + "-" + pageSize + "-" + lastUpdateTime;
        Log.i(TAG, "get news, url: " + url);
        Request request = new Request.Builder().url(url).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onNetworkError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                NewsInfoResponse newsInfoResponse = mGson.fromJson(response.body().string(), NewsInfoResponse.class);
                if (newsInfoResponse.isSuccess()){
                    callback.onSuccess(newsInfoResponse.getNewsInfoList(),
                            newsInfoResponse.isFirstPage(),
                            newsInfoResponse.isLastPge());
                } else {
                    callback.onFailed(newsInfoResponse.getError());
                }
            }
        });
        
    }

    public void getNewsDetail(int newsId, final BaseCallback<String> callback){
        String url = NewsApi.URL_GET_NEWS_DETAIL + newsId;
        Log.i(TAG, "get news detail, url: " + url);
        Request request = new Request.Builder().url(url).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onNetworkError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                JLog.i(string);
                ArticleResponse articleResponse = mGson.fromJson(string, ArticleResponse.class);
                if (articleResponse.isSuccess()){
                    callback.onSuccess(articleResponse.getContent());
                }else{
                    callback.onFailed(articleResponse.getError());
                }
            }
        });
    }

}
