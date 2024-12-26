package com.juan.adx.model.ssp.qutt.response;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @Author: Kevin.赵伟凯
 * @CreateTime: 2024-05-18 15:44
 * @Description:
 * @Version: 1.0
 */

@Data
public class QuttSspRespVideo {

    /**是
     视频素材类型。⻅附录
     VideoType
     */
    private Integer type;

    /**
     * 视频宽，不传视频可能显示不正
     * 确。单位：像素
     * 是
     */
    private Integer width;

    /**
     * 视频⾼，不传视频可能显示不正
     * 确。单位：像素
     * 是
     */
    private Integer height;

    /**
     * 是
     * 视频时⻓，单位：秒
     */
    private Integer duration;

    /**
     * 是
     * 视频地址
     */
    private String src;

    /**
     * 视频播放相关监控
     * 否
     */
    @JSONField(name = "event_imp")
    private List<QuttSspRespEventImp> eventImp;
}
