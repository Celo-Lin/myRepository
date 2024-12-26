package com.juan.adx.model.dto.manage;

import lombok.Data;

@Data
public class SspAppAuditDto {
	
	private Integer id;
	
	private String name;
	
	private Integer sspPartnerId;
	
	private String sspPartnerName;
	
	private String packageName;
	
	private Integer systemPlatform;
	
	private Integer industryId;
	
	private String industryName;
	
	private Integer appStoreId;
	
	private String appStoreName;
	
	private String downloadUrl;
	
	private Integer auditStatus;
	
	private String auditComments;
	
	private Long ctime;
	
}
