package com.juan.adx.manage.config;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ManageParameterConfig {

	/**
	 * 内部后台 token签名算法密钥
	 */
	public static String tokenAlgSecretKey = "79sdfjasdw43g3gh45hjt2o8l8yklgx4";
	
	/**
	 * 内部后台 token过期时间，默认24小时
	 */
	public static Integer tokenExpiresDay = 1;
	
	
	/**
	 * 阿里云OSS图片访问域名
	 */
	public static String aliyunOssMaterialDomain = "";
	
	
	/**
	 * 管理员角色ID
	 */
	public static Long systemAdminRole = 1L;
	
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}
