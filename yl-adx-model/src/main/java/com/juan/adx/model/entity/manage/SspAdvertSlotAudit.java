package com.juan.adx.model.entity.manage;

import lombok.Data;

@Data
public class SspAdvertSlotAudit {

	private Integer id;
	
	private Integer sspPartnerId;
	
	private Integer appId;
	
	private String name;
	
	private Integer type;
	
	private Integer integrationMode;
	
	private String remarks;

	private Integer auditStatus;
	
	private String auditComments;
	
	private Long ctime;
	
	private Long utime;
}
