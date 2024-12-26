package com.juan.adx.model.ssp.xunfei.response;

import lombok.Data;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/22 9:46
 */
@Data
public class XunfeiSspRespBid {

    /**
     * DSP 回填 Bid Request 中对应的
     * imp_id
     */
    private String imp_id;

    /**
     * DSP 出价，单位是元/千次曝光,即
     * CPM
     */
    private Double price;

    /**
     * 获胜通知
     */
    private String nurl;

    /**
     * 竞价失败回调 url，需支持竞价失败
     * 宏替换，替换宏为__TYPE__
     * TYPE=101，即未竞得，原因是出价
     * 低
     * TYPE=102，即未竞得，原因是素材
     * 未审核
     * TYPE=103，即未竞得，原因是素材
     * 审核拒绝
     */
    private String failed_callback;

    /**
     * 用于 pmp 交易，指向请求中的
     * deal.id
     */
    private String deal_id;

    /**
     * 素材创意 id（素材送审必填）
     */
    private String creative_id;

    /**
     * 素材名称
     */
    private String creative_name;

    /**
     * 素材二级行业 id（见附录 7.10）
     */
    private String creative_industry_id;

    /**
     * 广告主 id
     */
    private String advertiser_id;

    /**
     * 广告主二级行业 id
     */
    private String advertiser_industry_id;

    /**
     * 是否是动态素材，默认为否
     */
    private Boolean is_creative_dynamic;

    /**
     * 是否是动态落地页，默认为否
     */
    private Boolean is_landing_dynamic;

    /**
     * 广告位创意 id，对应请求
     * CreativeTemplate 对象中的 id 的
     * 值
     */
    private Integer creative_template_id;

    /**
     * 落地页。iOS 端跳转 App Store 下载
     * 链接也请填入此处。
     */
    private String landing;

    /**
     * 广告交互类型。
     * 1 – 纯曝光无点击
     * 2 – 跳转
     * 3 – 安卓下载
     * 4 – iOS 下载
     * 6 – 拨打电话
     * 7 - 落地页跳转到语音互动广告页
     * 面
     * 9 – 小程序跳转
     */
    private Integer action_type;

    /**
     * Html 内容，目前只支持完整的
     * html。
     */
    private XunfeiSspRespHtml html_content;

    /**
     * 图片
     */
    private XunfeiSspRespImage img;

    /**
     * 图片 1，适用于多图模板
     */
    private XunfeiSspRespImage img1;

    /**
     * 图片 2，适用于多图模板
     */
    private XunfeiSspRespImage img2;

    /**
     * 图片 3，适用于多图模板
     */
    private XunfeiSspRespImage img3;

    /**
     * 视频内容
     */
    private XunfeiSspRespVideo video;

    /**
     * 视频 1，适用于多视频模板
     */
    private XunfeiSspRespVideo video1;

    /**
     * 视频 2，适用于多视频模板
     */
    private XunfeiSspRespVideo video2;

    /**
     * 音频内容
     */
    private XunfeiSspRespAudio audio;

    /**
     * 图标，必填，尺寸要求为 150*150。
     * 投品牌类广告（landing 跳转）为品
     * 牌 logo；
     * 投应用促活（deeplink 跳转）和应
     * 用下载（action_type 为 3,4）时为
     * 应用图标
     */
    private XunfeiSspRespImage icon;

    /**
     *品牌名称，必填
     */
    private String brand;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String desc;

    /**
     * 说明
     */
    private String content;

    /**
     * 行动按钮，动作行为按钮显示文本
     */
    private String ctatext;

    /**
     * 应用名称，投应用促活（deeplink
     * 跳转）和应用下载（action_type 为
     * 3,4）时必填
     */
    private String app_name;

    /**
     * 应用下载广告的下载地址（点击下
     * 载按钮的时候触发，没有的时候直
     * 接触发落地页），交互类型为安卓下
     * 载时必填
     */
    private String app_download_url;

    /**
     * 应用版本，交互类型为安卓下载时
     * 必填
     */
    private String app_ver;

    /**
     * 安卓应用包名或 iOS bundle_id（包
     *  讯飞 ADX 接入说明
     * 13
     * 名），交互类型为安卓下载或 iOS 下
     * 载时必填。例：
     * “com.iflytek.inputmethod”
     */
    private String package_name;

    /**
     * iOS 应用市场 ID，交互类型为 iOS
     * 下载时必填。例：“1582446193”
     */
    private String app_id;

    /**
     * 应用大小，单位 MB。交互类型为安
     * 卓下载时必填
     */
    private Double app_size;

    /**
     * 应用开发者名称，交互类型为安卓
     * 下载时必填
     */
    private String developer_name;

    /**
     * 隐私政策链接，交互类型为安卓下
     * 载时必填
     */
    private String app_privacy_url;

    /**
     * 应用权限链接，交互类型为安卓下
     * 载时必填
     */
    private String app_permission;

    /**
     * 安装量
     */
    private Long downloads;

    /**
     * 评分等级
     */
    private String rating;

    /**
     * 应用包的 Md5 值
     */
    private String pkg_md5;

    /**
     * 应用下载描述
     */
    private String app_desc;

    /**
     * 应用功能介绍，交互类型为安卓下
     * 载时必填
     */
    private String app_intro_url;

    /**
     * 语音互动广告，语音广告地址。
     */
    private XunfeiSspRespVoiceAd voice_ad_url;

    /**
     * 电话号码 action_type = 6 时必填
     */
    private String phone;

    /**
     * 小程序原始 id
     * 交互类型为“小程序跳转”时必
     * 填，该字段有值则跳转至小程序
     */
    private String mp_id;

    /**
     * 落地页跳转到小程序里面的页面路
     * 径(注意填原始路径，不要
     * urlencode，否则跳转不了小程序
     * 页面)
     * 交互类型为“小程序跳转”时必
     * 填，该字段有值则跳转至小程序
     */
    private String mp_path;

    /**
     * 监测链接
     */
    private XunfeiSspRespMonitor monitor;

    /**
     * Android 端请返回 deeplink 链接，
     * iOS 端推荐返回 universal link 链
     * 接。
     */
    private String deeplink;

    /**
     * 扩展字段
     */
    private Object ext;
 }
