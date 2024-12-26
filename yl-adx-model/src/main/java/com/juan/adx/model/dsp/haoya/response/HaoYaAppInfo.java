package com.juan.adx.model.dsp.haoya.response;
import java.util.List;

import lombok.Data;

@Data
public class HaoYaAppInfo {
    /**
     * <pre>
     * name: 应用名称
     * </pre>
     */
    private String name;

    /**
     * <pre>
     * downloadUrl: 应用下载地址，action.type=2 时该字段必有值
     * </pre>
     */
    private String downloadUrl;

    /**
     * <pre>
     * packageName: 应用包名
     * </pre>
     */
    private String packageName;

    /**
     * <pre>
     * version: 应用版本号
     * </pre>
     */
    private String version;

    /**
     * <pre>
     * size: 应用大小，单位 KB，下载类需要
     * </pre>
     */
    private Integer size;

    /**
     * <pre>
     * logo: 应用 logo，详见Image对象
     * </pre>
     */
    private HaoYaImage logo;

    /**
     * <pre>
     * intro: 应用介绍
     * </pre>
     */
    private String intro;

    /**
     * <pre>
     * developer: 应用开发者
     * </pre>
     */
    private String developer;

    /**
     * <pre>
     * privacy: 应用隐私协议
     * </pre>
     */
    private String privacy;

    /**
     * <pre>
     * privacyUrl: 应用隐私协议链接
     * </pre>
     */
    private String privacyUrl;

    /**
     * <pre>
     * permissions: 应用隐私权限列表，详见Permission对象
     * </pre>
     */
    private List<HaoYaPermission> permissions;

    /**
     * <pre>
     * permissionUrl: 应用隐私权限Url
     * </pre>
     */
    private String permissionUrl;

    /**
     * <pre>
     * functionDesc: 产品功能介绍
     * </pre>
     */
    private String functionDesc;
}
