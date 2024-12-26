package com.juan.adx.model.dsp.yidian.request;

import lombok.Data;

import java.util.List;

/**
 * @author caoliwu
 * @version 1.0
 * @ClassName YiDianDevice
 * @description: TODO
 * @date 2024/5/28 9:44
 */
@Data
public class YiDianDevice {
    /**
     * <pre>
     * 设备位置信息 ，见 YiDianGeo,强烈建议填
     * </pre>
     */
    private YiDianGeo geo;

    /**
     * <pre>
     * 浏览器的 User-Agent 属性字符串
     * </pre>
     */
    private String ua;

    /**
     * <pre>
     * 设备的 IPv4 地址
     * </pre>
     */
    private String ip;

    /**
     * <pre>
     * 设备的 IPv6 地址，选填
     * </pre>
     */
    private String ipv6;

    /**
     * <pre>
     * 设备类型：
     * 0: 未知
     * 1: 手机
     * 2: 平板
     * 3: PC
     * 4: 智能电视
     * </pre>
     */
    private Integer devicetype;

    /**
     * <pre>
     * 设备的操作系统，如 IOS
     * </pre>
     */
    private String os;

    /**
     * <pre>
     * 设备的操作系统版本，如 3.1.2
     * </pre>
     */
    private String osv;

    /**
     * <pre>
     * 物理屏幕宽度，单位像素 选填
     * </pre>
     */
    private Integer w;

    /**
     * <pre>
     * 物理屏幕高度，单位像素  选填
     * </pre>
     */
    private Integer h;

    /**
     * <pre>
     * 设备制造商
     * </pre>
     */
    private String make;

    /**
     * <pre>
     * 设备型号
     * </pre>
     */
    private String model;

    /**
     * <pre>
     * 每英大小寸屏幕，单位像素 选填
     * </pre>
     */
    private Integer ppi;

    /**
     * <pre>
     * 物理像素和设备独立像素的比例  选填
     * </pre>
     */
    private Double pxrate;

    /**
     * <pre>
     * 联网方式：
     * 0：未知
     * 1：Ethernet
     * 2：Wifi
     * 3：蜂窝网络
     * 4：蜂窝网络 2G
     * 5：蜂窝网络 3G
     * 6：蜂窝网络 4G
     * 7：蜂窝网络 5G
     * </pre>
     */
    private Integer connectiontype;

    /**
     * <pre>
     * 运营商：
     * 0：未知
     * 1：中国移动
     * 2：电信
     * 3：联通
     * 4：广电
     * 99：其他
     * </pre>
     */
    private Integer operatortype;

    /**
     * <pre>
     * 浏览器语言，ISO-639-1-alpha-2 编码 选填
     * </pre>
     */
    private String language;

    /**
     * <pre>
     * 已安装 app 定向  选填
     * </pre>
     */
    private List<String> appinstalled;

    /**
     * <pre>
     * 未安装 app 定向   选填
     * </pre>
     */
    private List<String> appuninstalled;

    /**
     * <pre>
     * 硬件设备 ID，对 Android 而言是 imei，
     * 对 ios 而言是 idfa
     * 原值和 md5 值必传其一
     * </pre>
     */
    private String didmd5;

    /**
     * <pre>
     * 硬件设备 ID，对 Android 而言是 imei，
     * 对 ios 而言是 idfa
     * 原值和 md5 值必传其一
     * </pre>
     */
    private String didsha1;

    /**
     * <pre>
     * 硬件设备 ID，对 Android 而言是 imei，
     * 对 ios 而言是 idfa
     * 原值和 md5 值必传其一
     * </pre>
     */
    private String did;

    /**
     * <pre>
     * 平台定义的设备 ID，对 Android 而言是
     * androidid，对 ios 而言是 idfv
     * Android 必填，原值和 md5 值必传其一
     * </pre>
     */
    private String dpidmd5;

