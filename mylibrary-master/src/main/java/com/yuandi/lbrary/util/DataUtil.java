package com.yuandi.lbrary.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by EdgeDi
 * 2017/9/6 14:19
 */
@SuppressLint("SimpleDateFormat")
public class DataUtil {

    public static final long one_day = 86400000;
    public static final long one_min = 3600000;

    public static final String YYYY = "yyyy年";
    public static final String YYYY_MM = "yyyy年MM月";
    public static final String YYYY_MM_dd = "yyyy年MM月dd日";
    public static final String YYYY_MM_dd_hh = "yyyy年MM月dd日 HH";
    public static final String YYYY_MM_dd_hh_mm = "yyyy年MM月dd日 HH:mm";
    public static final String MM_dd_hh_mm = "MM月dd日 HH:mm";
    public static final String hh_nn = "HH:mm";

    public static final String Day_jeikou = "yyyy-MM-dd";

    /**
     * 9:35之前之后
     *
     * @return
     */
    public static Boolean Judge() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int minuteOfDay = hour * 60 + minute;
        final int start = 9 * 60 + 35;
        return minuteOfDay >= start;
    }

    /**
     * 50分之前之后
     *
     * @return
     */
    public static Boolean JudgeMin() {
        Calendar cal = Calendar.getInstance();
        int minute = cal.get(Calendar.MINUTE);
        final int start = 50;
        return minute >= start;
    }

    public static String formattime(long time) {
        String timeString = null;
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_dd_hh_mm);
        timeString = sdf.format(new Date(time));//单位秒
        return timeString;
    }

    public static String formatDay(long time) {
        String timeString = null;
        SimpleDateFormat sdf = new SimpleDateFormat(MM_dd_hh_mm);
        timeString = sdf.format(new Date(time));//单位秒
        return timeString;
    }

    public static String FormatTime(long time, String format) {
        String timeString = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        timeString = sdf.format(new Date(time));//单位秒
        return timeString;
    }

    public static String CSDN_Time(String time, String format) {
        SimpleDateFormat sdr = new SimpleDateFormat(format);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;
    }

    public static String getMonday(int day) {
        long day_time = (day - 1) * 86400000;
        long l = System.currentTimeMillis() + day_time;
        Date date = new Date(l);
        SimpleDateFormat format = new SimpleDateFormat("E");
        return format.format(date);
    }

    public static Long BTtime(String time) {
        int nian = Integer.parseInt(time.substring(0, 4));
        int yue = Integer.parseInt(time.substring(4, 6));
        int ri = Integer.parseInt(time.substring(6, 8));
        int shi = Integer.parseInt(time.substring(8, 10));
        int fen = Integer.parseInt(time.substring(10, time.length()));
        time = nian + "-" + yue + "-" + ri + " " + shi + ":" + fen;
        return get(time);
    }

    public static String getLineTime(String format) {
        String result = null;
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = s.parse(format);
            s = new SimpleDateFormat("HH:mm");
            result = s.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getLineTime2(String format) {
        String result = null;
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = s.parse(format);
            s = new SimpleDateFormat("MM-dd");
            result = s.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getModeDay(String format) {
        String result = null;
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = s.parse(format);
            s = new SimpleDateFormat("MM月dd日");
            result = s.format(date);
            s = new SimpleDateFormat("EEEE");
            result += "  " + s.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static long get(String format) {
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = s.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static String getBeForeTime(String time) {
        long l = System.currentTimeMillis() - 2592000000l;
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");
        Date date = new Date(l);
        return format.format(date);
    }

    public static String getBeForeTime() {
        long l = System.currentTimeMillis() - 2592000000l;
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");
        Date date = new Date(l);
        return format.format(date);
    }

    public static String getBeForeDetail() {
        long l = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        Date date = new Date(l);
        return format.format(date);
    }

    public static String getStrTime(String timeStamp) {
        String timeString = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long l = Long.valueOf(timeStamp);
        timeString = sdf.format(new Date(l));//单位秒
        return timeString;
    }

    public static String getTime(String format) {
        String result = null;
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = s.parse(format);
            result = getPublishTime(System.currentTimeMillis() - date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getPublishTime(long time) {
        time = time / 1000;
        if (time > 60) {
            time = time / 60;
            if (time > 60) {
                time = time / 24;
                if (time > 24) {
                    return time + "天前发布";
                } else {
                    return time + "小时前发布";
                }
            } else {
                return time + "分钟前发布";
            }
        } else {
            return time + "秒前发布";
        }
    }

    public static int getLastAfternoon() {
        long time = System.currentTimeMillis();
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(time);
        int apm = mCalendar.get(Calendar.AM_PM);
        return apm;
    }

    /**
     * 字符串转时间戳
     *
     * @param timeString
     * @param format
     * @return
     */
    public static String getStringToTime(String timeString, String format) {
        if (timeString == null) return "--";
        String timeStamp = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date d;
        try {
            d = sdf.parse(timeString);
            long l = d.getTime();
            timeStamp = String.valueOf(l);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeStamp;
    }

    /**
     * 时间转字符串
     *
     * @param timeStamp
     * @param format
     * @return
     */
    public static String getTimeToString(String timeStamp, String format) {
        if (timeStamp == null) return "--";
        String timeString = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        long l = Long.valueOf(timeStamp);
        timeString = sdf.format(new Date(l));//单位秒
        return timeString;
    }

    public static String getStringToTimeToString(String timeStamp, String format_1, String format_2) {
        if (timeStamp == null) return "--";
        if (timeStamp.equals("--")) return "--";
        return getTimeToString(getStringToTime(timeStamp, format_1), format_2);
    }


}