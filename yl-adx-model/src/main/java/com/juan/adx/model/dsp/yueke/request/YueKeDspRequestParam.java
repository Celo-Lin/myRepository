package com.juan.adx.model.dsp.yueke.request;
import lombok.Data;
import java.util.List;

@Data
public class YueKeDspRequestParam {

    /**
     * <pre>
     * 请求唯一编号，生成方式：随机字符串的MD5加密小写
     * 是否必填: 是
     * </pre>
     */
    private String id;

    /**
     * <pre>
     * 当前广告协议版本
     * 是否必填: 是
     * 取值或描述: 针对本文档开发，默认为 "2.0.0"
     * </pre>
     */
    private String version;

    /**
     * <pre>
     * 竞价售卖广告位置信息
     * 是否必填: 是
     * 取值或描述: 至少要存在一个描述对象
     * </pre>
     */
    private List<YueKeImp> imp;

    /**
     * <pre>
     * 站点信息
     * 是否必填: 否
     * 取值或描述: 当流量类型为 "移动网页流量" 时必填
     * </pre>
     */
    private YueKeSite site;

    /**
     * <pre>
     * 应用信息
     * 是否必填: 是
     * 取值或描述: 当流量类型为 "移动应用流量" 时必填
     * </pre>
     */
    private YueKeApp app;

    /**
     * <pre>
     * 设备信息
     * 是否必填: 是
     * 取值或描述: 当流量类型为 "移动应用流量" 时必填
     * </pre>
     */
    private YueKeDevice device;

    /**
     * <pre>
     * 用户信息
     * 是否必填: 否
     * 取值或描述: 完善该部分信息有利于增加广告填充率
     * </pre>
     */
    private YueKeUser user;

    /**
     * <pre>
     * 拍卖类型
     * 是否必填: 否
     * 取值或描述: 1: GFP (第一价格)  2: GSP (第二价格)  默认为 2
     * </pre>
     */
    private Integer at;

    /**
     * <pre>
     * 流量类型
     * 是否必填: 否
     * 取值或描述: 0代表测试流量，1代表正式流量，默认为 1
     * </pre>
     */
    private Integer test = 1;

    /**
     * <pre>
     * 超时时间
     * 是否必填: 否
     * 取值或描述: 本次广告竞价请求，允许的最大超时时间，单位为毫秒
     * </pre>
     */
    private Integer tmax;

    /**
     * <pre>
     * 扩展描述
     * 是否必填: 是
     * 取值或描述: 针对 "一键调起"，"一键下载" 及 "上报协议" 等信息的采集
     * </pre>
     */
    private YueKeExt ext;
}
