package com.sungness.core.util.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期、时间相关工具类，提供时间、日期字符串解析、转换等简单静态方法
 * @author wanghongwei
 * @since 02.07.2013
 */
public class DateTools {

    /**
     * 清除字符串首位空格，如果传入字符串为null直接返回
     * @param dateStr String 日期字符串
     * @param format String 日期格式字符串
     * @return String 处理后的结果字符串
     */
    public static Date parseDate(String dateStr, String format) {
        if (StringTools.isEmpty(dateStr)) {
            return null;
        }
        try{
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            return formatter.parse(dateStr);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 格式化输出日期、时间，按照格式yyyy-MM-dd
     * @param date Date日期对象
     * @return String 格式化后的字符串，如果传入参数为null，则返回空字符串
     */
    public static String format(Date date) {
        return format(date, "yyyy-MM-dd");
    }

    public static String format(Date date, String format) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }
}
