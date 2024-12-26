package com.juan.adx.model.dsp.yueke.response;
import lombok.Data;

@Data
public class YueKeBanner {

    /**
     * <pre>
     * 物料的宽度，单位: 像素
     * 是否必填: 否
     * </pre>
     */
    private Integer w;

    /**
     * <pre>
     * 物料的高度，单位: 像素
     * 是否必填: 否
     * </pre>
     */
    private Integer h;

    /**
     * <pre>
     * 图片物料网络访问地址
     * 是否必填: 是
     * </pre>
     */
    private String iurl;

    /**
     * <pre>
     * 图片格式类型
     * 是否必填: 否
     * 取值或描述: 支持的图片格式，例如：image/gif，image/jpeg，image/jpg，image/png
     * </pre>
     */
    private String mimes;

    /**
     * <pre>
     * 开屏视频广告是否允许跳过
     * 是否必填: 否
     * 取值或描述: 0: 不允许跳过, 1: 允许跳过
     * 默认值: 0
     * </pre>
     */
    private Integer skip;

    /**
     * <pre>
     * 当样式为视频开屏样式时，视频广告的时长，单位：秒
     * 是否必填: 否
     * </pre>
     */
    private Integer duration;

    /**
     * <pre>
     * 开屏视频广告的网络访问地址
     * 是否必填: 是 (当样式为视频开屏样式时必填)
     * </pre>
     */
    private String videoUrl;

    /**
     * <pre>
     * 在播放或者展示超过指定的时间后，允许跳过视频广告，单位：秒
     * 是否必填: 否
     * </pre>
     */
    private Integer skipMinTime;
}
