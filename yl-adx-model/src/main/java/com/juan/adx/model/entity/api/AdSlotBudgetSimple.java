package com.juan.adx.model.entity.api;

import lombok.Data;

@Data
public class AdSlotBudgetSimple {
	
	private Integer slotId;
	
	private Integer budgetId;

	private Integer limitType;
	
	private Integer maxRequests;
	
}
