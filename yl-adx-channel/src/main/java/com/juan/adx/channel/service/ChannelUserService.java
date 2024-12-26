package com.juan.adx.channel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juan.adx.channel.dao.ChannelUserDao;
import com.juan.adx.common.alg.MD5Util;
import com.juan.adx.common.alg.bcrypt.BCrypt;
import com.juan.adx.common.exception.ExceptionCode;
import com.juan.adx.common.exception.ServiceRuntimeException;
import com.juan.adx.common.validator.ParamAssert;
import com.juan.adx.model.constants.ManageCommonConstants;
import com.juan.adx.model.dto.sspmanage.ChannelLoginDto;
import com.juan.adx.model.entity.sspmanage.ChannelTokenPayload;
import com.juan.adx.model.entity.sspmanage.ChannelUser;
import com.juan.adx.model.enums.Status;
import com.juan.adx.model.form.sspmanage.ChannelUserLoginForm;
import com.juan.adx.model.form.sspmanage.ChannelUserUpdatePasswordForm;

@Service
public class ChannelUserService {
	
	@Autowired
	private ChannelUserDao channelUserDao;

	@Autowired
	private ChannelJwtTokenService tokenService;
	

	public ChannelLoginDto userLogin(ChannelUserLoginForm form) {
		ChannelUser actualUser = this.channelUserDao.getUserWithPasswordByAccountId(form.getAccountId());
		ParamAssert.isTrue(actualUser == null, "账号不存在");
		Status status = Status.get(actualUser.getStatus());
		ParamAssert.isTrue(status == Status.INVALID, "账号异常，请联系商务！");
		boolean matched = BCrypt.checkpw(form.getPassword(), actualUser.getPassword());
		if(!matched) {
			throw new ServiceRuntimeException(ExceptionCode.PermissionCode.ACCOUNT_PASSWORE_ERROR, "账号或密码错误");
		}
		ChannelTokenPayload payload = new ChannelTokenPayload();
		payload.setUserId(actualUser.getId());
		payload.setUserName(actualUser.getName());
		payload.setSspPartnerId(actualUser.getSspPartnerId());
		String token = this.tokenService.getAccessToken(payload);
		ChannelLoginDto dto = new ChannelLoginDto();
		dto.setUserId(actualUser.getId());
		dto.setUserName(actualUser.getName());
		dto.setToken(token);
		return dto;
	}

	public void userLoginout(String userId) {
		this.tokenService.deleteTokenCode(userId);
	}

	public boolean updateUserPasswordByIndex(ChannelUserUpdatePasswordForm form) {
		ChannelUser actualChannelUser = this.channelUserDao.queryChannelUserWithPassword(form.getSspPartnerId(), form.getUserId());
		ParamAssert.isTrue(actualChannelUser == null, "用户不存在");
		boolean matched = BCrypt.checkpw(MD5Util.getMD5String(form.getOriginalPassword()), actualChannelUser.getPassword());
		if(!matched) {
			throw new ServiceRuntimeException(ExceptionCode.PermissionCode.ACCOUNT_PASSWORE_ERROR, "原始密码错误");
		}
		String newPasswordMd5 = MD5Util.getMD5String(form.getNewPassword());
		String salt = BCrypt.gensalt(ManageCommonConstants.Password.rounds);
		String newPasswordCrypt = BCrypt.hashpw(newPasswordMd5, salt);
		ChannelUser channelUser = new ChannelUser();
		channelUser.setId(form.getUserId());
		channelUser.setPassword(newPasswordCrypt);
		channelUser.setSspPartnerId(form.getSspPartnerId());
		int ret = this.channelUserDao.updatePassword(channelUser);
		this.userLoginout(String.valueOf(form.getUserId()));
		return ret > 0;
	}


	


}
