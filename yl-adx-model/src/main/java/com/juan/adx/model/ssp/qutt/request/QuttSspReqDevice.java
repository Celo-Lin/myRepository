package com.juan.adx.model.ssp.qutt.request;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * 设备信息
 */
@Data
public class QuttSspReqDevice {

	/**
	 * ⼩于 32 字节
	 * android设备imei和imei_md5必
	 * 须填⼀个，优先imei
	 */
	private String imei;

	/**
	 *否
	 * android设备imei和imei_md5必
	 * 须填⼀个，优先imei
	 */
	@JSONField(name = "imei_md5")
	private String imeiMd5;

	/**
	 *否
	 * 设备的oaid，由于代替imei等
	 */
	private String oaid;

	/**
	 * 设备的oaid，由于代替imei等
	 */
	@JSONField(name = "oaid_md5")
	private String oaidMd5;

	/**
	 *否
	 * string
	 * ios设备idfa和idfa_md5必须填⼀
	 * 个，优先idfa
	 */
	private String idfa;

	/**
	 *否
	 * ios设备idfa和idfa_md5必须填⼀
	 * 个， 优先idfa
	 */
	@JSONField(name = "idfa_md5")
	private String idfaMd5;

	/**
	 *否
	 * android设备的androidid
	 */
	@JSONField(name = "android_id")
	private String androidId;

	/**
	 *否
	 * Android设备的androidid md5值
	 */
	@JSONField(name = "android_id_md5")
	private String androidIdMd5;

	/**
	 *设备的meid
	 */
	private String meid;

	/**
	 *设备meid的md5值
	 */
	@JSONField(name = "meid_md5")
	private String meidMd5;

	/**
	 *设备的mac地址
	 */
	private String mac;

	/**
	 *设备mac的md5值
	 */
	@JSONField(name = "mac_md5")
	private String macMd5;

	/**
	 *设备联⽹ip
	 */
	private String ip;



	/**
	 *否
	 * 设备类型。具体值参⻅附录
	 * DeviceType
	 */
	@JSONField(name = "device_type")
	private Integer deviceType;
	/**
	 *设备运⾏的操作系统，具体参⻅
	 * 附录OperatingSystem
	 * 否
	 */
	private Integer os;

	/**
	 *否
	 * ⼩于 128 字节
	 * 操作系统版本，如：“4.2.1”
	 */
	@JSONField(name = "os_version")
	private String osVersion;

	/**
	 *否
	 * ⼩于 1024 字节
	 * 系统User-Agent
	 */
	@JSONField(name = "user_agent")
	private String userAgent;

	/**
	 *否
	 * ⼩于1024字节
	 * 浏览器User-Agent
	 */
	@JSONField(name = "browser_user_agent")
	private String browserUserAgent;

	/**
	 *否
	 * 屏幕宽度，单位：像素
	 */
	@JSONField(name = "screen_width")
	private Integer screenWidth;

	/**
	 *否
	 * 屏幕⾼度，单位：像素
	 */
	@JSONField(name = "screen_height")
	private Integer screenHeight;

	/**
	 *否
	 * 屏幕每英⼨像素
	 */
	private Integer dpi;

	/**
	 *否
	 * 每英⼨像素个数
	 */
	private Integer ppi;

	/**
	 *否
	 * 运营商。具体值⻅附录
	 * 未知
	 * 0
	 * CARRIER_UNKNOWN
	 * 中国移动
	 * 1
	 * CARRIER_MOBILE
	 * 中国联通
	 * 2
	 * CARRIER_UNICOM
	 */
	private Integer carrier;

	/**
	 * 否
	 * 设备联⽹⽅式具体值⻅附录
	 * ConnectionType
	 */
	@JSONField(name = "connection_type")
	private Integer connectionType;

	/**
	 *否
	 * ⼩于 128 字节
	 * 设备品牌
	 */
	private String brand;

	/**
	 *否
	 * ⼩于128字节
	 * 设备型号
	 */
	private String model;

	/**
	 *否
	 * 系统启动标识 可能为空
	 */
	@JSONField(name = "boot_mark")
	private String bootMark;

	/**
	 *否
	 * 系统更新标识 可能为空
	 */
	@JSONField(name = "update_mark")
	private String updateMark;

	/**
	 *否
	 * 开机时间，仅ios，caid参数
	 */
	@JSONField(name = "startup_time")
	private String startupTime;

	/**
	 *否
	 * 设备初始化时间，仅ios
	 */
	@JSONField(name = "device_start_time")
	private String deviceStartTime;

	/**
	 *系统总内存空间，仅ios需要回
	 * 传，caid参数
	 * 否
	 */
	@JSONField(name = "mem_total")
	private Long memTotal;
	/**
	 磁盘总空间，仅ios需要回传，
	 caid参数
	 否
	 int64
	 */
	@JSONField(name = "disk_total")
	private Long diskTotal;

	/**
	 *系统版本更新时间，仅ios需要
	 * 回传，caid参数
	 * 否
	 */
	@JSONField(name = "mb_time")
	private String mbTime;
	/**
	 *否
	 * 设备model，如“D22AP”，仅ios
	 * 需要回传，caid参数
	 */
	private String devicetype;
	/**
	 *否
	 * local地区，如“CN”，仅ios需要
	 * 回传，caid参数
	 */
	@JSONField(name = "country_code")
	private String countryCode;
	/**
	 *设备设置的语⾔：中⽂、英⽂、
	 * 其他，仅ios需要回传，caid参数
	 * 否
	 */
	private String language;
	/**
	 *⼿机名称，仅ios需要回传，caid
	 * 参数
	 * 否
	 */
	@JSONField(name = "phone_name")
	private String phoneName;
	/**
	 *运营商名称，如“中国移动”，仅
	 * ios需要回传，caid参数
	 * 否
	 */
	@JSONField(name = "carrier_name")
	private String carrierName;
	/**
	 *否
	 * 机型编码，如"iPhone10,3"，仅
	 * ios需要回传，caid参数
	 */
	private String hwmachine;
	/**
	 *否
	 * ocal时区，如"Asia/Shanghai"，
	 * 仅ios需要回传，caid参数
	 */
	@JSONField(name = "local_tz_time")
	private String localTzTime;
	/**
	 *否
	 * ⼿机品牌的“应⽤市场”的版本号
	 */
	@JSONField(name = "ag_version")
	private String agVersion;
	/**
	 *否
	 * HMS Core 版本号
	 */
	@JSONField(name = "huawei_verCodeOfHms")
	private String huaweiVerCodeOfHms;
	/**
	 *否
	 * 设备⽣产商
	 */
	@JSONField(name = "rom_version")
	private String romVersion;
	/**
	 *否
	 * 设备名称
	 */
	@JSONField(name = "device_name")
	private String deviceName;
	/**
	 *物理内存 kb
	 */
	@JSONField(name = "physical_memory")
	private Long physicalMemory;
	/**
	 *存储内存 kb
	 */
	@JSONField(name = "storage_memory")
	private Long storageMemory;
	/**
	 *开机时间
	 */
	@JSONField(name = "elapse_time")
	private Long elapseTime;
	/**
	 *系统编译时间
	 */
	@JSONField(name = "sys_compiling_time")
	private String sysCompilingTime;
	/**
	 *拼多多paid
	 */
	private String paid;

}
