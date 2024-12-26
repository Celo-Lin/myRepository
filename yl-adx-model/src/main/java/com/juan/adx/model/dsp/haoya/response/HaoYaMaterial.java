package com.juan.adx.model.dsp.haoya.response;
import lombok.Data;
import java.util.List;

@Data
public class HaoYaMaterial {
    /**
     * <pre>
     * title: 广告标题
     * </pre>
     */
    private String title;

    /**
     * <pre>
     * description: 广告描述
     * </pre>
     */
    private String description;

    /**
     * <pre>
     * btn: 广告按钮文字
     * </pre>
     */
    private String btn;

    /**
     * <pre>
     * brand: 品牌描述
     * </pre>
     */
    private String brand;

    /**
     * <pre>
     * images: 广告图片素材，图文或纯图时必填，详见Image对象
     * </pre>
     */
    private List<HaoYaImage> images;

    /**
     * <pre>
     * video: 广告视频素材，视频广告必填，详见Video对象
     * </pre>
     */
    private HaoYaVideo video;

    /**
     * <pre>
     * icon: 广告 icon 图片，详见Image对象
     * </pre>
     */
    private HaoYaImage icon;
}
