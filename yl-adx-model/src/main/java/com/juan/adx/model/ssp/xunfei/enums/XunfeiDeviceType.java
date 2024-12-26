package com.juan.adx.model.ssp.xunfei.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/23 9:09
 */
public enum XunfeiDeviceType {


    PHONE(1, 0, "手机"),

    TABLET(2, 1, "平板"),

    OTT(3, 2, "互联网电视"),

    OTHER(0, -1, "其他");

    @Getter
    @Setter
    private int type;

    @Getter
    @Setter
    private int sspType;

    @Getter
    @Setter
    private String desc;

    XunfeiDeviceType(int type, int sspType, String desc) {
        this.type = type;
        this.sspType = sspType;
        this.desc = desc;
    }

    public static XunfeiDeviceType getBySspType(Integer sspType){
        if(sspType == null){
            return OTHER;
        }
        for (XunfeiDeviceType advertType : values()) {
            if(advertType.getSspType() == sspType.intValue()){
                return advertType;
            }
        }
        return OTHER;
    }

}
