package com.juan.adx.model.dsp.yueke.request;
import lombok.Data;
import java.util.List;

import com.alibaba.fastjson2.annotation.JSONField;

@Data
public class YueKeImp {

    /**
     * <pre>
     * 售卖广告位置描述编号，从1递增
     * 是否必填: 是
     * 取值或描述: 默认为 "1"
     * </pre>
     */
    private String id = "1";

    /**
     * <pre>
     * 广告位置的真实宽度，单位：像素px
     * 是否必填: 是
     * </pre>
     */
    private Integer aw;

    /**
     * <pre>
     * 广告位置的真实高度，单位：像素px
     * 是否必填: 是
     * </pre>
     */
    private Integer ah;

    /**
     * <pre>
     * 广告位置ID，由智友广告交易系统提供
     * 是否必填: 是
     * </pre>
     */
    private String tagid;

    /**
     * <pre>
     * 竞价底价信息，每千次曝光底价，单位: 分，默认币种CNY，默认为 0
     * 是否必填: 否
     * </pre>
     */
    private Float bidfloor = 0f;

    /**
     * <pre>
     * 纯图片类广告
     * 是否必填: 否
     * 取值或描述: 当广告位为 "开屏"，"插屏" 和“横幅”样式时必填
     * </pre>
     */
    private YueKeBanner banner;

    /**
     * <pre>
     * 视频广告
     * 是否必填: 否
     * 取值或描述: 当广告位为“激励视频”样式时必填
     * </pre>
     */
    private YueKeVideo video;

    /**
     * <pre>
     * 原生广告
     * 是否必填: 否
     * 取值或描述: 当广告位为“图文信息流”或“视频信息流”样式时必填
     * </pre>
     */
    @JSONField(name = "native")
    private YueKeNative nativeAd;

    /**
     * <pre>
     * 私有交易，PB(保价模式)，PDB(保价保量模式) 时，该字段为必填
     * 是否必填: 否
     * </pre>
     */
    private YueKePmp pmp;

    /**
     * <pre>
     * 物料类型列表
     * 是否必填: 否
     * 取值或描述: MVB:视频物料，MGT:图文物料，MGB:仅图物料，MTB:仅文物料
     * </pre>
     */
    private List<String> mts;
}
