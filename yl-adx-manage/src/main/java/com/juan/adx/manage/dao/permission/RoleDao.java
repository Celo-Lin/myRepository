package com.juan.adx.manage.dao.permission;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juan.adx.manage.mapper.permission.RoleMapper;
import com.juan.adx.model.entity.permission.AuthRole;
import com.juan.adx.model.form.manage.RoleForm;

@Repository
public class RoleDao {

	@Autowired
	private RoleMapper roleMapper;

	public List<AuthRole> getRoles( RoleForm form ) {
		return roleMapper.getRoles( form );

	}

	public int checkRoleByName( String name ) {
		return roleMapper.checkRoleByName( name );
	}

	public void addRole( AuthRole role ) {
		roleMapper.addRole( role );
	}

	public void deleteRole( Long id ) {
		roleMapper.deleteRole( id );
	}

	public void updateRole( AuthRole role ) {
		roleMapper.updateRole( role );
	}

	public AuthRole getRoleById( Long id ) {
		return roleMapper.getRoleById( id );
	}
}
