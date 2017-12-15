package com.hdu.jerryhumor.multnewsreader.net.callback;

import com.hdu.jerryhumor.multnewsreader.net.bean.NewsInfo;

import java.util.List;

/**
 * Created by jerryhumor on 2017/11/20.
 */

public interface NewsInfoCallback {

    void onNetworkError();
    void onFailed(String error);
    void onSuccess(boolean isFirstPage, boolean isLastPage, List<NewsInfo> newsInfoList);
}
