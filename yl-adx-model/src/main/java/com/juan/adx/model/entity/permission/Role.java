package com.juan.adx.model.entity.permission;

import lombok.Data;

@Data
public class Role {

	private long id;

	/**
	 * 权限角色名称
	 */
	private String name;

	/**
	 * 是否授权
	 */
	private Boolean isGrant;

}
