package com.juan.adx.model.ssp.common.response;

import lombok.Data;

@Data
public class SspRespVideo {
    /**
     * 视频播放地址
     */
    private String url;
    
    /**
     * 视频封面地址
     */
    private String coverUrl;
    
    /**
     * 视频总时长,单位：秒
     */
    private Integer duration;
    
    /**
     * 视频强制播放时间, 单位:秒
     * 一般在激励视频中会有,代表播放到这个时间后,才会有激励成功
     */
    private Integer forceDuration;
    
    /**
     * 视频大小,单位：字节(byte)
     */
    private Integer size;
    
    /**
     * 视频宽度,单位：px(视频广告通用)
     */
    private Integer width;
    
    /**
     * 视频高度,单位：px(视频广告通用)
     */
    private Integer height;
    
    /**
     * 视频播放完成后，需要展示的资源类型：
     * 0：无
     * 1：需要展示的图片地址
     * 2：需要展示的落地页地址
     * 3：需要使用webview渲染的HTML代码
     */
    private Integer endUrlType;
    
    /**
     * 视频播放完成后，需要加载的资源，加载类型参见endurlType参数
     */
    private String endUrl;

}
