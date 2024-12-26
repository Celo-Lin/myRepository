package com.juan.adx.model.dsp.vivo.request;

import lombok.Data;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/25 16:07
 */
@Data
public class VivoDspRequestParam {

    /** 协议版本（非对接文档版本），当前协议版本 1.0 */
    private String apiVersion;

    /** vivo系统ROM版本号，无法获取可填写unknow */
    private String sysVersion;

    /** vivo应用商店版本号 */
    private Long appstoreVersion;

    /** 超时要求，设置后vivo侧会尽可能保证在该时间内返回 */
    private Integer timeout;

    /** 用户信息 */
    private VivoUser user;

    /** 媒体信息 */
    private VivoMedia media;

    /** 广告位信息 */
    private VivoPostion position;

    /** 设备信息 */
    private VivoDevice device;

    /** 网络信息 */
    private VivoNetwork network;

    /** 地理位置信息，若无法获取请勿传该字段 */
    private VivoGeo geo;

    /** 媒体与平台约定的PMP交易信息 */
    private VivoPmp pmp;

    /** 最大返回广告条数，默认为1 */
    private Integer adCount;

    /** 是否支持小程序调起，1-支持，0-不支持，不传默认不支持 */
    private Integer isSupportMiniProgram;

    /** vivo浏览器版本 */
    private Integer browserVersion;

}
