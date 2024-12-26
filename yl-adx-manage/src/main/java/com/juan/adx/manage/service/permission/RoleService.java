package com.juan.adx.manage.service.permission;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.juan.adx.manage.config.ManageParameterConfig;
import com.juan.adx.manage.dao.permission.ResourceDao;
import com.juan.adx.manage.dao.permission.RoleDao;
import com.juan.adx.manage.dao.permission.RoleResourceDao;
import com.juan.adx.manage.dao.permission.UserRoleDao;
import com.juan.adx.common.model.PageData;
import com.juan.adx.model.entity.permission.AuthResource;
import com.juan.adx.model.entity.permission.AuthRole;
import com.juan.adx.model.entity.permission.RoleUserSimple;
import com.juan.adx.model.form.manage.RoleForm;


@Service
public class RoleService {
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private UserRoleDao userRoleDao;
	
	@Autowired
	private RoleResourceDao roleResDao;
	
	@Autowired
	private ResourceDao resourceDao;

	
	public List<AuthRole> getRoles( RoleForm form ) {
        List<AuthRole> rules = roleDao.getRoles(form);
		return rules;
	}
	
    /**
     * 	查询绑定角色的所有用户信息
     * @param pageNo
     * @param pageSize
     * @param roleId
     * @return
     */
	public PageData getUserInfoByRoleId(RoleForm form) {
		PageHelper.startPage(form.getPageNo(), form.getPageSize());
        List<RoleUserSimple> dataList = userRoleDao.getUserInfoByRoleId(form.getRoleId());
        PageInfo<RoleUserSimple> pageInfo = new PageInfo<RoleUserSimple>(dataList);
		return new PageData().addPageInfo(pageInfo, dataList);
	}

    public boolean existRoleByName(String name) {
        return roleDao.checkRoleByName(name) > 0;
    }

    public void addRole(AuthRole role) {
        roleDao.addRole(role);
    }

    @Transactional(value = "permissionTransactionManager")
    public void deleteRole(Long id) {
    	//删除角色
        roleDao.deleteRole(id);
        // 删除用户角色关联
        userRoleDao.deleteByRid(id);
        // 删除角色资源关联
        roleResDao.deleteResByRid(id);
    }

    public void updateRole(AuthRole role) {
        roleDao.updateRole(role);
    }

    public AuthRole getRoleById(Long id) {
        return roleDao.getRoleById(id);
    }

    /**
     * 	根据角色ID查询关联的资源列表
     */
    public List<AuthResource> getResourceByRoleid(long roleId) {
        List<Long> resourceIds = roleResDao.getResourceIdsByRoleid(roleId);
        List<AuthResource> resources = resourceDao.getResources();
        //List<AuthResource> resources = grantService.getRoleResourceTree(uid);
        if (resources == null || resources.isEmpty()) {
            return null;
        }
        Set<Long> resourceIdset = new HashSet<Long>();
        for (Long resourceId : resourceIds) {
            resourceIdset.add(resourceId);
        }
        grantResource(resources, resourceIdset, roleId);
        return resources;
    }

    private void grantResource(List<AuthResource> resources, Set<Long> resourceIdset, long roleId) {
        for (AuthResource resource : resources) {
            resource.setGrant(roleId == ManageParameterConfig.systemAdminRole ? true : resourceIdset.contains(resource.getId()));
            List<AuthResource> subAuthResource = resource.getResources();
            if (CollectionUtils.isEmpty(subAuthResource)) {
                continue;
            }
            grantResource(subAuthResource, resourceIdset, roleId);
        }
    }

}
