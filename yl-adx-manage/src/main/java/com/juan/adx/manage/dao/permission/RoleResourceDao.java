package com.juan.adx.manage.dao.permission;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juan.adx.manage.mapper.permission.RoleResourceMapper;
import com.juan.adx.model.entity.permission.AuthResource;
import com.juan.adx.model.entity.permission.AuthRoleResource;

@Repository
public class RoleResourceDao {
	

	@Autowired
	private RoleResourceMapper resMapper;
	
	public List<Long> getResourceIdsByRoleid( long id ) {
		return resMapper.getResourceIdsByRoleid( id );
	}

	public void deleteResByRid( long id ) {
		resMapper.deleteResByRid( id );
	}

	public void deleteByResId( long id ) {
		resMapper.deleteByResId( id );
	}

	public void saveRoleRes( AuthRoleResource role ) {
		resMapper.saveRoleRes( role );
	}

	public void deleteByResIdAndRid(long resId, long rId) {
		resMapper.deleteByResIdAndRid(resId,rId);
	}

	public List<AuthResource> getResources(List<Long> roleIds ) {
		return resMapper.getResources( roleIds );
	}
	
}
