package com.juan.adx.model.dsp.yueke.request;
import lombok.Data;

@Data
public class YueKeAsset {

    /**
     * <pre>
     * 编号信息
     * 是否必填: 是
     * </pre>
     */
    private Integer id;

    /**
     * <pre>
     * 标题信息
     * 是否必填: 否
     * </pre>
     */
    private String title;

    /**
     * <pre>
     * 描述信息，品牌信息
     * 是否必填: 否
     * 取值或描述: 当广告样式为 "原生图文" 或者 "激励视频" 时，必填项
     * </pre>
     */
    private String data;

    /**
     * <pre>
     * 图片内容信息
     * 是否必填: 否
     * 取值或描述: 当广告样式为图文广告或者图片广告时必填
     * </pre>
     */
    private YueKeImg img;

    /**
     * <pre>
     * 视频广告采买标准描述信息
     * 是否必填: 否
     * 取值或描述: 当广告样式为“视频信息流”时，该字段为必填选项
     * </pre>
     */
    private YueKeVideo video;

    /**
     * <pre>
     * 强制状态
     * 是否必填: 否
     * 取值或描述: 1:必须准守  0:选择遵守  默认：0
     * </pre>
     */
    private Integer required;
}
