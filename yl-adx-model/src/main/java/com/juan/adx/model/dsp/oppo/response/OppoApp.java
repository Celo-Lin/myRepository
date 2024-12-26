package com.juan.adx.model.dsp.oppo.response;
import lombok.Data;

@Data
public class OppoApp {
    /**
     * <pre>
     * appPackage: 【下载类】-应用包名
     * </pre>
     */
    private String appPackage;

    /**
     * <pre>
     * apkSize: 【下载类】-应用包大小,字节
     * </pre>
     */
    private Long apkSize;

    /**
     * <pre>
     * privacyUrl: 隐私地址
     * </pre>
     */
    private String privacyUrl;

    /**
     * <pre>
     * permissionUrl: 许可证地址
     * </pre>
     */
    private String permissionUrl;

    /**
     * <pre>
     * developerName: 开发人者名称
     * </pre>
     */
    private String developerName;

    /**
     * <pre>
     * versionName: 版本名称
     * </pre>
     */
    private String versionName;

    /**
     * <pre>
     * appName: 应用名称
     * </pre>
     */
    private String appName;

    /**
     * <pre>
     * appIconUrl: 应用图标的 URL
     * </pre>
     */
    private String appIconUrl;

    /**
     * <pre>
     * downloadUrl: 应用下载地址
     * </pre>
     */
    private String downloadUrl;

    /**
     * <pre>
     * apkMd5: 应用 Md5 值
     * </pre>
     */
    private String apkMd5;

    /**
     * <pre>
     * versionCode: 应用版本号
     * </pre>
     */
    private Long versionCode;

    /**
     * <pre>
     * appDescUrl: 产品介绍（应用推广类广告下发）
     * </pre>
     */
    private String appDescUrl;
}
