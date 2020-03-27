package com.sungness.core.jms;

import com.sungness.core.crawler.ClientConfigure;
import com.sungness.core.util.tools.IntegerTools;
import org.apache.poi.ss.formula.functions.T;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.GenericTypeResolver;
import org.springframework.util.Base64Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ActiveMQ 队列监控
 * 调用ActiveMQ管理界面接口,获取指定队列的当前size--待决消息数量
 * Created by wanghongwei on 6/26/16.
 */
public class QueueStats {
    private static final Logger log = LoggerFactory.getLogger(QueueStats.class);

    protected String queuesURL;

    protected String authorization;

    public void setQueuesURL(String queuesURL) {
        this.queuesURL = queuesURL;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    /**
     * 从队列列表中解析指定队列名称的待处理消息数
     * @param name String 队列名称
     * @return int 消息数
     * @throws Exception
     */
    public int sizeOfQueue(String name) throws Exception {
        Document document =
                Jsoup.connect(queuesURL)
                        .ignoreContentType(true)
                        .timeout(ClientConfigure.TIMEOUT_FIVE_MINUTE)
                        .header("Authorization", "Basic " + getBase64())
                        .get();
//        log.debug(document.toString());
        Elements elements = document.select("queue[name=" + name + "] > stats");
        log.debug(elements.toString());
        if (elements != null) {
            return IntegerTools.parse(elements.attr("size"));
        }
        throw new Exception("Queue with name " + name + " not exist:" + document.toString());
    }

    private String getBase64() {
        return Base64Utils.encodeToString(authorization.getBytes());
    }

    public static boolean get(Object filterObj, Class<?> classOfT) {
        classOfT.isInstance(filterObj);
        if (classOfT.isInstance(filterObj)) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Map<String, Object> m = new HashMap<>();
        m.put("a", "test");
        List<Long> bList = new ArrayList<>();
        bList.add(1L);
        m.put("b", bList);
        Object aObj = m.get("a");
        System.out.println(aObj);
        System.out.println(aObj.getClass());
        Object bObj = m.get("b");
        System.out.println(bObj);
        System.out.println(bObj.getClass());

        if (get(aObj, String.class)) {
            System.out.println("aObj type is String");
        } else {
            System.out.println("aObj type is not String");
        }
        if (get(bObj, String.class)) {
            System.out.println("bObj type is String");
        } else {
            System.out.println("bObj type is not String");
        }
        if (get(bObj, List.class)) {
            System.out.println("bObj type is List");
        }
    }
}
