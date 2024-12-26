package com.juan.adx.model.ssp.common.response;

import java.util.List;

import lombok.Data;

@Data
public class SspRespTrack {
    /**
     * 广告被展示,上报链接集合
     */
    private List<String> showUrls;
    
    /**
     * 广告被点击,上报链接集合
     */
    private List<String> clickUrls;
    
    /**
     * 广告加载,上报链接集合
     */
    private List<String> adLoadUrls;
    
    /**
     * 广告被跳过,上报链接集合
     */
    private List<String> adSkipUrls;
    
    /**
     * 广告被关闭,上报链接集合
     */
    private List<String> adCloseUrls;
    
    /**
     * 唤起微信小程序时,上报链接集合
     */
    private List<String> wechatOpenUrls;
    
    
    
    /**
     * 应用下载开始,上报链接集合(下载类广告需要上报）
     */
    private List<String> startDownloadUrls;
    
    /**
     * 应用下载完成,上报链接集合(下载类广告需要上报）
     */
    private List<String> finishDownloadUrls;
    
    /**
     * 应用安装开始,上报链接集合(下载类广告需要上报）
     */
    private List<String> startInstallUrls;
    
    /**
     * 应用安装完成,上报链接集合(下载类广告需要上报）
     */
    private List<String> finishInstallUrls;
    
    /**
     * 应用应用激活(打开),上报链接集合
     */
    private List<String> activeAppUrls;
    
    /**
     * deeplink尝试调起,上报链接集合
     */
    private List<String> deeplinkTryUrls;
    
    /**
     * deeplink调起成功,上报链接集合
     */
    private List<String> deeplinkSuccessUrls;
    
    /**
     * deeplink调起失败,上报链接集合
     */
    private List<String> deeplinkFailureUrls;
    
    /**
     * deeplink被点击前,上报链接集合
     */
    private List<String> deeplinkClickUrls;
    
    /**
     * deeplink监测应用已安装,上报链接集合
     */
    private List<String> deeplinkInstalledkUrls;
    
    /**
     * deeplink监测应用未安装而无法唤醒,上报链接集合
     */
    private List<String> deeplinkUninstallkUrls;
    
    
    
    /*------------------------------------ 分界线：视频类上报链接 ------------------------------------*/
    
    /**
     * 视频播放开始,上报链接集合
     */
    private List<String> videoStartUrls;
    
    /**
     * 视频播放中点击,上报链接集合
     */
    private List<String> videoClickUrls;
    
    /**
     * 视频播放完成,上报链接集合
     */
    private List<String> videoCompleteUrls;
    
    /**
     * 视频播放失败,上报链接集合
     */
    private List<String> videoFailUrls;
    
    /**
     * 视频关闭,上报链接集合
     */
    private List<String> videoCloseUrls;
    
    /**
     * 视频播放跳过,上报链接集合
     */
    private List<String> videoSkipUrls;
    
    /**
     * 视频播放暂停,上报链接集合
     */
    private List<String> videoPauseUrls;
    
    /**
     * 视频暂停再开始,上报链接集合
     */
    private List<String> videoResumeUrls;
    
    /**
     * 视频重播,上报链接集合
     */
    private List<String> videoReplayUrls;
    
    /**
     * 视频开启静音,上报链接集合
     */
    private List<String> videoMuteUrls;
    
    /**
     * 视频解除静音,上报链接集合
     */
    private List<String> videoUnmuteUrls;
    
    /**
     * 视频全屏播放,上报链接集合
     */
    private List<String> videoFullscreenUrls;
    
    /**
     * 视频退出全屏播放,上报链接集合
     */
    private List<String> videoExitFullscreenUrls;
    
    /**
     * 视频上滑,上报链接集合
     */
    private List<String> videoUpscrollUrls;
    
    /**
     * 视频下滑,上报链接集合
     */
    private List<String> videoDownscrollUrls;
    
    /**
     * 视频播放25%,上报链接集合
     */
    private List<String> videoQuartileUrls;
    
    /**
     * 视频播放50%,上报链接集合
     */
    private List<String> videoHalfUrls;
    
    /**
     * 视频播放75%,上报链接集合
     */
    private List<String> videoThreeQuartileUrls;
    

}
