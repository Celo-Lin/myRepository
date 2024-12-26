package com.juan.adx.model.dsp.haoya.enums;

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
public enum HaoYaDeviceType {
	
	OTHER(0, -1, "其它"),
	
	MOBILE_PHONE(1, 1, "手机"),
	
	PAD(2, 2, "平板"),
	
	OTT(3, 3, "OTT 终端"),

	PC(4, 0, "PC"),
	
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
	
	private HaoYaDeviceType(int type, int dspType, String desc) {
		this.type = type;
		this.dspType = dspType;
		this.desc = desc;
	}
	
	public static HaoYaDeviceType get(Integer type){
		if(type == null){
			return OTHER;
		}
		for (HaoYaDeviceType osType : values()) {
			if(osType.getType() == type.intValue()){
				return osType;
			}
		}
		return OTHER;
	}
	
}
