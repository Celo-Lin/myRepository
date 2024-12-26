package com.juan.adx.model.entity.permission;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TokenPayload {

	private String userId;
	
	private String userName;
	
	private String roleIds;
	
	private String tokenCode;
	
}
