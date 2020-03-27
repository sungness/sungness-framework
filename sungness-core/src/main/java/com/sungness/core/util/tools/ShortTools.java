package com.sungness.core.util.tools;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Short相关工具类，提供短整数校验、处理等简单静态方法
 * @author wanghongwei
 * @since 29.06.2012
 */
public class ShortTools {

    /**
     * 判断短整数是否小于等于0（包括null和空字符串""）
     * @param i Short 目标短整数对象
     * @return boolean如果为空，返回true，否则返回false
     */
    public static boolean lessEqualZero(Short i) {
        if (i == null || i <= 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断短整数是否小于0（包括null和空字符串""）
     * @param i Short 目标短整数对象
     * @return boolean如果为空，返回true，否则返回false
     */
    public static boolean lessZero(Short i) {
        if (i == null || i < 0) {
            return true;
        }
        return false;
    }

    /**
     * 解析字符串，将其转换成Short，如果不是有效数字，则返回null
     * @param param String 目标数字字符串
     * @return Short 解析结果对象
     */
    public static Short parseShort(String param) {
        if (param != null && StringUtils.isNumeric(param.trim())) {
            return Short.parseShort(param.trim());
        }
        return null;
    }

    /**
     * 解析字符串中的id列表，以“,”分隔符，如果字符串为空返回空List
     * @param param String 传入的字符串
     * @return List<List> 解析结果列表
     */
    public static List<Short> parseShortList(String param) {
        List<Short> idList = new ArrayList<>();
        if (param != null) {
            String[] idArray = param.split(",");
            for (String idStr : idArray) {
                if (StringUtils.isNumeric(idStr.trim())) {
                    idList.add(Short.parseShort(idStr.trim()));
                }
            }
        }
        return idList;
    }

    /**
     * 解析字符串数组对应的id列表，如果数组为空返回空List
     * @param param String[] 传入的字符串数组
     * @return List<List> 解析结果列表
     */
    public static List<Short> parseShortList(String[] param) {
        List<Short> idList = new ArrayList<>();
        if (param != null && param.length > 0) {
            for (String idStr : param) {
                if (StringUtils.isNumeric(idStr.trim())) {
                    idList.add(Short.parseShort(idStr.trim()));
                }
            }
        }
        return idList;
    }
}
