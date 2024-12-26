package com.juan.adx.model.dsp.oppo.request;
import lombok.Data;

@Data
public class OppoGpsInfo {
    /**
     * <pre>
     * lat: 纬度（-90~90）
     * </pre>
     */
    private Double lat;

    /**
     * <pre>
     * lon: 经度（-180~180）
     * </pre>
     */
    private Double lon;

    /**
     * <pre>
     * timestamp: 获取经纬度的时间戳，单位毫秒
     * </pre>
     */
    private Long timestamp;
}
