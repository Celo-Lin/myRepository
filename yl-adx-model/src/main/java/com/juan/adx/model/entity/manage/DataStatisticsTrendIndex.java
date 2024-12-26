package com.juan.adx.model.entity.manage;

import lombok.Data;

@Data
public class DataStatisticsTrendIndex {
	
	private Integer id;
	
	private Integer requestCount;
	
	private Integer fillCount;
	
	private Integer displayCount;
	
	private Integer clickCount;
	
	private Integer adSlotType;
	
	private Long date;
}
