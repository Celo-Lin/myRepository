package com.juan.adx.model.dsp.yueke.response;
import lombok.Data;
import java.util.List;

@Data
public class YueKeDspResponseParam {

    /**
     * 响应编号，与广告本次竞价请求编号信息保持一致
     * 必须字段
     */
    private String id;

    /**
     * 智友竞价执行唯一编号，由智友广告交易平台自动生成
     * 必须字段
     */
    private String bidid;

    /**
     * 参竞信息，包含物料描述信息及广告主相关信息
     * 必须字段
     */
    private List<YueKeSeatBid> seatbid;

    /**
     * 接口返回状态码
     * 必须字段
     */
    private String code;

    /**
     * 信息字段
     * 非必须字段
     */
    private String info;

}
