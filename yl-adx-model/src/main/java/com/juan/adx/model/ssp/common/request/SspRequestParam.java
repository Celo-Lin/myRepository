package com.juan.adx.model.ssp.common.request;

import lombok.Data;

@Data
public class SspRequestParam {
	
	/**
	 * 每次广告请求，生成的唯一ID，便于出现问题时，提供此ID排查原因
	 */
	private String requestId;
	
	/**
	 * 广告位信息
	 */
	private SspReqSlot slot;
	
	/**
	 * 交易信息
	 */
	private SspReqDeal deal;
	
	/**
	 * App信息
	 */
	private SspReqApp app;
	
	/**
	 * 用户信息
	 */
	private SspReqUser user;
	
	/**
	 * 设备信息
	 */
	private SspReqDevice device;
	
	/**
	 *  移动设备序列号标识信息，允许同时传多个序列号，但至少传一种
	 */
	private SspReqDeviceId deviceId;
	
	/**
	 * IOS移动设备CAID
	 */
	private SspReqDeviceCaid caId;

	/**
	 * 网络信息
	 */
	private SspReqNetwork network;
	
	/**
	 * 地理位置信息
	 */
	private SspReqGeo geo;
	
	/**
	 * 扩展参数
	 */
	private SspReqExt ext;
	

}
