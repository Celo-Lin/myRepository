package com.juan.adx.model.entity.sspmanage;

import java.io.Serializable;

import lombok.Data;

@Data
public final class ChannelBrowserSession implements Serializable {

	private static final long serialVersionUID = -2645725386535856943L;


	/**
	 * 用户ID
	 */
	private String session_key_user_id;

	/**
	 * 用户名
	 */
	private String session_key_user_name;
	
	/**
	 * 流量方ID
	 */
	private String session_key_ssp_partner_id;

}
