package com.juan.adx.model.entity.permission;

import java.util.List;

import lombok.Data;

@Data
public class AuthResource {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 权限项URL
     */
    private String resource;

    /**
     * 权限项说明
     */
    private String description;

    /**
     * 权限类型:1,URL/0,ENTER
     */
    private Integer type;

    /**
     * 父权限项
     */
    private Long parentid;

    /**
     * 权限名称
     */
    private String name;

    private List<AuthResource> resources;

    /**
     *  是否授权
     */
    private Boolean grant;

    /**
     *  排序
     */
    private Integer order;

    /**
     *  请求url
     */
    private String url;
    
    /**
     * 前端icon
     */
    private String icon;
    
    /**
     * 前端路由
     */
    private String route;


}