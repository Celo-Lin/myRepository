package com.juan.adx.model.dsp.haoya.request;
import lombok.Data;

@Data
public class HaoYaGeo {
    /**
     * <pre>
     * lat: 纬度 (-90.0 至 90.0), 负值代表南
     * </pre>
     */
    private Double lat;

    /**
     * <pre>
     * lon: 经度 (-180.0 至 180.0), 负值代表西
     * </pre>
     */
    private Double lon;

    /**
     * <pre>
     * country: 国家，中文
     * </pre>
     */
    private String country;

    /**
     * <pre>
     * province: 省份，中文
     * </pre>
     */
    private String province;

    /**
     * <pre>
     * city: 城市，中文
     * </pre>
     */
    private String city;
}
