package com.sungness.core.util;

import com.sungness.core.util.tools.LongTools;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理工具
 * Created by wanghongwei on 8/6/15.
 */
public class DateUtil {
    /**
     * 标准年月日格式化输出
     * @param timestamp Long 时间戳
     * @return String 格式化字符串
     */
    public static String format(Long timestamp) {
        if (timestamp == null) {
            return "";
        } else {
            return DateFormatUtils.format(timestamp * 1000, "yyyy-MM-dd");
        }
    }

    public static String format(Date date) {
        return format(date, "yyyy-MM-dd");
    }

    public static String format(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }

    public static String patternFormat(Date date) {
        return format(date, "yyyyMMdd");
    }

    public static String fullFormat(Long timestamp) {
        if (LongTools.lessEqualZero(timestamp)) {
            return "";
        } else {
            return DateFormatUtils.format(timestamp * 1000, "yyyy-MM-dd HH:mm:ss");
        }
    }

    /**
     * 获取当前系统时间戳，精确到秒
     * @return Long 系统时间戳
     */
    public static Long getTimestamp() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 从字符串日期中解析日期对象
     * @param dateStr String 日期字符串
     * @return Date 日期对象
     * @throws ParseException
     */
    public static Date parseDate(String dateStr) throws ParseException {
        return parseDate(dateStr, "yyyy-MM-dd");
    }


    /**
     * 从字符串日期中解析日期对象
     * @param dateStr String 日期字符串
     * @return Date 日期对象
     * @throws ParseException
     */
    public static Date parseMonthDate(String dateStr) throws ParseException {
        return parseMonthDate(dateStr, "yyyy-MM");
    }

    /**
     * 从字符串日期中解析日期对象
     * @param dateStr String 日期字符串
     * @return Date 日期对象
     * @throws ParseException
     */
    public static Date parseMonthAddDate(Date dateStr) throws ParseException {
        Calendar cal=Calendar.getInstance();
        cal.setTime(dateStr);
        cal.add(Calendar.MONTH, 1); // 目前時間加1個月
        return cal.getTime();
    }


    /**
     * 从字符串日期中解析日期对象
     * @param dateStr String 日期字符串
     * @param parsePatterns String... 日期格式
     * @return Date 日期对象
     * @throws ParseException
     */
    public static Date parseMonthDate(
            final String dateStr, final String... parsePatterns)
            throws ParseException {
        return DateUtils.parseDate(dateStr, parsePatterns);
    }



    /**
     * 从字符串日期中解析日期对象
     * @param dateStr String 日期字符串
     * @param parsePatterns String... 日期格式
     * @return Date 日期对象
     * @throws ParseException
     */
    public static Date parseDate(
            final String dateStr, final String... parsePatterns)
            throws ParseException {
        return DateUtils.parseDate(dateStr, parsePatterns);
    }

    /**
     * 比较两个日期的大小
     * @param date1 Date
     * @param date2 Date
     * @param field int 截断的位置 <code>Calendar</code>
     * @return int a negative integer, zero, or a positive integer as the first
     * date is less than, equal to, or greater than the second.
     */
    public static int compare(Date date1, Date date2, int field) {
        return DateUtils.truncatedCompareTo(date1, date2, field);
    }

    /**
     * 比较两个日期的大小
     * @param date1 Date
     * @param date2 Date
     * @return int a negative integer, zero, or a positive integer as the first
     * date is less than, equal to, or greater than the second.
     */
    public static int compare(Date date1, Date date2) {
        return compare(date1, date2, Calendar.DAY_OF_MONTH);
    }

    public static long compareTime(Date date1, Date date2) {
        return date1.getTime() - date2.getTime();
    }

    /**
     * 在日期对象上增加指定天数
     * @param date Date 日期对象
     * @param amount int 天数
     * @return Date 增加后的天数
     */
    public static Date addDays(final Date date, int amount) {
        return DateUtils.addDays(date, amount);
    }

    /**
     * 在日期对象上增加指定月数
     * @param date Date 日期对象
     * @param amount int 月数
     * @return Date 增加后的日期
     */
    public static Date addMonths(final Date date, int amount) {
        return DateUtils.addMonths(date, amount);
    }

    /**
     * 获取昨天的日期对象
     * @return Date 日期对象
     */
    public static Date getYesterday() {
        return addDays(new Date(), -1);
    }

    public static String getYesterdayStr() {
        return format(getYesterday());
    }

    public static String getYesterdayStr(String pattern) {
        return format(getYesterday(), pattern);
    }

    /**
     * 获取一周前的日期字符串
     * @return 日期字符串
     */
    public static String getWeekDate() {
        return format(addDays(new Date(), -7));
    }

    /**
     * 获取6个月前的日期字符串
     * @return 日期字符串
     */
    public static String getMonthDate() {
        return format(addDays(new Date(), -30));
    }

    /**
     * 根据接口的请求跨度和开始日期计算并返回结束日期，如果结束日期超过最多结束日期，
     * 则以最大结束日期作为结束日期。
     * @param beginDate Date 开始日期
     * @param requestRange Integer 接口请求跨度
     * @param maxEndDate Date 最大结束日期
     * @return Date 即将请求的结束日期
     */
    public static Date getEndDate(Date beginDate, Integer requestRange, Date maxEndDate) {
        Date endDate = DateUtil.addDays(beginDate, requestRange - 1);
        if (DateUtil.compare(endDate, maxEndDate) > 0) {
            endDate = maxEndDate;
        }
        return endDate;
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(compare(parseMonthDate("2016-02"), getYesterday()));
//        Date date = DateUtils.addDays(parseDate("2015-02-28"), 1);
          System.out.println(format(new Date(),"yyyy-MM"));
//        System.out.println(compare(date, parseDate("2015-02-28")));
//        System.out.println(compare(parseDate("2015-02-20"), parseDate("2015-02-01")));
//        System.out.println(format(getYesterday()));
    }
}
