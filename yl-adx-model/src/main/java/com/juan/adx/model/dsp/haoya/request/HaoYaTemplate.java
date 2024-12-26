package com.juan.adx.model.dsp.haoya.request;
import lombok.Data;

@Data
public class HaoYaTemplate {
    /**
     * <pre>
     * id: 广告样式 ID，详见：附录 4.3-广告形式规范模版
     * </pre>
     */
    private Integer id;

    /**
     * <pre>
     * width: 素材宽度
     * </pre>
     */
    private Integer width;

    /**
     * <pre>
     * height: 素材高度
     * </pre>
     */
    private Integer height;
}
