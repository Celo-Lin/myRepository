package com.juan.adx.model.dsp.yueke.request;

import lombok.Data;

import java.util.List;

@Data
public class YueKeImg {
    /**
     * Y
     * 图片宽度，单位: 像素
     */
    private Integer w;

    /**
     * Y
     * 图片高度，单位: 像素
     */
    private Integer h;

    /**
     * N
     * 流量售卖位在设备屏幕上显示的位置编号，默认: 0
     */
    private Integer pos;

    /**
     * N
     * 1: 图标，2:品牌，3:主图   默认：3
     */
    private Integer type;

    /**
     * N
     * 默认填写: image/gif，image/jpeg，image/jpg，image/png
     */
    private List<String> mimes;
}
