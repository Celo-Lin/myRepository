package com.juan.adx.model.dto.manage;

import lombok.Data;

@Data
public class DspBudgetDto {

	private Integer id;
	
	private String name;
	
	private String title;

	private Integer dspPartnerId;
	
	private String dspPartnerName;
	
	private Integer cooperationMode;
	
	private String pictureUrl;

	private String videoUrl;
	
	private String deeplink;
	
	private String h5link;
	
	private String downloadUrl;
	
	private Integer deviceMaxRequests;
	
	private Integer type;
	
	private Integer status;
	
	private Long ctime;
	
	private Long utime;
}
