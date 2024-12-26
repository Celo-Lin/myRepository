package com.juan.adx.model.ssp.xunfei.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/24 14:20
 */
public enum XunfeiTemplate {

    TEMP_HTML(4, 1, "html"),

    TEMP_PHOTO(2, 3, "图片"),

    TEMP_VIDEO(5, 5, "视频"),

    TEMP_ONE_ONE_GRAPHIC(3, 6, "1图1文"),

    TEMP_ONE_TWO_GRAPHIC(3, 7, "1图2文"),

    TEMP_THREE_ONE_GRAPHIC(3, 10, "3图1文"),

    TEMP_ONE_ONE_ONE_VIDEO_GRAPHIC(3, 13, "1视频1图1文"),

    TEMP_ONE_ONE_TWO_VIDEO_GRAPHIC(3, 48, "1视频1图2文"),

    UNKNOW(0, 0, "未知");


    /**
     * 讯飞创意模版id
     */
    @Getter
    @Setter
    private int materialType;

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


    XunfeiTemplate( int materialType,int templateId, String templateName) {
        this.templateId = templateId;
        this.templateName = templateName;
        this.materialType = materialType;
    }

    public static XunfeiTemplate getByTemplateId(Integer templateId) {
        if (templateId == null) {
            return UNKNOW;
        }
        for (XunfeiTemplate advertType : values()) {
            if (advertType.getTemplateId() == templateId.intValue()) {
                return advertType;
            }
        }
        return UNKNOW;
    }


}
