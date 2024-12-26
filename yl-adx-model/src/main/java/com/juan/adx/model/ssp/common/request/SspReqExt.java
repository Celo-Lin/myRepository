package com.juan.adx.model.ssp.common.request;

import lombok.Data;

/**
 * 扩展参数
 */
@Data
public class SspReqExt {
	
	
    /**
     * <pre>
     * 是否支持微信小程序广告下发
     * 必填字段
     * </pre>
     */
    private Boolean supportWechat;

	/**
	 * 拼多多广告标识符(拼多多预算必填)
	 */
	private String paid;
	



}
