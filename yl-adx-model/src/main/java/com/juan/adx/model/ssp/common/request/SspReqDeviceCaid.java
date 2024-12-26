package com.juan.adx.model.ssp.common.request;

import lombok.Data;

/**
 * ios设备的caid信息
 */
@Data
public class SspReqDeviceCaid {
	
	/**
	 * 中国广告协会互联网广告标识 (填写可提高填充)
	 */
	private String caid;
	
	/**
	 * caid 版本号 (填写可提高填充)
	 */
	private String version;
	
	/**
	 * caid生成时间
	 */
	private Long generateTime;
	
	/**
	 * <pre>
	 * caid 供应商:
	 * 	0：热云
	 * 	1：信通院
	 * 	2：阿里因子 AAID
	 * </pre>
	 */
	private Integer vendor;

}
