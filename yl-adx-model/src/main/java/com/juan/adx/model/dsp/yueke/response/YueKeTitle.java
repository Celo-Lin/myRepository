package com.juan.adx.model.dsp.yueke.response;
import lombok.Data;

@Data
public class YueKeTitle {

    /**
     * <pre>
     * 标题文字长度
     * 是否必填: 否
     * 取值或描述: 标题内容信息的长度
     * </pre>
     */
    private Integer len;

    /**
     * <pre>
     * 标题内容信息
     * 是否必填: 是
     * </pre>
     */
    private String text;
}
