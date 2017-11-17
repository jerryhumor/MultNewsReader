package com.hdu.jerryhumor.multnewsreader.net.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jerryhumor on 2017/11/10.
 */

public class NewsInfoResponse {
    @SerializedName("status")
    private String status;
    @SerializedName("total_row")
    private int totalRow;
    @SerializedName("page_num")
    private int pageNum;
    @SerializedName("page_size")
    private int pageSize;
    @SerializedName("is_first_page")
    private boolean isFirstPage;
    @SerializedName("is_false_page")
    private boolean isLastPge;
    @SerializedName("data")
    private List<NewsInfo> newsInfoList;

    public boolean isSuccess() {
        return "ok".equals(status);
    }

    public List<NewsInfo> getNewsInfoList() {
        return newsInfoList;
    }

    public int getTotalRow() {
        return totalRow;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public boolean isLastPge() {
        return isLastPge;
    }
}
