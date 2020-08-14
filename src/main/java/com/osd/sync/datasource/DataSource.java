package com.osd.sync.datasource;

import java.lang.annotation.*;

/**
 * @title: DataSource
 * @description:
 * @date: 2020/8/12
 * @author: zwh
 * @copyright: Copyright (c) 2020
 * @version: 1.0
 */

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    DataSourceEnum value() default DataSourceEnum.DB1;
}
