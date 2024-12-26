package com.juan.adx.model.entity.manage;

import lombok.Data;

@Data
public class SlotBudget {

	private Integer id;
	
	private Integer slotId;
	
	private Integer budgetId;
	
	private String dspAppId;
	
	private String dspSlotId;
	
	private Integer limitType;
	
	private Integer maxRequests;
	
	private String packageName;
	
	private Integer sspFloorPrice;
	
	private Integer dspFloorPrice;
	
	private Integer sspPremiumRate;
	
	private Integer dspFloatingRate;
	
	private Boolean hasRta;
	
	private Integer rtaPriorityValue;
	
	private Integer rtaSspFloorPrice;
	
	private Long ctime;
	
	private Long utime;
}
