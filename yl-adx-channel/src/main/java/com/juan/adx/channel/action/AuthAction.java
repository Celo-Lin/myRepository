package com.juan.adx.channel.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.juan.adx.channel.filter.SessionKey;
import com.juan.adx.channel.service.ChannelUserService;
import com.juan.adx.common.model.ManageResponse;
import com.juan.adx.model.dto.sspmanage.ChannelLoginDto;
import com.juan.adx.model.form.sspmanage.ChannelUserLoginForm;

@RestController
@RequestMapping( "/auth" )
public class AuthAction {
	
	@Autowired
	private ChannelUserService channelUserService;
	
	
	@RequestMapping( "/login" )
	public ManageResponse login( ChannelUserLoginForm form ) {
		ChannelLoginDto dto = this.channelUserService.userLogin(form);
		return new ManageResponse(dto);
	}
	
	@RequestMapping( "/loginout" )
	public ManageResponse loginout(@RequestParam(SessionKey.USER_ID) String userId) {
		this.channelUserService.userLoginout(userId);
		return new ManageResponse();
	}
	
	
}
