package com.juan.adx.model.ssp.yoyo.request;

import lombok.Data;

/**
* @Author: ChaoLong Lin
* @CreateTime: 2024/12/19 9:51
* @Description: YoYo 请求设备位置信息对象
* @version: V1.0
*/
@Data
public class GeoBase {
    /**
     * 城市
     */
    private String city;

    /**
     * 国家编码 , ISO-3166-1-alpha-3 编码
     */
    private String country;

    /**
     * 拓展字段
     */
    private String ext;

    /**
     * 纬度-90.0～+90.0 , 负值代表南半球
     */
    private Double lat;

    /**
     * 经度-180.0～+180.0 , 负值代表西半球
     */
    private Double lon;

    /**
     * 地区
     */
    private String region;

    /**
     * 位置数据来源 , 1=GPS定位 , 2=IP地址 , 3=用户提供
     */
    private Integer type;

    /**
     * 省份
     */
    private String province;
}
