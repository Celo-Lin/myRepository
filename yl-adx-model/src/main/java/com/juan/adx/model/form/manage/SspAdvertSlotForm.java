package com.juan.adx.model.form.manage;

import lombok.Data;

@Data
public class SspAdvertSlotForm {

	/**
	 * 筛选条件：流量方ID
	 */
	private Integer sspPartnerId;
	
	/**
	 * 筛选条件：应用ID
	 */
	private Integer appId;
	
	/**
	 * 筛选条件 广告位ID
	 */
	private Integer slotId;
	
	/**
	 * 筛选条件：广告位名称（最左原则，模糊匹配）
	 */
	private String name;

	/**
	 * 筛选条件：广告类型 @AdvertType
	 */
	private Integer advertType;
	
	
	private Integer pageNo = 1;

	private Integer pageSize = 20;
}
