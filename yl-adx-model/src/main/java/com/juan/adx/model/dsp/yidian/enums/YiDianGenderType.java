package com.juan.adx.model.dsp.yidian.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * 性别：
 * 1：男
 * 2：女
 * 3：未知
 * </pre>
 */
public enum YiDianGenderType {

	/**
	 * 其他
	 */
	UNKNOWN(0, 3, "其他"),

	/**
	 * 男生
	 */
	FEMALE(1, 1, "男生"),

	/**
	 * 女生
	 */
	MAN(2, 2, "女生")

	;

	@Setter
	@Getter
	private int type;


	@Getter
	@Setter
	private int dspType;

	@Setter
	@Getter
	private String desc;

	private YiDianGenderType(int t, int dspType, String d) {
		this.type = t;
		this.dspType = dspType;
		this.desc = d;
	}
	
	public static YiDianGenderType get(Integer type) {
		if(type == null) {
			return UNKNOWN;
		}
		for (YiDianGenderType genderType : values()) {
			if(genderType.getType() == type) {
				return genderType;
			}
		}
		return UNKNOWN;
	}


	
}
