package com.juan.adx.model.dto.manage;

import lombok.Data;

@Data
public class DataStatisticsSlotMonitoringDto {

	private Integer id;
	private String sspPartnerName;
	private String appName;
	private Integer adSlotId;
	private String adSlotName;
	private Integer requestCount;
	private Integer fillCount;
	private Integer displayCount;
	private Integer clickCount;
	private Double fillRatio;
	private Double displayRatio;
	private Double clickRatio;
	private Long lastRequestTime;
	private Long lastFillTime;
	private Long lastDisplayTime;
	private Long lastClickTime;
	private Long date;
}
