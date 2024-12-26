package com.juan.adx.channel.config;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ChannelParameterConfig {
	
	/**
	 * 渠道后台 token签名算法密钥
	 */
	public static String channelTokenAlgSecretKey = "abc123def456ghi789jkl012mno345";
	
	/**
	 * 渠道后台 token过期时间，默认24小时
	 */
	public static Integer channelTokenExpiresDay = 1;
	
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}
