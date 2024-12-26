package com.juan.adx.manage.action.system;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juan.adx.manage.service.permission.ChannelUserService;
import com.juan.adx.common.model.ManageResponse;
import com.juan.adx.common.model.PageData;
import com.juan.adx.common.validator.ParamAssert;
import com.juan.adx.common.validator.Validation;
import com.juan.adx.model.dto.sspmanage.ChannelUserDto;
import com.juan.adx.model.entity.sspmanage.ChannelUser;
import com.juan.adx.model.form.sspmanage.ChannelUserForm;

import lombok.extern.slf4j.Slf4j;

/**
 * 渠道后台用户管理
 */
@Slf4j
@RestController
@RequestMapping( "/permission/channel_user" )
public class ChannelUserAction {
	
	@Resource
	private ChannelUserService channelUserService;
	

	/**
	 * 查询渠道后台用户列表
	 */
	@RequestMapping( "/list" )
	public ManageResponse list(ChannelUserForm form) {
		PageData data = this.channelUserService.listChannelUser(form);
		return new ManageResponse(data);
	}
	
	/**
	 * 新增渠道后台用户
	 */
	@RequestMapping( "/add" )
	public ManageResponse add(ChannelUser channelUser) {
		ParamAssert.isBlank(channelUser.getAccountId(), "用户登录账号不能为空");
		ParamAssert.isFalse(Validation.validate(channelUser.getAccountId(), Validation.ACCOUNT), "登录账号格式错误,长度：2-20位，只能包含：英文字母、数字、0~9特殊字符");
		ParamAssert.isBlank(channelUser.getName(), "用户姓名不能为空");
		ParamAssert.isTrue(channelUser.getSspPartnerId() == null || channelUser.getSspPartnerId().intValue() <= 0, "流量方ID不能为空");
		ParamAssert.isBlank(channelUser.getPassword(), "密码不能为空");
		ParamAssert.isFalse(Validation.validate(channelUser.getPassword(), Validation.PASSWD), "密码格式错误,长度：6-20位，必须由：英文字母+数字+特殊字符");
		boolean existAccountId = this.channelUserService.existChannelUserByAccountId(channelUser.getAccountId());
		ParamAssert.isTrue(existAccountId, "用户登录账号已存在");
		this.channelUserService.addChannelUser(channelUser);
		return new ManageResponse();
	}

	/**
	 * 渠道后台用户详情
	 */
	@RequestMapping( "/detail" )
	public ManageResponse detail(Integer userId) {
		ParamAssert.isTrue(userId == null || userId.intValue() <= 0, "用户ID不能为空");
		ChannelUserDto channelUser = this.channelUserService.getChannelUser(userId);
		return new ManageResponse(channelUser);
	}
	
	/**
	 * 更新渠道后台用户信息
	 */
	@RequestMapping( "/update" )
	public ManageResponse update(ChannelUser channelUser) {
		ParamAssert.isTrue(channelUser.getId() == null || channelUser.getId().intValue() <= 0, "用户ID不能为空");
		ParamAssert.isBlank(channelUser.getName(), "用户名称不能为空");
		this.channelUserService.updateChannelUser(channelUser);
		return new ManageResponse();
	}
	
	/**
	 * 修改登录密码
	 */
	@RequestMapping( "/update_passwd" )
	public ManageResponse updatePassword(ChannelUser channelUser) {
		ParamAssert.isTrue(channelUser.getId() == null || channelUser.getId().intValue() <= 0, "用户ID不能为空");
		ParamAssert.isBlank(channelUser.getPassword(), "密码不能为空");
		ParamAssert.isFalse(Validation.validate(channelUser.getPassword(), Validation.PASSWD), "密码格式错误,长度：6-20位，必须由：英文字母+数字+特殊字符");
		channelUser.setPassword(StringUtils.trimToEmpty(channelUser.getPassword()));
		this.channelUserService.updatePassword(channelUser);
		log.info( "update user password [{}] success", channelUser.getId() );
		return new ManageResponse();
	}
	
	/**
	 * 修改状态
	 */
	@RequestMapping( "/update_status" )
	public ManageResponse updateStatus(Integer userId, Integer status) {
		ParamAssert.isTrue(userId == null || userId <= 0, "用户ID不能为空");
		ParamAssert.isTrue(status == null || status <= 0, "用户状态不能为空");
		this.channelUserService.updateStatus(userId, status);
		log.info( "update user password [{}] status", userId );
		return new ManageResponse();
	}
}
