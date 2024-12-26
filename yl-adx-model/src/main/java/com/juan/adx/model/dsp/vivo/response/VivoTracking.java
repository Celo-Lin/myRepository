package com.juan.adx.model.dsp.vivo.response;

import lombok.Data;

import java.util.List;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/25 16:35
 */
@Data
public class VivoTracking {

    /** 跟踪事件类型 */
    private Integer trackingEvent;

    /** 监测链接集合，有多个监控地址时，每一个都要调用 */
    private List<String> trackUrls;

}
