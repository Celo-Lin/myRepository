package com.juan.adx.model.ssp.xunfei.response;

import lombok.Data;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/22 9:46
 */
@Data
public class XunfeiSspRespImage {

    /**
     * 图片地址
     */
    private String url;

    /**
     * 图片宽度
     */
    private Integer width;

    /**
     * 图片高度
     */
    private Integer height;

    /**
     * 图片大小（Kb）
     */
    private Integer size;

    /**
     * 图片的 Md5 值
     */
    private String md5;

    private Object ext;

}
