package com.juan.adx.model.ssp.yoyo.response;

import lombok.Data;

import java.util.List;

/**
 * @Author: ChaoLong Lin
 * @CreateTime: 2024/12/19 10:13
 * @Description: YoYo 响应广告创意物料信息对象
 * @version: V1.0
 */
@Data
public class BidAdContent {

    /**
     * 标题 , 文字
     */
    private String title;

    /**
     * 描述 , 文字
     */
    private String description;

    /**
     * 来源（DSP名称） , 文字
     */
    private String source;

    /**
     * 品牌名称（投放的产品名称），如“淘宝”，文字
     */
    private String brandName;

    /**
     * 广告主名称，投放的产品对应的公司主体名称，比如淘宝预算，则返回“淘宝（中国）软件有限公司”
     */
    private String advertiserName;

    /**
     * 行动语 , 文字
     */
    private String actionText;

    /**
     * 主图 , url
     */
    private String image;

    /**
     * 图标 , url
     */
    private String icon;

    /**
     * 视频 , url
     */
    private String video;

    /**
     * 视频时长 , 数字值 , 单位为秒
     */
    private Integer duration;

    /**
     * 视频文件大小 , 单位为KB
     */
    private Integer videoFileSize;

    /**
     * html
     */
    private String html;

    /**
     * 落地页链接 , url, targetType交互类型=1/3时必填
     */
    private String landingUrl;

    /**
     * 应用下载链接 , url, targetType交互类型=2时必填
     */
    private String actionUrl;

    /**
     * 应用图标 , url, targetType交互类型=2时必填
     */
    private String appIcon;

    /**
     * 应用名称 , 文字, targetType交互类型=2时必填
     */
    private String appName;

    /**
     * 包名 , 文字, targetType交互类型=2/3时必填
     */
    private String pkgName;

    /**
     * 应用内部版本号, targetType交互类型=2时必填
     */
    private String appVerName;

    /**
     * 应用版本名称, targetType交互类型=2时必填
     */
    private Integer appVerCode;

    /**
     * 应用文件大小 , 数字值 , 单位为Bit, targetType交互类型=2时必填
     */
    private Integer appFileSize;

    /**
     * appmd5, targetType交互类型=2时必填
     */
    private String appFileMd5;

    /**
     * app备案号, targetType交互类型=2时必填
     */
    private String appIcp;

    /**
     * app隐私协议--文本, targetType交互类型=2时必填
     */
    private String appPrivacy;

    /**
     * app隐私协议--url, targetType交互类型=2时必填
     */
    private String appPrivacyUrl;

    /**
     * app权限--文本, targetType交互类型=2时必填
     */
    private String appPermission;

    /**
     * app权限--url, targetType交互类型=2时必填
     */
    private String appPermissionUrl;

    /**
     * app开发者, targetType交互类型=2时必填
     */
    private String appDeveloper;

    /**
     * app描述--文本, targetType交互类型=2时必填
     */
    private String appDescription;

    /**
     * app描述--url, targetType交互类型=2时必填
     */
    private String appDescriptionUrl;

    /**
     * deeplink链接, targetType交互类型=3/4/7时必填
     */
    private String deeplink;

    /**
     * 行单图/三图
     */
    private List<String> imgUrlList;

    /**
     * 激励视频最后一图片
     */
    private String videoCover;

    /**
     * 应用下载通道
     * 1=应用商店包：指投放媒体侧应用商店主包，通过点击归因，不需要应用送审
     * 2=商店渠道包：通过渠道号区别不同类型的数据/归因，需要提前进行应用送审
     * 3=直链下载：需要返回下载链接
     */
    private Integer pkgChlType;

    /**
     * 微信小程序ID，targetType交互类型=5/6时必填
     */
    private String originId;

    /**
     * 微信小程序页面路径，targetType交互类型=5/6时必填
     */
    private String originPath;

    /**
     * 拉起小程序页面需要的拓展参数
     */
    private String extData;

    /**
     * IOS的universal link 链接
     */
    private String universalLink;

    /**
     * IOS Appstore AppID
     */
    private String iosAppid;
}
