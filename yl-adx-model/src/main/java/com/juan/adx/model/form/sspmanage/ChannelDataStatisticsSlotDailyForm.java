package com.juan.adx.model.form.sspmanage;

import lombok.Data;

@Data
public class ChannelDataStatisticsSlotDailyForm {

	
	/**
	 * 筛选条件：应用名称（最左原则，模糊匹配）
	 */
	private String appName;
	
	/**
	 * 筛选条件：广告位名称（最左原则，模糊匹配）
	 */
	private String adSlotName;
	
	/**
	 * 筛选条件：广告位ID（精准匹配）
	 */
	private String adSlotId;
	
	/**
	 * 筛选条件：开始时间，时间戳，精确到秒
	 */
	private Long startTime;
	
	/**
	 *筛选条件：结束时间，时间戳，精确到秒
	 */
	private Long endTime;
	
	private Integer sspPartnerId;
	
	private Integer pageNo = 1;

	private Integer pageSize = 20;
}
