package com.juan.adx.model.dsp.vivo.response;

import lombok.Data;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/25 16:34
 */
@Data
public class VivoVideo {
    /** 视频文件的 URL */
    private String videoUrl;

    /**
     * 视频素材的播放时长
     */
    private Integer duration;

    /** 视频大小，单位是 KB */
    private Integer size;

    /** 视频文件的尺寸：宽 */
    private Integer width;

    /** 视频文件的尺寸：高 */
    private Integer height;

    /** 视频预览图 URL */
    private String previewImgUrl;

}
