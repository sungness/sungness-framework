package com.sungness.core.util.tools;

import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;

/**
 * Double相关工具类，提供校验、处理等简单静态方法
 * @author wanghongwei
 * @since 02.05.2013
 */
public class DoubleTools {
    /**
     * 解析字符串，将其转换成Double，如果不是有效数字，则返回null
     * @param param String 目标数字字符串
     * @return Double 解析结果对象
     */
    public static Double parseDouble(String param) {
        if (param != null && NumberUtils.isNumber(param.trim())) {
            return Double.parseDouble(param.trim());
        }
        return null;
    }

    /**
     * 解析字符串，将其转换成Double，如果不是有效数字，则返回null
     * @param param String 目标数字字符串
     * @return Double 解析结果对象
     */
    public static BigDecimal parseBigDecimal(String param) {
        return parseBigDecimal(param, null);
    }

    public static BigDecimal parseBigDecimal(String param, BigDecimal defaultValue) {
        if (param != null && NumberUtils.isNumber(param.trim())) {
            return new BigDecimal(Double.parseDouble(param.trim()));
        }
        return defaultValue;
    }

    /**
     * 判断是否小于等于0（包括null）
     * @param d Double 目标对象
     * @return boolean如果为空，返回true，否则返回false
     */
    public static boolean lessEqualZero(Double d) {
        return d == null || d <= 0;
    }

    /**
     * 判断是否小于0（包括null）
     * @param d Double 目标整数对象
     * @return boolean如果为空，返回true，否则返回false
     */
    public static boolean lessZero(Double d) {
        return d == null || d < 0;
    }
}
