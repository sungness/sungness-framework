package com.sungness.core.httpclient;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * HTTPS链接请求处理类
 *     在HttpComponents API接口基础上封装HTTPS请求处理工具类
 * Created by wanghongwei on 5/21/15.
 */
public class HttpClientUtils {
    private static final Logger log = LoggerFactory.getLogger(HttpClientUtils.class);

    /**
     * 根据URL地址获取回应内容字符串，以get方式请求
     * @param url String 要请求得目标URL地址 http https
     * @return String 回应内容字符串
     * @throws HttpClientException 如果请求失败，将抛出该异常
     */
    public static String getString(String url, Header[] headers) throws HttpClientException {
        return parseContent(getEntity(url, headers));
    }

    /**
     * 根据URL地址获取回应内容字符串，以get方式请求
     * @param url String 要请求得目标URL地址 http https
     * @return String 回应内容字符串
     * @throws HttpClientException 如果请求失败，将抛出该异常
     */
    public static String getString(String url) throws HttpClientException {
        return getString(url, null);
    }

    /**
     * 根据URL地址获取回应内容实体，以get方式请求
     * @param url String 要请求得目标URL地址 http https
     * @return HttpEntity 回应内容实体
     * @throws HttpClientException 如果请求失败，将抛出该异常
     */
    public static HttpEntity getEntity(String url) throws HttpClientException {
        return getEntity(url, null);
    }

    /**
     * 根据URL地址获取回应内容实体，以get方式请求
     * @param url String 要请求得目标URL地址 http https
     * @return HttpEntity 回应内容实体
     * @throws HttpClientException 如果请求失败，将抛出该异常
     */
    public static HttpEntity getEntity(String url, Header[] headers) throws HttpClientException {
        try {
            return parseEntity(getResponse(url, headers));
        } catch (HttpClientException hce) {
            throw hce;
        } catch (Exception e) {
            throw new HttpClientException("HTTPS Get request failed！", e);
        }
    }

    /**
     * 以get方式请求URL，返回CloseableHttpResponse
     * @param url String URL地址
     * @return CloseableHttpResponse
     * @throws HttpClientException
     */
    public static CloseableHttpResponse getResponse(String url) throws HttpClientException {
        return getResponse(url, null);
    }

    /**
     * 以get方式请求URL，返回CloseableHttpResponse
     * @param url String URL地址
     * @return CloseableHttpResponse
     * @throws HttpClientException
     */
    public static CloseableHttpResponse getResponse(String url, Header[] headers)
            throws HttpClientException {
        try {
            HttpGet httpGet = new HttpGet(url);
            addHeader(httpGet, headers);
            httpGet.setConfig(buildRequestConfig());
            return buildClient(url).execute(httpGet);
        } catch (Exception e) {
            throw new HttpClientException("HTTPS Get request failed！", e);
        }
    }

    /**
     * 添加头部信息
     * @param httpRequestBase Http请求
     * @param headers Header []
     */
    public static void addHeader(HttpRequestBase httpRequestBase, Header[] headers) {
        if(headers != null) {
            for(Header header : headers){
                httpRequestBase.addHeader(header);
            }
        }
    }

    /**
     * 根据URL地址获取回应内容字符串，以get方式请求
     * @param url String 要请求得目标URL地址 http https
     * @param jsonData String post提交的参数内容（json格式）
     * @return String 回应内容字符串
     * @throws HttpClientException 如果请求失败，将抛出该异常
     */
    public static String postJson(String url, String jsonData)
            throws HttpClientException {
        return postJson(url, jsonData, null);
    }

    /**
     * 根据URL地址获取回应内容字符串，以post方式请求
     * @param url String 要请求得目标URL地址 http https
     * @param jsonData String post提交的参数内容（json格式）
     * @param headers Header []
     * @return String 回应内容字符串
     * @throws HttpClientException 如果请求失败，将抛出该异常
     */
    public static String postJson(String url, String jsonData, Header[] headers)
            throws HttpClientException {
        return parseContent(post(url, createJsonEntity(jsonData), headers));
    }

    public static String postApiJson(String url, String jsonData)
            throws HttpClientException {
        return postApiJson(url, jsonData, null);
    }

    public static String postApiJson(String url, String jsonData, Header[] headers)
            throws HttpClientException {
        return parseContent(post(url, createApiJsonEntity(jsonData), headers));
    }

    /**
     * 根据URL地址获取回应内容字符串，以get方式请求
     * @param url String 要请求得目标URL地址 http https
     * @param params String post提交的参数内容（string格式）
     * @return String 回应内容字符串
     * @throws HttpClientException 如果请求失败，将抛出该异常
     */
    public static String postForm(String url,String params)
            throws HttpClientException {
        return postForm(url, params, null);
    }

