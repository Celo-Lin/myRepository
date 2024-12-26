package com.juan.adx.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanMap;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ParamUtils {


    private static final Logger log = LoggerFactory.getLogger(ParamUtils.class);

    public static String getStringOrEmpty(String value) {
        return value != null ? value : StringUtils.EMPTY;
    }

    public static int getIntOrZero(Integer value) {
        return value != null ? value.intValue() : 0;
    }

    public static long getLongOrZero(Long value) {
        return value != null ? value.longValue() : 0l;
    }


    /**
     * java对象转map
     *
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String, String> beanToMap(Object bean) {
        BeanMap beanMap = BeanMap.create(bean);
        Map<String, String> map = new HashMap<>();
        beanMap.forEach((key, value) -> {
            if (value != null) {
                map.put(String.valueOf(key), value.toString());
            }
        });
        return map;
    }


    /**
     * 根据经度或纬度，获取经纬度精确度
     *
     * @return
     */
    public static String calculateAccuracy(Double ll) {
        if (ll == null) {
            return null;
        }

        // 将经度和纬度转换为字符串
        String llStr = String.valueOf(ll);

        // 查找经度和纬度的小数点位置
        int llDecimalIndex = llStr.indexOf('.');

        // 计算经度和纬度的精确度
        int decimalPlaces = llStr.length() - llDecimalIndex - 1;
        double accuracy = 0.0;

        if (decimalPlaces >= 1 && decimalPlaces <= 5) {
            // 精度(约等于) = 10^(5-decimalPlaces)
            accuracy = Math.pow(10, 5 - decimalPlaces);
        } else if (decimalPlaces >= 6 && decimalPlaces <= 7) {
            // 精度(约等于) = 0.1^(decimalPlaces-5)
            accuracy = Math.pow(0.1, decimalPlaces - 5);
        } else if (decimalPlaces == 8) {
            accuracy = 0.001; // 精度(约等于) = 0.001米
        } else {
            // 如果小数点位数不在1到8之间，则返回null
            return "0.001";
        }
        return String.valueOf(accuracy);
    }

    /**
     * 判断参数是否可以转为整型
     * @param str
     * @return
     */
    public static boolean isLong(String str) {
        if (str == null) {
            return false;
        }
        // 检查字符串是否匹配整数的正则表达式
        String regex = "-?\\d+";
        if (!str.matches(regex)) {
            return false;
        }

        // 尝试解析为整数并捕获异常
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
