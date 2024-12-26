package com.juan.adx.model.dsp.youdao.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * 有道网络类型
 */
public enum YouDaoNetworkType {

    UNKNOWN(0, 0,0, "UNKNOWN"),

    ETHERNET(10, 1,0, "ETHERNET"),

    WIFI(1, 2,0, "WIFI"),

	G2(2, 3,11, "2G"),

	G3(3, 3,12, "3G"),

	G4(4, 3,13, "4G"),

	G5(5, 3,50, "5G");

    @Getter
    @Setter
    private int type;

    @Getter
    @Setter
    private int dspType;

	/**
	 * 子网络类型
	 */
	@Getter
	@Setter
	private int subDspType;

    @Getter
    @Setter
    private String desc;

	YouDaoNetworkType(int type, int dspType, int subDspType, String desc) {
        this.type = type;
        this.dspType = dspType;
		this.subDspType = subDspType;
        this.desc = desc;
    }

    public static YouDaoNetworkType get(Integer type) {
        if (type == null) {
            return YouDaoNetworkType.UNKNOWN;
        }
        for (YouDaoNetworkType networkType : values()) {
            if (networkType.getType() == type) {
                return networkType;
            }
        }
        return YouDaoNetworkType.UNKNOWN;
    }


}