    public static String postForm(String url,String params, Header[] headers)
            throws HttpClientException {
        return parseContent(post(url, createPostEntity(params), headers));
    }

    /**
     * 根据URL地址获取回应内容实体，以post方式请求
     * @param url String 要请求得目标URL地址 http https
     * @param postEntity StringEntity 封装的post数据对象
     * @return HttpEntity 回应内容实体
     * @throws HttpClientException 如果请求失败，将抛出该异常
     */
    public static HttpEntity post(String url, StringEntity postEntity, Header[] headers)
            throws HttpClientException {
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(buildRequestConfig());
            httpPost.setEntity(postEntity);
            addHeader(httpPost, headers);
            CloseableHttpResponse response = buildClient(url).execute(httpPost);
            return parseEntity(response);
        } catch (HttpClientException he) {
            throw he;
        } catch (Exception e) {
            throw new HttpClientException("HTTPS Post request failed！", e);
        }
    }

    /**
     * 根据URL地址获取回应内容字符串，以post方式请求
     * @param url String 要请求得目标URL地址 http https
     * @param jsonData String post提交的参数内容（json格式）
     * @param headers Header []
     * @return String 回应内容字符串
     * @throws HttpClientException 如果请求失败，将抛出该异常
     */
    public static String putJson(String url, String jsonData, Header[] headers)
            throws HttpClientException {
        return parseContent(put(url, createJsonEntity(jsonData), headers));
    }

    /**
     * 根据URL地址获取回应内容实体，以put方式请求
     * @param url String 要请求得目标URL地址 http https
     * @param postEntity StringEntity 封装的post数据对象
     * @return HttpEntity 回应内容实体
     * @throws HttpClientException 如果请求失败，将抛出该异常
     */
    public static HttpEntity put(String url, StringEntity postEntity, Header[] headers)
            throws HttpClientException {
        try {
            HttpPut httpPut = new HttpPut(url);
            httpPut.setConfig(buildRequestConfig());
            httpPut.setEntity(postEntity);
            addHeader(httpPut, headers);
            CloseableHttpResponse response = buildClient(url).execute(httpPut);
            return parseEntity(response);
        } catch (HttpClientException he) {
            throw he;
        } catch (Exception e) {
            throw new HttpClientException("HTTPS Post request failed！", e);
        }
    }

    public static HttpEntity post(String url, StringEntity postEntity) throws HttpClientException {
        return post(url, postEntity, null);
    }

    /**
     * 上传文件到指定的url地址，并返回response字符串
     * @param url String 上传的url地址
     * @param name String 表单id
     * @param fileBytes byte[] 文件字符数组
     * @param fileName String 文件名
     * @return String 回应消息体
     * @throws HttpClientException
     * @throws IOException
     */
    public static String uploadFile(
            String url, String name, byte[] fileBytes, String fileName)
            throws HttpClientException, IOException {
        ContentType contentType = ContentType.create("multipart/form-data", Consts.UTF_8);
        HttpEntity reqEntity = MultipartEntityBuilder.create()
                .addBinaryBody(name, fileBytes, contentType, fileName)
                .build();

        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(buildRequestConfig());
        httpPost.setEntity(reqEntity);
        CloseableHttpResponse response = buildClient(url).execute(httpPost);
        HttpEntity resEntity = parseEntity(response);
        return parseContent(resEntity);
    }

    /**
     * 从回应内容实体中解析内容字符串
     * @param httpEntity HttpEntity 回应内容实体
     * @return String 回应内容字符串
     * @throws HttpClientException 如果解析失败，将抛出该异常
     */
    public static String parseContent(HttpEntity httpEntity)
            throws HttpClientException {
        try {
            if (!httpEntity.isStreaming()) {
                log.debug("httpEntity is not streaming");
                return "";
            }
            ContentType contentType = ContentType.getOrDefault(httpEntity);
            Charset charset = contentType.getCharset();
            String charsetStr = charset != null ? charset.name() : contentType.getParameter("encoding");
            log.debug("charsetStr=" + charsetStr);
            if (StringUtils.isBlank(charsetStr)) {
                charsetStr = "UTF-8";
            }
            return IOUtils.toString(httpEntity.getContent(), charsetStr);
        } catch (Exception e) {
            throw new HttpClientException("Get response content failed！", e);
        }
    }

    /**
     * 创建Json格式的post数据对象
     * @param data String post提交的数据内容
     * @return StringEntity post数据对象
     */
    public static StringEntity createJsonEntity(String data) {
        return createJsonEntity(data, Consts.UTF_8);
    }

    public static StringEntity createApiJsonEntity(String data) {
        return createJsonEntity(data, Consts.UTF_8, "application/vnd.api+json");
    }

    /**
     * 创建Json格式的post数据对象
     * @param data String post提交的数据内容
     * @param charset String post方式提交数据的编码
     * @return StringEntity post数据对象
     */
    public static StringEntity createJsonEntity(String data, Charset charset) {
        return createJsonEntity(data, charset, "application/json");
    }

    /**
     * 创建Json格式的post数据对象
     * @param data String post提交的数据内容
     * @param charset String post方式提交数据的编码
     * @return StringEntity post数据对象
     */
    public static StringEntity createJsonEntity(String data, Charset charset, String mimeType) {
        return new StringEntity(data, ContentType.create(mimeType, charset));
    }

    /**
     * 创建Post格式的post数据对象
     * @param data String post提交的数据内容
     * @param charset String post方式提交数据的编码
     * @return StringEntity post数据对象
     */
    public static StringEntity createPostEntity(String data, Charset charset) {
        return new StringEntity(
                data, ContentType.create("application/x-www-form-urlencoded", charset));
    }

    /**
     * 创建Post格式的post数据对象
     * @param data String post提交的数据内容
     * @param charset String post方式提交数据的编码
     * @return StringEntity post数据对象
     */
    public static StringEntity createPostEntity(String data, String charset) {
        return createPostEntity(
                data,
                StringUtils.isNoneBlank(charset) ? Charset.forName(charset) : null);
    }

    /**
     * 创建Post格式的post数据对象
     * @param data String post提交的数据内容
     * @return StringEntity post数据对象
     */
    public static StringEntity createPostEntity(String data) {
        return createPostEntity(data, Consts.UTF_8);
    }

    /**
     * 从response中获取HttpEntity对象，如果请求异常或者内容为空，将抛出异常。
     * @param response CloseableHttpResponse 请求回应对象
     * @return HttpEntity 回应中的内容实体对象
     * @throws HttpClientException
     */
    public static HttpEntity parseEntity(CloseableHttpResponse response)
            throws HttpClientException {
        try {
            StatusLine statusLine = response.getStatusLine();
            HttpEntity entity = response.getEntity();
            if (statusLine.getStatusCode() >= 300) {
                throw new HttpClientException(
                        statusLine.getStatusCode() + ":" + statusLine.getReasonPhrase(),
                        parseContent(entity));
            }
            if (entity == null) {
                throw new ClientProtocolException("Response contains no content");
            }
            return entity;
        } catch (HttpClientException he) {
            throw he;
        } catch (Exception e) {
            throw new HttpClientException(e.getMessage(), e);
        }
    }

    /**
     * 构件request请求配置对象，包括设置超时登。
     * @return RequestConfig 客户端请求配置
     */
    public static RequestConfig buildRequestConfig() {
        return RequestConfig.custom()
                .setSocketTimeout(30000)
                .setConnectTimeout(30000)
                .build();
    }

    /**
     * 构件客户端对象，根据url的schema判断是否使用SSL加密请求
     * @param url String 要请求的目标URL地址
     * @param laxRedirect boolean 是否使用放宽重定向限制，
     *                    使用自定义重定向策略来放宽对由HTTP规范施加的POST方法的自动重定向的限制。
     * @return CloseableHttpClient HTTP客户端对象
     * @throws HttpClientException
     */
    public static CloseableHttpClient buildClient(String url, boolean laxRedirect)
            throws HttpClientException {
        HttpClientBuilder clientBuilder = HttpClients.custom();
        if (url.startsWith("https")) {
            clientBuilder.setSSLSocketFactory(buildDefaultSSLFactory());
        }
        if(laxRedirect){
            clientBuilder.setRedirectStrategy(new LaxRedirectStrategy());
        }
        return clientBuilder.build();
    }

    /**
     * 构件客户端对象，根据url的schema判断是否使用SSL加密请求
     * @param url String 要请求的目标URL地址
     * @return CloseableHttpClient HTTP客户端对象
     * @throws HttpClientException
     */
    public static CloseableHttpClient buildClient(String url) throws HttpClientException {
        return buildClient(url, false);
    }

    /**
     * 构建默认的SSL链接工厂类
     * @return SSLConnectionSocketFactory 构件的工厂类对象
     * @throws HttpClientException 构建失败抛出的各种原因异常
     */
    private static SSLConnectionSocketFactory buildDefaultSSLFactory()
            throws HttpClientException {
        try {
            SSLContext sslContext = SSLContexts.custom()
                    .loadTrustMaterial(null, new DefaultTrustStrategy())
                    .build();
            return new SSLConnectionSocketFactory(sslContext);
        } catch (Exception e) {
            throw new HttpClientException(
                    "Build SSLConnectionSocketFactory failed!", e);
        }
    }

    public static void main(String[] args) {
        try {
            String res = postJson("http://msg.umeng.com/api/send", "");
            log.debug(res);
        } catch (HttpClientException he) {
            log.debug(he.getMessage());
            log.debug(he.getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
