package com.juan.adx.model.dsp.oppo.response;
import lombok.Data;

import java.util.List;

@Data
public class OppoUnionApiAdInfo {
    /**
     * <pre>
     * adId: 广告 ID
     * </pre>
     */
    private String adId;

    /**
     * <pre>
     * bannerAutoRF: Banner 广告刷新时间，单位毫秒
     * </pre>
     */
    private Integer bannerAutoRF;

    /**
     * <pre>
     * ssCountdown: 闪屏读秒时间 ,单位毫秒
     * </pre>
     */
    private Long ssCountdown;

    /**
     * <pre>
     * logoFile: 广告 logo 信息，见 MaterialFile Object
     * </pre>
     */
    private OppoMaterialFile logoFile;

    /**
     * <pre>
     * creativeType: 创意类型，见附录-创意类型
     * 
     * 值 	素材类型&尺寸 		描述
     * 2 	图文，1080*1920 		纯图片广告
     * 3 	图文，512*512 		图标广告
     * 6 	图文，1280*720 		大图广告
     * 7 	图文，320*210 		小图广告
     * 8 	图文，320*210*3 		组图广告
     * 10 	视频，9:16 或 16:9 	激励视频广告
     * 11 	视频，9:16 			开屏视频广告
     * 15 	视频，9:16 或 16:9 	插屏视频
     * 16 	视频，16:9 			横版视频
     * 18 	图文，1080*1920 		竖版大图
     * 19 	视频，9:16 			原生竖版视频
     * </pre>
     */
    private Integer creativeType;

    /**
     * <pre>
     * fileList: 文件集合，见 MaterialFile Object
     * </pre>
     */
    private List<OppoMaterialFile> fileList;

    /**
     * <pre>
     * title: 广告标题
     * </pre>
     */
    private String title;

    /**
     * <pre>
     * desc: 广告描述
     * </pre>
     */
    private String desc;

    /**
     * <pre>
     * targetUrl: 目标落地页
     * </pre>
     */
    private String targetUrl;

    /**
     * <pre>
     * deepLink: 应用直达链接
     * </pre>
     */
    private String deepLink;

    /**
     * <pre>
     * fullImageFlag: 是否全屏
     * </pre>
     */
    private Boolean fullImageFlag;

    /**
     * <pre>
     * verticalFlag: 是否竖屏
     * </pre>
     */
    private Boolean verticalFlag;

    /**
     * <pre>
     * contentType: 推广类型，见附录-推广类型
     * 1 链接推广 注：【跳转】优先跳转 deepLink，未下发时跳转 targetUrl
     * 2 应用推广 注：【跳转】只下发 targetUrl 时跳转 targetUrl，下发 deepLink 时优先跳转 deepLink，跳转 deeplink 失败后跳转 targetUrl；
     * </pre>
     */
    private Integer contentType;

    /**
     * <pre>
     * price: 单位：分/CPM，非竞价媒体默认为 0
     * </pre>
     */
    private Long price;

    /**
     * <pre>
     * videoDuration: 视频播放时长 ms 单位毫秒
     * </pre>
     */
    private Long videoDuration;

    /**
     * <pre>
     * trackingList: 跟踪对象，见 Tracking Object
     * </pre>
     */
    private List<OppoTracking> trackingList;

    /**
     * <pre>
     * App: 应用信息，见附录-App Object
     * </pre>
     */
    private OppoApp app;
}
