package com.juan.adx.model.dsp.oppo.request;
import lombok.Data;

@Data
public class OppoDeviceInfo {
    /**
     * <pre>
     * imei: 硬件设备 ID（imei 或 imeimd5 至少二选一），最好传 imei，
     * 否则会影响广告主的变现效果
     * </pre>
     */
    private String imei;

    /**
     * <pre>
     * imeiMd5: 硬件设备 ID MD5（imei 或 imeimd5 至少二选一）
     * </pre>
     */
    private String imeiMd5;

    /**
     * <pre>
     * oaId: Open Anonymous Device ID（无 imei 和 imeimd5 时 oaId
     * 或 vaId 至少二选一），匿名设备标识符（Android Q 必传，
     * OAID 和 VAID 获取方式）
     * </pre>
     */
    private String oaId;

    /**
     * <pre>
     * vaId: Vender Anonymous Device ID（无 imei 和 imeimd5 时
     * oaId 或 vaId 至少二选一），开发者匿名设备标识符（Android
     * Q 必传，OAID 和 VAID 获取方式）
     * </pre>
     */
    private String vaId;

    /**
     * <pre>
     * ip: IPv4 地址
     * </pre>
     */
    private String ip;

    /**
     * <pre>
     * ua: 浏览器用 user agent 串
     * </pre>
     */
    private String ua;

    /**
     * <pre>
     * mac: MAC 地址
     * </pre>
     */
    private String mac;

    /**
     * <pre>
     * macMd5: MACMd5 地址（MAC 或 MACMd5 至少二选一）
     * </pre>
     */
    private String macMd5;

    /**
     * <pre>
     * anId: android 设备的 Android ID，保留原始值。
     * </pre>
     */
    private String anId;

    /**
     * <pre>
     * colorOsv: colorOs 版本
     * </pre>
     */
    private String colorOsv;

    /**
     * <pre>
     * romv: ROM 版本
     * </pre>
     */
    private String romv;

    /**
     * <pre>
     * anVer: 设备操作系统版本(e.g., “7.1.1”).
     * </pre>
     */
    private String anVer;

    /**
     * <pre>
     * h: 屏幕的高(像素)(开屏必填)
     * </pre>
     */
    private Integer h;

    /**
     * <pre>
     * w: 屏幕的宽(像素)(开屏必填)
     * </pre>
     */
    private Integer w;

    /**
     * <pre>
     * density: 屏幕密度
     * </pre>
     */
    private Double density;

    /**
     * <pre>
     * connectionType: 网络连接类型.参照附录-网络连接类型
     * UNKNOWN 	Unknown 无法获取
     * 2G 		Cellular Network – 2G
     * 3G 		Cellular Network – 3G
     * 4G 		Cellular Network – 4G
     * 5G 		Cellular Network – 5G
     * WIFI 	WIFI
     * </pre>
     */
    private String connectionType;

    /**
     * <pre>
     * carrier: 运营商.参照附录-运营商
     * 0 未知
     * 1 移动
     * 2 联通
     * 3 电信
     * </pre>
     */
    private Integer carrier;

    /**
     * <pre>
     * ori: 屏幕旋转角度
     * </pre>
     */
    private Integer ori;

    /**
     * <pre>
     * gpsInfo: 推荐 设备当前地理位置(推荐填写，有助于触发 LBS 广告)，参见 GpsInfo Object
     * </pre>
     */
    private OppoGpsInfo gpsInfo;

    /**
     * <pre>
     * linkSpeed: Wifi 强度
     * </pre>
     */
    private Integer linkSpeed;

    /**
     * <pre>
     * brand: 设备制造商 (e.g., “OPPO”).
     * </pre>
     */
    private String brand;

    /**
     * <pre>
     * model: 设备型号(e.g., “OPPO R9”).
     * </pre>
     */
    private String model;

    /**
     * <pre>
     * bootMark: 安卓系统启动标识
     * </pre>
     */
    private String bootMark;

    /**
     * <pre>
     * updateMark: 安卓系统启动更新标识
     * </pre>
     */
    private String updateMark;
}
