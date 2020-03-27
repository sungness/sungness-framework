package com.sungness.core.util.tools;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Integer相关工具类，提供整数校验、处理等简单静态方法
 * @author wanghongwei
 * @since 29.06.2012
 */
public class IntegerTools {

    /**
     * 判断整数是否小于等于0（包括null和空字符串""）
     * @param i Integer 目标整数对象
     * @return boolean如果为空，返回true，否则返回false
     */
    public static boolean lessEqualZero(Integer i) {
        return i == null || i <= 0;
    }

    /**
     * 判断整数是否小于0（包括null和空字符串""）
     * @param i Integer 目标整数对象
     * @return boolean如果为空，返回true，否则返回false
     */
    public static boolean lessZero(Integer i) {
        return i == null || i < 0;
    }

    /**
     * 解析字符串，将其转换成Integer，如果不是有效数字，则返回null
     * @param param String 目标数字字符串
     * @return Integer 解析结果对象
     */
    public static Integer parse(String param) {
        return parse(param, null);
    }

    /**
     * 解析字符串，将其转换成Integer，如果不是有效数字，则返回默认值
     * @param param 目标整数对象
     * @param defaultValue 默认值
     * @return Integer
     */
    public static Integer parse(String param, Integer defaultValue){
        if(param != null && StringUtils.isNumeric(param.trim())){
            return Integer.parseInt(param.trim());
        }
        return defaultValue;
    }

    /**
     * 解析字符串中的id列表，以“,”分隔符，如果字符串为空返回空List
     * @param param String 传入的字符串
     * @return List<List> 解析结果列表
     */
    public static List<Integer> parseList(String param) {
        List<Integer> idList = new ArrayList<>();
        if (param != null) {
            String[] idArray = param.split(",");
            for (String idStr : idArray) {
                if (StringUtils.isNumeric(idStr.trim())) {
                    idList.add(Integer.parseInt(idStr.trim()));
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
    public static List<Integer> parseList(String[] param) {
        List<Integer> idList = new ArrayList<>();
        if (param != null && param.length > 0) {
            for (String idStr : param) {
                if (StringUtils.isNumeric(idStr.trim())) {
                    idList.add(Integer.parseInt(idStr.trim()));
                }
            }
        }
        return idList;
    }
}
