package com.hdu.jerryhumor.multnewsreader.net.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jerryhumor on 2017/11/9.
 *
 * 新闻信息类 包括标题 类型 来源 访问地址
 * 用于接受并转换json数据
 */

public class NewsInfo {

    @SerializedName("title")
    private String title;                                       //新闻标题
    @SerializedName("type")
    private int type;                                           //新闻类型
    @SerializedName("source")
    private int source;                                         //新闻来源
    @SerializedName("news_time")
    private long newsTime;                                      //新闻自身时间
    @SerializedName("create_time")
    private long createTime;                                    //新闻爬取时间
    @SerializedName("news_id")
    private int newsId;                                         //新闻ID

    public String getTitle() {
        return title;
    }

    public int getType() {
        return type;
    }

    public int getSource() {
        return source;
    }

    public long getNewsTime() {
        return newsTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public int getNewsId() {
        return newsId;
    }
}
