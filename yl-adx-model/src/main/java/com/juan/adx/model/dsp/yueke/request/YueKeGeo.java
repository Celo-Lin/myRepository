package com.juan.adx.model.dsp.yueke.request;

import lombok.Data;

@Data
public class YueKeGeo {
    /**
     * Y
     * 纬度，保留小数点后4位 建议必填   默认：0
     */
    private Double lat;

    /**
     * Y
     * 经度，保留小数点后4位建议必填    默认：0
     */
    private Double lon;

    /**
     * N
     * 坐标系
     * WGS84 = 1 全球卫星定位系统坐标系,
     * GCJ02 = 2 国家测绘局坐标系,
     * BD09 = 3 百度坐标系
     */
    private Integer coordinate;

    /**
     * N
     * GPS时间戳
     */
    private Long timestamp;

    /**
     * N
     * 定位精度
     */
    private Integer accu;
}
