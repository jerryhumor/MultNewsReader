package com.hdu.jerryhumor.multnewsreader.net.bean;

/**
 * Created by jerryhumor on 2017/11/15.
 */

public class NewsInfoRequest {

    private int page_num;
    private int page_size;

    public NewsInfoRequest(int pageNum, int pageSize){
        this.page_num = pageNum;
        this.page_size = pageSize;
    }

}
