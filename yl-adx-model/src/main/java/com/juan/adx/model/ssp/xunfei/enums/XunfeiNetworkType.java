package com.juan.adx.model.ssp.xunfei.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/23 9:59
 */
public enum XunfeiNetworkType {

    UNKNOWN(0, 0, "未知"),

    WIFI(1, 2, "wifi"),

    G2(2, 4,  "2G"),

    G3(3,5,  "3G"),

    G4(4,6,  "4G"),

    G5(5, 7, "5G"),

    ETHERNET(10, 1, "以太网");

    @Getter
    @Setter
    private int type;

    @Getter
    @Setter
    private int sspType;

    @Getter
    @Setter
    private String desc;

    XunfeiNetworkType(int type, int sspType, String desc) {
        this.type = type;
        this.sspType = sspType;
        this.desc = desc;
    }

    public static XunfeiNetworkType getBySspType(Integer sspType){
        if(sspType == null){
            return UNKNOWN;
        }
        for (XunfeiNetworkType advertType : values()) {
            if(advertType.getSspType() == sspType.intValue()){
                return advertType;
            }
        }
        return UNKNOWN;
    }

}
