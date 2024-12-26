package com.juan.adx.model.ssp.common.request;

import lombok.Data;

/**
 * 网络信息
 */
@Data
public class SspReqNetwork {

	/**
	 * 设备浏览器内核的User-Agent
	 */
	private String userAgent;
	
	/**
	 * <pre>
	 * 用户设备所处公网真实ipv4地址，禁止服务器ip和局域网ip，点分字符串形式，与 ipv6 一起必须存在一个有效值
	 * 示例： 14.28.7.140, 
	 * </pre>
	 */
	private String ip;
	
	/**
	 * 用户设备所处公网真实ipv6地址，禁止服务器ip和局域网ip，与 ipv4 一起必须存在一个有效值
	 */
	private String ipv6;
	
	/**
	 * 网卡mac地址
	 */
	private String mac;


	/**
	 * mac地址 MD5值（小写）
	 */
	private String macMd5;


	/**
	 * mac地址 SHA1值
	 */
	private String macSha1;
	
	/**
	 * <pre>
	 * wifi 热点 SSID名称
	 * </pre>
	 */
	private String ssid;
	
	/**
	 * <pre>
	 * wifi 热点 mac 地址
	 * </pre>
	 */
	private String bssid;
	
	/**
	 * <pre>
	 * 运营商
	 * 0：未知运营商;
	 * 1：中国移动;
	 * 2：中国电信;
	 * 3：中国联通;
	 * </pre>
	 */
	private Integer carrier;
	
	/**
	 * <pre>
	 * 客户端网络类型:
	 * 0：unkown
	 * 1：wifi
	 * 2：2G
	 * 3：3G
	 * 4：4G
	 * 5：5G
	 * 10：以太网
	 * </pre>
	 */
	private Integer networkType;
	
	/**
	 * 移动国家码
	 * 示例：460
	 */
	private String mcc;
	
	/**
	 * 移动网络码
	 * 示例：00
	 */
	private String mnc;
	
	/**
	 * 国家编码,使用 ISO-3166-1 Alpha-3
	 * 示例：CN
	 */
	private String country;	

}
