package com.juan.adx.model.ssp.yoyo.request;

import lombok.Data;

/**
 * @Author: ChaoLong Lin
 * @CreateTime: 2024/12/19 9:40
 * @Description: YoYo 请求出价对象
 * @version: V1.0
 */
@Data
public class BidInfoBase {
    /**
     * 支持的出价类型 , 1=CPM , 2=CPC , 3=CPD , 默认为1
     */
    private Integer billingType;

    /**
     * 底价，单位分(无底价填0)
     */
    private Double bidFloor;
}
