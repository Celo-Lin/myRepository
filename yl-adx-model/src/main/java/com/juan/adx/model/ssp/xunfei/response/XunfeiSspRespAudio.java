package com.juan.adx.model.ssp.xunfei.response;

import lombok.Data;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/22 9:47
 */
@Data
public class XunfeiSspRespAudio {

    /**
     * 音频链接 URL
     */
    private String url;

    /**
     * 音频格式
     */
    private Integer format;

    /**
     * 音频时长，单位：秒，须遵守
     * minduration 和 maxduration 的限制
     */
    private Integer duration;

    /**
     * 视频码率，单位 kbps
     */
    private Integer bitrate;

    /**
     * 结束生效时间戳（单位 s）（用作客户
     * 端参考缓存时间）
     */
    private Long end_time;
    /**
     * 音频大小（Kb）
     */
    private Integer size;

    /**
     * 音频的 Md5 值
     */
    private String md5;

    /**
     * 扩展字段（预留）
     */
    private Object ext;

}
