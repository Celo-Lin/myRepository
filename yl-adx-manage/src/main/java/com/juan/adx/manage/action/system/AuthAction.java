package com.juan.adx.manage.action.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.juan.adx.manage.filter.SessionKey;
import com.juan.adx.manage.service.permission.UserRoleService;
import com.juan.adx.manage.service.permission.UserService;
import com.juan.adx.common.model.ManageResponse;
import com.juan.adx.model.dto.manage.LoginDto;
import com.juan.adx.model.entity.permission.AuthResource;
import com.juan.adx.model.entity.permission.User;

@RestController
@RequestMapping( "/permission/auth" )
public class AuthAction {
	
	@Autowired
	private UserService authUserService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	
	@RequestMapping( "/login" )
	public ManageResponse login( User user ) {
		LoginDto dto = authUserService.userLogin(user);
		return new ManageResponse(dto);
	}
	
	@RequestMapping( "/loginout" )
	public ManageResponse loginout(@RequestParam(SessionKey.USER_ID) String userId) {
		authUserService.userLoginout(userId);
		return new ManageResponse();
	}
	
	/**
	 * 	查询当前登录用户的权限
	 */
	@RequestMapping( "/permissions" )
	public ManageResponse permissions(@RequestParam(SessionKey.USER_ID) String uid) {
		List<AuthResource> roleResourceTree = userRoleService.getRoleResourceTree(uid);
		return new ManageResponse( roleResourceTree );
	}
}
