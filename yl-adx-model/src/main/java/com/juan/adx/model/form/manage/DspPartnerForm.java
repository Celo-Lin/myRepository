package com.juan.adx.model.form.manage;

import lombok.Data;

@Data
public class DspPartnerForm {
	
	private String name;
	
	private Integer pageNo = 1;

	private Integer pageSize = 20;
}