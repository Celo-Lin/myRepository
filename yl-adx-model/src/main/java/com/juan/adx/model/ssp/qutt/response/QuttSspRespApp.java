package com.juan.adx.model.ssp.qutt.response;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @Author: Kevin.赵伟凯
 * @CreateTime: 2024-05-18 23:19
 * @Description:
 * @Version: 1.0
 */
@Data
public class QuttSspRespApp {

    /**
     * 否
     * app名称
     */
    @JSONField(name = "app_name")
    private String appName;

    /**
     * 否
     * android app包名
     */
    @JSONField(name = "package_name")
    private String packageName;

    /**
     * 否
     * ios应⽤唯⼀标识
     */
    @JSONField(name = "bundle_id")
    private String bundleId;

    /**
     * 否
     * app评论⼈数
     */
    @JSONField(name = "comment_num")
    private Integer commentNum;

    /**
     * 否
     * 评分（5分制）
     */
    private Float score;

    /**
     * 否
     * package⼤⼩，单位：字节
     */
    @JSONField(name = "package_size")
    private Long packageSize;

    /**
     * 否
     * 安装包md5
     */
    @JSONField(name = "package_md5")
    private String packageMd5;
    /**
     * 否
     * 下载地址
     */
    private String src;
    /**
     * 否
     * 应⽤版本号，下载类必填
     */
    @JSONField(name = "app_version")
    private String appVersion;

    /**
     * 否
     * 开发者信息，下载类必填
     */
    private String developers;
    /**
     * 否
     * 隐私协议url，下载类必填
     */
    @JSONField(name = "privacy_protocol_url")
    private String privacyProtocolUrl;
    /**
     * 否
     * 应⽤权限url，下载类必填
     */
    @JSONField(name = "permission_protocol_url")
    private String permissionProtocolUrl;
    /**
     * 否
     * 应⽤介绍url，下载类必填
     */
    @JSONField(name = "function_desc_url")
    private String function_desc_url;

}
