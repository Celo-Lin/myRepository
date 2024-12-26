package com.juan.adx.model.dsp.vivo.request;

import lombok.Data;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/25 16:06
 */
@Data
public class VivoDevice {

    /** Mac 地址，如 00:0A:D5:B7:80:5E，不传或乱填或修改原始值或传固定值或可能会影响收益 */
    private String mac;

    /** 安卓设备 IMEI 原始值，Android Q 以下版本必须提供，无法提供时则需传 didMd5 */
    private String imei;

    /** IMEI md5 串（小写），原始 IMEI 取 md5。Android Q 以下版本若原始 imei 无法提供时，则需传 didMd5 */
    private String didMd5;

    /** 匿名设备标识符，详见-OAID和VAID获取方式。Android Q 以上必传，否则可能无广告返回 */
    private String oaid;

    /** 开发者匿名设备标识符，详见-OAID 和 VAID 获取方式 */
    private String vaid;

    /** 安卓设备 ID，原始值 */
    private String androidId;

    /** 安卓系统版本，示例：6.0，详见 Android 版本 */
    private String an;

    /** 安卓系统版本号，安卓 API 等级。示例：23。 av >= 29 时，视为安卓系统为 Android Q 及以上 */
    private Integer av;

    /** 用户设备 HTTP 请求头中的 User-Agent 字段 */
    private String ua;

    /** 用户设备的公网出口 IPv4 地址，点分字符串形式，IP 示例：14.28.7.140 */
    private String ip;

    /** 设备厂商，示例：vivo，vivo 需为全小写 */
    private String make;

    /** 设备型号 ，示例：vivo x20 */
    private String model;

    /** 设备设置的语言，示例：zh-CN */
    private String language;

    /** 设备屏幕宽 */
    private Integer screenWidth;

    /** 设备手机屏幕高 */
    private Integer screenHeight;

    /** 屏幕 ppi */
    private Integer ppi;

    /** 开机时长 */
    private Long elapseTime;

    /** 个性化推荐开关状态 */
    private Integer personalizedState;

}
