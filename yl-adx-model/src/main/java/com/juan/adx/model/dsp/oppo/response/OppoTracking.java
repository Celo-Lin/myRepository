package com.juan.adx.model.dsp.oppo.response;
import lombok.Data;

import java.util.List;

@Data
public class OppoTracking {
    /**
     * <pre>
     * trackingEvent: 跟踪事件类型，见附录-跟踪事件类型
     * 1 广告点击事件
     * 2 广告曝光事件
     * 3 广告关闭事件
     * 5 竞价回调事件
     * 10010 视频广告开始播放
     * 10011 视频广告播放完成
     * 10012 视频广告播放 25%
     * 10013 视频广告播放 50%
     * 10014 视频广告播放 75%
     * </pre>
     */
    private Integer trackingEvent;

    /**
     * <pre>
     * trackUrls: 监测链接集合
     * </pre>
     */
    private List<String> trackUrls;
}
