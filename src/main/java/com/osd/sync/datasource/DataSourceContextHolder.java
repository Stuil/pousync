package com.osd.sync.datasource;

/**
 * @title: DataSourceContextHolder
 * @description: 数据源配置
 * @date: 2020/8/12
 * @author: zwh
 * @copyright: Copyright (c) 2020
 * @version: 1.0
 */

public class DataSourceContextHolder {
    private static final ThreadLocal<String> contextHolder = new InheritableThreadLocal<>();

    /**
     *  设置数据源
     * @param db
     */
    public static void setDataSource(String db){
        contextHolder.set(db);
    }

    /**
     * 取得当前数据源
     * @return
     */
    public static String getDataSource(){
        return contextHolder.get();
    }

    /**
     * 清除上下文数据
     */
    public static void clear(){
        contextHolder.remove();
    }
}
