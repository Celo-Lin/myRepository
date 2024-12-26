package com.juan.adx.model.form.manage;

import lombok.Data;

@Data
public class DingLiDataStatisticsForm {
	
	/**
	 * 筛选条件：开始时间，时间戳，精确到秒
	 */
	private Long startTime;
	
	/**
	 *筛选条件：结束时间，时间戳，精确到秒
	 */
	private Long endTime;
	
	/**
	 * 广告ID
	 */
	private Integer adId;
	
	/**
	 * 渠道ID
	 */
	private Integer channelId;
	
	private Integer pageNo = 1;

	private Integer pageSize = 20;
}
