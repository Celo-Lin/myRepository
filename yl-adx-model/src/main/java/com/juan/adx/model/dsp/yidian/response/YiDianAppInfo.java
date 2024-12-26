package com.juan.adx.model.dsp.yidian.response;

import lombok.Data;

import java.util.List;

/**
 * @author caoliwu
 * @version 1.0
 * @ClassName YiDianAppInfo
 * @description: TODO
 * @date 2024/5/28 11:34
 */
@Data
public class YiDianAppInfo {
    /**
     * <pre>
     * app 名称    选填
     * </pre>
     */
    private String app_name;

    /**
     * <pre>
     * app 版本号   选填
     * </pre>
     */
    private String version_name;

    /**
     * <pre>
     * 开发者名称  选填
     * </pre>
     */
    private String developer_name;

    /**
     * <pre>
     * 权限描述，数组内取值    选填
     * permission_name: 权限标题
     * permission_desc: 权限描述
     * </pre>
     */
    private List<YiDianPermission> permissions;

    /**
     * <pre>
     * 隐私政策链接  选填
     * </pre>
     */
    private String privacy_policy_url;
}
