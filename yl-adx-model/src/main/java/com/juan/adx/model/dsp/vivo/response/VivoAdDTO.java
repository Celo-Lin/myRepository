package com.juan.adx.model.dsp.vivo.response;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/25 16:32
 */
@Data
public class VivoAdDTO {

    /** 广告 ID */
    private String adId;

    /** 广告位类型 */
    private Integer adType;

    /** 创意类型 */
    private Integer materialType;

    /** 目标落地页链接地址 */
    private String targetUrl;

    /** deeplink 唤醒链接地址 */
    private String deeplink;

    /** 按钮 deeplink 唤醒链接地址 */
    private String deeplinkButton;

    /** 广告标题 */
    private String title;

    /** 广告描述 */
    private String description;

    /** 广告主头像图片链接地址 */
    private String sourceAvatar;

    /** 广告主头像图片链接地址 */
    private VivoImage image;

    /** 图片素材列表 */
    private List<VivoImage> imageList;

    /** 视频素材 */
    private VivoVideo video;

    /** deeplink 类被调起应用包名 */
    private String appPackage;

    /** deeplink 类被调起应用 icon 地址 */
    private String appIconUrl;

    /** deeplink 类被调起应用名称 */
    private String appName;

    /** deeplink 类被调起应用大小，单位 KB */
    private Long appSize;

    /** deeplink 类被调起应用开发者名称 */
    private String appDeveloper;

    /** deeplink 类被调起应用权限信息 */
    private List<Map<String, String>> appPermission;

    /** deeplink 类被调起应用隐私协议地址 */
    private String appPrivacyPolicyUrl;

    /** deeplink 类被调起应用版本号 */
    private String appVersionName;

    /** 应用下载地址 */
    private String downloadUrl;

    /** 广告效果监测列表 */
    private List<VivoTracking> trackingList;

    /** 广告来源标识 url */
    private String adLogo;

    /** 出价模式 */
    private Integer bidMode;

    /** 出价金额，单位：分/CPM */
    private Long price;

    /** 出价模式参竞结果通知链接 */
    private String noticeUrl;

    /** Deal 对象对应的 id 字段内容 */
    private String dealId;

    /** 广告位 ID */
    private String positionId;

    /** 广告 token，用于媒体侧与 vivo 联盟数据串联 */
    private String token;

    /** 小程序调起广告信息 */
    private VivoMiniProgram miniProgram;

}
