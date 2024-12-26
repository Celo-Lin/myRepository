package com.juan.adx.model.dsp.haoya.request;
import lombok.Data;

@Data
public class HaoYaDevice {
    /**
     * <pre>
     * ua: 浏览器的 user agent
     * </pre>
     */
    private String ua;

    /**
     * <pre>
     * type: 设备类型， 
     * 0-PC 
     * 1-手机 
     * 2-平板 
     * 3-互联网电视
     * </pre>
     */
    private Integer type;

    /**
     * <pre>
     * brand: 设备品牌(如: apple)
     * </pre>
     */
    private String brand;

    /**
     * <pre>
     * model: 设备型号(如: iphone)
     * </pre>
     */
    private String model;

    /**
     * <pre>
     * make: 设备制造商
     * </pre>
     */
    private String make;

    /**
     * <pre>
     * hmsVersion: 华为机型 HMS Core 版本号(如: 60000306)
     * </pre>
     */
    private String hmsVersion;

    /**
     * <pre>
     * asVersion: 大陆厂商安卓设备 AS 版本号(如: 110302302)
     * </pre>
     */
    private String asVersion;

    /**
     * <pre>
     * os: 操作系统，
     * 0-windows、
     * 1-macOS、
     * 2-Linux、
     * 3-iOS、
     * 4-Android、
     * 5-wp
     * </pre>
     */
    private Integer os;

    /**
     * <pre>
     * osVersion: 操作系统版本号
     * </pre>
     */
    private String osVersion;

    /**
     * <pre>
     * density: 屏幕密度，默认 400，即将废弃，用ppi字段取代
     * </pre>
     */
    private Integer density;

    /**
     * <pre>
     * ppi: 像素密度，表示每英寸像素点，默认为400
     * </pre>
     */
    private Integer ppi;

    /**
     * <pre>
     * pxRatio: 屏幕密度， 例如 1.5
     * </pre>
     */
    private Double pxRatio;

    /**
     * <pre>
     * width: 屏幕宽度，单位：像素
     * </pre>
     */
    private Integer width;

    /**
     * <pre>
     * height: 屏幕高度，单位：像素
     * </pre>
     */
    private Integer height;

    /**
     * <pre>
     * carrier: 运营商，
     * 0-电信、
     * 1-移动、
     * 2-联通、
     * 3-网通、
     * 4-未知
     * </pre>
     */
    private Integer carrier;

    /**
     * <pre>
     * network: 网络类型，
     * 0-wifi、
     * 1-有线网络、
     * 2-2G、
     * 3-3G、
     * 4-4G、
     * 5-5G、
     * 6-未知
     * </pre>
     */
    private Integer network;

    /**
     * <pre>
     * orientation: 屏幕方向 0-未知 1-竖屏 2-横屏
     * </pre>
     */
    private Integer orientation;

    /**
     * <pre>
     * ip: ipv4，客户端IP, 与ipv6至少有一个必传
     * </pre>
     */
    private String ip;

    /**
     * <pre>
     * ipv6: ipv6，客户端IP
     * </pre>
     */
    private String ipv6;

    /**
     * <pre>
     * imei: 设备号 imei
     * </pre>
     */
    private String imei;

    /**
     * <pre>
     * imeiMD5: 设备号 imei 的 MD5
     * </pre>
     */
    private String imeiMD5;

    /**
     * <pre>
     * oaid: Android Q 以上版本的设备号 明文形式
     * </pre>
     */
    private String oaid;

    /**
     * <pre>
     * oaidMD5: oaid 的 MD5
     * </pre>
     */
    private String oaidMD5;

    /**
     * <pre>
     * dpid: 设备的 Android ID
     * </pre>
     */
    private String dpid;

    /**
     * <pre>
     * dpidMD5: 设备的 Android ID 对应 MD5
     * </pre>
     */
    private String dpidMD5;

    /**
     * <pre>
     * mac: 设备 mac 地址
     * </pre>
     */
    private String mac;

    /**
     * <pre>
     * macMD5: 设备 mac 地址的 MD5
     * </pre>
     */
    private String macMD5;

    /**
     * <pre>
     * idfa: ios 设备号 idfa
     * </pre>
     */
    private String idfa;

    /**
     * <pre>
     * idfaMD5: ios 设备号 idfa 的 MD5
     * </pre>
     */
    private String idfaMD5;

    /**
     * <pre>
     * bootMark: 取原值进⾏传输 iOS：1623815045.970028 Android：ec7f4f33-411a-47bc-8067-744a4e7e0723
     * </pre>
     */
    private String bootMark;

    /**
     * <pre>
     * updateMark: 取原值进⾏传输 iOS：1581141691.570419583 Android：1004697.709999999
     * </pre>
     */
    private String updateMark;

    /**
     * <pre>
     * birthTime: 设备初始化时间
     * </pre>
     */
    private String birthTime;

    /**
     * <pre>
     * bootTime: 系统启动时间
     * </pre>
     */
    private String bootTime;

    /**
     * <pre>
     * updateTime: 系统更新时间
     * </pre>
     */
    private String updateTime;

    /**
     * <pre>
     * language: 系统语言
     * </pre>
     */
    private String language;

    /**
     * <pre>
     * geo: 地域信息
     * </pre>
     */
    private HaoYaGeo geo;

    /**
     * <pre>
     * paid: 拼多多版caid，仅 IOS，预算要求尽量填写，生成规则见下方
     * </pre>
     */
    private String paid;

    /**
     * <pre>
     * caid: 中广协设备 id，仅 iOS
     * </pre>
     */
    private HaoYaCaid caid;

    /**
     * <pre>
     * aaid: 阿里集团内推出的匿名广告标识符
     * </pre>
     */
    private String aaid;

    /**
     * <pre>
     * elapseTime: 开机时长
     * </pre>
     */
    private String elapseTime;

    /**
     * <pre>
     * sysCpuNum: 设备cpu数量
     * </pre>
     */
    private Integer sysCpuNum;

    /**
     * <pre>
     * batteryState: 设备当前充电状态 1:未知状态，2:正在充电，3 放电 4:未充满，5:满状态
     * </pre>
     */
    private Integer batteryState;

    /**
     * <pre>
     * battery: 设备电量
     * </pre>
     */
    private Integer battery;

    /**
     * <pre>
     * romVersion: 系统rom版本
     * </pre>
     */
    private String romVersion;

    /**
     * <pre>
     * sdFreeSpace: 磁盘剩余空间
     * </pre>
     */
    private String sdFreeSpace;
}
