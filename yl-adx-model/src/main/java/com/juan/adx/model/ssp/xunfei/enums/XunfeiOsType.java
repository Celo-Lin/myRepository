package com.juan.adx.model.ssp.xunfei.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/23 9:05
 */
public enum XunfeiOsType {

    ANDROID(2, 0, "Android"),

    ios(1, 1, "iOS"),

    UNKNOWN(0, 3, "Unknown");

    @Getter
    @Setter
    private int type;

    @Getter
    @Setter
    private int sspType;

    @Getter
    @Setter
    private String desc;

    XunfeiOsType(int type, int sspType, String desc) {
        this.type = type;
        this.sspType = sspType;
        this.desc = desc;
    }

    public static XunfeiOsType getBySspType(Integer sspType){
        if(sspType == null){
            return UNKNOWN;
        }
        for (XunfeiOsType advertType : values()) {
            if(advertType.getSspType() == sspType.intValue()){
                return advertType;
            }
        }
        return UNKNOWN;
    }
}
