package com.osd.sync.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @title: MultipleDataSource
 * @description:
 * @date: 2020/8/12
 * @author: zwh
 * @copyright: Copyright (c) 2020
 * @version: 1.0
 */

public class MultipleDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSource();
    }
}
