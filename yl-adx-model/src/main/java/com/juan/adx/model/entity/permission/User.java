package com.juan.adx.model.entity.permission;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Data;

@Data
public class User{

	private String id;

	private String name;
	
	private String password;
	
	private String position;
	
	private String description;
	
	private Long ctime;
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}
