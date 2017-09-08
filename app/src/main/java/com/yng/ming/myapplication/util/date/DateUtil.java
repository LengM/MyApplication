package com.yng.ming.myapplication.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Bright on 2017/7/31 0031
 * 日期工具类
 */
public class DateUtil {

    public static Date StringToDate(String time, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String DateToString(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static Date LongToDate(Long time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return StringToDate(sdf.format(time), format);
    }

    public static String formatString(String time, String format, String returnFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String s = "";
        try {
            s = new SimpleDateFormat(returnFormat).format(sdf.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return s;
    }

    public static String LongToString(Long time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(time);
    }

    public static Calendar StringToCalendar(String time, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(StringToDate(time, format));
        return calendar;
    }

    public static long TimeToLong(Date time, String format) {
        String s = DateToString(time, format);
        return StringToDate(s, format).getTime();
    }

}
