package com.juan.adx.manage.service.permission;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.juan.adx.manage.config.ManageParameterConfig;
import com.juan.adx.manage.dao.permission.ResourceDao;
import com.juan.adx.manage.dao.permission.RoleDao;
import com.juan.adx.manage.dao.permission.RoleResourceDao;
import com.juan.adx.manage.dao.permission.UserRoleDao;
import com.juan.adx.model.constants.ManageCommonConstants;
import com.juan.adx.model.entity.permission.AuthResource;
import com.juan.adx.model.entity.permission.AuthRole;
import com.juan.adx.model.entity.permission.AuthRoleResource;
import com.juan.adx.model.entity.permission.Role;
import com.juan.adx.model.form.manage.RoleForm;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RoleResourceDao roleResDao;

    @Autowired
    private ResourceDao authResourceDao;

    
    /**
     * 	通过角色id查询用户列表	
     */
    public List<String> getUsersByRoleId(long roleId) {
        return userRoleDao.getUsersByRoleId(roleId);
    }

    /**
     * 	获取所有的角色信息
     */
    public List<Role> getRolesByUid(String uid) {
        List<Role> vos = new ArrayList<Role>();

        RoleForm form = new RoleForm();
        List<Long> rids = userRoleDao.getRolesByUid(uid);
        List<AuthRole> roles = roleDao.getRoles(form);// 获取所有角色
        if (roles == null || roles.isEmpty()) {
            return null;
        }
        Set<Long> set = new HashSet<Long>();
        for (Long rid : rids) {
            set.add(rid);
        }
        // 返回所有角色信息，true为该用户的角色
        for (AuthRole role : roles) {
            Role vo = new Role();
            vo.setId(role.getId());
            vo.setName(role.getName());
            vo.setIsGrant(set.contains(role.getId()));
            vos.add(vo);
        }
        return vos;
    }

    /**
     * 	保存角色和用户关系
     */
    public void saveUserRole(Long roleIds, List<String> userId) {
        if (CollectionUtils.isEmpty(userId)) {
            return;
        }
        // 保存该用户的角色信息
        userRoleDao.saveUserRole(roleIds,userId);
    }

    /**
     * 	删除角色和用户关系
     */
    public void deleteUserRole(Long roleId, List<String> userId) {
        if (CollectionUtils.isEmpty(userId)) {
            return;
        }
        // 删除该用户的角色信息
        userRoleDao.deleteUserRole(roleId,userId);
    }

    /**
     * 	保存角色和资源关系
     */
    public void saveRoleRes(Long rid, List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        for (Long resId : ids) {
            AuthRoleResource roleRes = new AuthRoleResource();
            roleRes.setResourceId(resId);
            roleRes.setRoleId(rid);
            roleResDao.saveRoleRes(roleRes);
        }
    }

    /**
     * 	删除角色和资源关系
     */
    public void deleteRoleRes(Long rid, List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        for (Long resId : ids) {
            roleResDao.deleteByResIdAndRid(resId,rid);
        }
    }
    
    /**
     * 检查用户是否为管理员
     */
    public boolean checkSystemAdmin(String uid) {
    	if(StringUtils.isBlank(uid)) {
    		return false;
    	}
    	List<Long> roleIds = userRoleDao.getRolesByUid(uid);
        if (CollectionUtils.isEmpty(roleIds)) {
            return false;
        }
        // 平台管理员
        if (roleIds.contains(ManageParameterConfig.systemAdminRole)) {
            return true;
        }
        return false;
    }

    /**
     * 	用户权限判断
     */
    public boolean auth(String uid, String uri) {
    	if (ManageCommonConstants.WITHOUT_PERMISSION.contains(uri)) {
            return true;
        }
        List<Long> roleIds = userRoleDao.getRolesByUid(uid);
        if (CollectionUtils.isEmpty(roleIds)) {
            return false;
        }
        // 平台管理员拥有所有权限
        if (roleIds.contains(ManageParameterConfig.systemAdminRole)) {
            return true;
        }
        List<AuthResource> ress = getRoleResources(roleIds);
        if (ress.isEmpty()) {
            return false;
        }
        Set<String> urls = new HashSet<String>();
        for (AuthResource res : ress) {
        	List<String> subUrls = Arrays.asList(res.getUrl().trim().split(","));
            urls.addAll(subUrls);
        }
        return urls.contains(uri);
    }
    
    
    /*
    public boolean auth2(String sessionStr, String uri) {
        if (Constants.WITHOUT_PERMISSION.contains(uri) || StringUtils.startsWith(uri,"/cop/receive/")) {
            return true;
        }
        if (StringUtils.isEmpty(sessionStr)) {
            return true;
        }
        BrowserSession bs = JSONObject.parseObject(sessionStr, BrowserSession.class);
        log.warn( "BIP账号[{}]", bs.getUserBip());
        List<Long> roleIds = userRoleDao.getRolesByUid(bs.getEmployeeNumber());
        if (CollectionUtils.isEmpty(roleIds)) {
            return false;
        }
        // 平台管理员拥有所有权限
        if (roleIds.contains(1L)) {
            return true;
        }
        List<AuthResource> ress = getRoleResources(roleIds);
        if (ress.isEmpty()) {
            return false;
        }

        Set<String> urls = new HashSet<>();
        for (AuthResource res : ress) {
            List<String> successOpenIdList = Arrays.asList(res.getUrl().trim().split(","));
            urls.addAll(successOpenIdList);
        }
        return urls.contains(uri);
    }*/
    

    /**
     * 获取用户所属角色关联的资源
     */
    public List<AuthResource> getRoleResources(List<Long> roleIds) {
        //List<Long> roleIds = userRoleDao.getRolesByUid(uid);
        if (CollectionUtils.isEmpty(roleIds)) {
            return Lists.newArrayList();
        }
        List<AuthResource> ress = roleResDao.getResources(roleIds);
        return ress;
    }

