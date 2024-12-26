package com.juan.adx.model.form.sspmanage;

import lombok.Data;

@Data
public class ChannelAppForm {

	/**
	 * 筛选条件：流量方ID
	 */
	private Integer sspPartnerId;
	
	/**
	 * 筛选条件：应用名称（最左原则，模糊匹配）
	 */
	private String appName;
	
	private Integer pageNo = 1;

	private Integer pageSize = 20;
}
