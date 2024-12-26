package com.juan.adx.model.dsp.youdao.response;

import lombok.Data;

import java.util.List;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/16 9:45
 */
@Data
public class YouDaoDspResponseParam {

    /**
     * 广告 id
     */
    private Integer creativeid;

    /**
     * 点击链接
     */
    private String clk;

    /**
     * 点击跟踪链接数组,
     * PC 端对接广告位客户端只需要获取clktrackers 里的第一条链接进行上报即可
     */
    private List<String> clktrackers;

    /**
     * 展示跟踪链接数组，该字段支持的宏定义见下文展示上报说明，由 exchange 做替换
     */
    private List<String> imptracker;

    /**
     * URI schemes 形式的 deep link 链接。如果广告商设置了此URL 将会返回。
     */
    private String deeplink;

    /**
     * 尝试吊起 deeplink，用户点击应用直达广告后，尝试调用universal_link 或 customized_invoke_url 时上报至此链接
     */
    private List<String> dptrackers;

    /**
     * deepLink吊起场景下，该参数下发链接为应用已安装的监测上报链接；
     * 应用是否安装场景下，该参数下发链接为应用已安装的监测上报链接；
     */
    private List<String> dpInstallTrackers;

    /**
     * 因未安装应用导致调起失败的上报地址
     */
    private List<String> dpNotInstallTrackers;

    /**
     * deeplink 调起成功的上报地址
     */
    private List<String> dpSuccessTrackers;

    /**
     * deeplink 应用已安装但吊起失败时的上报链接
     */
    private List<String> dpFailedTrackers;

    /**
     * 广告类型，0：落地页广告；1：下载类型广告
     */
    private Integer ydAdType;

    /**
     * 广告样式名称
     */
    private String styleName;

    private String iconimage;

    private String mainimage;

    private String mainimage1;

    private String mainimage2;

    private String mainimage3;

    private String mainimage4;


    private String text;

    private String title;

    /**
     * 当广告类型为下载类型时，并且 appName 不为空时，会返回此字段。该字段会将样式中的同名字段覆盖
     */
    private String appName;

    /**
     * 当广告类型为下载类型时，并且 packageName 不为空时，会返回此字段。该字段会将样式中的同名字段覆盖
     */
    private String packageName;

    /**
     * 开始下载，用户点击或自动下载触发时上报至此链接；
     */
    private List<String> apkStartDownloadTrackers;

    /**
     * 下载完成，下载任务完成时上报至此链接；
     */
    private List<String> apkDownloadTrackers;

    /**
     * 开始安装时，用户点击或自动安装触发时上报至此链接；
     */
    private List<String> apkStartInstallTrackers;

    /**
     * 用户点击安装并完成安装的打点上报地址
     */
    private List<String> apkInstallTrackers;

    /**
     * 将要下载的应用相关信息
     */
    private YouDaoDownloadAppInfo downloadAppInfo;

    /**
     * 效果广告出价（单位：分）
     */
    private Double ecpm;

    /**
     * 微信小程序跳转相关信息
     */
    private YouDaoWxMiniProgram wxMiniProgram;

    /**
     * 尝试吊起微信小程序，用户点击微信小程序广告后，尝试调用 WxMiniProgram.path 时上报至此链接；
     */
    private List<String> wxTrackers;

    /**
     * 微信小程序吊起成功时上报至此链接
     */
    private List<String> wxSuccessTrackers;

    /**
     * 微信小程序吊起失败时上报至此链接；
     */
    private List<String> wxFailedTrackers;

    /**
     *竞胜上报链接，该字段支持的宏定义见下文展示上报说明，
     * 由 exchange 做替换
     */
    private String winNotice;

    /**
     * 广告包含的图标类图片的宽高信息，一个广告最多只有一个
     * icon 图片及其宽高，需在有道侧配置后，对应广告位方可下
     * 发该信息
     */
    private YouDaoWidthHeightInfo icon;

    /**
     * 广告包含的主图类图片的宽高信息集合，包含主图、视频封
     * 面图等，多个主图图片将提供多个宽高信息，以图片 URL
     * 逐一区分，需在有道侧配置后，对应广告位方可下发该信息
     */
    private List<YouDaoWidthHeightInfo> images;

    /**
     * 广告包含的视频的宽高信息集合，多个视频将提供多个宽高
     * 信息，以视频 URL 逐一区分，需在有道侧配置后，对应广
     * 告位方可下发该信息
     */
    private List<YouDaoWidthHeightInfo> videos;


    /**--------------------------激励视频/信息流视频公共参数----------------------**/

    /**
     * 广告视频地址
     */
    private String videourl;

    /**
     * 播放视频错误追踪 URL
     */
    private List<String> error;

    /**
     * 播放事件追踪 URL。
     */
    private YouDaoVideoPlaytrackers playtrackers;


    /**--------------------------信息流视频参数----------------------**/
    /**
     * 有道自有数据，需用其内容替换所有返回的 xxxtrackers 中的 [YD_EXT]
     * 宏
     */
    private String yd_ext;

    /**
     * cta 按钮所显示的文字内容，若没有，则 ctaText 按钮默认显示“查看详情”
     */
    private String ctaText;

    /**
     * 点击 cta 按钮时需上报本 trackers 内所有 tracker（没有 ctaText 则不返回
     * 此字段）
     */
    private List<String> ctatrackers;


    /**-----------------------激励视频参数------------------**/
    /**
     * 客户端是否预先加载广告视频。
     */
    private Boolean prefetch;

    /**
     * 视频播放完成后显示的 endCard HTML 页面。
     */
    private String endcardhtml;

    /**
     * 视频的宽度，单位 px
     */
    private Integer videowidth;

    /**
     * 视频的高度，单位 px
     */
    private Integer videoheight;

    /**
     * 视频时长，格式为 HH:mm:ss.SSS, 如 00:02:30.000, 表示时长为 2分 30 秒
     */
    private String videoduration;

    /**
     * 视频文件大小，单位 byte
     */
    private Long videosize;

    /**
     * 服务器激励回调上报 URL。此字段只有在使用服务器激励回调时会返回。
     */
    private List<String> callbackTrackers;

    /**
     * 广告视频加载成功追踪 URL。
     */
    private List<String> videoloaded;


}
