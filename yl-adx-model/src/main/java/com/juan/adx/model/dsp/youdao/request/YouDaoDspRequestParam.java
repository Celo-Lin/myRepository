package com.juan.adx.model.dsp.youdao.request;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * 有道公共必填参数
 * @author： HeWen Zhou
 * @date： 2024/5/16 8:50
 */
@Data
public class YouDaoDspRequestParam {

    /**
     * 移动广告位 ID
     */
    private String id;

    /**
     *当前应用的真实版本号
     */
    private String av;

    /**
     * 网络连接类型（UNKNOWN, ETHERNET, WIFI, MOBILE;），值可能为 0,1,2,3
     */
    private Integer ct;

    /**
     * 子网络连接类型
     * 当 ct 字段为 MOBILE，子网络为 2G 时为 11，3G 时值为 12，4G 时为 13，5G 时为 50；其它情况统一传入 0
     */
    private Integer dct;

    /**
     * 设备 ID，如 AndroidID 或 IDFA，要求明文大写
     */
    private String udid;

    /**
     * 设备平台 ID(e.g. CAID)，经 MD5 哈希处理。
     */
    @JSONField(name = "idfa_md5")
    private String idfa_md5;

    /**
     * MD5 后的 AndroidID
     */
    private String auidmd5;

    /**
     * IMEI（International Mobile Equipment Identity）是移动设备国际身份码的缩写
     */
    private String imei;

    /**
     * MD5 后的 IMEI
     */
    private String imeimd5;

    /**
     * 设备广告 id
     * 当 os 类型为 Android 时，表示 Android Advertising ID
     * 当 os 类型为 IOS 时，表示阿里巴巴广告标识
     */
    private String aaid;

    /**
     * 开放匿名 ID
     */
    private String oaid;

    /**
     * 设备平台 ID(e.g. OAID)，经 MD5 哈希处理。
     */
    @JSONField(name = "oaid_md5")
    private String oaid_md5;

    /**
     * 供应商标识符
     */
    private String idfv;

    /**
     * 中国广告协会互联网广告标识，需将值和版本号使用”_”拼接（若无版本号使用 0，多个使用”,” 拼接）
     */
    private String caid;

    /**
     * 中国广告协会互联网广告标识，经 MD5 哈希处理
     */
    @JSONField(name = "caid_md5")
    private String caid_md5;

    /**
     * 用户原始 IP 地址，当通过服务器请求广告时，必须传入此参数。客户端请求广告，无需传入此参数
     */
    private String rip;

    /**
     * 位置信息，GPS 或者网络位置，经纬度逗号分隔（经度在前，纬度在后），WGS-84 坐标系
     */
    private String ll;

    /**
     * 经纬度精确度，单位：米。若当前获取的 ll（位置信息）为小数点后 3 位/4 位小数，则填为 100 米/10 米，以此类推
     */
    private String lla;

    /**
     * 获取位置信息的时间与发起广告请求的时间差，单位为分钟
     */
    private String llt;

    /**
     * 定位所用的 provider
     * n(network) 为网络定位
     * g(gps) 为gps 定位
     * p(passive) 为其他 app 里面的定位信息，
     * f(fused)为系统返回的最佳定位
     */
    private String llp;

    /**
     * wifi 信息，用户将当前连接或者附近的 wifi 的 ssid 和 mac传送过来，非当前连接无法获取 mac，
     */
    private String wifi;


    /**----------------------------可选参数------------------------------------**/
    /**
     * 加密请求时使用此字段，其值为整个加密后的字段
     */
    private String s;

    /**
     * 加密请求时使用此字段，表明具体使用的加密方法
     */
    private Integer ydet;

    /**
     * 设备信息
     * 格式为“Build.MANUFACTURER,Build.MODEL,Build.PRODUAndroid
     * 如:samsung,GT-S5830,GT-S5830；iOS
     * 如：iPhone5,3
     */
    private String dn;

    /**
     * 时区，如：+0800
     */
    private String z;

    /**
     * 广告物料是否需要满足 HTTPS URL，
     * 如果值为 1，表示需要返回物料 URL 为 HTTPS 协议 (包括图片素材、展示点击 tracker 等 URL)；
     * 如果无此参数，表示无 HTTPS 要求，
     * 但广告物料 URL 可能为 HTTPS
     */
    private String isSecure;

    /**
     * 操作系统版本信息，
     * 如：3.1.2
     * 注意：请填写版本名，而不是 API 等级
     */
    private String osv;

    /**
     *竖屏横屏,
     * 可能值分别为:p,l,s,u;
     * u: 未知
     * p:portrait
     * l:landscape
     * s:square
     */
    private String o;

    /**
     * 国家类型，如中国 460
     */
    private String mcc;

    /**
     * 运营商，如移动 00,联通 01
     */
    private String mnc;

    /**
     * 国家代号，值如 cn
     */
    private String iso;

