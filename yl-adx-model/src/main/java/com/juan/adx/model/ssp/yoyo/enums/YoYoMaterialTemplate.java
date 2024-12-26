package com.juan.adx.model.ssp.yoyo.enums;

import com.google.common.collect.Lists;
import com.juan.adx.model.enums.AdvertType;
import com.juan.adx.model.enums.OrientationType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <pre>
 *
 * 期望返回的广告物料类型：
 * 0：未知
 * 1：纯文字广告
 * 2：纯图片广告
 * 3：图文广告
 * 4：HTML 广告
 * 5：视频广告
 * 6：音频广告
 * </pre>
 */
public enum YoYoMaterialTemplate {

    BANNER(AdvertType.BANNER.getType(), Lists.newArrayList("2.1.1", "2.2.4", "2.1.5"), OrientationType.UNKNOWN.getType(), "横幅"),
    INTERSTITIAL_PORTRAIT(AdvertType.INTERSTITIAL.getType(), Lists.newArrayList("4.2.2", "4.3.1"), OrientationType.PORTRAIT.getType(), "插屏_竖屏"),
    INTERSTITIAL_LANDSCAPE(AdvertType.INTERSTITIAL.getType(), Lists.newArrayList("4.2.1"), OrientationType.LANDSCAPE.getType(), "插屏_横屏"),
    SPLASH_PORTRAIT(AdvertType.SPLASH.getType(), Lists.newArrayList("3.1.1", "3.2.1"), OrientationType.PORTRAIT.getType(), "开屏_竖屏"),
    SPLASH_LANDSCAPE(AdvertType.SPLASH.getType(), Lists.newArrayList("3.1.6", "3.1.8"), OrientationType.LANDSCAPE.getType(), "开屏_横屏"),
    INFORMATION_STREAM_LANDSCAPE(AdvertType.INFORMATION_STREAM.getType(),
            Lists.newArrayList("1.1.1", "1.2.1", "1.2.11", "1.3.1", "1.5.1"), OrientationType.LANDSCAPE.getType(), "信息流_横屏"),
    INFORMATION_STREAM_PORTRAIT(AdvertType.INFORMATION_STREAM.getType(),
            Lists.newArrayList("1.2.9", "1.5.2"), OrientationType.PORTRAIT.getType(), "信息流_竖屏"),
    INFORMATION_STREAM_UNKNOWN(AdvertType.INFORMATION_STREAM.getType(),
            Lists.newArrayList("1.4.1", "1.4.2", "1.4.4"), OrientationType.UNKNOWN.getType(), "信息流_无方向"),
    REWARDED_VIDEO_LANDSCAPE(AdvertType.REWARDED_VIDEO.getType(), Lists.newArrayList("5.1.2"), OrientationType.LANDSCAPE.getType(), "激励视频_横屏"),
    REWARDED_VIDEO_PORTRAITE(AdvertType.REWARDED_VIDEO.getType(), Lists.newArrayList("5.2.2"), OrientationType.PORTRAIT.getType(), "激励视频_竖屏"),
    PATCH(AdvertType.PATCH.getType(), Lists.newArrayList("6.1.1"), OrientationType.UNKNOWN.getType(), "Push 贴片")
    ;

    @Getter
    @Setter
    private final Integer adType;
    @Getter
    @Setter
    private final Integer orientation;
    @Getter
    @Setter
    private List<String> templateId;
    @Getter
    @Setter
    private String desc;

    YoYoMaterialTemplate(int adType, List<String> templateId, Integer orientation, String desc) {
        this.adType = adType;
        this.templateId = templateId;
        this.orientation = orientation;
        this.desc = desc;
    }

    public static YoYoMaterialTemplate getByTemplateId(String templateId) {
        for (YoYoMaterialTemplate item : YoYoMaterialTemplate.values()) {
            if (item.getTemplateId().contains(templateId)) {
                return item;
            }
        }
        return null;
    }


}
