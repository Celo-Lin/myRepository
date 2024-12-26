package com.juan.adx.model.ssp.yoyo.enums;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * * <pre>
 *  * 广告操作行为：
 *  *  0：无交互
 *  *  1：应用内WebView打开落地页地址 (落地页)
 *  *  2：使用系统浏览器打开落地页地址 (落地页)
 *  *  3：Deeplink 唤醒
 *  *  4：点击后下载，仅出现在Android设备 (下载)
 *  *  5：打开微信小程序
 *  *  6：广点通下载
 *  * </pre>
 * <pre>
 * 支持交互类型 :
 * 1=落地页，2=应用下载，3=dp+落地页，4=dp+应用下载，5=微信小程序，6=微信小游戏，7=快应用；
 * 不同的广告位/模板支持的交互类型不相同. 同时支持多种交互方式时, 多个值间使用,分隔, 例如: 1,3,4
 *
 * </pre>
 */
public enum YoYoInteractionType {


    INTERACTIONTYPE_LANDDOWN(Lists.newArrayList(1, 2), 1, "落地页"),
    INTERACTIONTYPE_LANDDOWN_DP(Lists.newArrayList(3), 3, "dp+落地页"),

    INTERACTIONTYPE_DOWNLOAD(Lists.newArrayList(4, 6), 2, "下载 dp类统⼀也算下载"),
    INTERACTIONTYPE_DOWNLOAD_DP(Lists.newArrayList(3), 4, "下载 dp类统⼀也算下载"),

    INTERACTIONTYPE_MINI_PROGRAM(Lists.newArrayList(5), 5, "微信⼩程序"),
    INTERACTIONTYPE_MINI_PROGRAM_GAME(Lists.newArrayList(5), 6, "微信⼩程序"),

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

    YoYoInteractionType(List<Integer> type, int sspType, String desc) {
        this.type = type;
        this.sspType = sspType;
        this.desc = desc;
    }

    public static YoYoInteractionType getBySspType(Integer sspType) {
        if (sspType == null) {
            return INTERACTIONTYPE_LANDDOWN;
        }
        for (YoYoInteractionType advertType : values()) {
            if (advertType.getSspType() == sspType) {
                return advertType;
            }
        }
        return INTERACTIONTYPE_LANDDOWN;
    }

    public static YoYoInteractionType getByType(Integer type) {
        if (type == null) {
            return INTERACTIONTYPE_LANDDOWN;
        }
        for (YoYoInteractionType advertType : values()) {
            if (advertType.getType().contains(type)) {
                return advertType;
            }
        }
        return INTERACTIONTYPE_LANDDOWN;
    }

}