//    public List<AuthResource> getRoleResourceTree(String uid) {
//        List<Long> roleIds = userRoleDao.getRolesByUid(uid);
//        if (CollectionUtils.isEmpty(roleIds)) {
//            return Lists.newArrayList();
//        }
//        List<AuthResource> ress = roleResDao.getResources(roleIds);
//        List<AuthResource> resources = roleResource(ress);
//        return resources;
//
//    }
//
//    private List<AuthResource> roleResource(List<AuthResource> ress) {
//        Map<Long, List<AuthResource>> parentMap = Maps.newHashMap();
//        for (AuthResource authResource : ress) {
//            Long parentid = authResource.getParentid();
//            if (parentid == null || parentid.longValue() == 0L) {
//                continue;
//            }
//            List<AuthResource> resources = parentMap.get(parentid);
//            if (CollectionUtils.isEmpty(resources)) {
//                resources = Lists.newArrayList();
//                parentMap.put(authResource.getParentid(), resources);
//            }
//            resources.add(authResource);
//        }
//        if (parentMap.isEmpty()) {
//            return ress;
//        }
//        List<AuthResource> resources = authResourceDao.selectByIds(parentMap.keySet());
//        for (AuthResource resource : resources) {
//            resource.setResources(parentMap.get(resource.getId()));
//        }
//        return roleResource(resources);
//    }

    /**
     * 	获取所有父节点
     * @param authResourceList
     * @param etc
     * @return
     */
    public List<AuthResource> getParent(List<AuthResource> authResourceList,Set<AuthResource> etc) {
        Set<AuthResource> resourceSet = etc;

        List<Long> categoryTypeList = authResourceList.stream().map(AuthResource::getParentid).collect(Collectors.toList());
        List<AuthResource> parentResource = authResourceDao.selectByIds(categoryTypeList);
        parentResource.sort(Comparator.comparing(AuthResource::getOrder));
        if (CollectionUtils.isNotEmpty(resourceSet)) {
            resourceSet.addAll(parentResource);
        } else {
            resourceSet = Sets.newConcurrentHashSet();
            resourceSet.addAll(parentResource);
            authResourceList.sort(Comparator.comparing(AuthResource::getOrder));
            resourceSet.addAll(authResourceList);
        }

        if (!categoryTypeList.contains(0L)) {
            getParent(parentResource,resourceSet);
        }
        Set<Long> resourceIdsSet = new HashSet<Long>();
        Set<AuthResource> resultSet = new HashSet<AuthResource>();
        for (AuthResource authResource : resourceSet) {
        	if(resourceIdsSet.add(authResource.getId())) {
        		resultSet.add(authResource);
        	}
		}
        return new ArrayList<>(resultSet);
    }

    public List<AuthResource> getRoleResourceTree(String uid) {
        List<Long> roleIds = userRoleDao.getRolesByUid(uid);
        if (CollectionUtils.isEmpty(roleIds)) {
            return Lists.newArrayList();
        }
        // 平台管理员拥有所有权限
        if( roleIds.contains(ManageParameterConfig.systemAdminRole) ) {
            roleIds = null;
        }
        List<AuthResource> resourceList = roleResDao.getResources(roleIds);
        if (CollectionUtils.isEmpty(resourceList)) {
            // 角色无权限
            return Lists.newArrayList();
        }
        if (CollectionUtils.isNotEmpty(roleIds)) {
            resourceList = getParent(resourceList, null);
        }

        //获取顶层元素集合
        List<AuthResource> resultList = new ArrayList<>();
        Long parentid;
        for (AuthResource entity : resourceList) {
            parentid = entity.getParentid();
            //顶层元素的parentCode==null或者为0
            if (parentid == null || parentid == 0L) {
                resultList.add(entity);
            }
        }

        //获取每个顶层元素的子数据集合
        for (AuthResource entity : resultList) {
            List<AuthResource> subList = getSubList(entity.getId(), resourceList);
            if (CollectionUtils.isNotEmpty(subList)) {
                subList.sort(Comparator.comparing(AuthResource::getOrder));
            }
            entity.setResources(subList);
        }
        
        return resultList;
    }

    private static List<AuthResource> getSubList(Long id, List<AuthResource> entityList) {
        List<AuthResource> childList = new ArrayList<>();
        Long parentId;

        //子集的直接子对象
        for (AuthResource entity : entityList) {
            parentId = entity.getParentid();
            if (id.equals(parentId)) {
                childList.add(entity);
            }
        }

        //子集的间接子对象
        for (AuthResource entity : childList) {
            List<AuthResource> subList = getSubList(entity.getId(), entityList);
            if (CollectionUtils.isNotEmpty(subList)) {
                subList.sort(Comparator.comparing(AuthResource::getOrder));
            }
            entity.setResources(subList);
        }

        //递归退出条件
        if (childList.size() == 0) {
            return null;
        }

        return childList;
    }

}
