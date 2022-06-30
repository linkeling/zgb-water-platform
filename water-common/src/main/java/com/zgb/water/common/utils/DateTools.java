package com.zgb.water.common.utils;

import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * 时间工具类
 */
public class DateTools {

    /**
     * 字符串转时间类型
     * @param str
     * @return
     */
    public static Date stringTranDate(String str){
        Date date= DateUtil.parseDate(str);
        return date;
    }
}
