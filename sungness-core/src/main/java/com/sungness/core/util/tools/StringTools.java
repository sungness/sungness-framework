package com.sungness.core.util.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 字符串相关工具类，提供字符串校验、处理等简单静态方法
 * @author wanghongwei
 * @since 29.06.2012
 */
public class StringTools {

    /**
     * 判断字符串是否为空（包括null和空字符串""）
     * @param str String目标字符串
     * @return boolean如果为空，返回true，否则返回false
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 清除字符串首位空格，如果传入字符串为null直接返回
     * @param param String 传入参数字符串
     * @return String 处理后的结果字符串
     */
    public static String parseString(String param) {
        return param == null ? null : param.trim();
    }

    /**
     * 清除字符串首位空格，如果传入字符串为null返回空字符串
     * @param param String 传入参数字符串
     * @return String 处理后的结果字符串
     */
    public static String parseEmptyString(String param) {
        return param == null ? "" : param.trim();
    }

    /**
     * 清除字符串的单引号、双引号
     * @param str String 源字符串
     * @return String 清除引号后的字符串
     */
    public static String removeQuote(String str) {
        if (str == null) {
            return null;
        }
        return str.replaceAll("\"", "").replaceAll("\'", "");
    }

    /**
     * 解析字符串
     * @param params 字符串
     * @return 字符串列表 不可操作
     */
    public static List<String> parseList(String params){
        return parseList(params, ",", false);
    }

    /**
     * 解析字符串
     * @param params 字符串
     * @param supportedOperation 得到可增减的list
     * @return 字符串列表
     */
    public static List<String> parseList(
            String params, String regex, boolean supportedOperation){
        List<String> list = Arrays.asList(params.split(regex));
        return supportedOperation ? new ArrayList<>(list) : list;
    }
}
