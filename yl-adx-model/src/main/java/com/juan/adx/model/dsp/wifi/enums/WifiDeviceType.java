package com.juan.adx.model.dsp.wifi.enums;

import com.juan.adx.model.dsp.wifi.WifiDspProtobuf;
import lombok.Getter;
import lombok.Setter;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/29 16:57
 */
public enum WifiDeviceType {

    DEVICE_TYPE_UNSPECIFIED(0,WifiDspProtobuf.DeviceType.DEVICE_TYPE_UNSPECIFIED,"未定义"),

    DEVICE_TYPE_PHONE(1,WifiDspProtobuf.DeviceType.DEVICE_TYPE_PHONE,"手机"),

    DEVICE_TYPE_TABLET(2,WifiDspProtobuf.DeviceType.DEVICE_TYPE_TABLET,"平板"),

    DEVICE_TYPE_TV(3,WifiDspProtobuf.DeviceType.DEVICE_TYPE_TV,"电视"),

    DEVICE_TYPE_PC(4,WifiDspProtobuf.DeviceType.DEVICE_TYPE_PC,"PC");

    @Getter
    @Setter
    private int type;

    @Getter
    @Setter
    private WifiDspProtobuf.DeviceType dspType;

    @Getter
    @Setter
    private String desc;

    WifiDeviceType(int type, WifiDspProtobuf.DeviceType dspType, String desc) {
        this.type = type;
        this.dspType = dspType;
        this.desc = desc;
    }

    public static WifiDeviceType get(Integer type) {
        if (type == null) {
            return DEVICE_TYPE_UNSPECIFIED;
        }
        for (WifiDeviceType advertType : values()) {
            if (advertType.getType() == type.intValue()) {
                return advertType;
            }
        }
        return DEVICE_TYPE_UNSPECIFIED;
    }
}
