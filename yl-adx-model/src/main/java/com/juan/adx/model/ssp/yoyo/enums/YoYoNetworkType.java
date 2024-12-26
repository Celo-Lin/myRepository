package com.juan.adx.model.ssp.yoyo.enums;

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
public enum YoYoNetworkType {

	UNKNOWN(0, 0, "未知"),

	WIFI(1, 1, "wifi"),

    G2(2, 2,  "2G"),

    G3(3, 3, "3G"),

    G4(4, 4, "4G"),

    G5(5, 5, "5G"),

    ETHERNET(10, -1, "以太网"),

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

	YoYoNetworkType(int type, int sspType, String desc) {
        this.type = type;
        this.sspType = sspType;
        this.desc = desc;
    }
	
	public static YoYoNetworkType getBySspType(Integer sspType){
		if(sspType == null){
			return YoYoNetworkType.UNKNOWN;
		}
		for (YoYoNetworkType networkType : values()) {
			if(networkType.getSspType() == sspType){
				return networkType;
			}
		}
		return YoYoNetworkType.UNKNOWN;
	}

	
}
