package com.juan.adx.model.form.manage;

import lombok.Data;

@Data
public class DspBudgetForm {

	/**
	 * 筛选条件：预算方ID
	 */
	private Integer dspPartnerId;
	
	/**
	 * 筛选条件：广告类型 @AdvertType
	 */
	private Integer advertType;
	
	/**
	 * 筛选条件：预算名称（最左原则，模糊匹配）
	 */
	private String name;
	
	/**
	 * 合作模式：@CooperationMode
	 */
	private Integer cooperationMode;
	
	private Integer pageNo = 1;

	private Integer pageSize = 20;
}
