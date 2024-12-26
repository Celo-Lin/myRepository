package com.juan.adx.model.ssp.yoyo.request;

import lombok.Data;

import java.util.List;

/**
 * @Author: Kevin.赵伟凯
 * @CreateTime: 2024-12-18 14:51
 * @Description: YoYo 请求实体根对象
 * @Version: 1.0
 */
@Data
public class YoYoBidRequest {
    /**
     * 请求唯一ID
     */
    private String id;

    /**
     * 曝光信息
     */
    private List<ImpBase> imp;

    /**
     * 设备信息
     */
    private DeviceBase device;

    /**
     * 请求APP信息
     */
    private AppBase app;

    /**
     * 计费模式 , 1=一价 , 2=二价 , 默认为1
     */
    private String at;

    /**
     * 广告行业黑名单(yoyo广告行业ID) , 取值详见：
     */
    private List<String> bcat;

    /**
     * badv	广告主黑名单
     */
    private List<String> badv;

    /**
     * 扩展字段
     */
    private String ext;

    /**
     * 用户信息
     */
    private UserBase user;

    /**
     * 用户已安装应用包名列表
     */
    private List<String> installAppList;

    /**
     * crowdPackageIds	人群 ID列表，流量命中的DMP人群ID，部分流量支持，使用人群ID时请在返回体中回传请求体传入的值
     */
    private List<Integer> crowdPackageIds;

    /**
     * 本次请求的等待耗时
     */
    private Integer timeout;
}
