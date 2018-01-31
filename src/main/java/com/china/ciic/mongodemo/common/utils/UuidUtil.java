package com.china.ciic.mongodemo.common.utils;

import java.util.UUID;

/**
 * uuid的工具类
 */
public class UuidUtil {

    /**
     * 生成uuid去掉"-"
     * @return
     */
    public static String randomUuidString(){
        String str = UUID.randomUUID().toString();
        return str.replaceAll("-","");
    }

}
