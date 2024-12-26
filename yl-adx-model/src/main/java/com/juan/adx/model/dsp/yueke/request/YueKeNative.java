package com.juan.adx.model.dsp.yueke.request;
import lombok.Data;
import java.util.List;

@Data
public class YueKeNative {

    /**
     * <pre>
     * OPEN-RTB原生广告请求协议版本编号
     * 是否必填: 否
     * 取值描述: 默认为 "1.2"
     * </pre>
     */
    private String version = "1.2";

    /**
     * <pre>
     * 原生广告信息
     * 是否必填: 是
     * 取值描述: 该集合不能为空，至少存在一项
     * </pre>
     */
    private List<YueKeAsset> assets;
}
