package com.juan.adx.model.ssp.common.request;

import lombok.Data;

/**
 * 移动设备序列号标识
 */
@Data
public class SspReqDeviceId {

	/**
	 * android设备imei
	 */
	private String imei;
	
	/**
	 * android设备imei MD5值 (小写)
	 */
	private String imeiMd5;
	
	/**
	 * android设备OAID
	 */
	private String oaid;
	
	/**
	 * android设备OAID MD5值 (小写)
	 */
	private String oaidMd5;
	
	/**
	 * android设备device id
	 */
	private String androidId;
	
	/**
	 * android设备device id MD5值 (小写)
	 */
	private String androidIdMd5;


	/**
	 * android设备device id SHA1值
	 */
	private String androidIdSha1;
	
	/**
	 * ios设备idfa
	 */
	private String idfa;
	
	/**
	 * ios设备idfa MD5值
	 */
	private String idfaMd5;
	
	/**
	 * ios设备idfv
	 */
	private String idfv;
	
	/**
	 * ios设备OpenUDID (IOS系统必填)
	 */
	private String openUdid;
}
