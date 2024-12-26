package com.juan.adx.model.ssp.yoyo.request;

import lombok.Data;

/**
* @Author: ChaoLong Lin
* @CreateTime: 2024/12/19 9:36
* @Description: YoYo 请求来源应用对象
* @version: V1.0
*/
@Data
public class AppBase {
    /**
     * 请求来源的应用名称
     */
    private String name;

    /**
     * 请求来源的应用包名
     */
    private String bundle;

    /**
     * 请求来源的应用版本号
     */
    private String version;

    /**
     * 保留字段
     */
    private String ext;
}

