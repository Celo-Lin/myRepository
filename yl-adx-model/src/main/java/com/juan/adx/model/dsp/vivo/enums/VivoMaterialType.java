package com.juan.adx.model.dsp.vivo.enums;

import com.juan.adx.model.enums.MaterialType;
import lombok.Getter;
import lombok.Setter;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/27 10:49
 */
public enum VivoMaterialType {

    BIG_IMAGE(2, 1,"大图"),

    SMALL_IMAGE(2, 2,"小图"),

    IMAGE_LIST(2,3, "组图"),

    IMAGE(2,4, "纯图片广告"),

    VIDEO_IMAGE(0, 5,"视频+前导图"),

    VIDEO(5, 7,"全屏视频"),

    VERTICAL_VIDEO_IMAGE(0, 10,"竖版视频+前导图"),

    ICON(0, 20,"纯 icon（应用下载类图标）"),

    UNKNOWN(0, 0,"未知");

    @Getter
    @Setter
    private Integer type;

    @Getter
    @Setter
    private Integer dspType;

    @Getter
    @Setter
    private String desc;

    VivoMaterialType(Integer type, Integer dspType,String desc) {
        this.type = type;
        this.desc = desc;
        this.dspType = dspType;
    }

    public static VivoMaterialType getByDspType(Integer type){
        if(type == null){
            return UNKNOWN;
        }
        for (VivoMaterialType materialType : values()) {
            if(materialType.getType() == type.intValue()){
                return materialType;
            }
        }
        return UNKNOWN;
    }

}
