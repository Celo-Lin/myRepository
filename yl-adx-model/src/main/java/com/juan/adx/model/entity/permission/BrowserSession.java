package com.juan.adx.model.entity.permission;

import java.io.Serializable;

import lombok.Data;

@Data
public final class BrowserSession implements Serializable {

	private static final long serialVersionUID = -2645725386535856943L;


	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 角色ID列表
	 */
	private String roleIds;

}
