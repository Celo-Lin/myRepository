package com.juan.adx.model.ssp.qutt.enums;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <pre>
 *  交互类型（InteractionType）
 *  落地⻚打开 ：0
 * 下载 dp类统⼀也算下载： 1
 * 包括落地⻚和下载：  2
 * 微信⼩程序： 3
 *
 * </pre>
 */
public enum QuttInteractionType {

    INTERACTIONTYPE_SURFING(Lists.newArrayList(1, 2), 0, "落地⻚打开"),

    INTERACTIONTYPE_DOWNLOAD(Lists.newArrayList(3, 6), 1, "下载 dp类统⼀也算下载"),

    INTERACTIONTYPE_LANDDOWN(Lists.newArrayList(1, 2, 4), 2, "包括落地⻚和下载"),

    INTERACTIONTYPE_MINI_PROGRAM(Lists.newArrayList(5), 3, "微信⼩程序"),

    ;

    @Getter
    @Setter
    private List<Integer> type;

    @Getter
    @Setter
    private int sspType;

    @Getter
    @Setter
    private String desc;

    private QuttInteractionType(List<Integer> type, int sspType, String desc) {
        this.type = type;
        this.sspType = sspType;
        this.desc = desc;
    }

    public static QuttInteractionType getBySspType(Integer sspType) {
        if (sspType == null) {
            return INTERACTIONTYPE_LANDDOWN;
        }
        for (QuttInteractionType advertType : values()) {
            if (advertType.getSspType() == sspType) {
                return advertType;
            }
        }
        return INTERACTIONTYPE_LANDDOWN;
    }

    public static QuttInteractionType getByType(Integer type) {
        if (type == null) {
            return INTERACTIONTYPE_LANDDOWN;
        }
        for (QuttInteractionType advertType : values()) {
            if (advertType.getType().contains(type)) {
                return advertType;
            }
        }
        return INTERACTIONTYPE_LANDDOWN;
    }

}
