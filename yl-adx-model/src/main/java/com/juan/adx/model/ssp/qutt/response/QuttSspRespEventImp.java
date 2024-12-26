package com.juan.adx.model.ssp.qutt.response;

import lombok.Data;

import java.util.List;

/**
 * @Author: Kevin.赵伟凯
 * @CreateTime: 2024-05-19 7:39
 * @Description:
 * @Version: 1.0
 */

@Data
public class QuttSspRespEventImp {

    /**
     * 视频播放相关事件类型，⻅附录
     * 视频播放事件
     * 是
     */
    private String event;

    /**
     * 是
     * 视频播放相关事件的监控地址
     */
    private List<String> imps;
}
