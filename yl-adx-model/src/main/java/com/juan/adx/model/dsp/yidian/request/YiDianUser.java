package com.juan.adx.model.dsp.yidian.request;

import lombok.Data;

/**
 * @author caoliwu
 * @version 1.0
 * @ClassName YiDianUser
 * @description: TODO
 * @date 2024/5/28 9:52
 */
@Data
public class YiDianUser {
    /**
     * <pre>
     * 年龄 选填
     * </pre>
     */
    private Integer age;

    /**
     * <pre>
     * 性别：选填
     * 1：男
     * 2：女
     * 3：未知
     * </pre>
     */
    private Integer gender;
}
