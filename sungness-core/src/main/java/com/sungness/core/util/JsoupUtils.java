package com.sungness.core.util;

import com.sungness.core.crawler.ClientConfigure;
import com.sungness.core.crawler.ClientUserAgent;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * 封装的 Jsoup 工具类,方便文档解析操作
 * Created by wanghongwei on 5/3/16.
 */
public class JsoupUtils {
    /**
     * 从文档中获取指定id的元素的值,如果不存在则返回 null,只用于 input 类的html标签
     * @param element Element 文档对象
     * @param id String 元素id
     * @return String 匹配的元素值(value)
     */
    public static String getValueById(Element element, String id) {
        Element targetElement = element.getElementById(id);
        return targetElement != null ? targetElement.val() : null;
    }

    /**
     * 从文档中获取指定name的元素的值,如果不存在则返回 null,只用于 input 类的html标签
     * @param element Element 文档对象
     * @param name String 元素 name
     * @return String 匹配的元素值(value)
     */
    public static String getInputValueByName(Element element, String name) {
        Elements targetElement = element.select("input[name=" + name + "]");
        return targetElement != null ? targetElement.val() : null;
    }

    /**
     * 从文档中获取指定name的checkbox元素的值,如果不存在则返回 null,只用于 input checkbox html标签
     * @param element Element 文档对象
     * @param name String 元素 name
     * @return String 匹配的元素值(value)
     */
    public static String getCheckedValueByName(Element element, String name) {
        Elements targetElement = element.select("input[name=" + name + "][checked=checked]");
        return targetElement != null ? targetElement.val() : null;
    }

    /**
     * 从文档js代码中获取指定名称的变量后的内容
     * @param element Element 文档对象
     * @param name String js变量名
     * @return String 变量定义的内容
     */
    public static String getJSVariableByName(Element element, String name) {
        Elements scriptElements = element.select("script[type=text/javascript]");
        for (Element jsElement : scriptElements) {
            String value = getJSVariableByName(jsElement.data(), name);
            if (value != null) {
                return value;
            }
        }
        return null;
    }

    /**
     * 从文档js代码中获取指定名称的变量后的内容
     * @param jsContent String js代码字符串
     * @param name String js变量名
     * @return String 变量定义的内容
     */
    public static String getJSVariableByName(String jsContent, String name) {
        String[] jsLines = jsContent.split("\n");
        for (String jsLine: jsLines) {
            String jsLine2 = jsLine.trim();
            if (jsLine2.startsWith("var " + name)) {
                String[] varArray = jsLine2.split("=");
                if (varArray.length > 1) {
                    String json = varArray[1].trim();
                    if (json.endsWith(";")) {
                        return json.substring(0, json.length() - 1);
                    }
                }
            }
        }
        return null;
    }

    public static String getJSFromUrl(String url) throws IOException {
        Connection conn = Jsoup.connect(url);
        conn.userAgent(ClientUserAgent.getChromeUserAgent())
                .timeout(ClientConfigure.TIMEOUT_FIVE_MINUTE)
                .ignoreContentType(true)
                .followRedirects(false);
        Connection.Response response = conn.execute();
        return response.body();
    }
}
