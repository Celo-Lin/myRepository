package com.juan.adx.model.dto.manage;

import lombok.Data;

@Data
public class DataStatisticsSlotDailyDto {

	private Integer id;
	private Integer sspPartnerId;
	private String sspPartnerName;
	private Integer dspPartnerId;
	private String dspPartnerName;
	private Integer appId;
	private String appName;
	private Integer adSlotId;
	private String adSlotName;
	private Integer cooperationMode;
	private Integer budgetId;
	private String budgetName;
	private Integer maxRequests;
	private Integer requestCount;
	private Integer fillCount;
	private Integer displayCount;
	private Integer clickCount;
	private Integer downloadCount;
	private Integer installCount;
	private Integer deeplinkCount;
	private Double fillRatio;
	private Double displayRatio;
	private Double clickRatio;
	private Double downloadRatio;
	private Double installRatio;
	private Double deeplinkRatio;
	private Double sspEstimateIncome;
	private Double sspEcpm;
	private Double dspEstimateIncome;
	private Double dspEcpm;
	private Long date;

	
}
