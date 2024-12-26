package com.juan.adx.model.ssp.qutt.response;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @Author: Kevin.赵伟凯
 * @CreateTime: 2024-05-18 15:44
 * @Description:
 * @Version: 1.0
 */

@Data
public class QuttSspRespSeatBid {

    /**
     * 是
     * Bid Request中对应Impression的
     * ID。该字段必填。
     */
    @JSONField(name = "impression_id")
    private String impressionId;

    /**
     * 否
     * 针对请求中imp的bid信息。
     */
    private List<QuttSspRespBid> bids;

}
