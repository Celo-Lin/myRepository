package com.juan.adx.model.dsp.yueke.enums;

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
public enum YueKeOsType {

	UNKNOWN(0, "unknown", "Unknown"),
	
	IOS(1, "ios", "IOS"),
	
	ANDROID(2, "android", "Android"),
	
	ANDROID_PAD(3, "android", "Android pad"),
	
	I_PAD(4, "ios", "iPad "),
	
	HARMONY_OS(7, "android", "HarmonyOS"),
	
	

	;
	
	@Getter
	@Setter
	private int type;
	
	@Getter
	@Setter
	private String dspType;
	
	@Getter
	@Setter
	private String desc;
	
	private YueKeOsType(int type, String dspType, String desc) {
		this.type = type;
		this.dspType = dspType;
		this.desc = desc;
	}
	
	public static YueKeOsType get(Integer type){
		if(type == null){
			return YueKeOsType.UNKNOWN;
		}
		for (YueKeOsType osType : values()) {
			if(osType.getType() == type.intValue()){
				return osType;
			}
		}
		return YueKeOsType.UNKNOWN;
	}
	
	
}
