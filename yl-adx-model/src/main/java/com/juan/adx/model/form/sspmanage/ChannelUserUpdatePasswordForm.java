package com.juan.adx.model.form.sspmanage;

import lombok.Data;

@Data
public class ChannelUserUpdatePasswordForm {
	
	private Integer sspPartnerId;
	
	private Integer userId;

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
