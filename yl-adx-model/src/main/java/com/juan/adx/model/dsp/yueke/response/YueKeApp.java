package com.juan.adx.model.dsp.yueke.response;
import lombok.Data;

@Data
public class YueKeApp {

    /**
     * <pre>
     * 应用安装包的大小，单位：KB
     * 是否必填: 否
     * </pre>
     */
    private Long size;

    /**
     * <pre>
     * 应用安装包的MD5值，用于校验安装包完整性
     * 是否必填: 否
     * </pre>
     */
    private String md5;

    /**
     * <pre>
     * 应用安装包的图标信息
     * 是否必填: 否
     * </pre>
     */
    private String icon;

    /**
     * <pre>
     * 应用安装包的包名
     * 是否必填: 否
     * </pre>
     */
    private String pack;

    /**
     * <pre>
     * 应用安装包的应用版本
     * 是否必填: 否
     * </pre>
     */
    private String vers;

    /**
     * <pre>
     * 应用安装包的应用名称
     * 是否必填: 否
     * </pre>
     */
    private String name;

    /**
     * <pre>
     * 应用权限信息地址
     * 是否必填: 否
     * </pre>
     */
    private String permission;

    /**
     * <pre>
     * 应用隐私政策地址
     * 是否必填: 否
     * </pre>
     */
    private String privacy;

    /**
     * <pre>
     * 应用开发者名称
     * 是否必填: 否
     * </pre>
     */
    private String dev;

    /**
     * <pre>
     * 产品功能介绍
     * 是否必填: 否
     * </pre>
     */
    private String desc;
}
