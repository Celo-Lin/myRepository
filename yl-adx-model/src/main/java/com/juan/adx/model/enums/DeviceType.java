package com.juan.adx.model.enums;

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
public enum DeviceType {
	
	OTHER(0, "其它"),
	
	MOBILE_PHONE(1, "手机"),
	
	PAD(2, "平板"),
	
	OTT(3, "OTT 终端"),

	PC(4, "PC"),
	
	;
	
	@Getter
	@Setter
	private int type;
	
	@Getter
	@Setter
	private String desc;
	
	private DeviceType(int type, String desc) {
		this.type = type;
		this.desc = desc;
	}
	
	public static DeviceType valueOf(Integer type){
		if(type == null){
			return null;
		}
		for (DeviceType osType : values()) {
			if(osType.getType() == type.intValue()){
				return osType;
			}
		}
		return null;
	}
	
}
