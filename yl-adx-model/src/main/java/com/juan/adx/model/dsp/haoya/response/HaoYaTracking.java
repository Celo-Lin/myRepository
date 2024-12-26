package com.juan.adx.model.dsp.haoya.response;
import lombok.Data;
import java.util.List;

@Data
public class HaoYaTracking {
    /**
     * <pre>
     * impUrls: 曝光监测
     * </pre>
     */
    private List<String> impUrls;

    /**
     * <pre>
     * clickUrls: 点击监测
     * </pre>
     */
    private List<String> clickUrls;

    /**
     * <pre>
     * videoStartUrls: 视频开始播放时上报
     * </pre>
     */
    private List<String> videoStartUrls;

    /**
     * <pre>
     * videoFirstQUrls: 视频播放 25% 时上报
     * </pre>
     */
    private List<String> videoFirstQUrls;

    /**
     * <pre>
     * videoMidUrls: 视频播放 50% 时上报
     * </pre>
     */
    private List<String> videoMidUrls;

    /**
     * <pre>
     * videoThirdQUrls: 视频播放 75% 时上报
     * </pre>
     */
    private List<String> videoThirdQUrls;

    /**
     * <pre>
     * videoFinUrls: 视频播放完成时上报
     * </pre>
     */
    private List<String> videoFinUrls;

    /**
     * <pre>
     * downStartUrls: 仅用于下载类广告，开始下载时上报
     * </pre>
     */
    private List<String> downStartUrls;

    /**
     * <pre>
     * downFinUrls: 仅用于下载类广告，下载完成时上报
     * </pre>
     */
    private List<String> downFinUrls;

    /**
     * <pre>
     * downInstallStartUrls: 仅用于下载类广告，开始安装时上报
     * </pre>
     */
    private List<String> downInstallStartUrls;

    /**
     * <pre>
     * downInstallUrls: 仅用于下载类广告，安装完成时上报
     * </pre>
     */
    private List<String> downInstallUrls;

    /**
     * <pre>
     * dpSuccessUrls: 仅用于唤起类广告，唤醒成功时上报
     * </pre>
     */
    private List<String> dpSuccessUrls;

    /**
     * <pre>
     * dpFailedUrls: 仅用于唤起类广告，唤醒失败时上报
     * </pre>
     */
    private List<String> dpFailedUrls;

    /**
     * <pre>
     * miniSuccessUrls: 仅用于小程序唤起类广告，唤醒成功时上报
     * </pre>
     */
    private List<String> miniSuccessUrls;

    /**
     * <pre>
     * miniFailedUrls: 仅用于小程序唤起类广告，唤醒失败时上报
     * </pre>
     */
    private List<String> miniFailedUrls;
}
