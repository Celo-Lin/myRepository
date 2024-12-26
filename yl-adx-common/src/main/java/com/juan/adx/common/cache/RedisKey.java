package com.juan.adx.common.cache;

import org.apache.commons.lang3.StringUtils;


public interface RedisKey {
	
	String KEY_PREX = "adx_";
	
	interface CommonKey{
		
		
	}
	
	interface ManageKey{
		
		String TOKEN_CODE_KEY = StringUtils.join(KEY_PREX, "token_code_%s");
		
		String INDEX_INCOME_KEY = StringUtils.join(KEY_PREX, "index_income_%s");
		
		String INDEX_INCOME_SSP_KEY = StringUtils.join(KEY_PREX, "index_income_ssp_%s_%d");

	}
	
	interface AdxApiKey{

		/**
		 * DSP策略想参数key
		 */
		String DSP_STRATEGY_DATA_KEY = StringUtils.join(KEY_PREX, "dsp_strategy_config_%d");


		String ADVERT_SLOT_DATA_KEY = StringUtils.join(KEY_PREX, "ad_slot_data_%d");

		/**
		 * DSP 单设备每日请求数
		 */
		String DSP_DEVICE_REQUEST_MAX = StringUtils.join(KEY_PREX, "dsp_device_request_max_%s_%d_%s");

		/**
		 * DSP 请求限制维度类型
		 */
		String DSP_REQUEST_LIMIT_TYPE = StringUtils.join(KEY_PREX, "dsp_request_limit_type_%s_%d_%d");
		
		/**
		 * DSP 每日请求数
		 */
		String DSP_REQUEST_MAX = StringUtils.join(KEY_PREX, "dsp_request_max_%s_%d_%d_%d");
		
		/**
		 * DSP API 请求数记录
		 */
		String STATISTICS_REQUEST_RECORD = StringUtils.join(KEY_PREX, "statistics_request_record_%s");
		
		/**
		 * 广告出价记录
		 */
		String AD_BID_RECORD = StringUtils.join(KEY_PREX, "ad_bid_record_%s");
		
		/**
		 * 广告位上报数据统计
		 */
		String SLOT_REPORT_DATA_STATISTICS = StringUtils.join(KEY_PREX, "slot_report_data_statistics_%s");
		
		/**
		 * 广告位请求时间监控
		 */
		String SLOT_REQUEST_TIME_MONITORING = StringUtils.join(KEY_PREX, "slot_request_time_monitoring_%s_%d");
		
		/**
		 * RTB展示事件回传记录
		 */
		String RTB_REPORT_DISPLAY_RECORD = StringUtils.join(KEY_PREX, "rtb_report_display_record");
		
	}
	
	interface ChannelManageKey{
		
		String CHANNEL_TOKEN_CODE_KEY = StringUtils.join(KEY_PREX, "channel_token_code_%s");
		
	}
	

}
