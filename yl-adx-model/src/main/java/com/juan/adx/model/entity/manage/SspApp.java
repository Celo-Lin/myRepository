package com.juan.adx.model.entity.manage;

import lombok.Data;

@Data
public class SspApp {

	private Integer id;
	
	private Integer sspPartnerId;
	
	private String name;
	
	private String packageName;
	
	private Integer systemPlatform;
	
	private Integer industryId;
	
	private Integer appStoreId;
	
	private String downloadUrl;
	
	private Integer status;
	
	private Long ctime;
	
	private Long utime;
}
