package com.juan.adx.model.entity.manage;

import lombok.Data;

@Data
public class SspAdvertSlot {

	private Integer id;
	
	private Integer sspPartnerId;
	
	private Integer appId;
	
	private String name;
	
	private Integer type;
	
	private Integer integrationMode;
	
	private Integer cooperationMode;
	
	private Integer sspBidPrice;
	
	private Integer status;
	
	private String remarks;
	
	private Long ctime;
	
	private Long utime;
}
