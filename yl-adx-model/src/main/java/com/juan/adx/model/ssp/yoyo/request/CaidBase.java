package com.juan.adx.model.ssp.yoyo.request;

import lombok.Data;

/**
* @Author: ChaoLong Lin
* @CreateTime: 2024/12/19 9:42
* @Description: YoYo 请求中广协对象
* @version: V1.0
*/
@Data
public class CaidBase {
    /**
     * 中广协caid, eg: 5e755f34033d1c94728c4583b49ea326
     */
    private String caid;

    /**
     * 中广协caid版本, eg: 20230330
     */
    private String version;
}

