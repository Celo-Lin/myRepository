package com.juan.adx.model.ssp.xunfei.request;

import lombok.Data;

import java.util.List;
import java.util.Objects;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/22 8:58
 */
@Data
public class XunfeiSspReqImpression {

    /**
     * 曝光 ID,由讯飞 ADX 唯一
     * 产生
     */
    private String imp_id;

    /**
     * 广告位 ID
     */
    private String tag_id;
    /**
     * 广告形式 id
     */
    private Integer adunit_form;

    /**
     * 广告位支持的创意模板信
     * 息
     */
    private List<XunfeiSspReqCreativeTemplate> creative_templates;

    /**
     * 底价，单位：元/千次，即 CPM（RTB 竞价模式必填）
     */
    private Double bidfloor;

    /**
     * 用于 pmp 交易模式如pdb
     */
    private XunfeiSspReqPmp pmp;

    /**
     * deeplink 支持状态
     * 0: 不支持任何形式的
     * deeplink。
     * 1：支持原生的 deeplink
     * （点击广告可以直接唤起
     * APP）
     * 2：支持 H5 形式的
     * deeplink(点击跳转到 H5
     * 页面后在 H5 页面上点击
     * 唤起 APP)
     * 为空代表 deeplink 支持
     * 状态未知。
     */
    private List<Integer> deeplink_support_status;

    /**
     * 本次请求期望物料中所有
     * 的 url 的网络协议类型
     * 是 http 还是 https 取
     * 值范围。
     * 0 - 未知
     * 1 - 只支持 http
     * 2 - 只支持 https
     * 3 - https 和 http 都支
     * 持
     */
    private Integer http_support_status;

    /**
     * 诱导素材禁投类型（若为
     * 空则代表所有诱导类型都
     * 可投）
     * 1: 弹框
     * 2: 红包
     * 3: 带“x”/跳过
     */
    private List<Integer> banned_induce_types;
    /**
     * 扩展字段（预留）
     */
    private Objects ext;

}
