package com.juan.adx.model.ssp.wifi.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * 设备类型：
 * 0：其它
 * 1：手机
 * 2：平板
 * 3：OTT 终端
 * 4：PC
 * 注：OTT 终端包括互联网电视和电视机顶盒
 * </pre>
 */
public enum WifiDeviceType {
	
	OTHER(0, 0, "其它"),
	
	MOBILE_PHONE(1, 1, "手机"),
	
	PAD(2, 2, "平板"),
	
	OTT(3, 3, "OTT 终端"),

	PC(4, 4, "PC"),
	
	;
	
	@Getter
	@Setter
	private int type;
	
	@Getter
	@Setter
	private int sspType;
	
	@Getter
	@Setter
	private String desc;
	
	private WifiDeviceType(int type, int sspType, String desc) {
		this.type = type;
		this.sspType = sspType;
		this.desc = desc;
	}
	
	public static WifiDeviceType getBySspType(Integer sspType){
		if(sspType == null){
			return OTHER;
		}
		for (WifiDeviceType osType : values()) {
			if(osType.getSspType() == sspType.intValue()){
				return osType;
			}
		}
		return OTHER;
	}
	
}
