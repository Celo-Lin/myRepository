package com.juan.adx.model.dto.manage;

import lombok.Data;

@Data
public class SspAdvertSlotAuditDto {
	
	private Integer id;
	
	private String name;
	
	private Integer sspPartnerId;
	
	private String sspPartnerName;
	
	private Integer appId;
	
	private String appName;
	
	private String systemPlatform;
	
	private Integer type;
	
	private Integer integrationMode;
	
	private String remarks;
	
	private Integer auditStatus;
	
	private String auditComments;
	
	private Long ctime;
	
	private Long utime;
}
