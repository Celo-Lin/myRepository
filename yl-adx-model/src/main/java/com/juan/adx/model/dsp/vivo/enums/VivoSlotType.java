package com.juan.adx.model.dsp.vivo.enums;

import com.juan.adx.model.dsp.yueke.enums.YueKeAdvertType;
import lombok.Getter;
import lombok.Setter;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/27 9:24
 */
public enum VivoSlotType {

    BANNER(1, 3, "横幅"),

    INTERSTITIAL(2, 4, "插屏"),

    SPLASH(3, 2, "开屏"),

    REWARDED_VIDEO(5, 9, "激励视频"),

    NATIVE(6, 5, "Native原生视频"),

    INFORMATION_STREAM(4, 5, "信息流");

    @Getter
    @Setter
    private int type;

    @Getter
    @Setter
    private int dspType;

    @Getter
    @Setter
    private String desc;

    VivoSlotType(int type, int dspType, String desc) {
        this.type = type;
        this.dspType = dspType;
        this.desc = desc;
    }

    public static VivoSlotType get(Integer type){
        if(type == null){
            return INFORMATION_STREAM;
        }
        for (VivoSlotType advertType : values()) {
            if(advertType.getType() == type.intValue()){
                return advertType;
            }
        }
        return INFORMATION_STREAM;
    }

}
