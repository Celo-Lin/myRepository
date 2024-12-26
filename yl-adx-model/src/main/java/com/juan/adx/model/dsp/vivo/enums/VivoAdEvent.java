package com.juan.adx.model.dsp.vivo.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/27 11:41
 */
public enum VivoAdEvent {

    AD_IMPRESSION(2, "广告曝光事件"),
    AD_CLICK(3, "广告点击事件"),
    VIDEO_AD_START(5, "视频广告开始播放事件"),
    VIDEO_AD_PLAY_25(6, "视频广告播放 25%"),
    VIDEO_AD_PLAY_50(7, "视频广告播放 50%"),
    VIDEO_AD_PLAY_75(8, "视频广告播放 75%"),
    VIDEO_AD_COMPLETE(9, "视频广告播放完成事件"),
    DEEPLINK_TRIGGER(21, "deeplink 调起事件"),
    DOWNLOAD_EVENT(10, "下载事件"),
    MINI_PROGRAM_TRIGGER(24, "小程序调起事件");

    @Getter
    @Setter
    private int value;
    @Getter
    @Setter
    private String description;

    VivoAdEvent(int value, String description) {
        this.value = value;
        this.description = description;
    }

}
