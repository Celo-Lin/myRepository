package com.juan.adx.manage.dao.permission;

import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.juan.adx.manage.mapper.permission.ResourceMapper;
import com.juan.adx.model.entity.permission.AuthResource;

@Repository
public class ResourceDao {

    @Autowired
    private ResourceMapper resourceMapper;

    public List<AuthResource> getResources() {
        List<AuthResource> authResources = resourceMapper.selectByParentid(0L);
        if (CollectionUtils.isEmpty(authResources)) {
            return Lists.newArrayList();
        }

        for (AuthResource authResource : authResources) {
            getSubResource(authResource);
        }
        return authResources;
    }

    private void getSubResource(AuthResource authResource){
        // 查询一级菜单下属菜单
        List<AuthResource> resources = resourceMapper.selectByParentid(authResource.getId());
        if (CollectionUtils.isEmpty(resources)) {
            return;
        }
        for (AuthResource resource : resources) {
            getSubResource(resource);
        }
        authResource.setResources(resources);
    }

    public Long checkResByName(String name) {
        return resourceMapper.countByName(name);
    }

    public Long checkResByResource(String resource) {
        return resourceMapper.countByResource(resource);
    }

    public void addResource(AuthResource resource) {
        resourceMapper.insertSelective(resource);
    }

    public void deleteResource(Long id) {
        resourceMapper.deleteByPrimaryKey(id);
    }

    public void updateResource(AuthResource resource) {
        resourceMapper.updateByPrimaryKeySelective(resource);
    }

    public AuthResource getResourceById(Long id) {
        return resourceMapper.selectByPrimaryKey(id);
    }

    public List<AuthResource> selectByIds(Collection<Long> parentIds) {
        return resourceMapper.selectByIdIn(parentIds);
    }
}
