package com.hdu.jerryhumor.multnewsreader.net.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jerryhumor on 2017/11/10.
 */

public class NewsInfoResponse {
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private List<NewsInfo> newsInfoList;

    public String getStatus() {
        return status;
    }

    public List<NewsInfo> getNewsInfoList() {
        return newsInfoList;
    }
}
