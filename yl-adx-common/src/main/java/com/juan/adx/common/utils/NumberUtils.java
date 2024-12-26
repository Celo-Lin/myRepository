package com.juan.adx.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

public class NumberUtils {

    public static float toScaledBigDecimal(final Float value, final int scale, final RoundingMode roundingMode) {
        if (value == null) {
            return BigDecimal.ZERO.floatValue();
        }
        BigDecimal bg = new BigDecimal(value);
        return bg.setScale(scale, (roundingMode == null) ? RoundingMode.HALF_UP : roundingMode).floatValue();
    }
    
    
    public static double toScaledBigDecimal(final Double value, final int scale, final RoundingMode roundingMode) {
    	if (value == null) {
    		return BigDecimal.ZERO.doubleValue();
    	}
    	BigDecimal bg = new BigDecimal(value);
    	return bg.setScale(scale, (roundingMode == null) ? RoundingMode.HALF_UP : roundingMode).doubleValue();
    }
    
    public static boolean isNumber(String value) {
    	return org.apache.commons.lang3.math.NumberUtils.isCreatable(value);
    }
    
    public static int toInt(String value) {
    	int result = Optional.ofNullable(value)
                .map(s -> {
                    try {
                        return Integer.parseInt(s);
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                })
                .orElse(0);
    	return result;
    }

    /**
     * 获取double类型小数点位数
     * @param number
     * @return
     */
    public static int getDecimalPlaces(Double number) {
        if(number == null){
            return 0;
        }
        String numberStr = Double.toString(number);
        int index = numberStr.indexOf('.');
        if (index < 0) {
            return 0; // 如果没有小数点，则返回0
        } else {
            return numberStr.length() - index - 1; // 返回小数点后的位数
        }
    }

}
