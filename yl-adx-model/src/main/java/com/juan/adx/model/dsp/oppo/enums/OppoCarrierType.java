package com.juan.adx.model.dsp.oppo.enums;

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
public enum OppoCarrierType {

	UNKNOWN(0, 0, "未知运营商"),
	
	MOBILE(1, 1, "中国移动"),
    
	TELECOM(2, 3, "中国电信"),
    
	UNICOM(3, 2, "中国联通"),
    
	
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
	
	private OppoCarrierType(int type, int dspType, String desc) {
        this.type = type;
        this.dspType = dspType;
        this.desc = desc;
    }
	
	public static OppoCarrierType get(Integer type){
		if(type == null){
			return UNKNOWN;
		}
		for (OppoCarrierType carrierType : values()) {
			if(carrierType.getType() == type.intValue()){
				return carrierType;
			}
		}
		return UNKNOWN;
	}

	
}
