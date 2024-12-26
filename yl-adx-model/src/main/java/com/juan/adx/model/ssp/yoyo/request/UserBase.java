package com.juan.adx.model.ssp.yoyo.request;

import lombok.Data;

import java.util.List;

/**
* @Author: ChaoLong Lin
* @CreateTime: 2024/12/19 10:00
* @Description: YoYo 请求用户信息对象
* @version: V1.0
*/
@Data
public class UserBase {
    /**
     * 用户性别 , 0=男 , 1=女
     */
    private Integer gender;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 逗号分隔的用户关键词或兴趣点
     */
    private List<String> keywordList;
}