    /**
     * <pre>
     * 平台定义的设备 ID，对 Android 而言是
     * androidid，对 ios 而言是 idfv
     * Android 必填，原值和 md5 值必传其一
     * </pre>
     */
    private String dpidsha1;

    /**
     * <pre>
     * 平台定义的设备 ID，对 Android 而言是
     * androidid，对 ios 而言是 idfv
     * Android 必填，原值和 md5 值必传其一
     * </pre>
     */
    private String dpid;

    /**
     * <pre>
     * OAID
     * Android O 版本及以上必传，原值和 md5
     * 值必传其一
     * </pre>
     */
    private String oaidsha1;

    /**
     * <pre>
     * OAID
     * Android O 版本及以上必传，原值和 md5
     * 值必传其一
     * </pre>
     */
    private String oaidmd5;

    /**
     * <pre>
     * OAID
     * Android O 版本及以上必传，原值和 md5
     * 值必传其一
     * </pre>
     */
    private String oaid;

    /**
     * <pre>
     * MAC 地址  选填
     * </pre>
     */
    private String macmd5;

    /**
     * <pre>
     * MAC 地址  选填
     * </pre>
     */
    private String macsha1;

    /**
     * <pre>
     * MAC 地址  选填
     * </pre>
     */
    private String mac;

    /**
     * <pre>
     * 系统启动标识，bootid，每次开机都会产生的一个唯一 ID。  选填
     * </pre>
     */
    private String bootMark;

    /**
     * <pre>
     * 系统更新标识  选填
     * </pre>
     */
    private String updateMark;

    /**
     * <pre>
     * 应用市场版本号。与下载类广告的转化路径有关。 选填
     * 华为设备必填。
     * </pre>
     */
    private String vercodeofag;

    /**
     * <pre>
     * HMS Core 版本号，实现被推广应用的静默安装依赖 HMS Core 能力。 选填
     * 华为设备必填。
     * </pre>
     */
    private String vercodeofhms;

    /**
     * <pre>
     * 设备名称的 MD5 值，ios 获取不到 idfa的情况下必填。（caid 所需字段）
     * </pre>
     */
    private String devicenamemd5;

    /**
     * <pre>
     * 设备 machine 值，ios 获取不到 idfa 的情况下必填。（caid 所需字段）
     * </pre>
     */
    private String hardwaremachine;

    /**
     * <pre>
     * 物理内存大小(字节) ，ios 获取不到 idfa的情况下必填。（caid 所需字段）
     * </pre>
     */
    private Long physicalmemorysize;

    /**
     * <pre>
     * 硬盘大小(字节) ，ios 获取不到 idfa 的情况下必填。（caid 所需字段）
     * </pre>
     */
    private Long harddisksize;

    /**
     * <pre>
     * 系统更新时间（秒）如：1647545826.588793，ios获取不到 idfa 的情况下必填。
     * </pre>
     */
    private String systemupdatetime;

    /**
     * <pre>
     * 国家,如:CN，ios 获取不到 idfa 的情况下必填。
     * </pre>
     */
    private String countrycode;

    /**
     * <pre>
     * 时区,如:28800，ios 获取不到 idfa 的情况下必填。
     * </pre>
     */
    private String timezone;

    /**
     * <pre>
     * 设备初始化时间(秒)如 ：1647545718.125795458，ios 获取不到 idfa的情况下必填。
     * </pre>
     */
    private String deviceinitializetime;

    /**
     * <pre>
     * 深度因子 T2，ios 选填。
     * </pre>
     */
    private String t2;

    /**
     * <pre>
     * 深度因子 T8，ios 选填。
     * </pre>
     */
    private String t8;

    /**
     * <pre>
     * keychain 持久化 ID，ios 选填。
     * </pre>
     */
    private String kid;

    /**
     * <pre>
     * caid 参数。 选填
     * </pre>
     */
    private String caid;
}
