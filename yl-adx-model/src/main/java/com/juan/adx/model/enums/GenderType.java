package com.juan.adx.model.enums;

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
public enum GenderType {

	/**
	 * 其他
	 */
	UNKNOWN(0, "其他"),
	
	/**
	 * 男生
	 */
	FEMALE(1, "男生"),

	/**
	 * 女生
	 */
	MAN(2, "女生")
	
	;
	
	@Setter
	@Getter
	private int type;
	
	@Setter
	@Getter
	private String desc;
	
	private GenderType(int t, String d) {
		this.type = t;
		this.desc = d;
	}
	
	public static GenderType get(Integer type) {
		if(type == null) {
			return null;
		}
		for (GenderType genderType : values()) {
			if(genderType.getType() == type.intValue()) {
				return genderType;
			}
		}
		return null;
	}


	
}
