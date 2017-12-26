package com.hdu.jerryhumor.multnewsreader.util;

/**
 * Created by hjhji on 2017/12/24.
 *
 * 生成html文档 加入css js样式
 */

public class HtmlUtil {

    public static StringBuffer handleHtml(String body){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"css/detail.css\" ></head>");
        stringBuffer.append(body);
        stringBuffer.append("</html>");
        return stringBuffer;
    }
}
