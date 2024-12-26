package com.juan.adx.model.ssp.xunfei.response;

import lombok.Data;

import java.util.List;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/22 9:47
 */
@Data
public class XunfeiSspRespMonitor {

    /**
     * 广告曝光的时候触发，竞价模式需要支
     * 持替换价格宏${AUCTION_PRICE}详细见
     * 成交价通知说明
     */
    private List<String>  impress_urls;

    /**
     * 点击检测，广告点击的时候触发
     */
    private List<String> click_urls;

    /**
     * deeplink 检测已安装，不支持宏替换
     */
    private List<String> app_installed_urls;

    /**
     * deeplink 检测未安装（未安装调起失
     * 败），不支持宏替换
     */
    private List<String> app_uninstalled_urls;

    /**
     * deeplink 已安装调起成功，不支持宏
     * 替换
     */
    private List<String> app_invoke_success_urls;

    /**
     * deeplink 已安装调起失败，不支持宏
     * 替换
     */
    private List<String> app_invoke_failed_urls;

    /**
     * 下载开始监控（应用下载场景下使用）
     */
    private List<String> download_start_urls;

    /**
     * 下载结束监控（应用下载场景下使用）
     */
    private List<String> download_complete_urls;

    /**
     * 安装开始监控（应用安装场景下使用）
     */
    private List<String> install_start_urls;

    /**
     * 安装结束监控（应用安装场景下使用）
     */
    private List<String> install_complete_urls;

    /**
     * 音频/视频广告开始播放时触发
     */
    private List<String> start_urls;

    /**
     * 音视频广告播放了 25%时触发（音频/视
     * 频广告下发场景下使用）
     */
    private List<String> first_quartile_urls;

    /**
     * 音频/视频广告播放了 50%时触发
     */
    private List<String> mid_point_urls;

    /**
     * 音频/视频广告播放了 75%时触发（音频
     * /视频广告下发场景下使用）
     */
    private List<String> third_quartile_urls;

    /**
     * 音频/视频广告播放完成之后触发（音频
     * /视频广告下发场景下使用）
     */
    private List<String> complete_urls;

    /**
     * 用户点击暂停按钮停止播放音频/视频
     * 广告时触发（音频/视频广告下发场景下
     * 使用）
     */
    private List<String> pause_urls;

    /**
     * 用户在音频/视频广告被暂停或者被停
     * 止之后，主动继续播放音频/视频时触发
     * （音频/视频广告下发场景下使用）
     */
    private List<String> resume_urls;

    /**
     * 用户点击音频/视频跳过按钮跳过音频/
     * 视频播放（音频/视频广告场景下使用）
     */
    private List<String> skip_urls;

    /**
     * 用户主动关闭音频/视频广告声音（音频
     * /视频广告下发场景下使用）
     */
    private List<String> mute_urls;

    /**
     * 用户主动开启音频/视频广告声音（音频
     * /视频广告下发场景下使用）
     */
    private List<String> unmute_urls;

    /**
     * 重播音频/视频（音频/视频广告下发场景
     * 下使用）
     */
    private List<String> replay_urls;

    /**
     * 关闭音频/视频（音频/视频广告下发场景
     * 下使用）
     */
    private List<String> close_linear_urls;

    /**
     * 全屏（视频广告下发场景下使用）
     */
    private List<String> fullscreen_urls;

    /**
     * 退出全屏（视频广告场景下使用）
     */
    private List<String> exit_fullscreen_urls;

    /**
     * 上滑事件（视频广告场景下使用）
     */
    private List<String> up_scroll_urls;

    /**
     * 下滑事件（视频广告场景下使用）
     */
    private List<String> down_scroll_urls;

}
