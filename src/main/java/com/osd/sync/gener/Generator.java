package com.osd.sync.gener;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * @title: Generator
 * @description: mybatis自动生成  测试
 * @date: 2020/7/18
 * @author: stuil
 * @copyright: Copyright (c) 2020
 * @version:
 */

public class Generator {
    // 固定
    private static final String projectPath = System.getProperty("user.dir");
    private static final String outPutDir = projectPath + "/src/main/java";
    // 自定义
    private static final String author = "stuil";
    public static String Pagk=".mydb";
    // 包名
    private static final String packageName = "com.osd.sync";
    private static final String moduleName = "";
    private static final String[] tableName = ("").split(",");
    private static final String tablePrefix = "";

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 设置全局配置
        mpg.setGlobalConfig(getGlobalConfig());
        // 设置数据源配置
        mpg.setDataSource(getDataSourceConfig());
        // 包配置
        PackageConfig pc = getPackageConfig();
        mpg.setPackageInfo(pc);
        // 自定义配置
        mpg.setCfg(getInjectionConfig());
        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);
        // 策略配置
        mpg.setStrategy(getStrategyConfig());
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

    private static GlobalConfig getGlobalConfig() {
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(outPutDir);
        gc.setAuthor(author);
        gc.setOpen(false);
        gc.setEntityName("%sEntity");
        gc.setMapperName("%sMapper");
        gc.setServiceName("%sService");
        // 是否覆盖文件，默认false不覆盖
        gc.setFileOverride(true);
        // XML ResultMap
        gc.setBaseResultMap(true);
        // XML columList
        gc.setBaseColumnList(true);
      //  gc.setSwagger2(true); //实体属性 Swagger2 注解
        return gc;
    }

    private static DataSourceConfig getDataSourceConfig() {
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/mydb?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false");
        // dsc.setSchemaName("public");
        String driverName = "com.mysql.cj.jdbc.Driver";
        dsc.setDriverName(driverName);
        dsc.setUsername("root");
        dsc.setPassword("abc123");
        return dsc;
    }

    private static PackageConfig getPackageConfig() {
        // 包配置
        PackageConfig pc = new PackageConfig();
        // 包名称
        pc.setParent(packageName);
        // 模块名称
        pc.setModuleName(moduleName);
        pc.setParent("com.osd.sync");
        pc.setEntity("entity"+Pagk);
        pc.setMapper("mapper"+Pagk);
        pc.setService("service"+Pagk);
        pc.setServiceImpl("service"+Pagk);
        pc.setXml("mapper");
        return pc;
    }

    private static InjectionConfig getInjectionConfig() {
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // ** 子项目 本文为父子项目 所以加上子项目路径，否则文件生成至父项目下
                return projectPath + "/src/main/resources/mapper/mydb/" + tableInfo.getEntityName().replace("Entity", "")
                        + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        return cfg;
    }

    private static StrategyConfig getStrategyConfig() {
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类
//        strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        // 写于父类中的公共字段
//        strategy.setSuperEntityColumns("id");
//        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setInclude(tableName);
        strategy.setControllerMappingHyphenStyle(true);
       // strategy.setTablePrefix(tablePrefix + "_");
        return strategy;
    }

}
