package com.juan.adx.model.dsp.yidian.response;

import lombok.Data;

import java.util.List;

/**
 * @author caoliwu
 * @version 1.0
 * @ClassName YiDianDspResponseParam
 * @description: TODO
 * @date 2024/5/28 11:29
 */
@Data
public class YiDianDspResponseParam {
    /**
     * <pre>
     * 对应 YiDianDspRequestParam 中的 ID
     * </pre>
     */
    private String id;

    /**
     * <pre>
     * DSP 竞价信息，如果出价会固定返回一个 选填
     * </pre>
     */
    private List<YiDianSeatBid> seatBid;

    /**
     * <pre>
     * DSP 产生的响应 ID，用于日志与追踪 选填
     * </pre>
     */
    private String bidid;

    /**
     * <pre>
     * DSP 处理时间，单位（ms） 选填
     * </pre>
     */
    private String processtime;

    /**
     * <pre>
     * 0：有广告填充
     * 707：无广告填充
     * 600：反作弊
     * 712：设备冷却
     * </pre>
     */
    private Integer code;

    /**
     * <pre>
     * code=712 时有效，nbt 结束时间，时间戳 单位：s 选填
     * </pre>
     */
    private Integer nbtendtime;

}
