package com.juan.adx.model.ssp.wifi.enums;

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
public enum WifiNetworkType {

	UNKNOWN(0, 0, "未知"),
	
	WIFI(1, 2, "wifi"),
    
    G2(2, 4,  "2G"),
    
    G3(3, 5, "3G"),
    
    G4(4, 6, "4G"),
    
    G5(5, 7, "5G"),

    ETHERNET(10, 1, "以太网"),
    
    
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
	
	private WifiNetworkType(int type, int sspType, String desc) {
        this.type = type;
        this.sspType = sspType;
        this.desc = desc;
    }
	
	public static WifiNetworkType getBySspType(Integer sspType){
		if(sspType == null){
			return WifiNetworkType.UNKNOWN;
		}
		for (WifiNetworkType networkType : values()) {
			if(networkType.getSspType() == sspType.intValue()){
				return networkType;
			}
		}
		return WifiNetworkType.UNKNOWN;
	}

	
}
