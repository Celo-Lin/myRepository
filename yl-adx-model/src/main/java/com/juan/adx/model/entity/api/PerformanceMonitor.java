package com.juan.adx.model.entity.api;

import java.util.List;

import lombok.Data;

@Data
public class PerformanceMonitor {

	private String traceId;
	
	private Long requestStartTime;
	
	private Long requestEndTime;
	
	private Integer sspPartnerId;
	
	private Integer appId;
	
	private Integer slotId;
	
	private List<PerformanceMonitorItem> monitorItems;
	
	
	@Data
	class PerformanceMonitorItem{
		
		private Long requestDspStartTime;
		
		private Long requestDspEndTime;
		
		private Integer dspPartnerId;
		
		private Integer budgetId;
		
		private String apiUrl;
	}
}
