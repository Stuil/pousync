package com.osd.sync.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.osd.sync.entity.gas.*;
import com.osd.sync.entity.mydb.CardoprecordEntity;
import com.osd.sync.entity.mydb.ConsumerEntity;
import com.osd.sync.entity.mydb.MakeupcardEntity;
import com.osd.sync.entity.mydb.UsercardinfoEntity;
import com.osd.sync.model.UserCardModel;
import com.osd.sync.service.IndexServer;
import com.osd.sync.service.UserCardService;
import com.osd.sync.service.gas.*;
import com.osd.sync.service.mydb.*;
import com.osd.sync.utils.MoneyUtil;
import freemarker.template.utility.NumberUtil;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.nutz.lang.Lang;
import org.nutz.lang.Times;
import org.nutz.lang.util.NutMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @title: IndexController
 * @description: hh
 * @date: 2020/8/13
 * @author: zwh
 * @copyright: Copyright (c) 2020
 * @version: 1.0
 */
@RestController
@Slf4j
public class IndexController {

    @Autowired
    IndexServer indexServer;

    @RequestMapping("/sync")
    public String sync(Integer id) {
        long start = System.currentTimeMillis() / 1000;
        indexServer.syncInfo(id);
        long end = System.currentTimeMillis() / 1000;
        return start + "-----" + end + "----------" + (end - start)+"===同步人員id"+(id==null?"all":id+"");
    }
}
