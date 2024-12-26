package com.juan.adx.model.dsp.yueke.request;

import lombok.Data;

@Data
public class YueKeExt {
    /**
     * Y
     * 是否支持落地页或上报地址302重定向？
     * 0：不支持，1：支持，-1: 不限制
     */
    private Integer rdt;

    /**
     * Y
     * 是否支持Https协议落地页和事件上报？
     * 0：不支持，1：支持，-1：不限制
     */
    private Integer https;

    /**
     * Y
     * 是否支持深度链接调起？
     * 0：不支持三方应用调起，1: 支持三方应用调起，-1：不限制
     */
    private Integer deepLink;

    /**
     * Y
     * 是否支持一键下载？
     * 0：不支持一键下载，1：支持一键下载，-1：不限制
     */
    private Integer download;

    /**
     * Y
     * 是否支持HtML代码渲染展示？
     * 0: 不支持，1: 支持，2: 物料组合与HTML均支持
     */
    private Integer admt;

    /**
     * Y
     * 是否支持激励视频尾帧页面HtML加载渲染?
     * 0: 不支持，1: 支持，2: 物料组合与HTML均支持
     */
    private Integer vech;

    /**
     * Y
     * 是否支持激励视频页面VAST协议？
     * 0: 不支持，1: 支持，2: 物料组合与VAST均支持
     */
    private Integer vecv;
}
