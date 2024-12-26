package com.juan.adx.model.ssp.xunfei.request;

import lombok.Data;

import java.util.List;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/22 9:28
 */
@Data
public class XunfeiSspReqPmp {
    /**
     * 订单列表
     */
    private List<XunfeiSspReqDeal> deals;
    /**
     * 扩展字段（预留）
     */
    private Object ext;
}
