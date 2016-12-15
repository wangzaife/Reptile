package com.yhf.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wangzaifei on 2016/11/14.
 */
public class DateUtil {


    /**
     * 将指定字符串转换成日期
     * @param date
     * @param datePattern
     * @return
     */
    public static java.util.Date getFormatDate(String date, String datePattern) {
        SimpleDateFormat sd = new SimpleDateFormat(datePattern);
        return sd.parse(date, new java.text.ParsePosition(0));
    }

    /**
     * 将指定日期对象转换成格式化字符串
     * @param date
     * @param datePattern
     * @return
     */
    public static String getFormattedString(Date date, String datePattern) {
        SimpleDateFormat sd = new SimpleDateFormat(datePattern);
        return sd.format(date);
    }

    public static void main(String[] args) {
        System.out.println(getFormatDate("1992年8月13日", "yyyy年mm月dd日"));
    }
}
