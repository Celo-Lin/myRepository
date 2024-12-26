package com.juan.adx.model.dsp.yueke.response;
import java.util.List;

import com.alibaba.fastjson2.annotation.JSONField;

import lombok.Data;

@Data
public class YueKeBid {

    /**
     * <pre>
     * 竞价编号，智友针对每个IMP自动生成一个唯一编号
     * 是否必填: 是
     * </pre>
     */
    private String id;

    /**
     * <pre>
     * 曝光编号，与BidRequest中的Imp编号一一对应
     * 是否必填: 是
     * </pre>
     */
    private String impid;

    /**
     * <pre>
     * 私有交易编号，对应着私有交易模式下的订单编号信息
     * 是否必填: 否
     * </pre>
     */
    private String dealid;

    /**
     * <pre>
     * 广告创意编号
     * 是否必填: 否
     * </pre>
     */
    private String crid;

    /**
     * <pre>
     * 广告投递计划编号
     * 是否必填: 否
     * </pre>
     */
    private String cid;

    /**
     * <pre>
     * 广告编号
     * 是否必填: 否
     * </pre>
     */
    private String adid;

    /**
     * <pre>
     * 广告主域名集合
     * 是否必填: 否
     * </pre>
     */
    private List<String> adomain;

    /**
     * <pre>
     * 广告创意类别信息
     * 是否必填: 否
     * </pre>
     */
    private List<String> cat;

    /**
     * <pre>
     * 竞价出价，单位：分
     * 是否必填: 是
     * </pre>
     */
    private Float price;

    /**
     * <pre>
     * 公开交易赢价通知链接，可能需要进行宏替换
     * 是否必填: 否
     * </pre>
     */
    private String nurl;

    /**
     * <pre>
     * 私有交易赢价通知链接，可能需要进行宏替换
     * 是否必填: 否
     * </pre>
     */
    private String burl;

    /**
     * <pre>
     * 公开交易竞价失败通知链接，可能需要进行宏替换
     * 是否必填: 否
     * </pre>
     */
    private String lurl;

    /**
     * <pre>
     * 点击目标地址，存在宏替换
     * 是否必填: 是
     * </pre>
     */
    private String target;

    /**
     * <pre>
     * 深度链接地址
     * 是否必填: 否
     * </pre>
     */
    private String deepLink;

    /**
     * <pre>
     * 点击广告后的交互类型，1 :跳转类，2 :下载类
     * 是否必填: 是
     * </pre>
     */
    private String actionType;

    /**
     * <pre>
     * 如果actionType=2并且demand=GDT时，需要执行二次请求下载
     * 是否必填: 否
     * </pre>
     */
    private String demand;

    /**
     * <pre>
     * 应用包名
     * 是否必填: 否
     * </pre>
     */
    private String bundle;

    /**
     * <pre>
     * 返回物料为HTML代码时存在，媒体需要加载该HTML代码进行广告渲染
     * 是否必填: 否
     * </pre>
     */
    private String adm;

    /**
     * <pre>
     * 广告位下载类时，点击链接下载时的应用信息
     * 是否必填: 否
     * </pre>
     */
    private YueKeApp app;

    /**
     * <pre>
     * 如果不为空优先处理小程序吊起，查看微信小程序官方文档
     * 是否必填: 否
     * </pre>
     */
    private YueKeWechat wechat;

    /**
     * <pre>
     * 图片物料，广告位为 "开屏" , "插屏" , "横幅" 或 "焦点图" 样式时必填
     * 是否必填: 是
     * </pre>
     */
    private YueKeBanner banner;

    /**
     * <pre>
     * 视频物料，广告位为 "激励视频" 时必填
     * 是否必填: 是
     * </pre>
     */
    private YueKeVideo video;

    /**
     * <pre>
     * 原生物料，广告位为 "原生广告" 和 "视频信息流" 时必传
     * 是否必填: 是
     * </pre>
     */
    @JSONField(name = "native")
    private YueKeNative nativeMaterial;

    /**
     * <pre>
     * 上报监控地址，GET请求，需进行宏替换
     * 是否必填: 是
     * </pre>
     */
    private YueKeEvents events;

}
