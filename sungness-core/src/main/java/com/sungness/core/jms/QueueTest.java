package com.sungness.core.jms;

import com.sungness.core.crawler.ClientConfigure;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

/**
 * test
 * Created by wanghongwei on 6/24/16.
 */
public class QueueTest {
    private static final Logger log = LoggerFactory.getLogger(QueueTest.class);

    public static void main(String[] args) {
        try {
//            ApplicationContext context =
//                    new ClassPathXmlApplicationContext(
//                            "conf/spring-activemq.xml");
//            int i = 0;
//            while (true) {
//                log.debug("QueueTest ...");
//                i++;
//                Thread.sleep(200000);
//                if (i > 100) {
//                    break;
//                }
//            }
            String queuesURL = "http://127.0.0.1:8161/admin/xml/queues.jsp";
            String authorization = "admin:admin";
            Document document =
                    Jsoup.connect(queuesURL)
                            .ignoreContentType(true)
                            .timeout(ClientConfigure.TIMEOUT_FIVE_MINUTE)
                            .header("Authorization", "Basic " + Base64Utils.encodeToString(authorization.getBytes())).get();
            Elements elements = document.select("queue[name=Queue.crawler] > stats");
            log.debug(elements.attr("size"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
