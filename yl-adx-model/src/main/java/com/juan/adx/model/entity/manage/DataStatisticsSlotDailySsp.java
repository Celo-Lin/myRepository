package com.juan.adx.model.entity.manage;

import lombok.Data;

@Data
public class DataStatisticsSlotDailySsp {

	private Integer id;
	private Integer sspPartnerId;
	private Integer appId;
	private Integer adSlotId;
	private Integer maxRequests;
	private Integer requestCount;
	private Integer fillCount;
	private Integer displayCount;
	private Integer clickCount;
	private Integer downloadCount;
	private Integer installCount;
	private Integer deeplinkCount;
	private Long sspEstimateIncome;
	private Integer auditStatus;
	private Long date;

	
}
