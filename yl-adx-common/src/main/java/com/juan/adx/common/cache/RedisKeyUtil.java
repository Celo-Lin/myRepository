package com.juan.adx.common.cache;

import org.apache.commons.lang3.StringUtils;

public class RedisKeyUtil {

	/*************************** 管理后台 Redis Key ***************************/
	public static String getTokenCodeKey(String uid) {
		return String.format(RedisKey.ManageKey.TOKEN_CODE_KEY, uid);
	}
	
	public static String getChannelTokenCodeKey(String uid) {
		return String.format(RedisKey.ChannelManageKey.CHANNEL_TOKEN_CODE_KEY, uid);
	}
	
	public static String getIndexIncomeKey(String dateStr) {
		return String.format(RedisKey.ManageKey.INDEX_INCOME_KEY, dateStr);
	}
	
	public static String getIndexIncomeSspKey(String dateStr, Integer sspPartnerId) {
		return String.format(RedisKey.ManageKey.INDEX_INCOME_SSP_KEY, dateStr, sspPartnerId);
	}
	
	
	/*************************** ADX API Redis Key ***************************/

	/**
	 * DSP策略想参数key
	 */
	public static String getDspStrategyConfigKey(Integer dspId) {
		return String.format(RedisKey.AdxApiKey.DSP_STRATEGY_DATA_KEY, dspId);
	}

	public static String getAdSlotDataKey(Integer slotId) {
		return String.format(RedisKey.AdxApiKey.ADVERT_SLOT_DATA_KEY, slotId);
	}

	public static String getDspDeviceReqMaxKey(String dateStr, Integer budgetId, String deviceId) {
		return String.format(RedisKey.AdxApiKey.DSP_DEVICE_REQUEST_MAX, dateStr, budgetId, deviceId);
	}
	
	public static String getDspReqLimitTypeKey(String dateStr, Integer slotId, Integer budgetId) {
		return String.format(RedisKey.AdxApiKey.DSP_REQUEST_LIMIT_TYPE, dateStr, slotId, budgetId);
	}
	
	public static String getDspReqMaxKey(String dateStr, Integer slotId, Integer budgetId, Integer limitType) {
		return String.format(RedisKey.AdxApiKey.DSP_REQUEST_MAX, dateStr, slotId, budgetId, limitType);
	}
	
	public static String getRequestRecordKey(String dateStr) {
		return String.format(RedisKey.AdxApiKey.STATISTICS_REQUEST_RECORD, dateStr);
	}
	
	public static String getAdBidRecordKey(String dateStr) {
		return String.format(RedisKey.AdxApiKey.AD_BID_RECORD, dateStr);
	}
	
	public static String getSlotReportDataStatisticsKey(String dateStr) {
		return String.format(RedisKey.AdxApiKey.SLOT_REPORT_DATA_STATISTICS, dateStr);
	}
	
	public static String getSlotRequestTimeMonitoringKey(String dateStr, Integer slotId) {
		return String.format(RedisKey.AdxApiKey.SLOT_REQUEST_TIME_MONITORING, dateStr, slotId);
	}
	
	public static String getRtbReportDisplayRecordKey() {
		return RedisKey.AdxApiKey.RTB_REPORT_DISPLAY_RECORD;
	}


}
