package com.juan.adx.model.dsp.yidian.response;

import lombok.Data;

import java.util.List;

/**
 * @author caoliwu
 * @version 1.0
 * @ClassName YiDianSeatBid
 * @description: TODO
 * @date 2024/5/28 11:31
 */
@Data
public class YiDianSeatBid {
    /**
     * <pre>
     * 出价对象数组 ，如果出价则对应于BidRequest 中的 Imp
     * </pre>
     */
    private List<YiDianBid> bid;
}
