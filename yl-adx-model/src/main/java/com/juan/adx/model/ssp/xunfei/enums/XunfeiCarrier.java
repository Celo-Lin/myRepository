package com.juan.adx.model.ssp.xunfei.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/23 10:06
 */
public enum XunfeiCarrier {

    UNKNOW(0,0,"未知"),

    CMCC(1,1,"中国移动"),

    CUCC(3,2,"中国联通"),

    CTC(2,3,"中国电信");

    @Getter
    @Setter
    private int type;

    @Getter
    @Setter
    private int sspType;

    @Getter
    @Setter
    private String desc;

    XunfeiCarrier(int type, int sspType, String desc) {
        this.type = type;
        this.sspType = sspType;
        this.desc = desc;
    }

    public static XunfeiCarrier getBySspType(Integer sspType) {
        if (sspType == null) {
            return UNKNOW;
        }
        for (XunfeiCarrier advertType : values()) {
            if (advertType.getSspType() == sspType.intValue()) {
                return advertType;
            }
        }
        return UNKNOW;
    }


}
