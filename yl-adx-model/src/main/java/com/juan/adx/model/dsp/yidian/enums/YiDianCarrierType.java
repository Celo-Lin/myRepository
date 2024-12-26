package com.juan.adx.model.dsp.yidian.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * 运营商：
 * 0：未知
 * 1：中国移动
 * 2：电信
 * 3：联通
 * 4：广电
 * 99：其他
 * </pre>
 */
public enum YiDianCarrierType {

	UNKNOWN(0, 0, "未知运营商"),

	MOBILE(1, 1, "中国移动"),

	TELECOM(2, 2, "中国电信"),

	UNICOM(3, 3, "中国联通"),


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

	private YiDianCarrierType(int type, int dspType, String desc) {
        this.type = type;
        this.dspType = dspType;
        this.desc = desc;
    }
	
	public static YiDianCarrierType get(Integer type){
		if(type == null){
			return UNKNOWN;
		}
		for (YiDianCarrierType carrierType : values()) {
			if(carrierType.getType() == type){
				return carrierType;
			}
		}
		return UNKNOWN;
	}

	
}
