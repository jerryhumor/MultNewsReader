package com.hdu.jerryhumor.multnewsreader.push;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jerryhumor on 2017/12/20.
 */

public class PushNewsBean {

    @SerializedName("title")
    private String newsTitle;
    @SerializedName("news_id")
    private int newsId;
    @SerializedName("news_source")
    private int newsSource;

    public String getNewsTitle() {
        return newsTitle;
    }

    public int getNewsId() {
        return newsId;
    }

    public int getNewsSource() {
        return newsSource;
    }
}
