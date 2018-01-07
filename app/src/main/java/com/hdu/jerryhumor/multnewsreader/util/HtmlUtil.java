package com.hdu.jerryhumor.multnewsreader.util;

import com.hdu.jerryhumor.multnewsreader.constant.NewsSource;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
                newsHtml = handleNeteaseNews(newsHtml);
                break;
            case NewsSource.CODE_SINA:
                newsHtml = handleSinaNews(newsHtml);
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
        Document document = Jsoup.parse(body);
        Elements imgElements = document.getElementsByTag("img");
        if (imgElements != null && imgElements.size() > 0){
            for (Element img : imgElements){
                img.removeAttr("width");
                img.removeAttr("height");
                String imgUrl = img.attr("data-src");
                imgUrl = imgUrl.substring(2, imgUrl.length());
                imgUrl = "http://" + imgUrl;
                img.removeAttr("data-src");
                img.removeAttr("alt");
                img.attr("src", imgUrl);
            }
        }
        Elements videoElements = document.getElementsByTag("video");
        if (videoElements != null && videoElements.size() > 0){
            for (Element videoElement : videoElements){
                String videoUrl = videoElement.attr("data-src");
                videoElement.removeAttr("data-src");
                videoElement.attr("src", videoUrl);
            }
        }
        return document.toString();
    }

    private static String handleSinaNews(final String body){
        Document document = Jsoup.parse(body);
        Elements followElements = document.getElementsByClass("follow");
        if (followElements != null && followElements.size() > 0){
            for (Element followElement : followElements){
                followElement.remove();
            }
        }
        Elements imgElements = document.getElementsByTag("img");
        if (imgElements != null && imgElements.size() > 0){
            for (Element img : imgElements){
                img.removeAttr("width");
                img.removeAttr("height");
                String imgUrl = img.attr("src");
                imgUrl = "http:" + imgUrl;
                img.removeAttr("src");
                img.attr("src", imgUrl);
            }
        }
        return document.toString();
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
        stringBuffer.append("<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"css/detail.css\" ></head><body>");
        stringBuffer.append(body);
        stringBuffer.append("</body></html>");
        return stringBuffer.toString();
    }
}
