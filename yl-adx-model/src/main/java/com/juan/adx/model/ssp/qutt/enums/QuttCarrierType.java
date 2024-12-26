package com.juan.adx.model.ssp.qutt.enums;

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
public enum QuttCarrierType {

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
	private int sspType;

	@Getter
	@Setter
	private String desc;

	private QuttCarrierType(int type, int sspType, String desc) {
        this.type = type;
        this.sspType = sspType;
        this.desc = desc;
    }
	
	public static QuttCarrierType getBySspType(Integer sspType){
		if(sspType == null){
			return UNKNOWN;
		}
		for (QuttCarrierType carrierType : values()) {
			if(carrierType.getSspType() == sspType){
				return carrierType;
			}
		}
		return UNKNOWN;
	}

	
}