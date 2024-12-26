package com.juan.adx.model.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * deeplink类型
 * 1：普通scheme
 * 2：iOS Universal link
 */
public enum DeeplinkType {

	DEEPLINK(1, "普通scheme"),
	
	IOS_UNIVERSAL_LINK(2, "iOS Universal link"),
	
	;
	
	@Getter
	@Setter
	private int type;
	
	@Getter
	@Setter
	private String desc;
	
	private DeeplinkType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
	
	public static DeeplinkType get(Integer type){
		if(type == null){
			return null;
		}
		for (DeeplinkType dpType : values()) {
			if(dpType.getType() == type.intValue()){
				return dpType;
			}
		}
		return null;
	}

}
