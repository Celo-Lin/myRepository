package com.juan.adx.model.dsp.youdao.response;

import lombok.Data;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/16 9:46
 */
@Data
public class YouDaoDownloadAppInfo {

    /**
     * 应用权限链接
     */
    private String appPermission;

    /**
     * 隐私政策链接。
     */
    private String privacyPolicy;

    /**
     * 应用开发者名称
     */
    private String developerName;

    /**
     * 应用名称
     */
    private String appTitle;

    /**
     * 应用图标。
     */
    private String appIconImage;

    /**
     * 应用版本号。
     */
    private String appVersion;

    /**
     * 应用介绍链接。。
     */
    private String appDescUrl;
}
