package com.juan.adx.model.ssp.yoyo.request;

import lombok.Data;

/**
* @Author: ChaoLong Lin
* @CreateTime: 2024/12/19 9:55
* @Description: YoYo 请求广告位资源文档广告位支持的模板对象
* @version: V1.0
*/
@Data
public class TemplateBase {
    /**
     * 广告模板ID
     */
    private String yoyoTemplateId;

    /**
     * 支持交互类型 :
     * 1=落地页，2=应用下载，3=dp+落地页，4=dp+应用下载，5=微信小程序，6=微信小游戏，7=快应用；不同的广告位/模板支持的交互类型不相同. 同时支持多种交互方式时, 多个值间使用,分隔, 例如: 1,3,4
     */
    private String targetTypeStr;
}