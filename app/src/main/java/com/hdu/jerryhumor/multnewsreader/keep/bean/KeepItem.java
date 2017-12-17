package com.hdu.jerryhumor.multnewsreader.keep.bean;

/**
 * Created by hanjianhao on 2017/12/17.
 *
 * 用户收藏条目bean
 */

public class KeepItem {

    private int id;
    private String title;
    private int source;
    private int newsId;
    private long createTime;

    public KeepItem(final int id, final String title, final int source, final int newsId, final long createTime) {
        this.id = id;
        this.title = title;
        this.source = source;
        this.newsId = newsId;
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getSource() {
        return source;
    }

    public int getNewsId() {
        return newsId;
    }

    public long getCreateTime() {
        return createTime;
    }
}
