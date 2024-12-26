package com.juan.adx.model.dsp.wifi.enums;

import com.juan.adx.model.dsp.wifi.WifiDspProtobuf;
import lombok.Getter;
import lombok.Setter;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/29 17:46
 */
public enum WifiNetType {

    NET_TYPE_UNSPECIFIED(0, WifiDspProtobuf.NetType.NET_TYPE_UNSPECIFIED,"未定义"),

    NET_TYPE_ETHERNET(10,WifiDspProtobuf.NetType.NET_TYPE_ETHERNET,"有线⽹络"),

    NET_TYPE_WIFI(1,WifiDspProtobuf.NetType.NET_TYPE_WIFI,"WiFi⽹络"),

    NET_TYPE_CELLULAR_UNKNOWN(0,WifiDspProtobuf.NetType.NET_TYPE_CELLULAR_UNKNOWN,"蜂窝⽹络（未识别出的G⽹）"),

    NET_TYPE_CELLULAR_2G(2,WifiDspProtobuf.NetType.NET_TYPE_CELLULAR_2G,"2G蜂窝⽹络"),

    NET_TYPE_CELLULAR_3G(3,WifiDspProtobuf.NetType.NET_TYPE_CELLULAR_3G,"3G蜂窝⽹络"),

    NET_TYPE_CELLULAR_4G(4,WifiDspProtobuf.NetType.NET_TYPE_CELLULAR_4G,"4G蜂窝⽹络"),

    NET_TYPE_CELLULAR_5G(5,WifiDspProtobuf.NetType.NET_TYPE_CELLULAR_5G,"5G蜂窝⽹络");

    @Getter
    @Setter
    private int type;

    @Getter
    @Setter
    private WifiDspProtobuf.NetType dspType;

    @Getter
    @Setter
    private String desc;

    WifiNetType(int type, WifiDspProtobuf.NetType dspType, String desc) {
        this.type = type;
        this.dspType = dspType;
        this.desc = desc;
    }

    public static WifiNetType get(Integer type) {
        if (type == null) {
            return NET_TYPE_UNSPECIFIED;
        }
        for (WifiNetType advertType : values()) {
            if (advertType.getType() == type.intValue()) {
                return advertType;
            }
        }
        return NET_TYPE_UNSPECIFIED;
    }
}

