package com.hdu.jerryhumor.multnewsreader.net.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jerryhumor on 2017/11/9.
 *
 * 新闻信息类 包括标题 类型 来源 访问地址
 */

public class NewsInfo {

    @SerializedName("title")
    private String title;                                       //新闻标题
    @SerializedName("type")
    private String type;                                        //新闻类型
    @SerializedName("source")
    private String source;                                      //新闻来源
    @SerializedName("time")
    private long time;                                          //新闻时间
    @SerializedName("url")
    private String url;                                         //新闻访问地址

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getSource() {
        return source;
    }

    public long getTime() {
        return time;
    }

    public String getUrl() {
        return url;
    }
}
