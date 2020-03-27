package com.sungness.core.crawler;

import java.util.Random;

/**
 * 浏览器客户端标识类，可随即获取一个客户端标识，用于抓取程序请求地址时使用。
 * @author wanghongwei
 *
 */
public class ClientUserAgent {
	private static String[] userAgentArray = {
		"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)",
		"Mozilla/5.0 (Windows; U; Windows NT 5.1; en; rv:1.9.2) Gecko/20100115 MRA 5.6 (build 03278) Firefox/3.6 (.NET CLR 3.5.30729)",
		"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 2.0.50727; MAXTHON 2.0)",
		"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; MALN; Media Center PC 6.0; .NET4.0C; BRI/2; 360SE)",
		"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_4) AppleWebKit/534.57.2 (KHTML, like Gecko) Version/5.1.7 Safari/534.57.2",
		"Mozilla/5.0 (Macintosh; Intel Mac OS X 10.7; rv:8.0.1) Gecko/20100101 Firefox/8.0.1",
		"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_4) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.54 Safari/536.5",
		"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2; SV1; QQDownload 665; .NET CLR 1.1.4322)"	,
		"Mozilla/5.0 (Windows NT 5.2) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.52 Safari/536.5"
	};

	/**
	 * 随机获取userAgentArray中的userAgent字符串
	 * @return String userAgent字符串
	 */
	public static String getRandomUserAgent() {
		try {
			Random random = new Random();
			int randIndex = random.nextInt(userAgentArray.length);
			return userAgentArray[randIndex];
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userAgentArray[0];
	}

    public static String getChromeUserAgent() {
        return "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36";
    }
}

