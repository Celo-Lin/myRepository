package com.juan.adx.model.dsp.yueke.enums;

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
public enum YueKeCarrierType {

	UNKNOWN(0, "46020", "未知运营商"),
	
	MOBILE(1, "46000", "中国移动"),
    
	TELECOM(2, "46003", "中国电信"),
    
	UNICOM(3, "46001", "中国联通"),
    
	
	;
	
	@Getter
	@Setter
	private int type;
	
	@Getter
	@Setter
	private String dspType;
	
	@Getter
	@Setter
	private String desc;
	
	private YueKeCarrierType(int type, String dspType, String desc) {
        this.type = type;
        this.dspType = dspType;
        this.desc = desc;
    }
	
	public static YueKeCarrierType get(Integer type){
		if(type == null){
			return UNKNOWN;
		}
		for (YueKeCarrierType carrierType : values()) {
			if(carrierType.getType() == type.intValue()){
				return carrierType;
			}
		}
		return UNKNOWN;
	}

	
}
