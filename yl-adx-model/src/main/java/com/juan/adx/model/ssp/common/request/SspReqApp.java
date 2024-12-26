package com.juan.adx.model.ssp.common.request;

import lombok.Data;

/**
 * 应用信息
 */
@Data
public class SspReqApp {
	
	/**
	 * 此参数仅用于DSP对接我方时使用
	 * DSP的应用ID
	 */
	private String appId;
	
	/**
	 * <pre>
	 * 媒体APP名称
	 * 必填字段
	 * </pre>
	 */
	private String name;
	
	/**
	 * <pre>
	 * 媒体APP版本，句点分隔的数字
	 * 示例值：3.1.5
	 * 必填字段
	 * </pre>
	 */
	private String verName;
	
	/**
	 * <pre>
	 * 媒体APP包名
	 * 必填字段
	 * </pre>
	 */
	private String pkgName;
	
	/**
	 * 应用商店版本号,vivo、oppo广告时必填
	 * 必填字段
	 */
	private String appStoreVersion;
	
}
