package com.juan.adx.model.ssp.yoyo.enums;

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
public enum YoYoDeviceType {

	OTHER(0, 0, "其它"),

	MOBILE_PHONE(1, 1, "手机"),

	PAD(2, 2, "平板"),

	OTT(3, 3, "OTT")
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

	YoYoDeviceType(int type, int sspType, String desc) {
		this.type = type;
		this.sspType = sspType;
		this.desc = desc;
	}
	
	public static YoYoDeviceType getBySspType(Integer sspType){
		if(sspType == null){
			return OTHER;
		}
		for (YoYoDeviceType type : values()) {
			if(type.getSspType() == sspType){
				return type;
			}
		}
		return OTHER;
	}
	
}
