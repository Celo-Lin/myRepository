package com.juan.adx.model.form.manage;

import lombok.Data;

@Data
public class IndexTrendDataForm {

	/**
	 * 趋势图类型：@IndexTrendType
	 */
	private Integer trendType;

	/**
	 * 筛选条件：开始时间，时间戳，精确到秒
	 */
	private Long startTime;
	
	/**
	 * 筛选条件：结束时间，时间戳，精确到秒
	 */
	private Long endTime;
}
