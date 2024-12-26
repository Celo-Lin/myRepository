package com.juan.adx.model.entity.manage;

import lombok.Data;

@Data
public class DingLiDataStatistics {

	private Long id;
	
	private Integer channelId;
	
	private Integer adId;
	
	private String adSlotName;
	
	private String adSlotUuid;
	
	private String adSlotType;
	
	private Integer displayCount;
	
	private Integer clickCount;
	
	private Double clickRatio;
	
	private Double ecmp;
	
	private Double cpc;
	
	private Double sspIncome;
	
	private Double dspIncome;
	
	private Long date;
}
