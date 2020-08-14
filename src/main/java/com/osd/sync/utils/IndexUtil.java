package com.osd.sync.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: IndexUtil
 * @description:
 * @date: 2020/8/13
 * @author: zwh
 * @copyright: Copyright (c) 2020
 * @version: 1.0
 */

public class IndexUtil {
    public static void main(String[] args) {
        List<Df> dfs=new ArrayList<>();
        Df df=new Df();
        df.setA(1);
        df.setB(2);
        df.setC("3");
        Df df2=new Df();
        df2.setA(5);
        df2.setB(6);
        df2.setC("7");
        Df df3=new Df();
        df3.setA(8);
        df3.setB(9);
        df3.setC("u");
        dfs.add(df);
        dfs.add(df2);
        dfs.add(df3);
        Map<String,Object> map=new HashMap<>();
        dfs.forEach(item->{
            map.put(item.getC(),item.getA());
        });
        System.out.println(JSONObject.toJSONString(map));
    }
}
