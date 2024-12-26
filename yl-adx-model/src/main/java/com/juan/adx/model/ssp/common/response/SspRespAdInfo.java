package com.juan.adx.model.ssp.common.response;

import java.util.List;

import lombok.Data;

@Data
public class SspRespAdInfo {

	private String requestId;
    
	/**
	 * <pre>
	 *  广告类型：
	 * 	1：横幅广告(banner)
	 * 	2：开屏广告(splash)
	 * 	3：插屏广告(interstitial)
	 * 	4：信息流广告(flow)
	 * 	5：激励视频广告(rewardvod)
	 * 	6：Native原生广告(native)
	 * </pre>
	 */
	private Integer adType;
    
    /**
     * <pre>
     * 广告物料类型：
     * 	0：未知
     *  1：纯文字广告
     *  2：纯图片广告
     *  3：图文广告
     *  4：HTML广告
     *  5：视频广告
     *  6：音频广告
     * </pre>
     */
    private Integer materialType;
    
    /**
     * <pre>
     * 广告操作行为：
     *  0：无交互
     *  1：应用内WebView打开落地页地址 (落地页)
     *  2：使用系统浏览器打开落地页地址 (落地页)
     *  3：Deeplink 唤醒
     *  4：点击后下载，仅出现在Android设备 (下载)
     *  5：打开微信小程序
     *  6：广点通下载
     * </pre>
     */
    private Integer interactionType;

    /**
     * 广告标题文案，短文案
     */
    private String title;
    
    /**
     * 广告描述文案，长文案
     */
    private String desc;
    
    /**
     * 广告图标地址
     */
    private List<String> adIcons;
    
    /**
     * deeplink类型
     * 1：普通scheme
     * 2：iOS Universal link
     */
    private Integer deeplinkType;

    /**
     * deeplink地址
     */
    private String deeplink;
    
    /**
     * 用户点击后需要跳转到的落地页地址
     */
    private String landingPageUrl;
    
    /**
     * 应用下载地址
     */
    private String downloadUrl;
    
	/**
     * 本广告千次展示预估收益(竞价模式存在)，单位：分
     */
    private Integer bidPrice;
    
    /**
     * 竞胜上报链接(竞价模式存在), 由服务端上报, 需要替换价格宏
     */
    private List<String> winNoticeUrl;
    
    /**
     * 竞败上报链接(竞价模式存在), 由服务端上报, 需要替换价格宏
     */
    private List<String> lossNoticeUrl;

    


    
    /**
     * 广告物料-应用信息
     */
    private SspRespApp app;

    /**
     * 广告物料-视频信息
     */
    private SspRespVideo video;
    
    /**
     * 广告物料-广告图片
     */
    private List<SspRespImage> images;

    /**
     * 微信小程序信息，当action为小程序时有值
     */
    private SspRespMiniProgram miniProgram;
    
    /**
     * 广告效果监测信息
     */
    private SspRespTrack track;


}
