package com.juan.adx.model.dto.sspmanage;

import lombok.Data;

@Data
public class ChannelDataStatisticsIndexIncomeDto {

	/**
	 * 昨日预估收益, 精确2位小数
	 */
	private Double estimatedIncomeYesterday;

	/**
	 * 昨日预估收益环比, 精确2位小数
	 */
	private Double estimatedIncomeYesterdayRatio;
	
	/**
	 * 近7天预估收益, 精确2位小数
	 */
	private Double estimatedIncomeSevenDay;
	
	/**
	 * 本月预估收益, 精确2位小数
	 */
	private Double estimatedIncomeMonth;
	
	/**
	 * 本月预估收益环比, 精确2位小数
	 */
	private Double estimatedIncomeMonthRatio;
	
}
