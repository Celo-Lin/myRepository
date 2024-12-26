package com.juan.adx.model.dsp.oppo.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * 客户端网络类型:
 * 0：unkown
 * 1：wifi
 * 2：2G
 * 3：3G
 * 4：4G
 * 5：5G
 * 10：以太网
 * </pre>
 */
public enum OppoNetworkType {

	UNKNOWN(0, "UNKNOWN", "未知"),
	
	WIFI(1, "WIFI", "wifi"),
    
    G2(2, "2G",  "2G"),
    
    G3(3, "3G", "3G"),
    
    G4(4, "4G", "4G"),
    
    G5(5, "5G", "5G"),

    ETHERNET(10, "UNKNOWN", "以太网"),
    
    
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
	
	private OppoNetworkType(int type, String dspType, String desc) {
        this.type = type;
        this.dspType = dspType;
        this.desc = desc;
    }
	
	public static OppoNetworkType get(Integer type){
		if(type == null){
			return OppoNetworkType.UNKNOWN;
		}
		for (OppoNetworkType networkType : values()) {
			if(networkType.getType() == type.intValue()){
				return networkType;
			}
		}
		return OppoNetworkType.UNKNOWN;
	}

	
}
