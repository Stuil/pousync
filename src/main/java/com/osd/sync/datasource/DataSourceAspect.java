package com.osd.sync.datasource;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @title: DataSourceAspect
 * @description: AOP
 * @date: 2020/8/12
 * @author: zwh
 * @copyright: Copyright (c) 2020
 * @version: 1.0
 */

@Component
@Slf4j
@Aspect
@Order(-1)
public class DataSourceAspect {

    @Pointcut("@within(com.osd.sync.datasource.DataSource) || @annotation(com.osd.sync.datasource.DataSource)")
    public void pointCut(){

    }

    @Before("pointCut() && (@annotation(dataSource)||@within(dataSource))")
    public void doBefore(DataSource dataSource){
        DataSourceContextHolder.setDataSource(dataSource.value().getValue());
    }

    @After("pointCut()")
    public void doAfter(){
        DataSourceContextHolder.clear();
    }
}
