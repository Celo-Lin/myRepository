package com.juan.adx.model.dsp.haoya.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * 用户性别
 * 	0：其他
 * 	1：男
 * 	2：女
 * </pre>
 */
public enum HaoYaGenderType {

	/**
	 * 其他
	 */
	UNKNOWN(0, "默认", "其他"),
	
	/**
	 * 男生
	 */
	FEMALE(1, "M", "男生"),

	/**
	 * 女生
	 */
	MAN(2, "F", "女生")
	
	;
	
	@Setter
	@Getter
	private int type;
	
	
	@Getter
	@Setter
	private String dspType;
	
	@Setter
	@Getter
	private String desc;
	
	private HaoYaGenderType(int t, String dspType, String d) {
		this.type = t;
		this.dspType = dspType;
		this.desc = d;
	}
	
	public static HaoYaGenderType get(Integer type) {
		if(type == null) {
			return UNKNOWN;
		}
		for (HaoYaGenderType genderType : values()) {
			if(genderType.getType() == type.intValue()) {
				return genderType;
			}
		}
		return UNKNOWN;
	}


	
}
