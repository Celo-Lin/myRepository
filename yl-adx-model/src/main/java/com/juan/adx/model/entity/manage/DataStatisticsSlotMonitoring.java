package com.juan.adx.model.entity.manage;

import lombok.Data;

@Data
public class DataStatisticsSlotMonitoring {

	private Integer id;
	private Integer adSlotId;
	private Integer requestCount;
	private Integer fillCount;
	private Integer displayCount;
	private Integer clickCount;
	private Long lastRequestTime;
	private Long lastFillTime;
	private Long lastDisplayTime;
	private Long lastClickTime;
	private Long date;
}
