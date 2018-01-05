package com.hdu.jerryhumor.multnewsreader.util;

import com.hdu.jerryhumor.multnewsreader.constant.NewsSource;

/**
 * Created by hjhji on 2017/12/24.
 *
 * 生成html文档 加入css js样式
 */

public class HtmlUtil {

    public static String handleHtml(final String body, final int newsSource){
        String newsHtml = replaceQuotes(body);
        newsHtml = addHtmlFramework(newsHtml);
        switch (newsSource){
            case NewsSource.CODE_NETEASE:
                newsHtml = handleNeteaseNews(body);
                break;
            case NewsSource.CODE_SINA:
                newsHtml = handleSinaNews(body);
                break;
            case NewsSource.CODE_ZHIHU:
//                newsHtml = handleZhihuNews(body);
                break;
            default:
                JLog.e("处理新闻数据，新闻源未知");
                break;
        }
        return newsHtml;
    }

    private static String handleZhihuNews(final String body){
        return body;
    }

    private static String handleNeteaseNews(final String body){
        //todo 处理网易原新闻
        return body;
    }

    private static String handleSinaNews(final String body){
        //todo 处理新浪原新闻
        return body;
    }

    /**
     * 替换原新闻中为了传输时的转换符
     * @param body
     * @return
     */
    private static String replaceQuotes(final String body){
        return body.replace("\\\"", "\"");
    }

    /**
     * 为新闻主题添加html标签
     * @param body
     * @return
     */
    private static String addHtmlFramework(final String body){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"css/detail.css\" ></head>");
        stringBuffer.append(body);
        stringBuffer.append("</html>");
        return stringBuffer.toString();
    }
}
