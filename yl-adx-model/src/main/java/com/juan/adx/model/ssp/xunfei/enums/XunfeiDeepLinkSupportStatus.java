package com.juan.adx.model.ssp.xunfei.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/23 9:47
 */
public enum XunfeiDeepLinkSupportStatus {

    NOT_SUPPORT_DEEPLINK(0, 0, "不支持deeplink"),

    SUPPORT_DEEPLINK(1, 1, "支持deeplink"),

    SUPPORT_H5_DEEPLINK(1, 2, "支持H5形式deeplink"),

    UNKNOW(null, null, "未知");

    @Getter
    @Setter
    private Integer type;

    @Getter
    @Setter
    private Integer sspType;

    @Getter
    @Setter
    private String desc;

    XunfeiDeepLinkSupportStatus(Integer type, Integer sspType, String desc) {
        this.type = type;
        this.sspType = sspType;
        this.desc = desc;
    }

    public static XunfeiDeepLinkSupportStatus getBySspType(Integer sspType) {
        if (sspType == null) {
            return UNKNOW;
        }
        for (XunfeiDeepLinkSupportStatus advertType : values()) {
            if (advertType.getSspType() == sspType.intValue()) {
                return advertType;
            }
        }
        return UNKNOW;
    }

}
