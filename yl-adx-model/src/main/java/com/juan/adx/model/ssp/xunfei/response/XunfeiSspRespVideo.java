package com.juan.adx.model.ssp.xunfei.response;

import lombok.Data;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/22 9:47
 */
@Data
public class XunfeiSspRespVideo {

    /**
     * 视频链接 URL
     */
    private String url;

    /**
     * 视频宽度，单位 pixel，必须与请求中
     * 视频元素宽度相吻合
     */
    private Integer width;

    /**
     * 视频高度，单位 pixel，必须与请求中
     * 视频元素高度相吻合
     */
    private Integer height;

    /**
     * 视频码率，单位 kbps
     */
    private Integer bitrate;

    /**
     * 视频格式
     */
    private Integer format;

    /**
     * 视频时长，单位：秒，须遵守
     * min_duration 和 max_duration 的限制
     */
    private Integer duration;

    /**
     * 结束生效时间戳（单位 s）（用作客户
     * 端参考缓存时间）
     */
    private Long end_time;

    /**
     * 视频大小（Kb）
     */
    private Integer size;

    /**
     * 视频的 Md5 值
     */
    private String md5;

    private Object ext;
}
