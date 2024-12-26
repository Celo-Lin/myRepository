package com.juan.adx.model.ssp.qutt.response;

import com.alibaba.fastjson2.annotation.JSONField;
import com.juan.adx.model.ssp.qutt.request.*;
import lombok.Data;

import java.util.List;

/**
 * @Author: Kevin.赵伟凯
 * @CreateTime: 2024-05-18
 * @Description: 趣头条ssp响应外层对象
 * @Version: 1.0
 */
@Data
public class QuttSspResp {

    /**
     * 是
     * 响应状态码
     */
    private Integer code;
    /**
     * 是
     * ⼩于128字节
     * ADX 提供的 Bid Request 唯⼀标识
     */
    @JSONField(name = "request_id")
    private String requestId;

    /**
     * 否
     * DSP的响应席位，每个seat_bid
     * 对应Bid Request中的⼀个
     * impression
     */
    @JSONField(name = "seat_bids")
    private List<QuttSspRespSeatBid> seatBids;

    /**
     * 否
     * 是否冷却。是1，否0，默认否
     */
    @JSONField(name = "cooling_down_status")
    private Integer coolingDownStatus;
    /**
     * 否
     * 冷却时间，单位秒。默认600s。
     */
    @JSONField(name = "cooling_down_time")
    private Integer coolingDownTime;
    /**
     * 否
     * 收到Bid Request⾄发送完Bid
     * Respnsoe的⽤时。单位：毫 秒。
     */
    @JSONField(name = "processing_time")
    private Long processingTime;
}
