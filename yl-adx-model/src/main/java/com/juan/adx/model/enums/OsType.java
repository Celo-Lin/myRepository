package com.juan.adx.model.enums;

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
public enum OsType {

	UNKNOWN(0, "Unknown"),
	
	IOS(1, "IOS"),
	
	ANDROID(2, "Android"),
	
	ANDROID_PAD(3, "Android pad"),
	
	I_PAD(4, "iPad "),
	
	WINDOWS(5, "Windows"),
	
	MAC_BOOK(6, "MacBook"),
	
	HARMONY_OS(7, "HarmonyOS"),
	
	;
	
	@Getter
	@Setter
	private int type;
	
	@Getter
	@Setter
	private String desc;
	
	private OsType(int type, String desc) {
		this.type = type;
		this.desc = desc;
	}
	
	public static OsType get(Integer type){
		if(type == null){
			return null;
		}
		for (OsType osType : values()) {
			if(osType.getType() == type.intValue()){
				return osType;
			}
		}
		return null;
	}
	
	
}
