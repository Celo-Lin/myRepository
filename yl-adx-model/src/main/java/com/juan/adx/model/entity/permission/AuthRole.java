package com.juan.adx.model.entity.permission;

import lombok.Data;

@Data
public class AuthRole {

	/**
	 * 主键id
	 */
	private long id;

	/**
	 * 角色名称
	 */
	private String name;

	/**
	 * 描述
	 */
	private String description;

}
