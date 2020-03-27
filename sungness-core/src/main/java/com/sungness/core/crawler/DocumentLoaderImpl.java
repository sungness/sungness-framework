package com.sungness.core.crawler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;


/**
 * 页面加载接口实现类
 *
 * 根据给定的url加载页面内容
 * @author wanghongwei
 */
public class DocumentLoaderImpl implements DocumentLoader {
	/** 程序处理日志记录对象 */
	private final Log logger = LogFactory.getLog(this.getClass().getSimpleName());

	private int timeOutMillis;

	public DocumentLoaderImpl() {
		this.timeOutMillis = ClientConfigure.TIMEOUT_FIVE_MINUTE;
	}

	public void setTimeOutMillis(int timeOutMillis) {
		this.timeOutMillis = timeOutMillis;
	}

	public int getTimeOutMillis() {
		return timeOutMillis;
	}

	/**
	 * 读取目标URL地址的页面内容，返回Document对象
	 * @param url String 目标url地址
	 * @return Document 页面文档对象，如果读取失败返回null，
	 * 		错误原因可查看异常或logger日志文件。
	 */
	@Override
	public Document getDocument(String url) throws IOException {
		try {
			return Jsoup.connect(url)
                    .userAgent(ClientUserAgent.getRandomUserAgent())
                    .timeout(timeOutMillis)
                    .get();
		} catch (IOException e) {
			logger.error("获取文档失败，url地址：" + url + "，错误原因：" + e.getMessage());
			throw e;
		}
	}

	@Override
	public Document getDocument(String url, Map<String, String> params) throws IOException {
		try {
			return Jsoup.connect(url)
                    .userAgent(ClientUserAgent.getRandomUserAgent())
                    .timeout(timeOutMillis)
                    .data(params)
                    .post();
		} catch (IOException e) {
			logger.error("获取文档失败，url地址：" + url + "，错误原因：" + e.getMessage());
			throw e;
		}
	}

    /**
     * 读取目标URL地址的页面内容，返回Document对象
     *
     * @param url     String 目标url地址
     * @param params  Map<String, String> POST方式提交的参数map
     * @param cookies
     * @return Document 页面文档对象，如果读取失败抛出IOException，
     * 错误原因可查看异常或logger日志文件。
     */
    @Override
    public Document getDocument(String url, Map<String, String> params,
                                Map<String, String> cookies) throws IOException {
        try {
            return Jsoup.connect(url)
                    .followRedirects(true)
                    .ignoreContentType(true)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36")
                    .timeout(timeOutMillis)
                    .data(params)
                    .cookies(cookies)
                    .referrer("http://218.206.191.22/prm/portal/login_inner.jsp")
                    .post();
        } catch (IOException e) {
            logger.error("获取文档失败，url地址：" + url + "，错误原因：" + e.getMessage());
            throw e;
        }
    }

    @Override
    public String getJson(String url, Map<String, String> params,
                          Map<String, String> cookies, String referer) throws IOException {
        try {
            return Jsoup.connect(url)
                    .followRedirects(true)
                    .ignoreContentType(true)
                    .userAgent(ClientUserAgent.getChromeUserAgent())
                    .timeout(timeOutMillis)
                    .data(params)
                    .method(Connection.Method.POST)
                    .cookies(cookies)
                    .referrer(referer)
                    .execute().body();
        } catch (IOException e) {
            logger.error("获取文档失败，url地址：" + url + "，错误原因：" + e.getMessage());
            throw e;
        }
    }

    @Override
	public String getJson(String url) throws IOException {
		try {
			return Jsoup.connect(url)
					.userAgent(ClientUserAgent.getRandomUserAgent())
					.timeout(timeOutMillis)
					.ignoreContentType(true)
					.execute().body();
		} catch (IOException e) {
			logger.error("获取文档失败，url地址：" + url + "，错误原因：" + e.getMessage());
			throw e;
		}
	}


	public static void main(String[] argv) {
	}

}
