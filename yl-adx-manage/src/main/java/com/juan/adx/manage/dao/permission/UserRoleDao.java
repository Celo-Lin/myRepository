package com.juan.adx.manage.dao.permission;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juan.adx.manage.mapper.permission.UserRoleMapper;
import com.juan.adx.model.entity.permission.RoleUserSimple;

@Repository
public class UserRoleDao {

	@Autowired
	private UserRoleMapper userRoleMapper;

	public List<Long> getRolesByUid( String uid ) {
		return userRoleMapper.getRolesByUid( uid );
	}

	/**
	 * 通过角色id查询用户列表
	 *
	 * @param id
	 * @return
	 */
	public List<String> getUsersByRoleId(long id) {
		return userRoleMapper.getUsersByRoleId(id);
	}

	public void deleteByRid( long id ) {
		userRoleMapper.deleteByRid( id );
	}

	public void saveUserRole(Long roleIds, List<String> user ) {
		userRoleMapper.saveUserRole(roleIds, user );
	}

	/**
	 * 删除用户角色关系
	 *
	 * @param user
	 */
	public void deleteUserRole(Long roleId,List<String> user) {
		userRoleMapper.deleteUserRole( roleId, user );
	}

	/**
	 * 查询绑定角色的所有用户信息
	 *
	 * @param roleId
	 * @return
	 */
	public List<RoleUserSimple> getUserInfoByRoleId(long roleId) {
		return userRoleMapper.getUserInfoByRoleId(roleId);
	}

	public int deleteUserRoleByUid(String uid) {
		return this.userRoleMapper.deleteUserRoleByUid(uid);
	}
}
