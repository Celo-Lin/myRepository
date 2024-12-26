package com.juan.adx.model.dsp.yueke.request;

@lombok.Data
public class YueKeData {
    /**
     * N
     * 文字数据类型 ( 2:描述 )，默认：2
     */
    private Integer type;

    /**
     * Y
     * 文本长度要求，默认: 25
     */
    private Integer len;
}
