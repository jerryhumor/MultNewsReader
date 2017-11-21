package com.hdu.jerryhumor.multnewsreader.net.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hanjianhao on 2017/11/21.
 */

public class ArticleResponse {
    @SerializedName("status")
    private String status;
    @SerializedName("error")
    private String error;
    @SerializedName("content")
    private String content;

    public boolean isSuccess(){
        return "ok".equals(status);
    }

    public String getError(){
        return error;
    }

    public String getContent() {
        return content;
    }
}
