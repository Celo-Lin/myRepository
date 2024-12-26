package com.juan.adx.model.dsp.yueke.request;

import lombok.Data;

@Data
public class YueKeUser {
    /**
     * Y
     * 用户编号
     */
    private String id;

    /**
     * N
     * 用户常驻地理位置信息
     */
    private YueKeGeo geo;

    /**
     * N
     * 用户数据，暂未使用
     */
    private Data data;

    /**
     * N
     * 关键字列逗号分隔
     */
    private String keywords;
}
