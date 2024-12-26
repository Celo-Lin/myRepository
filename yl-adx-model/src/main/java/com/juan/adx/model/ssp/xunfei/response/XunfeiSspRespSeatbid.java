package com.juan.adx.model.ssp.xunfei.response;

import lombok.Data;

import java.util.List;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/22 9:45
 */
@Data
public class XunfeiSspRespSeatbid {

    /**
     * 针对 Imp 投标信息，目前只支持一个。
     */
    private List<XunfeiSspRespBid> bid;

    private Object ext;
}
