package com.juan.adx.model.dsp.haoya.enums;

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
public enum HaoYaOsType {

	UNKNOWN(0, 4, "Unknown"),
	
	IOS(1, 3, "IOS"),
	
	ANDROID(2, 4, "Android"),
	
	ANDROID_PAD(3, 4, "Android pad"),
	
	I_PAD(4, 3, "iPad "),
	
	WINDOWS(5, 0, "Windows"),
	
	MAC_BOOK(6, 1, "MacBook"),
	
	HARMONY_OS(7, 4, "HarmonyOS"),
	
	

	;
	
	@Getter
	@Setter
	private int type;
	
	@Getter
	@Setter
	private int dspType;
	
	@Getter
	@Setter
	private String desc;
	
	private HaoYaOsType(int type, int dspType, String desc) {
		this.type = type;
		this.dspType = dspType;
		this.desc = desc;
	}
	
	public static HaoYaOsType get(Integer type){
		if(type == null){
			return HaoYaOsType.UNKNOWN;
		}
		for (HaoYaOsType osType : values()) {
			if(osType.getType() == type.intValue()){
				return osType;
			}
		}
		return HaoYaOsType.UNKNOWN;
	}
	
	
}
