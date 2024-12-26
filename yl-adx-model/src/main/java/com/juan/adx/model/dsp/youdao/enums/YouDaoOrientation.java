package com.juan.adx.model.dsp.youdao.enums;

import com.juan.adx.model.dsp.oppo.enums.OppoNetworkType;
import lombok.Getter;
import lombok.Setter;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/16 16:48
 */
public enum YouDaoOrientation {

    UNKNOWN(0, "u", "未知"),

    PORTRAIT(1, "p", "竖屏"),

    LANDSCAPE(2, "l",  "横屏");

    @Getter
    @Setter
    private int type;

    @Getter
    @Setter
    private String dspType;

    @Getter
    @Setter
    private String desc;

    YouDaoOrientation(int type, String dspType, String desc) {
        this.type = type;
        this.dspType = dspType;
        this.desc = desc;
    }

    public static YouDaoOrientation get(Integer type){
        if(type == null){
            return YouDaoOrientation.UNKNOWN;
        }
        for (YouDaoOrientation orientation : values()) {
            if(orientation.getType() == type){
                return orientation;
            }
        }
        return YouDaoOrientation.UNKNOWN;
    }

}
