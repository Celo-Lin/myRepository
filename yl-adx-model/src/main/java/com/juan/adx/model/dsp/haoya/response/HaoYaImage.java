package com.juan.adx.model.dsp.haoya.response;
import lombok.Data;

@Data
public class HaoYaImage {
    /**
     * <pre>
     * url: 图片地址
     * </pre>
     */
    private String url;

    /**
     * <pre>
     * width: 图片宽度，单位：像素
     * </pre>
     */
    private Integer width;

    /**
     * <pre>
     * height: 图片高度，单位：像素
     * </pre>
     */
    private Integer height;
}
