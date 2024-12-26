package com.juan.adx.model.dsp.yidian.response;

import lombok.Data;

/**
 * @author caoliwu
 * @version 1.0
 * @ClassName YiDianVideo
 * @description: TODO
 * @date 2024/5/28 11:33
 */
@Data
public class YiDianVideo {
    /**
     * <pre>
     * 视频宽度  选填
     * </pre>
     */
    private Integer w;

    /**
     * <pre>
     * 视频高度  选填
     * </pre>
     */
    private Integer h;

    /**
     * <pre>
     * 视频文件大小，单位字节   选填
     * </pre>
     */
    private Long size;

    /**
     * <pre>
     * 视频素材地址   选填
     * </pre>
     */
    private String videourl;

    /**
     * <pre>
     * 视频时长，单位秒    选填
     * </pre>
     */
    private Long videoduration;

    /**
     * <pre>
     * 下载图标 url，仅用于竖版版视频    选填
     * </pre>
     */
    private String logourl;
}
