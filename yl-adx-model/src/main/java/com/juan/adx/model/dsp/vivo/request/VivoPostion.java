package com.juan.adx.model.dsp.vivo.request;

import lombok.Data;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/25 16:06
 */
@Data
public class VivoPostion {

    /** 广告位 ID，在 vivo 联盟创建广告位时分配的 posID */
    private String positionId;

    /** 广告位类型，见附录-广告位类型 */
    private Integer displayType;

    /** 广告位宽，单位 px，如：720 */
    private Integer width;

    /** 广告位高，单位 px，如：1280 */
    private Integer height;

    /** 广告位底价，单位：分/CPM */
    private Long bidFloor;

}
