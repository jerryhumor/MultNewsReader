package com.hdu.jerryhumor.multnewsreader.base;

/**
 * Created by jerryhumor on 2017/11/9.
 */

public interface BaseCallback<T> {
    void onNetworkError(Exception e);
    void onFailed(String error);
    void onSuccess(T data);
}
