package com.juan.adx.model.dto.manage;

import lombok.Data;

@Data
public class SspAdvertSlotDto {
	
	private Integer id;
	
	private String name;
	
	private Integer sspPartnerId;
	
	private String sspPartnerName;
	
	private Integer appId;
	
	private String appName;
	
	private Integer systemPlatform;
	
	private Integer type;
	
	private Integer integrationMode;
	
	private Integer cooperationMode;
	
	private Integer sspBidPrice;
	
	private Integer status;
	
	private String remarks;
	
	private Long ctime;
	
	private Long utime;
}
