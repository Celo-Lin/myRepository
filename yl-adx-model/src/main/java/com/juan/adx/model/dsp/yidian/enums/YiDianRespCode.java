package com.juan.adx.model.dsp.yidian.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/18 9:10
 */
public enum YiDianRespCode {
    AD_FILLED(0, "有广告填充"),

    ANTI_CHEATING(600, "反作弊"),

    AD_NO_FILLED(707, "无广告填充"),

    DEVICE_COOLING(712, "设备冷却"),

    ;

    @Getter
    @Setter
    private int code;

    @Getter
    @Setter
    private String reason;


    YiDianRespCode(int code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    public static YiDianRespCode get(Integer code) {
        if (code == null) {
            return null;
        }
        for (YiDianRespCode networkType : values()) {
            if (networkType.getCode() == code.intValue()) {
                return networkType;
            }
        }
        return null;
    }

}
