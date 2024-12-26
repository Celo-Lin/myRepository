package com.juan.adx.model.ocpx;

import lombok.Data;

@Data
public class OcpxClickLog {
    /**
     * 主键
     */
    private Long id;

    /**
     * 广告ID（来自ocpx_ad表）
     */
    private Integer adId;

    /**
     * 渠道ID（来自ocpx_channel表）
     */
    private Integer channelId;

    /**
     * 链接对应的渠道号 如 1024339d，
     * 是否必填，是
     */
    private String packageId;

    /**
     * 广告计划ID
     */
    private String planId;

    /**
     * 广告计划名称
     */
    private String planName;

    /**
     * 广告单元ID
     */
    private String unitId;

    /**
     * 广告单元名称
     */
    private String unitName;

    /**
     * 广告创意ID
     */
    private String creativeId;

    /**
     * 广告创意名称
     */
    private String creativeName;

    /**
     * Android设备标识
     */
    private String imei;

    /**
     * Android设备标识（MD5）
     */
    private String imeiMd5;

    /**
     * Android设备标识
     */
    private String oaid;

    /**
     * Android设备标识（MD5）
     */
    private String oaidMd5;

    /**
     * Android设备标识
     */
    private String androidId;

    /**
     * 移动设备UA
     */
    private String ua;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 回调事件类型
     */
    private Integer eventType;

    /**
     * 渠道回调地址
     */
    private String callbackUrl;

    /**
     * 渠道请求唯一标识（渠道请求我方时携带）
     */
    private String channelRequestId;

    /**
     * 广告点击时间戳（秒）
     */
    private Long adClickTime;

    /**
     * 广告主核实状态:0 未核实; 1 已核实
     */
    private Integer verifyStatus;

    /**
     * 通知渠道状态: 0 等待通知; 1 已通知
     */
    private Integer notifyStatus;

    /**
     * 通知渠道次数
     */
    private Integer notifyNum;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 广告主核实回调时间
     */
    private Long verifyTime;

    /**
     * 通知渠道时间
     */
    private Long notifyTime;

    /**
     * 分表字段，按月分表（yyyyMM）
     */
    private Integer sharMonth;
    
}
