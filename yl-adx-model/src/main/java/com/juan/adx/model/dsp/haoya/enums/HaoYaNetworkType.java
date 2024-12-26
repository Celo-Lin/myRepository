package com.juan.adx.model.dsp.haoya.enums;

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
public enum HaoYaNetworkType {

	UNKNOWN(0, 6, "未知"),
	
	WIFI(1, 0, "wifi"),
    
    G2(2, 2,  "2G"),
    
    G3(3, 3, "3G"),
    
    G4(4, 4, "4G"),
    
    G5(5, 5, "5G"),

    ETHERNET(10, 1, "以太网"),
    
    
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
	
	private HaoYaNetworkType(int type, int dspType, String desc) {
        this.type = type;
        this.dspType = dspType;
        this.desc = desc;
    }
	
	public static HaoYaNetworkType get(Integer type){
		if(type == null){
			return HaoYaNetworkType.UNKNOWN;
		}
		for (HaoYaNetworkType networkType : values()) {
			if(networkType.getType() == type.intValue()){
				return networkType;
			}
		}
		return HaoYaNetworkType.UNKNOWN;
	}

	
}
