package com.juan.adx.model.dsp.youdao.response;

import lombok.Data;

import java.util.List;

/**
 * 激励视频-播放进度追踪
 *
 * @author： HeWen Zhou
 * @date： 2024/5/16 10:34
 */
@Data
public class YouDaoRewardedVideoplaypercentage {

    /**
     * 视频播放进度
     */
    private Double checkpoint;
    /**
     * 客户端在视频播放进度
     * 为 checkpoint 时上报对应的所有 URLs
     */
    private List<String> urls;

}
