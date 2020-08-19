package com.osd.sync.utils;

import lombok.extern.slf4j.Slf4j;
import org.nutz.lang.Times;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @title: SyncUtil
 * @description: 同步Utils
 * @date: 2020/8/18
 * @author: zwh
 * @copyright: Copyright (c) 2020
 * @version: 1.0
 */
@Slf4j
public class SyncUtil {
    static final String PATH_FORMAT1 = "yyyy-MM-dd HH:mm:ss";
    static final String PATH_FORMAT2 = "yyyyMMddHHmmss";



    // Map比较
    public boolean keyAndValue(String val, Map<String, Object> map2) {
        return map2.containsValue(val);
    }

    // val -> key
    public static List<String> getKey(Map<String, Object> map, Object value) {
        List<String> keyList = new ArrayList<>();
        for (String key : map.keySet()) {
            if (map.get(key).equals(value)) {
                keyList.add(key);
            }
        }
        return keyList;
    }

    // 时间
    private long parseDate(String dateStr) {
        long openTime = 0;
        try {
            Date openDate = Times.parse("yyyy-MM-dd HH:mm:ss", dateStr);
            openTime = openDate.getTime() / 1000;
        } catch (Exception e) {
            try {
                Date openDate = Times.D(dateStr.substring(0, 10));
                openTime = openDate.getTime() / 1000;
            } catch (Exception exception) {
                openTime = 1451606400L;//保底，交易记录中最早的一条都是2016，2016-01-01 08:00:00
            }
        }
        return openTime;
    }

    // 时间格式 转换
    public static String getTimeToString(String str) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(PATH_FORMAT1);
        SimpleDateFormat df = new SimpleDateFormat(PATH_FORMAT2);
        try {
            return df.format(dateFormat.parse(str));
        } catch (ParseException e) {
            return df.format(new Date());
        }
    }

    // 左补0
    public static String addZeroForNum(String str, int strLength) {
        int strLen = str.length();
        StringBuffer sb = null;
        while (strLen < strLength) {
            sb = new StringBuffer();
            sb.append("0").append(str);
            str = sb.toString();
            strLen = str.length();
        }
        return str;
    }

    // 购气、补气、退气单号
    public static String accNo(LocalDateTime localDateTime, Integer accId) {
        String str = String.valueOf(localDateTime).replace("T", " ");
        return  getTimeToString(str) + "JF" + addZeroForNum(String.valueOf(accId), 5);
    }

    // bean copy
    public static void copyProperties(Object source, Object target) {
        try {
            BeanUtils.copyProperties(source, target);
        } catch (BeansException e) {
            log.error("BeanUtil property copy  failed :BeansException", e);
        } catch (Exception e) {
            log.error("BeanUtil property copy failed:Exception", e);
        }
    }
    // 随机数
    public static String getRandom(){
        Random random = new Random();
        int ends = random.nextInt(99);
        String res= String.format("%02d",ends);
        return res.equals("10")?"11":res;
    }

}
