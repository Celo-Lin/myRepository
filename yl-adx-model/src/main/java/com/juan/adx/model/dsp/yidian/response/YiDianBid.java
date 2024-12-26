package com.juan.adx.model.dsp.yidian.response;

import lombok.Data;

import java.util.List;

/**
 * @author caoliwu
 * @version 1.0
 * @ClassName YiDianBid
 * @description: TODO
 * @date 2024/5/28 11:32
 */
@Data
public class YiDianBid {
    /**
     * <pre>
     * DSP 产生的本次 BID 的 ID，用于日志与追踪
     * </pre>
     */
    private String id;

    /**
     * <pre>
     * CPM 出价，单位分 选填
     * </pre>
     */
    private Integer price;

    /**
     * <pre>
     * Win Notice Url
     * </pre>
     */
    private String nurl;

    /**
     * <pre>
     * 广告点击类型：
     * 1：跳转
     * 2：下载
     * </pre>
     */
    private Integer ctype;

    /**
     * <pre>
     * 参与竞价的广告位模板类型
     * </pre>
     */
    private Integer templateid;

    /**
     * <pre>
     * 广告 ID
     * </pre>
     */
    private String adid;

    /**
     * <pre>
     * 创意 ID
     * </pre>
     */
    private String crid;

    /**
     * <pre>
     * 素材高，单位像素  选填
     * </pre>
     */
    private Integer h;

    /**
     * <pre>
     * 素材宽，单位像素  选填
     * </pre>
     */
    private Integer w;

    /**
     * <pre>
     * 用户点击后需要跳转到的落地页  选填
     * </pre>
     */
    private String curl;

    /**
     * <pre>
     * 下载地址  选填
     * </pre>
     */
    private String durl;

    /**
     * <pre>
     * deeplink链接，如返回结果中包括 deeplink链接则调起第三方应用，否则跳转落地页  选填
     * </pre>
     */
    private String deeplinkurl;

    /**
     * <pre>
     * 广告标题  选填
     * </pre>
     */
    private String title;

    /**
     * <pre>
     * 广告主名称  选填
     * </pre>
     */
    private String source;

    /**
     * <pre>
     * App 描述   选填
     * </pre>
     */
    private String summary;

    /**
     * <pre>
     * 仅用于下载广告，App 包名   选填
     * </pre>
     */
    private String dpkgname;

    /**
     * <pre>
     * 素材图片地址，如为视频广告则为封面图   选填
     * </pre>
     */
    private List<String> aurl;

    /**
     * <pre>
     * 曝光监测地址数组    选填
     * </pre>
     */
    private List<String> murl;

    /**
     * <pre>
     * 点击监测地址数组    选填
     * </pre>
     */
    private List<String> cmurl;

    /**
     * <pre>
     * 仅用于下载广告，app 下载开始的监测地址数组    选填
     * </pre>
     */
    private List<String> dmurl;

    /**
     * <pre>
     * 仅用于下载广告，app 下载完成的监测地址数组    选填
     * </pre>
     */
    private List<String> downsuccessurl;

    /**
     * <pre>
     * 仅用于唤醒广告，deeplink 链接调起成功监测地址     选填
     * </pre>
     */
    private List<String> deeplinkmurl;

    /**
     * <pre>
     * 仅用于视频广告，视频开始播放监测地址     选填
     * </pre>
     */
    private List<String> playvideomurl;

    /**
     * <pre>
     * 仅用于视频广告，视频播放完成监测地址     选填
     * </pre>
     */
    private List<String> finishvideomurl;

    /**
     * <pre>
     * 视频素材，仅用于视频广告     选填
     * </pre>
     */
    private YiDianVideo video;

    /**
     * <pre>
     * 仅用于小程序    选填
     * </pre>
     */
    private YiDianMiniProgram miniProgram;

    /**
     * <pre>
     * 枚举值：1，0    选填
     * 1.is_support_app_info= 0，不支持（无需）做合规信息拼接
     * 2.is_support_app_info = 1，需要媒体做合规信息拼接
     * </pre>
     */
    private Integer isSupportAppInfo;

    /**
     * <pre>
     * App 信息     选填
     * </pre>
     */
    private YiDianAppInfo app_info;

}
