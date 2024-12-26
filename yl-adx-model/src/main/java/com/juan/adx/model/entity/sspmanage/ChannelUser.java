package com.juan.adx.model.entity.sspmanage;

import lombok.Data;

@Data
public class ChannelUser {

	private Integer id;
	private String accountId;
	private String password;
	private String name;
	private Integer sspPartnerId;
	private Integer status;
	private Long ctime;
	private Long utime;

}
