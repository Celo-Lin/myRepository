package com.juan.adx.manage.action.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juan.adx.manage.service.permission.RoleService;
import com.juan.adx.manage.service.permission.UserRoleService;
import com.juan.adx.common.model.ManageResponse;
import com.juan.adx.common.validator.ParamAssert;
import com.juan.adx.model.entity.permission.AuthResource;
import com.juan.adx.model.entity.permission.AuthRole;
import com.juan.adx.model.form.manage.RoleGrantForm;

/**
 * 用户角色关系绑定
 *
 */
@RestController
@RequestMapping( "/permission/rolegrant" )
public class RoleGrantAction {

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	/**
	 *	获取角色关联的资源列表
	 */
	@RequestMapping( "/get_resource" )
	public ManageResponse getResourcesByRoleId(Long roleId) {
		ParamAssert.isTrue(roleId == null || roleId.intValue() <= 0, "角色ID不能为空！");
		Map<String, Object> map = new HashMap<String, Object>();
		AuthRole role = roleService.getRoleById( roleId );
		ParamAssert.isTrue(role == null, "角色不存在");
		List<AuthResource> resByRoleId = roleService.getResourceByRoleid(roleId);
		map.put( "roleName", role.getName() );
		map.put( "data", resByRoleId );
		return new ManageResponse( map );
	}
	
	
	/**
	 * 	保存角色和资源的关系
	 */
	@RequestMapping( "/add_role_resource" )
	public ManageResponse grantResource( RoleGrantForm grantVO ) {
		ParamAssert.isTrue(grantVO.getRoleId() == null || grantVO.getRoleId().longValue() <= 0, "角色ID不能为空！");
		ParamAssert.isTrue(grantVO.getIds() == null || grantVO.getIds().isEmpty(), "权限项ID不能为空！");
		userRoleService.saveRoleRes( grantVO.getRoleId(), grantVO.getIds() );
		return new ManageResponse();
	}

	/**
	 * 	删除角色和资源的关系
	 */
	@RequestMapping( "/delete_role_resource" )
	public ManageResponse deleteGrantResource( RoleGrantForm grantVO ) {
		ParamAssert.isTrue(grantVO.getRoleId() == null || grantVO.getRoleId().longValue() <= 0, "角色ID不能为空！");
		ParamAssert.isTrue(grantVO.getIds() == null || grantVO.getIds().isEmpty(), "权限项ID不能为空！");
		userRoleService.deleteRoleRes( grantVO.getRoleId(), grantVO.getIds() );
		return new ManageResponse(true);
	}


}
