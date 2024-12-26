package com.juan.adx.model.ssp.qutt.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * 操作系统类型：
 * 	0：Unknown
 * 	1：Android
 * 	2：IOS
 * 	3：Windows
 * </pre>
 */
public enum QuttOsType {

	UNKNOWN(0, 0, "Unknown"),

	IOS(1, 2, "IOS"),

	ANDROID(2, 1, "Android"),

	WINDOWS(5, 3, "Windows")
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

	private QuttOsType(int type, int sspType, String desc) {
		this.type = type;
		this.sspType = sspType;
		this.desc = desc;
	}
	
	public static QuttOsType getBySspType(Integer sspType){
		if(sspType == null){
			return QuttOsType.UNKNOWN;
		}
		for (QuttOsType osType : values()) {
			if(osType.getSspType() == sspType){
				return osType;
			}
		}
		return QuttOsType.UNKNOWN;
	}
	
	
}
