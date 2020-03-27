package com.sungness.core.util.tools;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Long相关工具类，提供整数校验、处理等简单静态方法
 * @author wanghongwei
 * @since 01.22.2013
 */
public class LongTools {
    /**
     * 解析字符串，将其转换成Long，如果不是有效数字，则返回null
     * @param param String 目标数字字符串
     * @return Long 解析结果对象
     */
    public static Long parse(String param) {
        return parse(param, null);
    }

    public static Long parse(String param, Long defaultValue) {
        if (param != null && StringUtils.isNumeric(param.trim())) {
            return Long.parseLong(param.trim());
        }
        return defaultValue;
    }

    /**
     * 判断整数是否小于等于0（包括null）
     * @param l Long 目标整数对象
     * @return boolean如果为空，返回true，否则返回false
     */
    public static boolean lessEqualZero(Long l) {
        if (l == null || l.longValue() <= 0) {
            return true;
        }
        return false;
    }

    public static boolean greaterThanZero(Long l) {
        return !lessEqualZero(l);
    }

    /**
     * 判断整数是否小于0（包括null）
     * @param l Long 目标整数对象
     * @return boolean如果为空，返回true，否则返回false
     */
    public static boolean lessZero(Long l) {
        if (l == null || l < 0) {
            return true;
        }
        return false;
    }

    /**
     * 解析字符串中的id列表，以“,”分隔符，如果字符串为空返回空List
     * @param param String 传入的字符串
     * @return List<List> 解析结果列表
     */
    public static List<Long> parseList(String param) {
        return parseList(param, ",");
    }

    /**
     * 解析字符串中的id列表，以 flag 分隔符，如果字符串为空返回空List
     * @param param String 传入的字符串
     * @return List<List> 解析结果列表
     */
    public static List<Long> parseList(String param, String flag) {
        List<Long> idList = new ArrayList<>();
        if (param != null) {
            String[] idArray = param.split(flag);
            for (String idStr : idArray) {
                if (StringUtils.isNumeric(idStr.trim())) {
                    idList.add(Long.parseLong(idStr.trim()));
                }
            }
        }
        return idList;
    }
}
