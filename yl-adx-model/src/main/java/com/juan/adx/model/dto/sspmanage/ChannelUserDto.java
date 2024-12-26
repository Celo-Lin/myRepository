package com.juan.adx.model.dto.sspmanage;

import lombok.Data;

@Data
public class ChannelUserDto {
	
	private Integer id;
	private String accountId;
	private String name;
	private Integer sspPartnerId;
	private String sspPartnerName;
	private Integer status;
	private Long ctime;
	private Long utime;
}
