package com.juan.adx.model.ssp.xunfei.request;

import lombok.Data;

import java.util.List;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/22 9:35
 */
@Data
public class XunfeiSspReqDevice {

    /**
     * 设备屏幕宽度，单位：像素
     */
    private Integer w;

    /**
     * 设备屏幕高度，单位：像素
     */
    private Integer h;

    /**
     * 浏览器属性 User Agent
     */
    private String ua;

    /**
     * 设备的公网 ip 地址(ipV4)
     */
    private String ip;

    /**
     * 设备的当前地理位置信息
     */
    private XunfeiSspReqGeo geo;

    /**
     * 安卓 imei （安卓设备 imei,
     * imei_md5 ，adid, adid_md5，oaid，
     * oaid_md5 不可能同时为空）
     */
    private String imei;

    /**
     * 安卓 imei md5 值（安卓设备 imei,
     * imei_md5 ，adid, adid_md5，
     * oaid,oaid_mad5 不可能同时为空）
     */
    private String imei_md5;

    /**
     * 安卓 oaid（安卓设备 imei, imei_md5 ，
     * adid, adid_md5，oaid，oaid_md5 不可能
     * 同时为空）
     */
    private String oaid;

    /**
     * 安卓 oaid md5 值（安卓设备 imei,
     * imei_md5 ，adid, adid_md5，oaid，
     * oaid_md5 不可能同时为空）
     */
    private String oaid_md5;

    /**
     * 安卓 adid（安卓设备 imei, imei_md5 ，
     * adid, adid_md5，oaid，oaid_md5 不可能
     * 同时为空）
     */
    private String adid;

    /**
     * 安卓 adid md5 值（安卓设备 imei,
     * imei_md5 ，adid, adid_md5，oaid，
     * oaid_md5 不可能同时为空）
     */
    private String adid_md5;

    /**
     * iOS 设备 openUDID
     */
    private String openudid;

    /**
     * iOS 设备 openUDID md5 值
     */
    private String openudid_md5;
    /**
     * MAC 地址
     */
    private String mac;

    /**
     * MAC 的 md5 值
     */
    private String mac_md5;

    /**
     * iOS idfa(iOS 设备 idfa,idfa_md5,caid
     * 不可能同时为空)
     */
    private String idfa;

    /**
     * iOS idfa_md5 值(iOS 设备
     * idfa,idfa_md5,caid 不可能同时为空)
     */
    private String idfa_md5;

    /**
     * iOS 广协 caid 列表(iOS 设备
     * idfa,idfa_md5,caid 不可能同时为空)。
     * 若存在多个版本的 caid，则全部传送。
     */
    private List<XunfeiSspReqCaid> caid_list;

    /**
     * 设备生产商，如”samsang”
     */
    private String make;

    /**
     * 设备型号,如”gt-9128”
     */
    private  String model;

    /**
     * 操作系统 ：
     * 0 - Android
     * 1 - iOS
     */
    private Integer os;

    /**
     * 操作系统版本号，如”4.1”
     */
    private String osv;

    /**
     * 运营商 ID：
     * 0 – 未知
     * 1-中国移动
     * 2-中国联通
     * 3-中国电信
     */
    private Integer carrier;

    /**
     * 语言，如 zh-CN
     */
    private String lan;

    /**
     * 网络连接类型：
     * 0—未知
     * 1—Ethernet
     * 2—wifi
     * 3—蜂窝网络，未知代
     * 4—2G
     * 5—3G
     * 6—4G
     * 7—5G
     */
    private Integer connection_type;

    /**
     * 设备类型:
     * 0—手机
     * 1—平板
     * 2—互联网电视
     */
    private Integer device_type;

    /**
     * 电视屏幕尺寸，如 65 寸
     */
    private Integer tv_size;

    /**
     * 横竖屏
     * 0 – 竖屏
     * 1 - 横屏
     */
    private Integer orientation;

    /**
     * 系统启动标识，请填写原值，如
     * iOS：1623815045.970028，
     * Android：ec7f4f33-411a-47bc-8067-744a
     * 4e7e0723
     */
    private  String boot_mark;

    /**
     * 系统更新标识，请填写原值，如
     * iOS：1581141691.570419583
     * Android：1004697.709999999
     */
    private String update_mark;

    /**
     * 系统更新时间，保留 6 位小数，如
     * iOS：1654125072.366302
     */
    private String update_time;

    /**
     * 设备初始化时间，请填写原值，如
     * iOS:1647545718.125795458
     */
    private String birth_time;

    private Object ext;

}
