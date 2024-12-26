package com.juan.adx.model.entity.api;

import lombok.Data;

@Data
public class DspRequestRecord {

	private Integer slotId;
	
	private Integer budgetId;
	
	private Integer cooperationMode;
	
	private Long timeSeconds;
}
