package com.juan.adx.model.dsp.vivo.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/25 17:45
 */
public enum VivoNetworkType {

    UNKNOW(0, 0, "unkown"),
    WIFI(1, 100, "wifi"),
    G2(2, 2, "2G"),
    G3(3, 3, "3G"),
    G4(4, 4, "4G"),
    G5(5, 5, "5G"),
    ETHERNET(10, 0, "以太网");


    @Getter
    @Setter
    private int type;

    @Getter
    @Setter
    private int dspType;

    @Getter
    @Setter
    private String desc;

    VivoNetworkType(int type, int dspType, String desc) {
        this.type = type;
        this.dspType = dspType;
        this.desc = desc;
    }

    public static VivoNetworkType get(Integer type) {
        if (type == null) {
            return UNKNOW;
        }
        for (VivoNetworkType carrierType : values()) {
            if (carrierType.getType() == type.intValue()) {
                return carrierType;
            }
        }
        return UNKNOW;
    }

}
