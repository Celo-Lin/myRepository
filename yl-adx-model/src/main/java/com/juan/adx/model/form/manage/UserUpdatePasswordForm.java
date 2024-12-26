package com.juan.adx.model.form.manage;

import lombok.Data;

@Data
public class UserUpdatePasswordForm {
	
	private String userId;

	/**
	 * 新密码(明文值)
	 */
	private String newPassword;
	
	/**
	 * 重复新密码(明文值)
	 */
	private String confirmNewPassword;
	
	/**
	 * 原始密码(明文值)
	 */
	private String originalPassword;
}
