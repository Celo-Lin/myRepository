package com.juan.adx.model.form.manage;

import lombok.Data;

@Data
public class SspAppForm {

	/**
	 * 筛选条件：流量方ID
	 */
	private Integer sspPartnerId;
	
	/**
	 * 筛选条件：应用ID
	 */
	private Integer appId;
	
	/**
	 * 筛选条件：应用名称（最左原则，模糊匹配）
	 */
	private String appName;
	
	private Integer pageNo = 1;

	private Integer pageSize = 20;
}
