package com.juan.adx.manage.action.system;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.juan.adx.manage.service.permission.RoleService;
import com.juan.adx.manage.service.permission.UserRoleService;
import com.juan.adx.common.exception.ExceptionCode;
import com.juan.adx.common.exception.ServiceRuntimeException;
import com.juan.adx.common.model.ManageResponse;
import com.juan.adx.common.model.PageData;
import com.juan.adx.common.validator.ParamAssert;
import com.juan.adx.model.entity.permission.AuthRole;
import com.juan.adx.model.form.manage.AuthGrantForm;
import com.juan.adx.model.form.manage.RoleForm;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Validated
@RestController
@RequestMapping( "/permission/role" )
public class RoleAction {

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	/**
	 * 查询角色列表
	 */
	@RequestMapping( "/query" )
	public ManageResponse query( RoleForm form ) {
		List<AuthRole> pageInfo = roleService.getRoles( form );
		return new ManageResponse( pageInfo );
	}

	/**
	 * 根据角色ID查询用户列表
	 */
	@RequestMapping( "/getUserByRoleId" )
	public ManageResponse getUserByRoleId(RoleForm form) {
		ParamAssert.isTrue(form.getRoleId() == null || form.getRoleId().intValue() <= 0, "角色ID不能为空");
		PageData pageData = roleService.getUserInfoByRoleId(form);
		return new ManageResponse( pageData );
	}

	/**
	 * 新增角色
	 */
	@RequestMapping( "/add" )
	public ManageResponse add(String name) {
		ParamAssert.isTrue(StringUtils.isEmpty( name ), "角色名称不能为空！");
		boolean existRole = roleService.existRoleByName(name);
		if (existRole) {
			throw new ServiceRuntimeException(ExceptionCode.CommonCode.INVALID_PARAM,"角色名重复!");
		}
		AuthRole role = new AuthRole();
		role.setDescription(StringUtils.EMPTY);
		role.setName(StringUtils.trimToEmpty(name));
		roleService.addRole( role );
		log.info( "save [{}]success", role.getName() );
		return new ManageResponse();
	}

	/**
	 * 角色详情
	 */
	@RequestMapping( "/get" )
	public ManageResponse info( Long id ) {
		ParamAssert.isTrue(id == null || id.intValue() <= 0, "角色ID不能为空！");
		return new ManageResponse( roleService.getRoleById( id ) );
	}

	/**
	 * 修改角色
	 */
	@RequestMapping( "/update" )
	public ManageResponse update(String name, Long id) {
		ParamAssert.isTrue(id == null || id.intValue() <= 0, "角色ID不能为空！");
		ParamAssert.isTrue(StringUtils.isEmpty( name ), "角色名称不能为空！");
		AuthRole role = new AuthRole();
		role.setId(id);
		role.setName(StringUtils.trimToEmpty(name));
		roleService.updateRole( role );
		log.info( "update role,[{}]success",role.getName() );
		return new ManageResponse();
	}

	/**
	 * 删除角色
	 */
	@RequestMapping( "/delete" )
	public ManageResponse delete(Long id ) {
		ParamAssert.isTrue(id == null || id.intValue() <= 0, "角色ID不能为空！");
		roleService.deleteRole( id );
		log.info( "delete role[{}] success", id );
		return new ManageResponse();
	}
	
	
	/**
	 * 	通过角色id查询用户ID列表
	 */
	@RequestMapping( "/userids_by_roleid" )
	@Deprecated
	public ManageResponse getUsersByRoleId( @RequestParam( "roleId" ) long roleId ) {
		List<String> vos = userRoleService.getUsersByRoleId( roleId );
		return new ManageResponse( vos );
	}
	
	
	/**
	 * 新增用户角色关系
	 */
	@RequestMapping(value = "/add_relation_role_user")
	public ManageResponse addAuthGrant(AuthGrantForm authGrantForm) {
		userRoleService.saveUserRole(authGrantForm.getRoleId(), authGrantForm.getUserIds());
		return new ManageResponse(true);
	}

	/**
	 * 删除用户角色关系
	 */
	@RequestMapping(value = "/delete_relation_role_user")
	public ManageResponse deleteAuthGrant(AuthGrantForm authGrantForm) {
		userRoleService.deleteUserRole(authGrantForm.getRoleId(), authGrantForm.getUserIds());
		return new ManageResponse(true);
	}
	

}
