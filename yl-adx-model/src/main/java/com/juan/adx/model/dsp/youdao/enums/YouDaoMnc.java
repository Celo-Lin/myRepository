package com.juan.adx.model.dsp.youdao.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/16 16:54
 */
public enum YouDaoMnc {

    UNKNOWN(0, null, "未知","未知"),

    CMCC(1, "00", "中国移动","中国移动"),

    CTCC(2, "03",  "中国电信","中国电信"),

    CUCC(3, "01",  "中国联通","中国联通");

    @Getter
    @Setter
    private int type;

    @Getter
    @Setter
    private String dspType;

    @Getter
    @Setter
    private String dspName;

    @Getter
    @Setter
    private String desc;

    YouDaoMnc(int type, String dspType, String dspName,String desc) {
        this.type = type;
        this.dspType = dspType;
        this.dspName = dspName;
        this.desc = desc;
    }

    public static YouDaoMnc get(Integer type){
        if(type == null){
            return YouDaoMnc.UNKNOWN;
        }
        for (YouDaoMnc mnc : values()) {
            if(mnc.getType() == type){
                return mnc;
            }
        }
        return YouDaoMnc.UNKNOWN;
    }

}
