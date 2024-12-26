package com.juan.adx.model.ssp.xunfei.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/23 9:30
 */
public enum XunfeiOrientation {


    PORTRAIT(1, 0, "竖屏"),

    LANDSCAPE(2, 1, "横屏"),

    UNKNOWN(0, -1, "Unknown");

    @Getter
    @Setter
    private int type;

    @Getter
    @Setter
    private int sspType;

    @Getter
    @Setter
    private String desc;

    XunfeiOrientation(int type, int sspType, String desc) {
        this.type = type;
        this.sspType = sspType;
        this.desc = desc;
    }

    public static XunfeiOrientation getBySspType(Integer sspType) {
        if (sspType == null) {
            return UNKNOWN;
        }
        for (XunfeiOrientation advertType : values()) {
            if (advertType.getSspType() == sspType.intValue()) {
                return advertType;
            }
        }
        return UNKNOWN;
    }
}
