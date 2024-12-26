package com.juan.adx.model.dsp.haoya.response;
import lombok.Data;

@Data
public class HaoYaVideo {
    /**
     * <pre>
     * url: 视频地址
     * </pre>
     */
    private String url;

    /**
     * <pre>
     * duration: 视频时长，单位：秒
     * </pre>
     */
    private Integer duration;

    /**
     * <pre>
     * size: 视频大小，单位：KB
     * </pre>
     */
    private Integer size;

    /**
     * <pre>
     * cover: 视频封面图片或视频后贴片图片，详见Image对象
     * </pre>
     */
    private HaoYaImage cover;

    /**
     * <pre>
     * protocol: 视频协议类型
     * </pre>
     */
    private Integer protocol;
}
