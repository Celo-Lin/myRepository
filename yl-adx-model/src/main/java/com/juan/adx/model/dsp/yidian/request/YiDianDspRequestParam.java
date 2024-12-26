package com.juan.adx.model.dsp.yidian.request;

import lombok.Data;

import java.util.List;


/**
 * @author caoliwu
 * @version 1.0
 * @ClassName YiDianDspRequestParam
 * @description: 一点资讯请求参数
 * @date 2024/5/28 9:43
 */
@Data
public class YiDianDspRequestParam {
    /**
     * <pre>
     * 检索 ID，唯一标示请求
     * </pre>
     */
    private String id;

    /**
     * <pre>
     * 曝光对象，每个请求最少包含一个 YiDianImp，
     * 见 YiDianImp
     * </pre>
     */
    private List<YiDianImp> imp;

    /**
     * <pre>
     * 设备信息对象，见 YiDianDevice
     * </pre>
     */
    private YiDianDevice device;

    /**
     * <pre>
     * 移动 YiDianApp 对象，见 YiDianApp
     * </pre>
     */
    private YiDianApp app;

    /**
     * <pre>
     * 用户信息对象，见 YiDianUser,推荐填写
     * </pre>
     */
    private YiDianUser user;

    /**
     * <pre>
     * 超时时长，单位毫秒，默认 300ms ,选填
     * </pre>
     */
    private Integer tmax;

    /**
     * <pre>
     * 测试字段，默认 false： ,选填
     * false: 生产模式
     * true: 测试模式
     * </pre>
     */
    private Boolean test;

    /**
     * <pre>
     * 扩展字段，用户特殊字段要求  ,选填
     * </pre>
     */
    private YiDianExt ext;
}
