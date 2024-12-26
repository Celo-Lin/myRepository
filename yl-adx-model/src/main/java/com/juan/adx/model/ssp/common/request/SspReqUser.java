package com.juan.adx.model.ssp.common.request;

import java.util.List;

import lombok.Data;


/**
 * 用户信息
 */
@Data
public class SspReqUser {

	/**
	 * <pre>
	 * 用户性别
	 * 	0：其他
	 * 	1：男
	 * 	2：女
	 * </pre>
	 */
	private Integer gender;
	
	/**
	 * <pre>
	 * 用户的年龄： 
	 * 	0：0-18
	 * 	18：18-24
	 * 	24：24-31
	 * 	31：31-41
	 * 	41：41-50
	 * 	50：50+以上
	 * </pre>
	 */
	private Integer age;
	
	/**
	 * 用户兴趣标签,示例：["xxx", "xxxxx"]
	 */
	private List<String> interest;
	
	/**
	 * 用户已安装的应用列表,示例：["com.taobao.taobao","com.tencent.mobileqq"]
	 */
	private List<String> installed;
}
