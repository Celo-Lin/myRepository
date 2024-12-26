package com.juan.adx.model.enums;

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
public enum OrientationType {

	UNKNOWN(0, "未知"),
	
	PORTRAIT(1, "竖屏"),
    
	LANDSCAPE(2, "横屏"),

	;
	
	@Getter
	@Setter
	private int type;
	
	@Getter
	@Setter
	private String desc;
	
	private OrientationType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
	
	public static OrientationType get(Integer type){
		if(type == null){
			return null;
		}
		for (OrientationType orientationType : values()) {
			if(orientationType.getType() == type.intValue()){
				return orientationType;
			}
		}
		return null;
	}

	
}
