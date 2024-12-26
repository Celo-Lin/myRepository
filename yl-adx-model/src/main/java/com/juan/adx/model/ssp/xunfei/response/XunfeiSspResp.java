package com.juan.adx.model.ssp.xunfei.response;

import lombok.Data;

import java.util.List;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/22 8:56
 */
@Data
public class XunfeiSspResp {

    /**
     * 由 dsp 回填 Bidrequest 的唯一标识
     */
    private String id;

    /**
     * 由 DSP 给出的该次竞价的 Bidresponse
     * 的唯一标识，可用于日志追踪
     */
    private String bidid;

    /**
     * DSP 投标信息
     */
    private List<XunfeiSspRespSeatbid> seatbid;
    /**
     * 价格单位，USD,CNY,默认 CNY
     */
    private String cur;

    private Object ext;
}
