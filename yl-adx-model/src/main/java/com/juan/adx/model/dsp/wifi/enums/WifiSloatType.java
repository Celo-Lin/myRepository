package com.juan.adx.model.dsp.wifi.enums;

import com.juan.adx.model.dsp.wifi.WifiDspProtobuf;
import com.juan.adx.model.dsp.yueke.enums.YueKeAdvertType;
import lombok.Getter;
import lombok.Setter;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/29 16:41
 */
public enum WifiSloatType {

    INTERSTITIAL(2, WifiDspProtobuf.SlotType.SLOT_TYPE_INTERSTITIAL, "插屏"),

    SPLASH(3, WifiDspProtobuf.SlotType.SLOT_TYPE_SPLASH, "开屏"),

    INFORMATION_STREAM(4, WifiDspProtobuf.SlotType.SLOT_TYPE_NATIVE, "信息流"),

    REWARDED_VIDEO(5, WifiDspProtobuf.SlotType.SLOT_TYPE_REWARD_VIDEO, "激励视频"),

    UNKNOW(0, WifiDspProtobuf.SlotType.SLOT_TYPE_UNSPECIFIED, "未知");

    @Getter
    @Setter
    private int type;

    @Getter
    @Setter
    private WifiDspProtobuf.SlotType dspType;

    @Getter
    @Setter
    private String desc;

    WifiSloatType(int type, WifiDspProtobuf.SlotType dspType, String desc) {
        this.type = type;
        this.dspType = dspType;
        this.desc = desc;
    }

    public static WifiSloatType get(Integer type) {
        if (type == null) {
            return UNKNOW;
        }
        for (WifiSloatType advertType : values()) {
            if (advertType.getType() == type.intValue()) {
                return advertType;
            }
        }
        return UNKNOW;
    }

}
