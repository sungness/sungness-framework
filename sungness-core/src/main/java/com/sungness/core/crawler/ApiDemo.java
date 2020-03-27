package com.sungness.core.crawler;

import com.sungness.core.httpclient.HttpClientUtils;
import com.sungness.core.util.DateUtil;
import com.sungness.core.util.GsonUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


/**
 * API测试工具
 * Created by wanghongwei on 03/03/2017.
 */
public class ApiDemo {
    private static final Logger log = LoggerFactory.getLogger(ApiDemo.class);

    public static void main3(String[] args) {
        try {
            log.debug("" + DateUtil.getTimestamp());
            String aesKey = "uNQGUU4OiGGrojx1NPQUzEgVe5SDal6qqdHt0xCJmsv";
            String token = "MTQ4ODc4MjAwMzgyMTo2NWYzODk4NzJkZDRiZmYwZmE2NGQ3NGIwMGVhMGFhMWVhMzdlN2RlN2JjZDRiZGM3YWJjZmNlZTczMzNhODg3OjFfXzU4YjhmZmY3MDAwMTczNmUwMDAwMDAwMV9fYXNkZmprbDIzNGxramFzZWZhMHNkOGZ1YXNkZl9fdU5RR1VVNE9pR0dyb2p4MU5QUVV6RWdWZTVTRGFsNnFxZEh0MHhDSm1zdjphNWQzZGFkZWQ1NGQwZjg2OGY0ZGU4OGI1YThlODJkYTkwZTY0NDdjOTUwMGM3YTg1MzU2NzcwYTkzNTgxZmZlN2MwMjkwZWRmMGMxMjY3NzgzOTRhYjMwMmVlOTkxNmUwNmNkOWU1Nzc0NjQyY2RhZDhhOGQxZjU5ZDQwMzU0MA__";
            log.debug("token=" + token);
            Map<String, String> cookies = new HashMap<>();
            cookies.put("token", token);
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("username", "13426395130");
            dataMap.put("password", "123456");
//            dataMap.put("captcha", "123456");
            dataMap.put("ctype", "1");
            dataMap.put("secret", "asdfjkl234lkjasefa0sd8fuasdf");
            String postData = GsonUtils.toJson(dataMap);
            String getUrl = "http://api.grand.com//v1.0/resource/province";

            Connection conn = Jsoup.connect(getUrl);
            conn.userAgent(ClientUserAgent.getChromeUserAgent())
                    .timeout(ClientConfigure.TIMEOUT_FIVE_MINUTE)
                    .ignoreContentType(true)
                    .cookies(cookies)
                    .followRedirects(false)
                    .method(Connection.Method.GET);
            Connection.Response response = conn.execute();
            log.debug(conn.response().cookie("token"));
            log.debug("status=" + response.statusCode());
            log.debug("status=" + response.statusMessage());
            log.debug(response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            String aesKey = "uNQGUU4OiGGrojx1NPQUzEgVe5SDal6qqdHt0xCJmsv";
            String token = "MTQ4ODc4MjAwMzgyMTo2NWYzODk4NzJkZDRiZmYwZmE2NGQ3NGIwMGVhMGFhMWVhMzdlN2RlN2JjZDRiZGM3YWJjZmNlZTczMzNhODg3OjFfXzU4YjhmZmY3MDAwMTczNmUwMDAwMDAwMV9fYXNkZmprbDIzNGxramFzZWZhMHNkOGZ1YXNkZl9fdU5RR1VVNE9pR0dyb2p4MU5QUVV6RWdWZTVTRGFsNnFxZEh0MHhDSm1zdjphNWQzZGFkZWQ1NGQwZjg2OGY0ZGU4OGI1YThlODJkYTkwZTY0NDdjOTUwMGM3YTg1MzU2NzcwYTkzNTgxZmZlN2MwMjkwZWRmMGMxMjY3NzgzOTRhYjMwMmVlOTkxNmUwNmNkOWU1Nzc0NjQyY2RhZDhhOGQxZjU5ZDQwMzU0MA__";
            log.debug("token=" + token);
            Map<String, String> cookies = new HashMap<>();
            cookies.put("token", token);
            String postData = "{\"provId\":\"100\"}";
            String getUrl = "http://api.grand.com//v1.0/city";


            Connection conn = Jsoup.connect(getUrl);
            conn.userAgent(ClientUserAgent.getChromeUserAgent())
                    .timeout(ClientConfigure.TIMEOUT_FIVE_MINUTE)
                    .ignoreContentType(true)
                    .cookies(cookies)
                    .followRedirects(false)
                    .method(Connection.Method.POST).requestBody(postData);
            Connection.Response response = conn.execute();
            log.debug(conn.response().cookie("token"));
            log.debug("status=" + response.statusCode());
            log.debug("status=" + response.statusMessage());
            log.debug(response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main1(String[] args) {
        try {
            log.debug("" + DateUtil.getTimestamp());
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("username", "13426395130");
            dataMap.put("password", "123456");
//            dataMap.put("captcha", "123456");
            dataMap.put("ctype", "1");
            dataMap.put("secret", "asdfjkl234lkjasefa0sd8fuasdf");
            String postData = GsonUtils.toJson(dataMap);
//            String url = "http://api.grand.com/v1.0/user/register";
            String url = "http://api.grand.com/v1.0/user/login";

            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(HttpClientUtils.buildRequestConfig());
            httpPost.setEntity(HttpClientUtils.createJsonEntity(postData));
            CloseableHttpClient client = HttpClientUtils.buildClient(url);
            CloseableHttpResponse response = client.execute(httpPost);
            log.debug(GsonUtils.toJson(HttpClientUtils.parseContent(response.getEntity())));
            log.debug(GsonUtils.toJson(response.getStatusLine()));
//            log.debug(GsonUtils.toJson(response.getAllHeaders()));
            Header header = response.getFirstHeader("Set-Cookie");
            log.debug(header.getName());
            log.debug(header.getValue());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main2(String[] args) {
        try {
            String aesKey = "yiqeiLxMN9efGTBJqBYE6uxcaXe6TTSy9W1f12TjAXa";
            String token = "MTQ4ODYxMTg4ODkzNTpiMDhmZDBiYmUxNWRkMDUzYTVmYWVmNDA1YjAwYTFjZjVlNzYxMTYyNmVkYmY1OTBkNTRmNjg4MGRmNWJmZWNhOjFfXzU4YjhmZmY3MDAwMTczNmUwMDAwMDAwMV9fYXNkZmprbDIzNGxramFzZWZhMHNkOGZ1YXNkZl9feWlxZWlMeE1OOWVmR1RCSnFCWUU2dXhjYVhlNlRUU3k5VzFmMTJUakFYYTpjZTNiZTlkNDA3Yzk5MjA5MTg4YmY3YTJjODE0MzBiYjA4NWNkY2Q3MzQyMGFmMDQ0Y2I2ZjA1NWJiMjM3YzAzNjM3YzU2NzMxOTRjOTg3MDFkZTE5NGE5ODM3NGY1YTczYTgzYjExNmNlYTJmZTkwY2FmYWEyNTYxMmY5MzU0Zg==";
            String getUrl = "http://api.grand.com//v1.0/resource/province";
            Header header = new BasicHeader("Cookie", "token=\"MTQ4ODYxMTg4ODkzNTpiMDhmZDBiYmUxNWRkMDUzYTVmYWVmNDA1YjAwYTFjZjVlNzYxMTYyNmVkYmY1OTBkNTRmNjg4MGRmNWJmZWNhOjFfXzU4YjhmZmY3MDAwMTczNmUwMDAwMDAwMV9fYXNkZmprbDIzNGxramFzZWZhMHNkOGZ1YXNkZl9feWlxZWlMeE1OOWVmR1RCSnFCWUU2dXhjYVhlNlRUU3k5VzFmMTJUakFYYTpjZTNiZTlkNDA3Yzk5MjA5MTg4YmY3YTJjODE0MzBiYjA4NWNkY2Q3MzQyMGFmMDQ0Y2I2ZjA1NWJiMjM3YzAzNjM3YzU2NzMxOTRjOTg3MDFkZTE5NGE5ODM3NGY1YTczYTgzYjExNmNlYTJmZTkwY2FmYWEyNTYxMmY5MzU0Zg==\"; Version=1; Path=/");

            log.debug("" + DateUtil.getTimestamp());
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("username", "13426395130");
            dataMap.put("password", "123456");
//            dataMap.put("captcha", "123456");
            dataMap.put("ctype", "1");
            dataMap.put("secret", "asdfjkl234lkjasefa0sd8fuasdf");
            HttpGet httpGet = new HttpGet(getUrl);
            httpGet.setConfig(HttpClientUtils.buildRequestConfig());
            httpGet.setHeader(header);
            CloseableHttpClient client = HttpClientUtils.buildClient(getUrl);
            CloseableHttpResponse response = client.execute(httpGet);
            log.debug(GsonUtils.toJson(HttpClientUtils.parseContent(response.getEntity())));
            log.debug(GsonUtils.toJson(response.getStatusLine()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
