package com.juan.adx.model.dto.manage;

import lombok.Data;

@Data
public class SspAppDto {
	
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
	
	private Integer status;
	
	private Long ctime;
	
	private Long utime;
}
