package com.juan.adx.model.entity.api;

import lombok.Data;

@Data
public class AdSlotBudgetWrap {

	/********************************Begin 广告预算配置信息 ********************************/
	
	private String dspAppId;
	
	private String dspSlotId;
	
	private Integer limitType;
	
	private Integer maxRequests;
	
	private String packageName;
	
	private Integer sspFloorPrice;
	
	private Integer dspFloorPrice;
	
	private Integer sspPremiumRate;
	
	private Integer dspFloatingRate;
	
	private Integer dspPartnerId;
	
	private Integer slotId;
	
	private Integer budgetId;
	
	private boolean hasRta;
	
	private Integer rtaPriorityValue;
	
	private Integer rtaSspFloorPrice;
	
	
	/********************************End 广告预算信息 ********************************/
	
	
	
	
	/********************************Begin 预算关联的DSP信息 ********************************/
	
	/**
	 * DSP 拼音名称
	 */
	private String pinyinName;
	
	/**
	 * DSP API 地址
	 */
	private String apiUrl;
	
	/**
	 * DSP 价格加密密钥
	 */
	private String priceEncryptKey;
	
	/**
	 * RTA 接口请求地址
	 */
	private String rtaApiUrl;
	
	/**
	 * RTA 接口签名密钥
	 */
	private String rtaApiSignKey;
	
	/********************************End 预算关联的DSP信息 ********************************/
	
	
	
	
	/********************************Begin 预算信息 ********************************/
	
	private String budgetName;
	
	private String budgetTitle;
	
	private String pictureUrl;
	
	private String videoUrl;
	
	private String deeplink;
	
	private String h5link;

	private String downloadUrl;
	
	private Integer deviceMaxRequests;
	
	private Integer budgetType;
	
	/********************************End 预算信息 ********************************/
	
}
