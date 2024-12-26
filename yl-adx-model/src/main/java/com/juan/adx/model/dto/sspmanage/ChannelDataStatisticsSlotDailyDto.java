package com.juan.adx.model.dto.sspmanage;

import lombok.Data;

@Data
public class ChannelDataStatisticsSlotDailyDto {

	private Integer id;
	private Integer sspPartnerId;
	private Integer appId;
	private String appName;
	private Integer adSlotId;
	private String adSlotName;
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
	private Long date;

	
}
