package com.osd.sync.utils;

import java.util.concurrent.atomic.AtomicInteger;

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
        System.out.println(getA());
    }
    public static int getA(){
        int a=0;
        for(int i=0;i<5;i++){

           a++;
            System.out.println(a);
        }
        return a;
    }
}
