package com.juan.adx.model.dsp.yueke.response;
import lombok.Data;

@Data
public class YueKeWechat {

    /**
     * <pre>
     * 移动应用(App)的ID
     * 是否必填: 否
     * </pre>
     */
    private String appId;

    /**
     * <pre>
     * 小程序原始ID，微信官方文档
     * 是否必填: 是
     * </pre>
     */
    private String programId;

    /**
     * <pre>
     * 小程序页面路径
     * 是否必填: 是
     * </pre>
     */
    private String programPath;

    /**
     * <pre>
     * 小程序发布类型
     * 是否必填: 否
     * 取值或描述: 0: 正式版, 1: 测试版, 2: 预览版
     * </pre>
     */
    private Integer releaseType;
}
