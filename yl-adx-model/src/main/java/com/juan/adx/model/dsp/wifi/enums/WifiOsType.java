package com.juan.adx.model.dsp.wifi.enums;

import com.juan.adx.model.dsp.wifi.WifiDspProtobuf;
import lombok.Getter;
import lombok.Setter;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/29 17:03
 */
public enum WifiOsType {

    OS_UNSPECIFIED(0,WifiDspProtobuf.OS.OS_UNSPECIFIED, "未定义"),

    OS_ANDROID(2, WifiDspProtobuf.OS.OS_ANDROID, "安卓"),

    OS_IOS(1, WifiDspProtobuf.OS.OS_IOS, "ios");

    @Getter
    @Setter
    private int type;

    @Getter
    @Setter
    private WifiDspProtobuf.OS dspType;

    @Getter
    @Setter
    private String desc;

    WifiOsType(int type, WifiDspProtobuf.OS dspType, String desc) {
        this.type = type;
        this.dspType = dspType;
        this.desc = desc;
    }

    public static WifiOsType get(Integer type) {
        if (type == null) {
            return OS_UNSPECIFIED;
        }
        for (WifiOsType advertType : values()) {
            if (advertType.getType() == type.intValue()) {
                return advertType;
            }
        }
        return OS_UNSPECIFIED;
    }
}
