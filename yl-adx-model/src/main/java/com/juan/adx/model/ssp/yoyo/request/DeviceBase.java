package com.juan.adx.model.ssp.yoyo.request;

import lombok.Data;

import java.util.List;

/**
* @Author: ChaoLong Lin
* @CreateTime: 2024/12/19 9:44
* @Description: YoYo 请求设备信息对象
* @version: V1.0
*/
@Data
public class DeviceBase {
    /**
     * 浏览器用户代理
     */
    private String ua;

    /**
     *设备当前位置信息
     */
    private GeoBase geo;

    /**
     * ipv4地址
     */
    private String ip;

    /**
     * ipv6地址
     */
    private String ipv6;

    /**
     * 设备类型 , 0=未知 , 1=手机 , 2=平板 , 3=智能TV
     */
    private Integer deviceType;

    /**
     * 设备制造厂商
     */
    private String make;

    /**
     * 设备型号
     */
    private String model;

    /**
     * 手机机型
     */
    private String mobileModel;

    /**
     * 操作系统 , 0=未知/其他 , 1=ios , 2=android , 3=window , 4=macos , 5=linux , 6=wphone
     */
    private String os;

    /**
     * 操作系统版本
     */
    private String osv;

    /**
     *设备硬件版本号
     */
    private String hwv;

    /**
     * 屏幕高度物理像素
     */
    private Integer h;

    /**
     * 屏幕宽度物理像素
     */
    private Integer w;

    /**
     * 屏幕像素密度 , 每英寸屏幕包好的像素
     */
    private Integer ppi;

    /**
     * 物理像素和设备独立像素比例
     */
    private Double pxRatio;

    /**
     * 浏览器语言 , ISO-639-1-alpha-2编码
     */
    private String language;

    /**
     * 运营商 , 0=未知 , 1=中国移动 , 2=中国联通 , 3=中国电信 , 4=网通
     */
    private Integer carrier;

    /**
     * 网络连接类型 , -1=有线网络/宽带 , 0=未知 , 1=WIFI , 2=2G , 3=3G , 4=4G , 5=5G
     */
    private Integer connectionType;

    /**
     * 设备序列号
     */
    private String sn;

    /**
     * 设备序列号md5值
     */
    private String snMd5;

    /**
     * 安卓系统的imei原值 , 纯数字
     */
    private String did;

    /**
     * 安卓系统的imei原值MD5小写 , 纯数字的md5小写
     */
    private String didMd5;

    /**
     * 与didmd5两者必传其一 , Android Q 以上传oaId
     */
    private String oaId;

    /**
     * oaId原值Md5小写
     */
    private String oaIdMd5;

    /**
     * ios idfa原值
     */
    private String idfa;

    /**
     * idfa原值的md5小写
     */
    private String idfaMd5;

    /**
     * 平台设备ID (e.g：Android ID)
     */
    private String dpId;

    /**
     * 平台设备ID (e.g：Android ID)的MD5小写
     */
    private String dpIdMd5;

    /**
     * mac地址 , 对外不提供
     */
    private String mac;

    /**
     * mac地址的MD5值
     */
    private String macMd5;

    /**
     * 国际移动用户识别码
     */
    private String imsi;

    /**
     * 鸿蒙内核版本
     */
    private String hmsCoreVersion;

    /**
     * 手机应用商店版本号（Android）如vivo手机为vivo应用和商店版本号，华为为华为应用商店版本号
     */
    private String appStoreVersion;

    /**
     * 系统启动标识
     */
    private String bootMark;

    /**
     * 系统更新标识
     */
    private String updateMark;

    /**
     * 系统初始化时间
     */
    private String fileInitTime;

    /**
     * ios caid信息列表
     */
    private List<CaidBase> caidList;

    /**
     * 拼多多paid
     */
    private String paid;

    /**
     * 拓展字段
     */
    private String ext;
}