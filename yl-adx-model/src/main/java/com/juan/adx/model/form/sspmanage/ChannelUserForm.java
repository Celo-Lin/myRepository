package com.juan.adx.model.form.sspmanage;

import lombok.Data;

@Data
public class ChannelUserForm {
	
	private String accountId;
	
	private String name;
	
	private Integer pageNo = 1;
	
	private Integer pageSize = 20;
}
