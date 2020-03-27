package com.sungness.core.crawler;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;


/**
 * 页面加载接口类
 *
 * 根据给定的url加载页面内容
 * @author wanghongwei
 */
public interface DocumentLoader {

	/**
	 * 读取目标URL地址的页面内容，返回Document对象
	 * @param url String 目标url地址
	 * @return Document 页面文档对象，如果读取失败抛出IOException，
	 * 		错误原因可查看异常或logger日志文件。
	 */
	Document getDocument(String url) throws IOException;

	/**
	 * 读取目标URL地址的页面内容，返回Document对象
	 * @param url String 目标url地址
	 * @param params Map<String, String> POST方式提交的参数map
	 * @return Document 页面文档对象，如果读取失败抛出IOException，
	 * 		错误原因可查看异常或logger日志文件。
	 */
	Document getDocument(String url, Map<String, String> params) throws IOException;

    /**
     * 读取目标URL地址的页面内容，返回Document对象
     * @param url String 目标url地址
     * @param params Map<String, String> POST方式提交的参数map
     * @param cookies Map<String, String> 提交的cookie map
     * @return Document 页面文档对象，如果读取失败抛出IOException，
     * 		错误原因可查看异常或logger日志文件。
     */
    Document getDocument(String url, Map<String, String> params,
                         Map<String, String> cookies) throws IOException;

    /**
     * 读取目标URL地址的页面内容，返回body
     * @param url String 请求的url地址
     * @param params Map<String, String> post参数集合
     * @param cookies Map<String, String> cookie集合
     * @param referer String 源页面
     * @return String 返回结果
     * @throws IOException
     */
    String getJson(String url, Map<String, String> params,
                   Map<String, String> cookies, String referer) throws IOException;

    /**
	 * 读取目标URL地址的内容，返回json字符串
	 * @param url String 目标url地址
	 * @return String json字符串，如果读取失败抛出IOException，
	 * 		错误原因可查看异常或logger日志文件。
	 */
	String getJson(String url) throws IOException;
}
