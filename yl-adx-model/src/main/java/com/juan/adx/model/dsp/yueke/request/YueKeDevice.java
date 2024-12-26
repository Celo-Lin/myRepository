package com.juan.adx.model.dsp.yueke.request;

import lombok.Data;

import java.util.List;

@Data
public class YueKeDevice {
    /**
     * Y
     * 用户代理
     */
    private String ua;

    /**
     * Y
     * 定位信息
     */
    private YueKeGeo geo;

    /**
     * Y
     * Ipv4地址
     */
    private String ip;

    /**
     * N
     * Ipv6地址
     */
    private String ipv6;

    /**
     * Y
     * 设备类型
     * pc:2, 
     * tv:3, 
     * 手机:4, 
     * 平板:5, 
     * 机顶盒:7, 
     * 未知:0
     */
    private Integer devicetype;

    /**
     * Y
     * 生产厂商
     */
    private String make;

    /**
     * Y
     * 设备型号
     */
    private String model;

    /**
     * Y
     * 操作系统
     * 苹果设备:“ ios ”  安卓设备: " android "
     */
    private String os;

    /**
     * Y
     * 系统版本
     * 例如: 1.0.0. 1.0.1
     */
    private String osv;

    /**
     * N
     * 硬件版本
     */
    private String hwv;

    /**
     * Y
     * 设备屏幕高度
     * 单位：像素px， 例如: 1920 * 1080
     */
    private Integer h;

    /**
     * Y
     * 设备屏幕宽度
     * 单位：像素px， 例如: 1920 * 1080
     */
    private Integer w;

    /**
     * Y
     * 屏幕逻辑分辨率宽
     * 例如: 320 * 480
     */
    private Integer sw;

    /**
     * Y
     * 屏幕逻辑分辨率高
     * 例如: 320 * 480
     */
    private Integer sh;

    /**
     * Y
     * 像素密度
     * 移动设备屏幕像素密度, 每英寸屏幕上显示的像素数量
     */
    private Integer ppi;

    /**
     * Y
     * 屏幕像素密度
     * 移动设备屏幕物理像素密度，常见的取值1.5，1.0
     */
    private Integer dpi;

    /**
     * Y
     * 广协caid
     * 苹果设备 caid与ifa必须存在其一
     */
    private String caid;

    /**
     * Y
     * 广协caid版本
     * 中国广告协会caid算法版本
     */
    private String caidVer;

    /**
     * Y
     * 苹果idfa
     * 苹果设备 caid与ifa必须存在其一
     */
    private String ifa;

    /**
     * Y
     * 苹果idfa
     * MD5（idfa），针对苹果设备无法传递明文时必填
     */
    private String ifamd5;

    /**
     * N
     * 苹果idfv
     */
    private String ifv;

    /**
     * N
     * 苹果idfv
     * MD5（idfv)
     */
    private String ifvmd5;

    /**
     * N
     * 苹果udid
     */
    private String udid;

    /**
     * N
     * 苹果udid
     * MD5（udid)
     */
    private String udidmd5;

    /**
     * Y
     * 设备imei
     * 安卓设备 did与oaId必须存在其一
     */
    private String did;

    /**
     * Y
     * 设备imei
     * MD5( imei ) ，针对安卓设备无法传递明文时必填
     */
    private String didmd5;

    /**
     * Y
     * AndroidId
     * AndroidId 针对安卓设备必填
     */
    private String dpid;

    /**
     * Y
     * AndroidId
     * MD5(AndroidId ) 针对安卓设备无法传递明文时必填
     */
    private String dpidmd5;

    /**
     * Y
     * 安卓oaid
     * 安卓设备 did与oaid必须存在其一
     */
    private String oaId;

    /**
     * Y
     * Mac地址
     * 苹果默认：" 02:00:00:00:00:00 "; 安卓必填
     */
    private String mac;

    /**
     * Y
     * Mac地址
     * MD5( Mac ) 安卓必填
     */
    private String macmd5;

    /**
     * Y
     * 运 营 商
     * 移动：46000 , 46002 , 46007
     * 联通：46001 , 46006
     * 电信：46003 , 46005
     * 铁通：46020
     */
    private String carrier;

    /**
     * Y
     * 网络类型
     * 未知: 0 , 
     * 以太网: 1 , 
     * WIFI: 2 , 
     * 2G: 4，
     * 3G: 5，
     * 4G: 6，
     * 5G: 7
     */
    private Integer connectiontype;

    /**
     * N
     * 设备卡串
     * IMSI卡串信息，必须以460开头，总长度不超过15位
     */
    private String imsi;

    /**
     * Y
     * 屏幕方向
     * 0:竖屏 1:横屏
     */
    private Integer orientation;

    /**
     * N
     * 应用集合
     * 本机上已经安装的应用包的集合 (包名)
     */
    private List<String> apps;

    /**
     * N
     * 应用商店版本
     * Huawei、oppo、vivo必传
     */
    private String appstore;

    /**
     * N
     * 华为鸿蒙版本
     * Huawei HMS Core版本号
     */
    private String hms;

    /**
     * N
     * 设备开机时间
     * (IOS必填) 单位秒 例如：1595643553
     */
    private String startupTime;

    /**
     * N
     * 系统更新时间
     * (IOS必填) 单位秒 例如：1595214620.383940，参考代码
     */
    private String updateTime;

    /**
     * N
     * 国家代码
     * (IOS必填) 国家代码 例如：CN ，参考代码
     */
    private String country;

    /**
     * N
     * 系统默认语言
     * (IOS必填) 默认：“zh-Hans-CN” ，参考代码
     */
    private String language;

    /**
     * N
     * 设备时区
     * (IOS必填) 例如：28800
     */
    private String timeZone;

    /**
     * N
     * 设备内存大小
     * (IOS必填) 单位KB ，参考代码
     */
    private Long memorySize;

    /**
     * N
     * 设备硬盘大小
     * (IOS必填) 单位KB ，参考代码
     */
    private Long diskSize;

    /**
     * N
     * 设备名称的MD5
     * (IOS必填)
     */
    private String phoneNameMd5;

    /**
     * N
     * idfa授权状态
     * (IOS必填) 0:未确定，1:受限制，2:被拒绝，3:已授权
     */
    private Integer idfaPolicy;

    /**
     * Y
     * 系统启动标识
     * 系统启动标识，原值传输，参考代码
     */
    private String boot_mark;

    /**
     * Y
     * 系统更新标识
     * 系统更新标识，原值传输，参考代码
     */
    private String update_mark;

    /**
     * N
     * 拼多多归因 ID
     */
    private String paid;

    /**
     * N
     * 阿里设备 AAID
     */
    private String aaid;

    /**
     * N
     * 设备硬件型号
     */
    private String hardware_model;

    /**
     * N
     * 设备硬件 machine值
     */
    private String hardware_mechine;

    /**
     * N
     * 设备初始化时间
     * 例如：1595214620.383940000 （9位小数）
     */
    private String birthTime;
}
