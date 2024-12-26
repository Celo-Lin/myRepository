package com.juan.adx.model.dsp.haoya.request;
import lombok.Data;

@Data
public class HaoYaUser {
    /**
     * <pre>
     * userId: 媒体侧提供的用户 ID
     * </pre>
     */
    private String userId;

    /**
     * <pre>
     * gender: 性别
     * 可选值：M-男 F-女 默认-未知
     * </pre>
     */
    private String gender;

    /**
     * <pre>
     * age: 年龄
     * </pre>
     */
    private Integer age;

    /**
     * <pre>
     * keywords: 关键词
     * </pre>
     */
    private String keywords;
}
