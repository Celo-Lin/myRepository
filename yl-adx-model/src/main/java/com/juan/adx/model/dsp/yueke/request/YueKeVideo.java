package com.juan.adx.model.dsp.yueke.request;
import java.util.Arrays;
import java.util.List;

import lombok.Data;

@Data
public class YueKeVideo {

    /**
     * <pre>
     * 广告位视频所要求的宽度
     * 是否必填: 是
     * 取值或描述: 单位为像素px
     * </pre>
     */
    private Integer w;

    /**
     * <pre>
     * 广告位视频所要求的高度
     * 是否必填: 是
     * 取值或描述: 单位为像素px
     * </pre>
     */
    private Integer h;

    /**
     * <pre>
     * 视频类型
     * 是否必填: 否
     * 取值或描述: 1：原生视频，2：激励视频，默认为 1
     * </pre>
     */
    private Integer type = 1;

    /**
     * <pre>
     * 视频播放的最小时长
     * 是否必填: 是
     * 取值或描述: 单位为秒
     * </pre>
     */
    private Integer minduration;

    /**
     * <pre>
     * 视频播放的最大时长
     * 是否必填: 否
     * 取值或描述: 单位为秒
     * </pre>
     */
    private Integer maxduration;

    /**
     * <pre>
     * 视频在流量售卖位中的位置
     * 是否必填: 否
     * 取值或描述: 0：前贴，1：中贴，2：后贴
     * </pre>
     */
    private Integer startdelay;

    /**
     * <pre>
     * 视频在广告位中的展示方式
     * 是否必填: 否
     * 取值或描述: 1：视频流中展示，2：视频内容上悬浮展示
     * </pre>
     */
    private Integer linearity;

    /**
     * <pre>
     * 流量售卖位在设备屏幕上显示的位置编号
     * 是否必填: 否
     * 取值或描述: 默认为 0
     * </pre>
     */
    private Integer pos = 0;

    /**
     * <pre>
     * 视频的 MIME 类型
     * 是否必填: 否
     * 取值或描述: 默认为 "video/mp4"
     * </pre>
     */
    private List<String> mimes = Arrays.asList("video/mp4");
}
