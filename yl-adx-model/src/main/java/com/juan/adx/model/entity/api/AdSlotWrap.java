package com.juan.adx.model.entity.api;

import java.util.List;

import lombok.Data;

@Data
public class AdSlotWrap {
	
	/********************************Begin 应用信息 ********************************/
	private String appName;
	
	private String appPackageName;
	
	private Integer systemPlatform;
	
	private Integer industryId;
	
	private Integer appStoreId;
	/********************************End 应用信息 ********************************/
	
	
	
	/********************************Begin 广告位信息 ********************************/
	private Integer slotId;

	private String slotName;
	
	private Integer appId;
	
	private Integer slotType;
	
	private Integer slotCooperationMode;
	
	private Integer sspBidPrice;

	private Integer sspPartnerId;
	
	private Boolean status;
	/********************************End 应用信息 ********************************/
	
	
	
	/********************************Begin 广告位预算信息 ********************************/
	
	private List<AdSlotBudgetWrap> adSlotBudgets;
	
	/********************************End 广告位预算信息 ********************************/
}
