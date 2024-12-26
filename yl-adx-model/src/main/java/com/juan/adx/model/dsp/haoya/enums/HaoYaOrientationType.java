package com.juan.adx.model.dsp.haoya.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * 屏幕方向：
 * 0：unknown
 * 1：竖屏
 * 2：横屏
 * </pre>
 */
public enum HaoYaOrientationType {

	UNKNOWN(0, 0, "未知"),
	
	PORTRAIT(1, 1, "竖屏"),
    
	LANDSCAPE(2, 2, "横屏"),

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
	
	private HaoYaOrientationType(int type, int dspType, String desc) {
        this.type = type;
        this.dspType = dspType;
        this.desc = desc;
    }
	
	public static HaoYaOrientationType get(Integer type){
		if(type == null){
			return UNKNOWN;
		}
		for (HaoYaOrientationType orientationType : values()) {
			if(orientationType.getType() == type.intValue()){
				return orientationType;
			}
		}
		return UNKNOWN;
	}

	
}
