package com.juan.adx.manage.service.permission;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.juan.adx.manage.dao.permission.UserDao;
import com.juan.adx.manage.dao.permission.UserRoleDao;
import com.juan.adx.common.alg.MD5Util;
import com.juan.adx.common.alg.bcrypt.BCrypt;
import com.juan.adx.common.exception.ExceptionCode;
import com.juan.adx.common.exception.ServiceRuntimeException;
import com.juan.adx.common.model.PageData;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.common.validator.ParamAssert;
import com.juan.adx.model.constants.ManageCommonConstants;
import com.juan.adx.model.dto.manage.LoginDto;
import com.juan.adx.model.entity.permission.TokenPayload;
import com.juan.adx.model.entity.permission.User;
import com.juan.adx.model.form.manage.UserForm;
import com.juan.adx.model.form.manage.UserUpdatePasswordForm;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private JwtTokenService tokenService;
	
	@Autowired
	private UserRoleDao userRoleDao;
	

	public LoginDto userLogin(User user) {
		User actualUser = this.userDao.getUserWithPassword(user.getId());
		ParamAssert.isTrue(actualUser == null, "用户不存在");
		boolean matched = BCrypt.checkpw(user.getPassword(), actualUser.getPassword());
		if(!matched) {
			throw new ServiceRuntimeException(ExceptionCode.PermissionCode.ACCOUNT_PASSWORE_ERROR, "账号或密码错误");
		}
		List<Long> roleIds = userRoleDao.getRolesByUid(actualUser.getId());
		TokenPayload payload = new TokenPayload();
		payload.setUserId(actualUser.getId());
		payload.setUserName(actualUser.getName());
		payload.setRoleIds(StringUtils.join(roleIds, ","));
		String token = tokenService.getAccessToken(payload);
		LoginDto dto = new LoginDto();
		dto.setUserId(actualUser.getId());
		dto.setUserName(actualUser.getName());
		dto.setToken(token);
		return dto;
	}

	public void userLoginout(String cuid) {
		this.tokenService.deleteTokenCode(cuid);
	}

	public PageData listUser(UserForm paramForm) {
		PageHelper.startPage(paramForm.getPageNo(), paramForm.getPageSize());
        List<User> dataList = userDao.listUser(paramForm);
        PageInfo<User> pageInfo = new PageInfo<User>(dataList);
		return new PageData().addPageInfo(pageInfo, dataList);
	}

	public User getUser(String id) {
		return this.userDao.getUser(id);
	}
	
	public User getUserByName(String name) {
		return this.userDao.getUserByName(name);
	}

	public void updateUser(User user) {
		this.userDao.updateUser(user);
	}

	public void saveUser(User user) {
		String password = user.getPassword();
		String salt = BCrypt.gensalt(ManageCommonConstants.Password.rounds);
		password = BCrypt.hashpw(MD5Util.getMD5String(password), salt);
		user.setPassword(password);
		user.setCtime(LocalDateUtils.getNowSeconds());
		this.userDao.saveUser(user);
	}
	

	public void updatePassword(User user) {
		String password = user.getPassword();
		String salt = BCrypt.gensalt(ManageCommonConstants.Password.rounds);
		password = BCrypt.hashpw(MD5Util.getMD5String(password), salt);
		user.setPassword(password);
		this.userDao.updatePassword(user);
		this.tokenService.deleteTokenCode(user.getId());
	}
	

	public boolean updateUserPasswordByIndex(UserUpdatePasswordForm form) {
		User actualUser = this.userDao.getUserWithPassword(form.getUserId());
		ParamAssert.isTrue(actualUser == null, "用户不存在");
		boolean matched = BCrypt.checkpw(MD5Util.getMD5String(form.getOriginalPassword()), actualUser.getPassword());
//		if(!matched) {
//			throw new ServiceRuntimeException(ExceptionCode.PermissionCode.ACCOUNT_PASSWORE_ERROR, "原始密码错误");
//		}
		String newPasswordMd5 = MD5Util.getMD5String(form.getNewPassword());
		String salt = BCrypt.gensalt(ManageCommonConstants.Password.rounds);
		String newPasswordCrypt = BCrypt.hashpw(newPasswordMd5, salt);
		User user = new User();
		user.setId(form.getUserId());
		user.setPassword(newPasswordCrypt);
		int ret = this.userDao.updatePassword(user);
		this.tokenService.deleteTokenCode(user.getId());
		return ret > 0;
	}

	public void deleteUser(String uid) {
		this.userRoleDao.deleteUserRoleByUid(uid);
		this.userDao.deleteUser(uid);
	}



}
