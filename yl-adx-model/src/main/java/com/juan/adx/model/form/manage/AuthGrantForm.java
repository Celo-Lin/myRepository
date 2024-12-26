package com.juan.adx.model.form.manage;

import java.util.List;

import lombok.Data;

@Data
public class AuthGrantForm {

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 用户id列表
     */
    private List<String> userIds;
}
