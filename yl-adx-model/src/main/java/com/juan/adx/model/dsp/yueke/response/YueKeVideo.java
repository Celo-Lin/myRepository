package com.juan.adx.model.dsp.yueke.response;
import lombok.Data;
import java.util.List;

@Data
public class YueKeVideo {

    /**
     * <pre>
     * 视频物料的宽度，单位: 像素
     * 是否必填: 否
     * </pre>
     */
    private Integer w;

    /**
     * <pre>
     * 视频物料的高度，单位: 像素
     * 是否必填: 否
     * </pre>
     */
    private Integer h;

    /**
     * <pre>
     * 视频类型
     * 是否必填: 是
     * 取值或描述: 1: 原生视频, 2: 激励视频
     * </pre>
     */
    private Integer type;

    /**
     * <pre>
     * 视频文件大小，单位：KB
     * 是否必填: 否
     * </pre>
     */
    private Integer size;

    /**
     * <pre>
     * 视频文件 MIME 类型
     * 是否必填: 否
     * </pre>
     */
    private String mimes;

    /**
     * <pre>
     * 视频物料的访问地址
     * 是否必填: 是
     * </pre>
     */
    private String iurl;

    /**
     * <pre>
     * 视频时长，单位：秒
     * 是否必填: 否
     * </pre>
     */
    private Integer duration;

    /**
     * <pre>
     * 视频物料的封面图信息
     * 是否必填: 否
     * </pre>
     */
    private YueKeImg cover;

    /**
     * <pre>
     * 是否允许跳过视频
     * 是否必填: 否
     * 取值或描述: 0: 不允许跳过, 1: 允许跳过
     * </pre>
     */
    private Integer skip;

    /**
     * <pre>
     * 强制播放时长，单位: 秒
     * 是否必填: 否
     * </pre>
     */
    private Integer skipMinTime;

    /**
     * <pre>
     * 最长缓存时间，单位: 毫秒
     * 是否必填: 否
     * 取值或描述: 当视频类型为激励视频 (type==2) 时，该字段才有效
     * </pre>
     */
    private Integer preloadTtl;

    /**
     * <pre>
     * 视频尾帧信息，视频播放完成后展示内容
     * 是否必填: 否
     * 取值或描述: 当视频类型为激励视频 (type==2) 时，该字段才有效
     * </pre>
     */
    private YueKeCard card;

    /**
     * <pre>
     * 广告描述资产信息
     * 是否必填: 否
     * </pre>
     */
    private List<YueKeAssets> assets;
}
