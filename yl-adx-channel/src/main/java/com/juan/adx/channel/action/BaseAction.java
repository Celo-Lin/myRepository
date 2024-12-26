package com.juan.adx.channel.action;

import com.juan.adx.common.validator.ParamAssert;

public abstract class BaseAction {
	
	//private final static Logger logger = LoggerFactory.getLogger(BaseAction.class);
	
	
	public Integer getSspPartnerId(String sspPartnerIdStr){
		ParamAssert.isBlank(sspPartnerIdStr, "流量方不存在");
		Integer sspPartnerId = Integer.valueOf(sspPartnerIdStr);
		ParamAssert.isTrue(sspPartnerId == null || sspPartnerId.intValue() <= 0, "流量方无效");
		return sspPartnerId;
	}
	
	public Integer getUserId(String userIdStr){
		ParamAssert.isBlank(userIdStr, "用户不存在");
		Integer userId = Integer.valueOf(userIdStr);
		ParamAssert.isTrue(userId == null || userId.intValue() <= 0, "用户方无效");
		return userId;
	}
	
	
}
