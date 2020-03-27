package com.sungness.core.util.tools;

import org.apache.commons.lang3.StringUtils;

/**
 * Byte相关工具类，提供整数校验、处理等简单静态方法
 * @author wanghongwei
 * @since 29.06.2012
 */
public class ByteTools {
    /**
     * 判断整数是否小于等于0（包括null）
     * @param b Byte 目标整数对象
     * @return boolean如果为空，返回true，否则返回false
     */
    public static boolean lessEqualZero(Byte b) {
        if (b == null || b <= 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断整数是否小于0（包括null）
     * @param b Byte 目标整数对象
     * @return boolean如果为空，返回true，否则返回false
     */
    public static boolean lessZero(Byte b) {
        if (b == null || b < 0) {
            return true;
        }
        return false;
    }
    /**
     * 解析字符串，将其转换成Byte，如果不是有效数字，则返回null
     * @param param String 目标数字字符串
     * @return Byte 解析结果对象
     */
    public static Byte parseByte(String param) {
        if (param != null && StringUtils.isNumeric(param.trim())) {
            return Byte.parseByte(param.trim());
        }
        return null;
    }
}
