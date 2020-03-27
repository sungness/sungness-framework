package com.sungness.core.security.aes;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;

/**
 * ShaUtils class
 *
 * 计算公众平台的消息签名接口.
 */
public class ShaUtils {

	/**
	 * 用SHA1算法生成安全签名
	 * @param token 票据
	 * @param timestamp 时间戳
	 * @param nonce 随机字符串
	 * @param encrypt 密文
	 * @return 安全签名
	 * @throws AesException 
	 */
	public static String sha1Hex(
			String token, String timestamp, String nonce, String encrypt)
			throws AesException {
        return sha1Hex(new String[] {token, timestamp, nonce, encrypt});
	}

	/**
	 * 用SHA1算法生成安全签名
	 * @param strArray String[] 要计算签名的字符串数组
	 * @return String 安全签名
	 * @throws AesException
	 */
	public static String sha1Hex(String[] strArray) throws AesException {
		try {
			return DigestUtils.sha1Hex(getSortBytes(strArray));
		} catch (Exception e) {
			e.printStackTrace();
			throw new AesException(AesException.ComputeSignatureError);
		}
	}

	/**
	 * 用SHA1算法生成安全签名
	 * @param token 票据
	 * @param timestamp 时间戳
	 * @param nonce 随机字符串
	 * @param encrypt 密文
	 * @return 安全签名
	 * @throws AesException
	 */
	public static String sha256Hex(
			String token, String timestamp, String nonce, String encrypt)
			throws AesException {
		return sha256Hex(new String[] {token, timestamp, nonce, encrypt});
	}

	/**
	 * 用SHA1算法生成安全签名
	 * @param strArray String[] 要计算签名的字符串数组
	 * @return String 安全签名
	 * @throws AesException
	 */
	public static String sha256Hex(String[] strArray) throws AesException {
		try {
            return DigestUtils.sha256Hex(getSortBytes(strArray));
		} catch (Exception e) {
			e.printStackTrace();
			throw new AesException(AesException.ComputeSignatureError);
		}
	}

    private static byte[] getSortBytes(String[] strArray) {
        StringBuilder sb = new StringBuilder();
        // 字符串排序
        Arrays.sort(strArray);
        for (String str : strArray) {
            sb.append(str);
        }
        return sb.toString().getBytes();
    }

    public static void main(String[] args) {
        String[] strArray = {
                "MTQ4ODYxMDY3NTgyMTpiYzRiNjhhYTc0NTJmMzAzMmEzNmRmYTIyZTM3NjIwODliNDdmYjU4NWNjYTcxMDk1ZjFmNTZhMTVjNGNhZjhhOjFfXzU4YmE1ZTY2MDBjOTYzYjYwMDAwMDAwMV9fQWlBaWVjS3ZSM1FHblNaU21hSVN0dFgxbUhQcXUyMk1KT09FS3FLbTNKelFfXzRsd09CSU1CbUVrdnAwWHpqUWRGa2cweE9nbWg5MUl6Nm9lQ0kxUGxqeXo6OTg1ZDFkZWU1YjY1NWE2NzhmMGQ4YjhhMTE3MGE2YWNjNWQ1ZmQwNjFhZTg3YWU4N2E4Yzc3MzE4MTM4ZTYxMmZjNTQ2NTNkNjQyNTQ5OWQyYjMxNGFhYTQwMGUzZTUzZDkxNmVhZTg4Mjk3ODBlNWM1NTJmM2E0YzNiNTJiZjg",
                "1488610680",
                "1914910957",
				"LoN0tP6pQlOIY7wEgAD7hLCVnmSYT53kUrd3F4R5Pp5i9r6TatRLhOG+T0eZUJ0+hiOFc2jQ/Ax1NEpmPUZVA1hbK4EE698rcn6G7LHyV8rNie8e3KC68kkuUKOzjfuBLJnbGIYjFg4G8Mis26FKxGxPbCc8lE7gTgZkZJRpmLCYUiZHcrxceKhisIyC/qBZNfttBNSysrXfm2akJUKKUCax4v7ZW8Um0K2rZTUTaL/nwYsbxEUpt5AQVsmgW+oA3Ngnh976fiqmMgHOl06cJjF4bi3dxid/WfbRKiwAE0VSRT0UsAHSV/a0R9FXP3PsNiAfNWANui/Ao5KoyQ3S+uxJzs66IgEufMOL39WWtI26/b1lp1CCcyRGAZRk4W/MnxgaBhDomdrxtmvMiOwpQUrbM8XOvIeySkUsBTddHBf3/a598kODpeeFkgrUU+aRtxrp1xqFA2ij/6+PvzjXi8IqIQmfJsLIzy5tmQi23mpeh+sh7/vlgCs3GR58ZmyfFUUC2svzseoAgmApgZExXZ3OEJokOEar3uqJFzUQE1yFQuH2UuR5l4JNMYX2zySqPq5OlsCXqIdvx6lI+2lZZY417J+4QjWlSW6vDyrAKI5yDZMTvwUNlRbVlPQo1BnnYLxGphWsN+cvLc53Bks1qurlraki4mHq3q8DonD4Lttxd0Hk0r5I/9Mbg02MFD5+DEAbr/0i5tKPYFdohL6QW36OArrUJasrHCbSQiZovVNIBMLPxIRM0vq3TKdmWykW6YuGvMk2izjcs7qBKUzh3PW93m3y/5vP86SPzyBjZNxHjBGrTA0PuSYv2m/zP6S6Ch4eUv/WdFywAWm85P5CAEr0YyNFrLsGKQVT1fpKhKRkWOQEQFLOYu917HxIpu1TQ/vkqkzVSPnhwZItUyPS9dB6flKgRQbybMcqmS/RIs1/C/HhmK89f/LLSF5zWzk6upZaFhlbn7J07dt/uYiKYalcudwxdcLpv0v4v+WbtaauKvH//Z7uHxLSffsjufs+0HsOAmpN0CSYEN+NvEi4hzM7xNFwYXmxl9KWfKvmAQ0="
        };
        try {
            System.out.println(sha256Hex(strArray));
        } catch (AesException e) {
            e.printStackTrace();
        }
    }
}
