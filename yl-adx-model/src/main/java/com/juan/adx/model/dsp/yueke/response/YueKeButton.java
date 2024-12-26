package com.juan.adx.model.dsp.yueke.response;
import lombok.Data;

@Data
public class YueKeButton {

    /**
     * <pre>
     * 点击按钮之后的目标地址
     * 是否必填: 是
     * </pre>
     */
    private String url;

    /**
     * <pre>
     * 按钮文字信息
     * 是否必填: 否
     * 取值或描述: 如无返回的话，下载类广告默认为 "免费下载"，跳转类默认为 "查看详情"
     * </pre>
     */
    private String text;
}
