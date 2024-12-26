package com.juan.adx.model.dsp.yueke.response;

@lombok.Data
public class YueKeData {

    /**
     * <pre>
     * 类型
     * 是否必填: 是
     * 取值或描述: 2:描述
     * </pre>
     */
    private Integer type;

    /**
     * <pre>
     * 描述文字长度信息
     * 是否必填: 否
     * 取值或描述: 描述内容详情文字的长度
     * </pre>
     */
    private Integer len;

    /**
     * <pre>
     * 描述内容详情文字
     * 是否必填: 是
     * </pre>
     */
    private String value;
}
