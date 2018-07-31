package com.zmdev.goldenbag.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    private final static SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");

    private final static SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取当前季度的总天数
     *
     * @return int
     */
    public static int getCurrentQuarterDays() {
        int count = 0;
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        int currentYear = c.get(Calendar.YEAR);
        if (currentMonth >= 1 && currentMonth <= 3) {
            count = 90;
            if (currentYear % 4 == 0 && currentYear % 100 != 0 || currentYear % 400 == 0) {
                count++;
            }
        } else if (currentMonth >= 4 && currentMonth <= 6) {
            count = 91;
        } else if (currentMonth >= 7 && currentMonth <= 9) {
            count = 92;
        } else if (currentMonth >= 10 && currentMonth <= 12) {
            count = 92;
        }
        return count;
    }

    /**
     * 获取当前年份
     *
     * @return int
     */
    public static int getCurrentYear() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR);
    }

    /**
     * 获取当前季度
     *
     * @return int
     */
    public static int getCurrentQuarter() {
        Calendar c = Calendar.getInstance();
        int currentQuarter = 0;
        int currentMonth = c.get(Calendar.MONTH) + 1;
        if (currentMonth >= 1 && currentMonth <= 3)
            currentQuarter = 1;
        else if (currentMonth >= 4 && currentMonth <= 6)
            currentQuarter = 2;
        else if (currentMonth >= 7 && currentMonth <= 9)
            currentQuarter = 3;
        else if (currentMonth >= 10 && currentMonth <= 12)
            currentQuarter = 4;
        return currentQuarter;
    }


    /**
     * 当前季度的开始时间
     *
     * @return Date
     */
    public static Date getCurrentQuarterStartTime() {

        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                c.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                c.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                c.set(Calendar.MONTH, 6);
            else if (currentMonth >= 10 && currentMonth <= 12)
                c.set(Calendar.MONTH, 9);
            c.set(Calendar.DATE, 1);

            now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前季度的结束时间
     *
     * @return Date
     */
    public static Date getCurrentQuarterEndTime() {
        Calendar c;
        Date now = null;
        try {
            c = setCalendar();
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前季度的结束日期
     *
     * @return Date
     */
    public static Date getCurrentQuarterEndDate() {
        Calendar c;
        Date now = null;
        try {
            c = setCalendar();
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 返回当前季度的结束时间操作类(Calendar)
     *
     * @return Calendar
     */
    private static Calendar setCalendar() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;

        if (currentMonth >= 1 && currentMonth <= 3) {
            c.set(Calendar.MONTH, 2);
            c.set(Calendar.DATE, 31);
        } else if (currentMonth >= 4 && currentMonth <= 6) {
            c.set(Calendar.MONTH, 5);
            c.set(Calendar.DATE, 30);
        } else if (currentMonth >= 7 && currentMonth <= 9) {
            c.set(Calendar.MONTH, 8);
            c.set(Calendar.DATE, 30);
        } else if (currentMonth >= 10 && currentMonth <= 12) {
            c.set(Calendar.MONTH, 11);
            c.set(Calendar.DATE, 31);
        }
        return c;
    }
}