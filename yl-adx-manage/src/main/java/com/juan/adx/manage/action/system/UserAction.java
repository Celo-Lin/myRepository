package com.juan.adx.manage.action.system;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.juan.adx.manage.service.permission.UserRoleService;
import com.juan.adx.manage.service.permission.UserService;
import com.juan.adx.common.model.ManageResponse;
import com.juan.adx.common.model.PageData;
import com.juan.adx.common.validator.ParamAssert;
import com.juan.adx.common.validator.Validation;
import com.juan.adx.model.entity.permission.Role;
import com.juan.adx.model.entity.permission.User;
import com.juan.adx.model.form.manage.UserForm;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping( "/permission/user" )
public class UserAction {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRoleService userRoleService;

	/**
	 * 用户列表
	 */
	@RequestMapping( "/list" )
	public ManageResponse list(UserForm form) {
		PageData data = userService.listUser(form);
		return new ManageResponse(data);
	}
	
	/**
	 * 用户详情
	 */
	@RequestMapping( "/detail" )
	public ManageResponse detail(String userId) {
		ParamAssert.isBlank(userId, "用户ID不能为空");
		ParamAssert.isFalse(Validation.validate(userId,Validation.EN_NICK_NAME), "用户ID无效");
		User user = userService.getUser(userId);
		return new ManageResponse(user);
	}
	
	/**
	 * 新增用户
	 */
	@RequestMapping( "/add" )
	public ManageResponse add(User user) {
		ParamAssert.isBlank(user.getId(), "用户登录ID不能为空");
		ParamAssert.isBlank(user.getName(), "姓名不能为空");
		ParamAssert.isBlank(user.getPassword(), "密码不能为空");
		ParamAssert.isFalse(Validation.validate(user.getId(),Validation.EN_NICK_NAME), "ID格式错误,正确格式：2-20位，英文字母、数字、下滑线或减号");
		ParamAssert.isFalse(Validation.validate(user.getPassword(),Validation.PASSWD), "密码格式错误,正确格式：6-20位，英文字母+数字+特殊字符");
		User oldUser = this.userService.getUser(user.getId());
		ParamAssert.isTrue(oldUser != null, "当前用户名已存在");
		user.setId(StringUtils.trimToEmpty(user.getId()));
		user.setName(StringUtils.trimToEmpty(user.getName()));
		user.setPassword(StringUtils.trimToEmpty(user.getPassword()));
		user.setPosition(StringUtils.trimToEmpty(user.getPosition()));
		user.setDescription(StringUtils.trimToEmpty(user.getDescription()));
		userService.saveUser(user);
		log.info( "add user [{}] success", user.getName() );
		return new ManageResponse();
	}
	
	/**
	 * 更新用户信息
	 */
	@RequestMapping( "/update" )
	public ManageResponse update(User user) {
		ParamAssert.isBlank(user.getId(), "用户ID不能为空");
		ParamAssert.isBlank(user.getName(), "姓名不能为空");
		ParamAssert.isFalse(Validation.validate(user.getId(),Validation.EN_NICK_NAME), "用户ID无效");
		user.setName(StringUtils.trimToEmpty(user.getName()));
		user.setPosition(StringUtils.trimToEmpty(user.getPosition()));
		user.setDescription(StringUtils.trimToEmpty(user.getDescription()));
		userService.updateUser(user);
		log.info( "update user [{}] success", user.getId() );
		return new ManageResponse();
	}
	
	/**
	 * 修改登录密码
	 */
	@RequestMapping( "/update_passwd" )
	public ManageResponse updatePassword(User user) {
		ParamAssert.isBlank(user.getId(), "用户ID不能为空");
		ParamAssert.isBlank(user.getPassword(), "密码不能为空");
		ParamAssert.isFalse(Validation.validate(user.getId(),Validation.EN_NICK_NAME), "用户ID无效");
		ParamAssert.isFalse(Validation.validate(user.getPassword(),Validation.PASSWD), "密码格式错误,正确格式：6-20位，英文字母、数字、指定字符");
		user.setPassword(StringUtils.trimToEmpty(user.getPassword()));
		userService.updatePassword(user);
		log.info( "update user password [{}] success", user.getId() );
		return new ManageResponse();
	}
	
	/**
	 * 删除用户
	 */
	@RequestMapping( "/delete" )
	public ManageResponse delete(String userId) {
		ParamAssert.isBlank(userId, "用户ID不能为空");
		ParamAssert.isFalse(Validation.validate(userId,Validation.EN_NICK_NAME), "用户ID无效");
		userService.deleteUser(userId);
		log.info( "delete user [{}] success", userId );
		return new ManageResponse();
	}
	
	/**
	 * 	根据用户ID获取所属角色列表
	 */
	@RequestMapping( "/get_roles_by_user" )
	public ManageResponse getRolesByUserId( @RequestParam( "userId" ) String userId ) {
		List<Role> vos = userRoleService.getRolesByUid( userId );
		return new ManageResponse( vos );
	}
	
}
