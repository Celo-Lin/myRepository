package com.juan.adx.model.entity.permission;

public class AuthRoleResource {

	private long id;

	private long roleId;

	private long resourceId;

	public long getId() {
		return id;
	}

	public void setId( long id ) {
		this.id = id;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId( long roleId ) {
		this.roleId = roleId;
	}

	public long getResourceId() {
		return resourceId;
	}

	public void setResourceId( long resourceId ) {
		this.resourceId = resourceId;
	}

}
