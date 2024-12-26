package com.juan.adx.model.ssp.wifi.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * 操作系统类型：
 * 	0：Unknown
 * 	1：IOS
 * 	2：Android
 * 	3：Android pad
 * 	4：iPad 
 * 	5：Windows
 * 	6：MacBook
 *  7: HarmonyOS
 * </pre>
 */
public enum WifiOsType {

	UNKNOWN(0, 0, "Unknown"),
	
	IOS(1, 2, "IOS"),
	
	ANDROID(2, 1, "Android"),
	
	ANDROID_PAD(3, 1, "Android pad"),
	
	I_PAD(4, 2, "iPad "),
	
	HARMONY_OS(7, 1, "HarmonyOS"),
	
	

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
	
	private WifiOsType(int type, int sspType, String desc) {
		this.type = type;
		this.sspType = sspType;
		this.desc = desc;
	}
	
	public static WifiOsType getBySspType(Integer sspType){
		if(sspType == null){
			return WifiOsType.UNKNOWN;
		}
		for (WifiOsType osType : values()) {
			if(osType.getSspType() == sspType.intValue()){
				return osType;
			}
		}
		return WifiOsType.UNKNOWN;
	}
	
	
}
