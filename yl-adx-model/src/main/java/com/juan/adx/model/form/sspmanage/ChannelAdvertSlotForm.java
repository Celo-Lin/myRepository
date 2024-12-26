package com.juan.adx.model.form.sspmanage;

import lombok.Data;

@Data
public class ChannelAdvertSlotForm {

	/**
	 * 筛选条件：流量方ID
	 */
	private Integer sspPartnerId;
	
	/**
	 * 筛选条件：应用名称（最左原则，模糊匹配）
	 */
	private Integer appName;
	
	/**
	 * 筛选条件：应用ID
	 */
	private Integer appId;
	
	/**
	 * 筛选条件：广告位名称（最左原则，模糊匹配）
	 */
	private String slotName;
	
	
	private Integer pageNo = 1;

	private Integer pageSize = 20;
}
