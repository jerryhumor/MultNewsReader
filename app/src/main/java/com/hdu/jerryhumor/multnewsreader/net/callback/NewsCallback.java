package com.hdu.jerryhumor.multnewsreader.net.callback;

import com.hdu.jerryhumor.multnewsreader.net.bean.NewsInfo;

import java.util.List;

/**
 * Created by hanjianhao on 2017/11/21.
 */

public interface NewsCallback {

    void onNetworkError();
    void onFailed(String error);
    void onSuccess(List<NewsInfo> newsInfoList, boolean isFirstPage, boolean isLastPage);
}
