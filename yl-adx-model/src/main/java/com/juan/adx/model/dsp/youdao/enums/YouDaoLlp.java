package com.juan.adx.model.dsp.youdao.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/17 15:20
 */
public enum YouDaoLlp {

    GPS(1, "g", "全球卫星定位系统坐标系"),

    NAD(2, "n", "GCJ02国家测绘局坐标系(火星坐标)"),

    BAIDU(3, "n", "BD09百度坐标系"),

    GAODE(4, "n", "高德坐标系"),

    TENCENT(5, "n", "腾讯坐标系"),

    GOOGLE(6, "n", "谷歌坐标系"),

    OTHER(100, "p", "其它");

    @Getter
    @Setter
    private int type;

    @Getter
    @Setter
    private String dspType;

    @Getter
    @Setter
    private String desc;

    YouDaoLlp(int type, String dspType, String desc) {
        this.type = type;
        this.dspType = dspType;
        this.desc = desc;
    }

    public static YouDaoLlp get(Integer type) {
        if (type == null) {
            return YouDaoLlp.OTHER;
        }
        for (YouDaoLlp networkType : values()) {
            if (networkType.getType() == type) {
                return networkType;
            }
        }
        return YouDaoLlp.OTHER;
    }

}
