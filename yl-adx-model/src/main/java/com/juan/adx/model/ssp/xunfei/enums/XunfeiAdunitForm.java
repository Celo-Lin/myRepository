package com.juan.adx.model.ssp.xunfei.enums;

import com.juan.adx.model.ssp.wifi.enums.WifiAdvertType;
import lombok.Getter;
import lombok.Setter;

/**
 * 讯飞广告形式
 * @author： HeWen Zhou
 * @date： 2024/5/22 14:33
 */
public enum XunfeiAdunitForm {

    UNKNOWN(0, 0, "未定义"),

    BANNER(1, 1, "横幅"),

    INTERSTITIAL(2, 3, "插屏"),

    SPLASH(3, 2, "开屏"),

    INFORMATION_STREAM(4, 6, "信息流"),

    REWARDED_VIDEO(5, 4, "激励视频"),

    VIDEP_SLICING(7, 11, "视频切片");

    @Getter
    @Setter
    private int type;

    @Getter
    @Setter
    private int sspType;

    @Getter
    @Setter
    private String desc;

     XunfeiAdunitForm(int type, int sspType, String desc) {
        this.type = type;
        this.sspType = sspType;
        this.desc = desc;
    }

    public static XunfeiAdunitForm getBySspType(Integer sspType){
        if(sspType == null){
            return UNKNOWN;
        }
        for (XunfeiAdunitForm advertType : values()) {
            if(advertType.getSspType() == sspType.intValue()){
                return advertType;
            }
        }
        return UNKNOWN;
    }

}
