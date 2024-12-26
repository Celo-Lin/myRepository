package com.juan.adx.model.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * 运营商
 * 0：未知运营商;
 * 1：中国移动;
 * 2：中国电信;
 * 3：中国联通;

 * </pre>
 */
public enum CarrierType {

	UNKNOWN(0, "未知运营商"),
	
	MOBILE(1, "中国移动"),
    
	TELECOM(2, "中国电信"),
    
	UNICOM(3, "中国联通"),
    
	
	;
	
	@Getter
	@Setter
	private int type;
	
	@Getter
	@Setter
	private String desc;
	
	private CarrierType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
	
	public static CarrierType get(Integer type){
		if(type == null){
			return null;
		}
		for (CarrierType carrierType : values()) {
			if(carrierType.getType() == type.intValue()){
				return carrierType;
			}
		}
		return null;
	}

	
}