    /**
     * 运营商名，值可能为‘中国联通’
     */
    private String cn;

    /**
     * 小区码
     */
    private String lac;

    /**
     * 基站码，更加准确定位位置
     */
    private String cid;

    /**
     * 次请求的广告数量，默认值为 1。
     * 如果大于 1 为批量广告请求（请注意广告返回格式）,建议一次不要请求太多广告，如果有需求可以分批取
     */
    private Integer ran;

    /**
     * 在同一个信息流广告会话中，会将最新加载的广告推广创意的 ID 都传送给服务端，以便服务端进行广告去重。
     * 示例：cids=1,2,3。
     * 那么本次请求将不会返回变体 id 为 1, 或者 2或者 3 的广告
     */
    private List<Integer> cids;

    /**
     * 当希望访问 clktracker 后跳转到 clk，即通过点击跟踪链接由有道的服务跳转到落地页时，
     * 可以通过 isrd=1 来启用此参数。如果启用此参数，无须通过访问 clk 进入落地页
     */
    private Integer isrd;

    /**
     * 设备屏幕物理尺寸高度，单位：像素。
     */
    @JSONField(name = "sc_h")
    private Integer sc_h;

    /**
     * 设备屏幕物理尺寸高度，单位：像素。
     */
    @JSONField(name = "sc_w")
    private Integer sc_w;

    /**
     * 屏 幕 分 辨 率， 值 如：1.0.
     */
    @JSONField(name = "sc_a")
    private Integer sc_a;

    /**
     * 设备屏幕像素密度。
     */
    @JSONField(name = "sc_ppi")
    private Integer sc_ppi;

    /**
     * 为用户在开发者域名下的 cookie ID，如果为 PC 广告位，此字段必填。
     */
    @JSONField(name = "user_id")
    private String user_id;

    /**
     *用户的年龄, 填写实际年龄数字
     */
    @JSONField(name = "m_age")
    private Integer m_age;

    /**
     *用户的性别,0: 未知,1: 男,2: 女
     */
    @JSONField(name = "m_gender")
    private Integer m_gender;

    /**
     * portrait keywords: 用户画像关键词, 多个关键词用,分割
     */
    @JSONField(name = "por_words")
    private String por_words;

    /**
     * 手机用户的名称
     */
    @JSONField(name = "phone_name")
    private String phone_name;

    /**
     * power on time: 从开机到请求广告的时间, 单位 ms
     */
    private Long pwot;

    /**
     * SIM 卡串号
     */
    private String imsi;

    /**
     * rom version: 手机 ROM 的版本
     */
    private String romv;

    /**
     * system compiling timestamp: 系统编译时间,rom 的编译时间 (时间戳)
     * 如:”1596270702.486691”,
     * 整数部分精确到 s,小数部分精确到 ns
     */
    private String sysct;

    /**
     * device language: 设备语言, 如:zh-CN
     */
    private String dlg;

    /**
     * device startup timestamp: 手机开机时间 (时间戳)
     * 如:”1596270702.486691”, 整数部分精确到 s, 小数部分精确到 ns
     */
    private String dst;

    /**
     * device update timestamp: 系统版本更新时间 (时间戳)
     * 如:”1596270702.486691”, 整数部分精确到 s, 小数部分精确到 ns
     */
    private String dut;

    /**
     * cpu 数目
     */
    private String cpum;

    /**
     * 磁盘最大存储, 单位 Byte
     */
    @JSONField(name = "rom_t")
    private String rom_t;

    /**
     * 内存最大容量, 单位 Byte
     */
    @JSONField(name = "ram_t")
    private String ram_t;

    /**
     * IOS 广告标识授权情况, 如”3”(代表 authorized)
     */
    private Integer iosauth;

    /**
     * 苹果设备的硬件代码, 如”D22AP”
     */
    @JSONField(name = "device_type")
    private String device_type;

    /**
     * 苹果账户 id
     */
    private String dsid;

    /**
     * 是否请求厂商应用商店下载类广告, 1 表示支持应用商店下载
     */
    @JSONField(name = "spt_mkt")
    private String spt_mkt;

    /**
     * 是否支持 ulink：0-不支持；1-支持，不传参默认支持，不支
     * 持时必须显式传参 isu=0，
     */
    private String isu;

    /**
     * 广告位样式 ID，指定多个样式时使用英文逗号分隔，携带
     * 该字段时，广告物料会优先按照指定样式的尺寸进行响应；
     * 如无特殊需求，不建议携带该参数进行请求，以免影响流量
     * 填充
     */
    private String rstyleID;

    /**
     * HMS Core 版本号，实现被推广应用的静默安装依赖 HMS
     * Core 能力。hwidv >= 50200100。
     */
    private String hwidv;

    /**
     * 应用市场版本号。与下载类广告的转化路径有关。mktv >=
     * 110002000。
     */
    private String mktv;

    /**
     * floorPrice: 广告位底价，单位：分
     */
    private Integer bidFloor;

}
