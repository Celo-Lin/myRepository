package com.juan.adx.model.dsp.oppo.request;
import lombok.Data;

@Data
public class OppoPosInfo {
    /**
     * <pre>
     * id: 联盟广告位 ID.
     * </pre>
     */
    private String id;

    /**
     * <pre>
     * posType: 联盟广告位类型：
     * 1 Banner,横幅
     * 2 插屏
     * 4 开屏
     * 8 Native 原生
     * 64 激励视频
     * </pre>
     */
    private Integer posType;

    /**
     * <pre>
     * w: 广告位宽度，以 px 为单位。
     * 注：用于素材选取，不要求与真实位置严格一致。
     * </pre>
     */
    private Integer w;

    /**
     * <pre>
     * h: 广告位高度，以 px 为单位。
     * 注：用于素材选取，不要求与真实位置严格一致。
     * </pre>
     */
    private Integer h;
}
