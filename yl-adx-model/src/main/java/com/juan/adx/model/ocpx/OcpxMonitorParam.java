package com.juan.adx.model.ocpx;

import lombok.Data;

@Data
public class OcpxMonitorParam {
	
	/**
	 * 渠道标识
	 */
	private Integer acmId;

	/**
	 * 链接对应的渠道号 如 1024339d，
	 * 是否必填，是
	 */
	private String packageId;
	
	/**
	 * 广告计划ID
	 */
	private String planId;
	
	/**
	 * 广告计划名称
	 */
	private String planName;
	
	/**
	 * 广告单元ID
	 */
	private String unitId;
	
	/**
	 * 广告单元名称
	 */
	private String unitName;
	
	/**
	 * 广告创意ID
	 */
	private String creativeId;
	
	/**
	 * 广告创意名称
	 */
	private String creativeName;
	
	/**
	 * Android设备标识
	 */
	private String imei;
	
	/**
	 * Android设备标识（MD5）
	 */
	private String imeiMd5;
	
	/**
	 * Android设备标识
	 */
	private String oaid;
	
	/**
	 * Android设备标识（MD5）
	 */
	private String oaidMd5;
	
	/**
	 * Android设备标识
	 */
	private String androidId;
	
	/**
	 * 移动设备UA 需要 urlencode 编码
	 */
	private String ua;
	
	/**
	 * IP地址
	 */
	private String ip;
	
	/**
	 * 渠道回调地址 需要 urlencode 编码
	 */
	private String callbackUrl;
	
	/**
	 * 渠道请求唯一标识（渠道请求我方时携带）
	 */
	private String requestId;
	
	/**
	 * 广告点击时间戳（秒）
	 */
	private Long adClickTime;
	
}
