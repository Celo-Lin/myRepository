package com.juan.adx.model.dsp.yueke.request;

import lombok.Data;

@Data
public class YueKeDeal {
    /**
     * Y
     * 交易编号，生成方式:随机字符串的MD5加密小写
     */
    private Integer id;

    /**
     * Y
     * 结算方式，1:第一价格  2：第二价格  3: bidfloor的值为交易价格
     */
    private Integer at;

    /**
     * N
     * 订单价格，单位：分
     */
    private Float bidfloor;
}
