package com.juan.adx.model.dsp.vivo.request;

import lombok.Data;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/25 16:06
 */
@Data
public class VivoGeo {

    /** 经度 */
    private Float lat;

    /** 纬度 */
    private Float lng;

    /** 获取经纬度的时间，毫秒值 */
    private Long coordTime;

}
