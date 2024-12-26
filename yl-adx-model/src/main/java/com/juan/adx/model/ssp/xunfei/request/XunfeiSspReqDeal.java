package com.juan.adx.model.ssp.xunfei.request;

import lombok.Data;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/22 9:29
 */
@Data
public class XunfeiSspReqDeal {

    /**
     * 双方线下确定好的 deal id
     */
    private String id;
    /**
     * 双方线下确认好的价格(单位：元)
     */
    private Double bidfloor;

    /**
     * 扩展字段（预留）
     */
    private Object ext;
}
