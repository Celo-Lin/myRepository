package com.juan.adx.model.dsp.yidian.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * 联网方式：
 * 0：未知
 * 1：Ethernet
 * 2：Wifi
 * 3：蜂窝网络
 * 4：蜂窝网络 2G
 * 5：蜂窝网络 3G
 * 6：蜂窝网络 4G
 * 7：蜂窝网络 5G
 * </pre>
 */
public enum YiDianNetworkType {

	UNKNOWN(0, 1, "未知"),

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
	private int dspType;

	@Getter
	@Setter
	private String desc;

	private YiDianNetworkType(int type, int dspType, String desc) {
        this.type = type;
        this.dspType = dspType;
        this.desc = desc;
    }
	
	public static YiDianNetworkType get(Integer type){
		if(type == null){
			return YiDianNetworkType.UNKNOWN;
		}
		for (YiDianNetworkType networkType : values()) {
			if(networkType.getType() == type){
				return networkType;
			}
		}
		return YiDianNetworkType.UNKNOWN;
	}

	
}
