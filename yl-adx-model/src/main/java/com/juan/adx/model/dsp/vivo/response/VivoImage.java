package com.juan.adx.model.dsp.vivo.response;

import lombok.Data;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/25 16:34
 */
@Data
public class VivoImage {

    /** 图片地址 */
    private String url;

    /** 图片尺寸：宽 */
    private Integer width;

    /** 图片尺寸：高 */
    private Integer height;

}
