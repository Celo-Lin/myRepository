package com.juan.adx.model.dsp.yueke.response;
import lombok.Data;
import java.util.List;

@Data
public class YueKeSeatBid {

    /**
     * 系统自动生成的唯一标识符
     * 必须字段
     */
    private String id;

    /**
     * 广告对象列表，每个Bid对应请求描述信息中的IMP
     * 必须字段
     */
    private List<YueKeBid> bid;

    /**
     * 赢价分组状态，0: 单独Bid赢价，1: 全部Bid均赢价
     * 非必须字段
     */
    private Integer group;

    /**
     * 本次广告请求参与出价的广告主编号
     * 非必须字段
     */
    private String seat;
}
