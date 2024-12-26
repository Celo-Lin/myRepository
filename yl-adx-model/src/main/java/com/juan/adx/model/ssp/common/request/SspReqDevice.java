package com.juan.adx.model.ssp.common.request;

import lombok.Data;

/**
 * 设备信息
 */
@Data
public class SspReqDevice {
	
	/**
	 * <pre>
	 * 系统类型：
	 * 	0：Unknown
	 * 	1：IOS
	 * 	2：Android
	 * 	3：Android pad
	 * 	4：iPad 
	 * 	5：Windows
	 * 	6：MacBook
	 * </pre>
	 */
	private Integer osType;
	
	/**
	 * <pre>
	 * 设备类型：
	 * 0：其它
	 * 1：手机
	 * 2：平板
	 * 3：OTT 终端
	 * 4：PC
	 * 注：OTT 终端包括互联网电视和电视机顶盒
	 * </pre>
	 */
	private Integer type;
	
	/**
	 * 系统版本，示例：6.0.2
	 */
	private String osVersion;
	
	/**
	 * 系统UI版本号，小米MIUI版本，或者华为UI版本号等
	 */
	private String osUiVersion;
	
	/**
	 * <pre>
	 * Android 操作系统 API Level。
	 * av >= 29时，视为安卓系统为 Android Q 及以上
	 * 示例：23
	 * </pre>
	 */
	private Integer androidApiLevel;
	
	/**
	 * 设备系统语言
	 */
	private String language;
	
	/**
	 * 系统当前时区，例如：Asia/Shanghai (ios必填)
	 */
	private String timeZone;
	
	/**
	 * 系统编译时间戳，单位：毫秒 (ios必填)
	 * 必填字段
	 */
	private String sysCompilingTime;
	
	/**
	 * 系统更新时间戳，单位：毫秒  (ios必填)
	 */
	private String sysUpdateTime;
	
	/**
	 * 系统启动时间戳，单位：毫秒  (ios必填)
	 */
	private String sysStartupTime;
	
	
	/**
	 * IOS设备初始化标识，
	 * 示例：1647545718.125795458
	 */
	private String birthMark;
	
	/**
	 * <pre>
	 * 系统启动标识
	 * 示例：
	 * 	iOS：1623815045.970028
	 * 	Android： ec7f4f33-411a-47bc-8067-744a4e7e0723
	 * </pre>
	 */
	private String bootMark;
	
	/**
	 * <pre>
	 * 系统更新标识
	 * 示例：
	 * 	iOS:1581141691.570419583
	 * 	Android:1004697.709999999
	 * </pre>
	 */
	private String updateMark;

	
	/**
	 * 设备ROM版本
	 */
	private String romVersion;

	
	/**
	 * 设备名称
	 */
	private String deviceName;
	
	/**
	 * 设备名称md5值
	 */
	private String deviceNameMd5;
	
	/**
	 * <pre>
	 * 设备内存大小 (IOS必填)，单位：字节 
	 * 示例： 6512302
	 * 必填
	 * </pre>
	 */
	private Long sysMemorySize;
	
	/**
	 * <pre>
	 * 硬盘大小 (IOS必填)，单位：字节
	 * 示例：63900340224
	 * </pre>
	 */
	private Long sysDiskSize;
	
	/**
	 * <pre>
	 * 设备CPU核数 (IOS必填)
	 * 示例：4
	 * </pre>
	 */
	private Integer cpuNum;
	
	/**
	 * <pre>
	 * 设备型号
	 * 示例
	 * 	iOS："iPhone12,3"
     * 	安卓："PCNM00"
     * 必填字段
     * </pre>
	 */
	private String model;
	
	/**
	 * <pre>
	 * IOS硬件型号 (IOS必填)
	 * 示例：D22AP
	 * </pre>
	 */
	private String hardwareModel;
	
	
	/**
	 * <pre>
	 * 设备 machine 值
	 * 示例：iPhone13,2
	 * </pre>
	 */
	//private String hardwareMachine;

	
	/**
	 * 华为HMS Core版本 (华为手机必填)
	 * 示例：60000306
	 */
	private String hmsVersion;
	
    /**
     * <pre>
     * 鸿蒙系统内核版本
     * </pre>
     */
    private String harmonyOsVersion;
	
	/**
	 * 华为 AppGallery 应用市场版本 (华为手机必填)
	 */
	private String hagVersion;
	
	/**
	 * <pre>
	 * 媒体是否支持 deeplink：
	 * 	0：不支持
	 * 	1：支持
	 * </pre>
	 */
	private Integer supportDeeplink; 
	
	/**
	 * <pre>
	 * 媒体是否支持 universal link：
	 * 	0：不支持
	 * 	1：支持
	 * </pre>
	 */
	private Integer supportUniversal;
	
	
	/**
	 * <pre>
	 * 设备厂商,中文需要 UTF-8 编码
	 * 示例：XiaoMi
	 * </pre>
	 */
	private String make;
	
	/**
	 * 设备品牌
	 */
	private String brand;

	/**
	 * 国际移动用户识别码
	 * 示例值：46001
	 */
	private String imsi;

	
	/**
	 * 设备屏幕宽度
	 */
	private Integer width;
	
	/**
	 * 设备屏幕高度
	 */
	private Integer height;
	
	/**
	 * <pre>
	 * 设备屏幕物理像素密度，获取方法：
	 * 安卓：context.getResources().getDisplayMetrics().density
	 * iOS： UIScreen.scale
	 * </pre>
	 */
	private Double density;
	
	/**
	 * 设备屏幕像素点密度，表示每英寸的点数
	 * 示例：240
	 */
	private Integer dpi;
	
	/**
	 * 设备屏幕像素密度，表示每英寸的像素数
	 * 示例：401
	 */
	private Integer ppi;
	
	/**
	 * <pre>
	 * 屏幕方向：
	 * 0：unknown
	 * 1：竖屏
	 * 2：横屏
	 * </pre>
	 */
	private Integer orientation;

	/**
	 * <pre>
	 * 屏幕尺寸，单位：英寸
	 * 示例值：4.7 , 5.5
	 * </pre>
	 */
	private String screenSize;

	/**
	 * <pre>
	 * 设备序列号
	 * </pre>
	 */
	private String serialno;

	

	
}
