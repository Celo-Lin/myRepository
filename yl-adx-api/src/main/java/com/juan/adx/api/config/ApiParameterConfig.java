package com.juan.adx.api.config;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ApiParameterConfig {
	
	/**
	 * 	接口签名key
	 */
	public static String apiSignKey = "c5409a3df08test4ac47baaa7fc32a8e";
	
	/**
	 * 	Base64密钥
	 */
	public static String base64EncryptKey = "YWTestAyAQAEgOIK";
	
	/**
	 * 打印DSP获取广告接口请求、响应日志开关：true 开 | false 关
	 */
	public static boolean printLogDspRequestSwitch = false;
	
	/**
	 * 打印SSP获取广告接口请求、响应日志开关：true 开 | false 关
	 */
	public static boolean printLogSspRequestSwitch = false;
	
	/**
	 * 打印SSP请求事件上报接口开关：true 开 | false 关
	 */
	public static boolean printLogSspReportSwitch = false;
	
	/**
	 *  请求DSP接口超时时间(毫秒)
	 */
	public static int requestDspApiTimeout = 2000;
	
	/**
	 *  RTB模式下等待竞价子线程执行超时时间(毫秒)
	 *  注意：建议此值需要大于 requestDspApiTimeout 200毫秒 
	 */
	public static int rtbWaitingChildThreadTimeout = 2200;
	
	
	/**
	 * ADX广告展示上报URL
	 */
	public static String adxReportDisplayUrl = "http://api.juan.com/yradx/tracking/event/display?info=%s&slotId=%d&ts=__MF_EVENT_TIME_MS__";

	/**
	 * ADX广告点击上报URL
	 */
	public static String adxReportClickUrl = "http://api.juan.com/yradx/tracking/event/click?info=%s&slotId=%d&ts=__MF_EVENT_TIME_MS__";
	
	/**
	 * ADX广告下载上报URL
	 */
	public static String adxReportDownloadUrl = "http://api.juan.com/yradx/tracking/event/download?info=%s&slotId=%d&ts=__MF_EVENT_TIME_MS__";
	
	/**
	 * ADX广告安装上报URL
	 */
	public static String adxReportInstallUrl = "http://api.juan.com/yradx/tracking/event/install?info=%s&slotId=%d&ts=__MF_EVENT_TIME_MS__";
	
	/**
	 * ADX广告dp上报URL
	 */
	public static String adxReportDpUrl = "http://api.juan.com/yradx/tracking/event/deeplink?info=%s&slotId=%d&ts=__MF_EVENT_TIME_MS__";
	
	/**
	 * ADX广告RTB竞胜上报URL
	 */
	public static String adxReportWinUrl = "http://api.juan.com/marketflow/tracking/event/win?info=%s&slotId=%d&ts=__MF_EVENT_TIME_MS__";
	
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}
