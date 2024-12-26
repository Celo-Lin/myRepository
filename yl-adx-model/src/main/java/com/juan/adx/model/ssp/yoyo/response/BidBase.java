package com.juan.adx.model.ssp.yoyo.response;

import lombok.Data;

import java.util.List;

/**
* @Author: ChaoLong Lin
* @CreateTime: 2024/12/19 10:04
* @Description: YoYo 响应创意对象
* @version: V1.0
*/
@Data
public class BidBase {
    /**
     * 广告唯一id
     */
    private String id;

    /**
     * 广告创意物料信息对象
     */
    private BidAdContent bidAdContent;

    /**
     * dsp供应商出价(单位为分)
     */
    private Integer price;

    /**
     * 监测链接对象
     */
    private TrackBase track;

    /**
     * 曝光对象ID，需要与请求对应
     */
    private String impId;

    /**
     * 广告位ID
     */
    private String tagId;

    /**
     * 交互类型：1=落地页，2=应用下载，3=dp+落地页，4=dp+应用下载，5=微信小程序，6=微信小游戏，7=快应用
     */
    private Integer targetType;

    /**
     * 投放所属行业(二级行业ID)，部分流量需要通过接口提交产品送审，通过审核接口获取
     */
    private String industry;

    /**
     * 投放产品ID，部分流量需要通过接口提交产品审核，通过审核接口获取
     */
    private Integer adProductId;

    /**
     * 投放商店渠道包通道时，需要先将应用提交yoyoadx进行审核，由yoyoadx进行分配 ; 审核接口见4.0应用应用送审接口部分
     */
    private Integer appId;

    /**
     * 模版ID，需返回请求支持的模板id
     */
    private String templateId;

    /**
     * 流量包ID，部分业务的请求体会传，使用流量包ID时请在返回体中回传请求体传入的值
     */
    private List<Integer> flowPkgIds;

    /**
     * 人群 ID列表，使用人群ID时请在返回体中回传请求体传入的值
     */
    private List<Integer> crowdPackageIds;
}
