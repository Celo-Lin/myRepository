package com.juan.adx.common.utils;

import java.math.RoundingMode;

public class PriceUtil {

	/**
	 * 将单位元的金额，转化为单位为分的每千次展示成本价格（ECPM）
	 * @return
	 */
    public static long convertToEcpm(Double actualAmount) {
    	if(actualAmount == null || actualAmount.doubleValue() <= 0) {
    		return 0l;
    	}
    	Double ecpm = actualAmount.doubleValue() * 1000 * 100;
    	return ecpm.longValue();
    }
    
    /**
     * 将单位元的金额，转化为单位为分的每千次展示成本价格（ECPM）
     * @return
     */
    public static long convertToEcpm(Long actualAmount) {
    	if(actualAmount == null || actualAmount.longValue() <= 0) {
    		return 0l;
    	}
    	Long ecpm = actualAmount.longValue() * 1000 * 100;
    	return ecpm.longValue();
    }
    
    public static void main(String[] args) {
		System.out.println(NumberUtils.toScaledBigDecimal(0.91, 2, RoundingMode.HALF_UP));
	}
    
    /**
     * 将单位为分的每千次展示成本价格，转换为元
     * @return
     */
    public static double convertToActualAmount(Long ecpm) {
    	if(ecpm == null || ecpm.longValue() <= 0) {
    		return 0d;
    	}
    	double actualAmount = ecpm.doubleValue() / (1000 * 100);
    	actualAmount = NumberUtils.toScaledBigDecimal(actualAmount, 2, RoundingMode.HALF_UP);
    	return actualAmount;
    }
    
    /**
     * 将单位为分的每千次展示成本价格，转换为元
     * @return
     */
    public static double convertToActualAmount(Double ecpm) {
    	if(ecpm == null || ecpm.doubleValue() <= 0) {
    		return 0d;
    	}
    	double actualAmount = ecpm.doubleValue() / (1000 * 100);
    	actualAmount = NumberUtils.toScaledBigDecimal(actualAmount, 2, RoundingMode.HALF_UP);
    	return actualAmount;
    }
    
    /**
     * 计算每千次展示单价（ECPM）
     * @param estimateIncome	总收益金额
     * @param displayCount		总展示数
     * @return					ecmp
     */
    public static double calcEcpm(Long estimateIncome, Integer displayCount) {
    	estimateIncome = estimateIncome == null || estimateIncome.longValue() <= 0 ? 0l : estimateIncome;
    	if(displayCount == null || displayCount.longValue() <= 0) {
    		return 0d;
    	}
    	double ecpm = (estimateIncome.doubleValue() / displayCount.doubleValue() * 1000) / (1000 * 100);
    	ecpm = NumberUtils.toScaledBigDecimal(ecpm, 2, RoundingMode.HALF_UP);
    	return ecpm;
    }
    
    /**
     * 计算每千次展示单价（ECPM）
     * @param estimateIncome	总收益金额
     * @param displayCount		总展示数
     * @return					ecmp
     */
    public static double calcEcpm(Double estimateIncome, Integer displayCount) {
    	estimateIncome = estimateIncome == null || estimateIncome.doubleValue() <= 0 ? 0d : estimateIncome;
    	if(displayCount == null || displayCount.longValue() <= 0) {
    		return 0d;
    	}
    	double ecpm = (estimateIncome.doubleValue() / displayCount.doubleValue() * 1000) / (1000 * 100);
    	ecpm = NumberUtils.toScaledBigDecimal(ecpm, 2, RoundingMode.HALF_UP);
    	return ecpm;
    }
    
    /**
     * 计算转化率
     * @param dividend	被除数（分子）
     * @param divisor	除数（分母），除数不能为0，否则返回 0
     * @return
     */
    public static double calcRatio(Integer dividend, Integer divisor) {
    	double ratio =  invalidValue(divisor) ? 0d : (dividend.doubleValue() / divisor.doubleValue()) * 100; 
    	ratio = NumberUtils.toScaledBigDecimal(ratio, 2, RoundingMode.HALF_UP);
    	return ratio;
    }
    
    /**
     * 计算转化率
     * @param dividend	被除数（分子）
     * @param divisor	除数（分母），除数不能为0，否则返回 0
     * @return
     */
    public static double calcRatio(Double dividend, Double divisor) {
    	double ratio =  invalidValue(divisor) ? 0d : (dividend.doubleValue() / divisor.doubleValue()) * 100; 
    	ratio = NumberUtils.toScaledBigDecimal(ratio, 2, RoundingMode.HALF_UP);
    	return ratio;
    }
    
    public static boolean invalidValue(Integer value) {
		return value == null || value.intValue() <= 0;
	}
    
    public static boolean invalidValue(Double value) {
    	return value == null || value.intValue() <= 0;
    }
}
