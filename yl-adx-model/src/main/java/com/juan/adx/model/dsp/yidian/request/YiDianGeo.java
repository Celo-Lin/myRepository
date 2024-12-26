package com.juan.adx.model.dsp.yidian.request;

import lombok.Data;

/**
 * @author caoliwu
 * @version 1.0
 * @ClassName YiDianGeo
 * @description: TODO
 * @date 2024/5/28 9:52
 */
@Data
public class YiDianGeo {
    /**
     * <pre>
     * 纬度 选填
     * </pre>
     */
    private Double lat;

    /**
     * <pre>
     * 经度 选填
     * </pre>
     */
    private Double lon;
}
