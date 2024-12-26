package com.juan.adx.model.ssp.xunfei.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/24 10:07
 */
public enum XunfeiCreativeTemplate {

    TEMP_762(762, 1, "html", 640, 100, 4, 1),

    TEMP_2(2, 3, "图片", 640, 960, 2, 3),
    TEMP_38(38, 3, "图片", 720, 1280, 2, 3),
    TEMP_196(196, 3, "图片", 1280, 720, 2, 4),
    TEMP_46(46, 3, "图片", 600, 500, 2, 2),
    TEMP_92(92, 3, "图片", 640, 100, 2, 1),
    TEMP_34(34, 3, "图片", 150, 150, 2, 4),

    TEMP_6(6, 5, "视频", 1280, 720, 5, 4),
    TEMP_18(18, 5, "视频", 640, 960, 5, 3),
    TEMP_228(228, 5, "视频", 720, 1280, 5, 3),

    TEMP_32(32, 6, "1图1文", 480, 320, 3, 4),
    TEMP_106(106, 6, "1图1文", 1280, 720, 3, 4),
    TEMP_150(150, 6, "1图1文", 640, 320, 3, 4),
    TEMP_838(838, 6, "1图1文", 150, 150, 3, 4),

    TEMP_74(74, 7, "1图2文", 480, 320, 3, 4),
    TEMP_104(104, 7, "1图2文", 1280, 720, 3, 4),
    TEMP_146(146, 7, "1图2文", 640, 320, 3, 4),
    TEMP_1110(1110, 7, "1图2文", 150, 150, 3, 4),

    TEMP_12(12, 10, "3图1文", 480, 320, 3, 4),
    TEMP_140(140, 10, "3图1文", 640, 320, 3, 4),
    TEMP_162(162, 10, "3图1文", 1280, 720, 3, 4),

    TEMP_58(58, 13, "1视频1图1文", 1280, 720, 0, 4),
    TEMP_664(664, 13, "1视频1图1文", 720, 1280, 0, 4),

    TEMP_542(542, 48, "1视频1图2文", 1280, 720, 0, 4),
    TEMP_726(726, 48, "1视频1图2文", 720, 1280, 0, 4);


    /**
     * 讯飞创意模版id
     */
    @Getter
    @Setter
    private int creativeTemplateId;

    /**
     * 讯飞模版id
     */
    @Getter
    @Setter
    private int templateId;

    /**
     * 讯飞模版名称
     */
    @Getter
    @Setter
    private String templateName;

    /**
     * 讯飞素材宽度
     */
    @Getter
    @Setter
    private int templateWidth;

    /**
     * 讯飞素材高度
     */
    @Getter
    @Setter
    private int templateHeight;

    /**
     * 期望返回的广告物料类型
     */
    @Getter
    @Setter
    private int materialType;

    /**
     * 有容广告位类型
     */
    @Getter
    @Setter
    private int slotType;

    XunfeiCreativeTemplate(int creativeTemplateId, int templateId, String templateName, int templateWidth, int templateHeight, int materialType, int slotType) {
        this.creativeTemplateId = creativeTemplateId;
        this.templateId = templateId;
        this.templateName = templateName;
        this.templateWidth = templateWidth;
        this.templateHeight = templateHeight;
        this.materialType = materialType;
        this.slotType = slotType;
    }

    public static XunfeiCreativeTemplate getBycreativeTemplateId(Integer creativeTemplateId) {
        if (creativeTemplateId == null) {
            return null;
        }
        for (XunfeiCreativeTemplate advertType : values()) {
            if (advertType.getCreativeTemplateId() == creativeTemplateId.intValue()) {
                return advertType;
            }
        }
        return null;
    }

}
