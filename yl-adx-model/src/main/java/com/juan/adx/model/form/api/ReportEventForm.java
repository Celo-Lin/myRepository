package com.juan.adx.model.form.api;

import lombok.Data;

@Data
public class ReportEventForm {

	/**
	 * 广告位ID
	 */
	private Integer slotId;
	
	/**
	 * base64加密串
	 */
	private String info;
	
	/**
	 * 发起请求时间戳
	 */
	private String ts;
	
}
