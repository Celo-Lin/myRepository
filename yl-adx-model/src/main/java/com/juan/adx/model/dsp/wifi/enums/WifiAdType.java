package com.juan.adx.model.dsp.wifi.enums;

import com.juan.adx.model.dsp.wifi.WifiDspProtobuf;
import lombok.Getter;
import lombok.Setter;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/30 9:40
 */
public enum WifiAdType {

    AD_TYPE_UNSPECIFIED(0, WifiDspProtobuf.AdType.AD_TYPE_UNSPECIFIED,"未定义"),

    AD_TYPE_REDIRECT(1,WifiDspProtobuf.AdType.AD_TYPE_REDIRECT,"落地⻚⼴告"),

    AD_TYPE_DOWNLOAD(4,WifiDspProtobuf.AdType.AD_TYPE_DOWNLOAD,"APP直接下载⼴告"),

    AD_TYPE_DEEPLINK(3,WifiDspProtobuf.AdType.AD_TYPE_DEEPLINK,"APP唤起⼴告"),

    AD_TYPE_MINI_APP(5,WifiDspProtobuf.AdType.AD_TYPE_MINI_APP,"⼩程序唤起⼴告"),

    AD_TYPE_MARKET(4,WifiDspProtobuf.AdType.AD_TYPE_MARKET,"跳应⽤市场下载APP⼴告");

    @Getter
    @Setter
    private int type;

    @Getter
    @Setter
    private WifiDspProtobuf.AdType dspType;

    @Getter
    @Setter
    private String desc;

    WifiAdType(int type, WifiDspProtobuf.AdType dspType, String desc) {
        this.type = type;
        this.dspType = dspType;
        this.desc = desc;
    }

    public static WifiAdType get(WifiDspProtobuf.AdType dspType) {
        if (dspType == null) {
            return AD_TYPE_UNSPECIFIED;
        }
        for (WifiAdType advertType : values()) {
            if (advertType.getDspType() == dspType) {
                return advertType;
            }
        }
        return AD_TYPE_UNSPECIFIED;
    }

}
