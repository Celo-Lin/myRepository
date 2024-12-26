package com.juan.adx.model.entity.manage;

import lombok.Data;

@Data
public class DspPartnerConfig {

	private Integer dspPartnerId;
	
	private String pinyinName;

	private String apiUrl;
	
	private String priceEncryptKey;
	
	private String rtaApiUrl;
	
	private String rtaApiSignKey;
}
