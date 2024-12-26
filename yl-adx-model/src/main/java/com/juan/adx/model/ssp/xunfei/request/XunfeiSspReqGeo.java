package com.juan.adx.model.ssp.xunfei.request;

import lombok.Data;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/22 11:54
 */
@Data
public class XunfeiSspReqGeo {

    /**
     * 纬度。采用 WGS-84 坐标系标准
     */
    private Double lat;

    /**
     * 经度。采用 WGS-84 坐标系标准
     */
    private Double lon;

    private Object ext;
}
