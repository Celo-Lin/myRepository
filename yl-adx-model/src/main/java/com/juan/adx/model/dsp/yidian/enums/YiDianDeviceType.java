package com.juan.adx.model.dsp.yidian.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * 设备类型：
 * 0: 未知
 * 1: 手机
 * 2: 平板
 * 3: PC
 * 4: 智能电视
 * </pre>
 */
public enum YiDianDeviceType {

    OTHER(0, 0, "其它"),

    MOBILE_PHONE(1, 1, "手机"),

    PAD(2, 2, "平板"),

    OTT(3, 4, "OTT 终端"),

    PC(4, 3, "PC"),

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

    private YiDianDeviceType(int type, int dspType, String desc) {
        this.type = type;
        this.dspType = dspType;
        this.desc = desc;
    }

    public static YiDianDeviceType get(Integer type){
        if(type == null){
            return OTHER;
        }
        for (YiDianDeviceType osType : values()) {
            if(osType.getType() == type.intValue()){
                return osType;
            }
        }
        return OTHER;
    }
}
