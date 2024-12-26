package com.juan.adx.model.dsp.yidian.response;

import lombok.Data;

/**
 * @author caoliwu
 * @version 1.0
 * @ClassName YiDianPermission
 * @description: TODO
 * @date 2024/5/28 11:34
 */
@Data
public class YiDianPermission {
    /**
     * <pre>
     * 权限标题  选填
     * </pre>
     */
    private String permission_name;

    /**
     * <pre>
     * 权限描述  选填
     * </pre>
     */
    private String permission_desc;
}
