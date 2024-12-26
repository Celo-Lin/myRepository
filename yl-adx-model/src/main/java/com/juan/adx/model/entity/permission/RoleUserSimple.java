package com.juan.adx.model.entity.permission;

import lombok.Data;

@Data
public class RoleUserSimple {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 岗位名称
     */
    private String postName;

    /**
     * 描述
     */
    private String description;
    
}
