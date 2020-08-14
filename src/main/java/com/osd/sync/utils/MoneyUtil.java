package com.osd.sync.utils;

import org.nutz.ioc.loader.annotation.IocBean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.math.BigDecimal.ROUND_HALF_UP;

/**
 * Created by wizzer on 2017/3/31.
 */
@Component
public class MoneyUtil {

    /**
     * 小数位数
     */
    private static final int FEN = 2;
    private static final int LI = 3;
    /**
     * 分转换为元
     *
     * @param fen 分
     * @return 元
     */
    public static String fenToYuan(Number fen) {
        if(fen==null){
            return "0.00";
        }
        try {
            return new BigDecimal(fen.longValue()).divide(new BigDecimal(Math.pow(10,FEN))).setScale(FEN).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0.00";
    }

    /**
     * 厘转换为元
     *
     * @param li 分
     * @return 元
     */
    public static String liToYuan(Number li) {
        if(li==null){
            return "0.000";
        }
        try {
            return new BigDecimal(li.longValue()).divide(new BigDecimal(Math.pow(10,LI))).setScale(LI).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0.000";
    }

    /**
     * 元转换为分
     *
     * @param yuan 元
     * @return 分
     */
    public static long yuanToFen(String yuan) {
        try {
            return BigDecimal.valueOf(Double.valueOf(yuan)).setScale(FEN,ROUND_HALF_UP).multiply(new BigDecimal(100)).longValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 元转换为厘
     *
     * @param yuan 元
     * @return 厘
     */
    public static long yuanToLi(String yuan) {
        try {
            return BigDecimal.valueOf(Double.parseDouble(yuan)).setScale(LI,ROUND_HALF_UP).multiply(BigDecimal.valueOf(Math.pow(10, LI))).longValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 计算两个价格折扣率
     *
     * @param price1
     * @param price2
     * @return
     */
    public static String rate(int price1, int price2) {
        try {
            BigDecimal num1 = new BigDecimal(price1 * 10);
            BigDecimal num2 = new BigDecimal(price2);
            BigDecimal num3 = num1.divide(num2, 1, ROUND_HALF_UP);
            return num3.toString().replace(".0", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "无";
    }

    /**
     * 月套餐天数计算
     * @param price1
     * @param price2
     * @return
     */
    public static String rateMonthPackage(int price1, int price2) {
        try {
            BigDecimal num1 = new BigDecimal(price1);
            BigDecimal num2 = new BigDecimal(price2);
            BigDecimal num3 = num1.divide(num2, 2, ROUND_HALF_UP);
            return num3.toString().replace(".0", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "无";
    }
    /**
     * 计算折扣后价格
     *
     * @param price
     * @param rate
     * @return
     */
    public static int getRatePrice(int price, int rate) {
        if (rate > 0 && rate < 100) {
            try {
                BigDecimal num1 = new BigDecimal(price * rate);
                BigDecimal num2 = new BigDecimal(100);
                BigDecimal num3 = num1.divide(num2, 0, ROUND_HALF_UP);
                return num3.intValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return price;
    }

    /**
     * 获取折扣价格，元为单位 结果保留两位小数
     * @param price
     * @param rate
     * @return
     */
    public static double getRatePriceDouble(double price, double rate) {
        if (rate > 0 && rate < 1d) {
            try {
                BigDecimal num1 = new BigDecimal(price * rate * 100);
                BigDecimal num2 = new BigDecimal(100);
                BigDecimal num3 = num1.divide(num2, 2, ROUND_HALF_UP);
                return num3.doubleValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return price;
    }

    /**
     * 乘法
     * @param price1
     * @param price2
     * @return
     */
    public static double multiply(double price1, double price2) {
        try {
            BigDecimal num1 = new BigDecimal(price1 * price2 * 10000);
            BigDecimal num2 = new BigDecimal(10000);
            BigDecimal num3 = num1.divide(num2, 2, ROUND_HALF_UP);
            return num3.doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 计算分成
     *
     * @param price
     * @param rate
     * @return
     */
    public static int getFcRatePrice(int price, int rate) {
        if (rate > 0 && rate < 100) {
            try {
                BigDecimal num1 = new BigDecimal(price * rate);
                BigDecimal num2 = new BigDecimal(100);
                BigDecimal num3 = num1.divide(num2, 0, ROUND_HALF_UP);
                return num3.intValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * 分转换为万元
     *
     * @param fen 分
     * @return 元
     */
    public static String fenToWan(int fen) {
        try {
            return new BigDecimal(fen).divide(new BigDecimal(1000000)).setScale(2).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0.00";
    }

    /**
     * 万元转换为分
     *
     * @param yuan 元
     * @return 分
     */
    public static int wanToFen(String yuan) {
        try {
            return BigDecimal.valueOf(Double.valueOf(yuan)).multiply(new BigDecimal(1000000)).intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    public static boolean isCorreactMoeny(Number money){
        return money != null && money.longValue() > 0;
    }


    /**
     * 将单位元,格式化输出,保留三位小数(厘)
     * @param n 数值,元
     * @return
     */
    public static double yuanFormatLi(Number n){
        if( n == null){
            return 0D;
        }

        BigDecimal b = null;
        if(n instanceof  BigDecimal){
            b = (BigDecimal)n;
        }else{
            b = new BigDecimal(n.doubleValue());
        }
        return b.setScale(LI, ROUND_HALF_UP).doubleValue();
    }

    /**
     * 厘转成厘分（精确到分的厘）
     *
     * @param li           厘
     * @param roundingMode 精度
     * @return 厘
     */
    public static long formatLiToLi(Long li, RoundingMode roundingMode) {
        if (li==null) {
            return 0;
        }
        return new BigDecimal(li.longValue()).divide(new BigDecimal("10"), 0, roundingMode).multiply(new BigDecimal("10")).longValue();
    }
}
