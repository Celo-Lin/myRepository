package com.juan.adx.model.dsp.youdao.response;

import lombok.Data;

import java.util.List;

/**
 * 播放事件追踪 URL
 * @author： HeWen Zhou
 * @date： 2024/5/16 10:49
 */
@Data
public class YouDaoVideoPlaytrackers {

    /**----------------信息流视频参数-----------------------**/
    /**
     * 播放事件上报 URLs
     */
    private List<String> play;

    /**
     * 暂停事件上报 URLs
     */
    private List<String> pause;
    /**
     * 重新播放事件上报 URLs
     */
    private List<String> replay;
    /**
     * 全屏事件上报 URLs
     */
    private List<String> fullscreen;
    /**
     * 退出全屏事件上报 URLs
     */
    private List<String> unfullscreen;
    /**
     * 上滑事件上报 URLs
     */
    private List<String> upscroll;

    /**
     * 下滑事件上报 URLs
     */
    private List<String> downscroll;
    /**
     * 播放进度追踪 URLs，视频播放 3/4 处上报
     */
    private List<String> playpercent;


    /**----------------激励视频参数-----------------------**/
    /**
     * 静音事件上报 URLs。
     */
    private List<String> mute;

    /**
     * 关闭静音事件上报 URLs。
     */
    private List<String> unmute;

    /**
     * 关闭视频事件上报 URLs。
     */
    private List<String> videoclose;

    /**
     * 播放进度追踪 URLs，客户端在视频播放进度
     * 为 checkpoint 时上报对应的所有 URLs。
     */
    private List<YouDaoRewardedVideoplaypercentage> playpercentage;

}
