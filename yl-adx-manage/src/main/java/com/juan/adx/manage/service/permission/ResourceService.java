package com.juan.adx.manage.service.permission;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juan.adx.manage.dao.permission.ResourceDao;
import com.juan.adx.manage.dao.permission.RoleResourceDao;
import com.juan.adx.model.entity.permission.AuthResource;

@Service
public class ResourceService {

	@Autowired
	private ResourceDao resourceDao;

	@Autowired
	private RoleResourceDao roleResDao;

	public List<AuthResource> getResources() {
        List<AuthResource> authResources = resourceDao.getResources();
		return authResources;
	}

	public Long checkResByName( String name ) {
		return resourceDao.checkResByName( name );
	}

	public Long checkResByResource( String resource ) {
		return resourceDao.checkResByResource( resource );
	}

	public void addResource( AuthResource resource ) {
		resourceDao.addResource( resource );
	}

	public void deleteResource( Long id ) {
		resourceDao.deleteResource( id );
		// 删除角色资源管理
		roleResDao.deleteByResId( id );
	}

	public void updateResource( AuthResource resource ) {
		resourceDao.updateResource( resource );
	}

	public AuthResource getResourceById(Long id ) {
		return resourceDao.getResourceById( id );
	}
}
