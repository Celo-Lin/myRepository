package com.juan.adx.model.entity.sspmanage;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ChannelTokenPayload {

	private Integer userId;
	
	private String userName;
	
	private Integer sspPartnerId;
	
	private String tokenCode;
	
}
