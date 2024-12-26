package com.juan.adx.model.entity.task;

import lombok.Data;

@Data
public class StatisticsSlotIncomeDto {

	private Integer sspPartnerId;
	
	private Integer slotId;
	
	private Integer budgetId;
	
	private Integer cooperationMode;
	
	private Long sspEstimateIncome;
	
	private Long dspEstimateIncome;
	
	private Long date;
}
