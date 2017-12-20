package com.hdu.jerryhumor.multnewsreader.net;


import android.util.Log;

import com.google.gson.Gson;
import com.hdu.jerryhumor.multnewsreader.constant.NewsApi;
import com.hdu.jerryhumor.multnewsreader.user.bean.LoginResponse;
import com.hdu.jerryhumor.multnewsreader.net.bean.ArticleResponse;
import com.hdu.jerryhumor.multnewsreader.net.bean.NewsInfoResponse;
import com.hdu.jerryhumor.multnewsreader.base.BaseCallback;
import com.hdu.jerryhumor.multnewsreader.net.callback.NewsCallback;
import com.hdu.jerryhumor.multnewsreader.user.bean.RegisterResponse;
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
    public void getNews(int pageNum, int pageSize, long lastUpdateTime, final int newsType, final NewsCallback callback){
        String url = NewsApi.URL_GET_NEWS_LIST + pageNum + "-" + pageSize + "-" + newsType + "-" + lastUpdateTime;
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

    /**
     * 登录接口
     * @param userName                  用户名
     * @param password                  加密后的密码
     * @param callback
     */
    public void login(final String userName, final String password, final BaseCallback<LoginResponse> callback){
        String url = NewsApi.URL_LOGIN + userName + "-" + password;
        Log.i(TAG, "login, url: " + url);
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
                LoginResponse loginResponse = mGson.fromJson(string, LoginResponse.class);
                if (loginResponse.isSuccess()){
                    callback.onSuccess(loginResponse);
                }else{
                    callback.onFailed(loginResponse.getError());
                }
            }
        });
    }


    /**
     * 注册接口
     * @param userName                  用户名
     * @param password                  加密后的密码
     * @param callback
     */
    public void register(final String account, final String userName, final String password, final BaseCallback<RegisterResponse> callback){
        String url = NewsApi.URL_REGISTER + account + "-" + password + "-" + userName;
        Log.i(TAG, "register, url: " + url);
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
                RegisterResponse registerResponse = mGson.fromJson(string, RegisterResponse.class);
                if (registerResponse.isSuccess()){
                    callback.onSuccess(registerResponse);
                }else{
                    callback.onFailed(registerResponse.getError());
                }
            }
        });
    }

}
