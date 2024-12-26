package com.juan.adx.model.ssp.xunfei.request;

import lombok.Data;

import java.util.List;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/22 8:56
 */
@Data
public class XunfeiSspReq {

    /**
     * 本次请求唯一标识，由讯飞 ADX 生成
     */
    private String id;
    /**
     * 是否是测试流量。
     * 0 – 正式流量
     * 1 – 测试流量
     */
    private Integer is_test;

    /**
     * 广告位描述数组，目前只支持一个
     */
    private List<XunfeiSspReqImpression> imps;

    /**
     * 应用信息
     */
    private XunfeiSspReqApp app;

    /**
     * 设备信息
     */
    private XunfeiSspReqDevice device;
    /**
     * 用户信息
     */
    private XunfeiSspReqUser user;
    /**
     * 频道 ID，用于 OTT 广告
     */
    private String channel_id;
    /**
     * 有内容定向（如：电视剧、综艺） 的
     * 广告位必填
     */
    private String content_channel;
    /**
     * 货币类型
     * USD – 美元
     * CNY – 元
     */
    private List<String> cur;
    /**
     * 扩展字段（预留）
     */
    private Object ext;

}
