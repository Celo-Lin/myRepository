package com.juan.adx.model.dto.sspmanage;

import lombok.Data;

@Data
public class ChannelAdvertSlotAuditDto {
	
	private Integer id;
	
	private String name;
	
	private Integer sspPartnerId;
	
	private String sspPartnerName;
	
	private Integer appId;
	
	private String appName;
	
	private Integer type;
	
	private Integer integrationMode;
	
	private String remarks;
	
	private Integer auditStatus;
	
	private String auditComments;
	
	private Long ctime;
	
	private Long utime;
}
