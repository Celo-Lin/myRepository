package com.juan.adx.model.dto.manage;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.Data;

@Data
public class SlotBudgetDto {
	
	private Integer id;
	
	private Integer slotId;
	
	private Integer budgetId;
	
	private String budgetName;
	
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
	
	@Min(value = 0, message = "RTA优先级值必须在0~100之间的整数值")
	@Max(value = 100, message = "RTA优先级值必须在1~100之间的整数值")
	private Integer rtaPriorityValue;
	
	private Integer rtaSspFloorPrice;
	
	private Long ctime;
	
	private Long utime;
	
	private Integer dspPartnerId;
	
	private String dspPartnerName;
	
	
}
