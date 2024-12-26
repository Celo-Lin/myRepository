package com.juan.adx.model.dsp.yueke.request;

import lombok.Data;

import java.util.List;

@Data
public class YueKePmp {
    /**
     * Y
     * 私有拍卖:  1:是 0:否
     */
    private Integer private_auction;

    /**
     * Y
     * 私有交易，流量采买标准描述信息
     */
    private List<YueKeDeal> deals;
}
