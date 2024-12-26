package com.juan.adx.model.dsp.yueke.response;
import lombok.Data;
import java.util.List;

@Data
public class YueKeNative {

    /**
     * <pre>
     * 原生版本
     * 是否必填: 是
     * 取值或描述: 默认为 "1.2"
     * </pre>
     */
    private String version = "1.2";

    /**
     * <pre>
     * 广告描述资产信息
     * 是否必填: 是
     * 取值或描述: 包含广告资产的列表，至少存在一项
     * </pre>
     */
    private List<YueKeAssets> assets;
}
