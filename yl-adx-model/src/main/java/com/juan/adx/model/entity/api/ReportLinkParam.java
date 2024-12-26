package com.juan.adx.model.entity.api;

import lombok.Data;

@Data
public class ReportLinkParam {

	private Long traceId;
	
	private Integer slotId;
	
	private Integer budgetId;
	
	private Integer mod;
	
	private String winUrl;
}
