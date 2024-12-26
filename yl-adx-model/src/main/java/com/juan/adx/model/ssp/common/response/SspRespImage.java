package com.juan.adx.model.ssp.common.response;

import lombok.Data;

@Data
public class SspRespImage {

    /**
     * 图片地址
     */
    private String url;

    /**
     * 图片宽
     */
    private Integer width;
    /**
     * 图片高
     */
    private Integer height;
}
