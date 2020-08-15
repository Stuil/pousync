package com.osd.sync.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * @title: TimeUtil
 * @description: 时间转换工具类
 * @date: 2020/8/15
 * @author: zwh
 * @copyright: Copyright (c) 2020
 * @version:
 */

public class TimeUtil {
    static final String PATH_FORMAT1="yyyy-MM-dd HH:mm:ss";
    static final String PATH_FORMAT2="yyyyMMddHHmmss";
    /**
     * 获取当前时间的时间戳
     * @return
     * */
    public static String getTimeInMillis() {
        long timeInMillis = Calendar.getInstance().getTimeInMillis();
        return timeInMillis+"";
    }
    /**
     * 获取位数为7位的随机数
     * @return
     * */
    public static String getRandom() {
        Random random = new Random();
        int nextInt = random.nextInt(9000000);
        nextInt=nextInt+1000000;
        String str=nextInt+"";
        return str;
    }
    public static String getTimeYearMonthDay() {
        Calendar c = Calendar.getInstance();
        int year=c.get(Calendar.YEAR);
        int month=c.get(Calendar.MONTH)+1;
        int day=c.get(Calendar.DAY_OF_MONTH);
        int hh=c.get(Calendar.HOUR_OF_DAY);
        int mi=c.get(Calendar.MINUTE);
        int ss=c.get(Calendar.SECOND);
        System.out.println(year+" "+month+" "+day+" "+hh+" "+mi+" "+ss);
        return null;
    }
    /**
     * 输入日期格式，返回格式内的内容，输入格式以-分隔
     * @param dateFormat
     * @return
     **/
    public static String getTimeYearMonthDay(String dateFormat) {
        String[] strNow = new SimpleDateFormat(dateFormat).format(new Date()).toString().split("-");
        String str="";
        for (String string : strNow) {
            str=str+string;
        }
        return str;
    }
    public static String getTimeToString(String str2,String dateFormat1,String dateFormat2)  {
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormat1);
        SimpleDateFormat df = new SimpleDateFormat(dateFormat2);
        try {
            return df.format(dateFormat.parse(str2));
        } catch (ParseException e) {
            return df.format(new Date());
        }
    }
    /**
     * 获得20位的主键值，主键值的格式是13位的当前时间戳加7位的随机数
     * @return
     **/
    public static String getMillPrimaryKey() {
        return getTimeInMillis()+getRandom();
    }
    /**
     * 获得21位的主键值，主键值的格式是14位的当前时间加7位的随机数
     * @return
     **/
    public static String getDatePrimaryKey() {
        return getTimeYearMonthDay("yyyyMMddHHmmss")+getRandom();
    }

    public static void main(String[] args) {
       /* System.out.println(getTimeToString("2019-02-25 16:45:59",PATH_FORMAT1,PATH_FORMAT2));
        System.out.println(getTimeYearMonthDay("yyyyMMddHHmmss"));
        getTimeYearMonthDay();
        System.out.println(getTimeInMillis());
        System.out.println(getRandom());
        System.out.println(getMillPrimaryKey());
        System.out.println(getDatePrimaryKey());*/
    }

}
