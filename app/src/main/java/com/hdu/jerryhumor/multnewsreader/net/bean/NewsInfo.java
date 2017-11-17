package com.hdu.jerryhumor.multnewsreader.net.bean;

import com.google.gson.annotations.SerializedName;
import com.hdu.jerryhumor.multnewsreader.constant.NewsConstant;

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

    public int getTypeInt(){
        if ("科技".equals(NewsConstant.TYPE_TECH)){
            return NewsConstant.TYPE_TECH;
        }
        if ("体育".equals(NewsConstant.TYPE_SPORT)){
            return NewsConstant.TYPE_SPORT;
        }
        if ("社会".equals(NewsConstant.TYPE_SOCIAL)){
            return NewsConstant.TYPE_SOCIAL;
        }
        if ("娱乐".equals(NewsConstant.TYPE_FUN)){
            return NewsConstant.TYPE_FUN;
        }
        if ("汽车".equals(NewsConstant.TYPE_CAR)){
            return NewsConstant.TYPE_CAR;
        }
        if ("艺术".equals(NewsConstant.TYPE_ART)){
            return NewsConstant.TYPE_ART;
        }
        if ("金融".equals(NewsConstant.TYPE_FINANCE)){
            return NewsConstant.TYPE_FINANCE;
        }
        if ("教育".equals(NewsConstant.TYPE_EDUCATE)){
            return NewsConstant.TYPE_EDUCATE;
        }
        return NewsConstant.TYPE_UNKNOWN;
    }

    public String getSource() {
        return source;
    }

    public int getSourceInt(){
        if ("网易".equals(source)){
            return NewsConstant.SOURCE_NETEASE;
        }
        if ("新浪".equals(source)){
            return NewsConstant.SOURCE_SINA;
        }
        if ("知乎".equals(source)){
            return NewsConstant.SOURCE_ZHIHU;
        }
        return NewsConstant.SOURCE_UNKNOWN;
    }

    public long getTime() {
        return time;
    }

    public String getUrl() {
        return url;
    }


}
