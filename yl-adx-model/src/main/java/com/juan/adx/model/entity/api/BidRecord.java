package com.juan.adx.model.entity.api;

import lombok.Data;

@Data
public class BidRecord {

	private Long traceId;
	
	private Integer sspPartnerId;
	
	private Integer dspPartnerId;
	
	private Integer appId;
	
	private Integer slotId;
	
	private Integer budgetId;
	
	private Integer cooperationMode;
	
	/**
	 * SSP的出价，单位：分
	 */
	private Integer sspBidPrice;
	
	/**
	 * 返回给SSP的报价，单位：分
	 */
	private Integer sspReturnPrice;
	
	/**
	 * 给DSP的出价，单位：分
	 */
	private Integer dspBidPrice;
	
	/**
	 * DSP返回的报价，单位：分
	 */
	private Integer dspReturnPrice;
	
	/**
	 * 展示事件上报状态 @ReportStatus
	 */
	private Integer reportDisplayStatus;
	
	/**
	 * 媒体底价，单位：分
	 */
	private Integer sspFloorPriceSnapshot;
	
	/**
	 * 上游底价，单位：分
	 */
	private Integer dspFloorPriceSnapshot;
	
	/**
	 * 溢价率
	 */
	private Integer sspPremiumRateSnapshot;
	
	/**
	 * 底价上浮比率
	 */
	private Integer dspFloatingRateSnapshot;
	
	private Long ctime;
}
