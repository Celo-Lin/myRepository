package com.juan.adx.model.ssp.xunfei.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/23 11:22
 */
public enum XunfeiActionType {

    NON_INREACTION(0, 1, "无交互"),

    WEBVIEW (1, 2, "应用内WebView打开落地页地址 (落地页)"),

	SYSTEM_BROWSER(2, 2, "使用系统浏览器打开落地页地址 (落地页)"),

    DEEPLINK(3, 2, "Deeplink 唤醒"),

    DOWNLOAD(4, 3, "点击后下载，仅出现在 Android 设备"),

    WECHAT_MINI_PROGRAM(5, 9, "打开微信小程序");

    @Getter
    @Setter
    private int type;

    @Getter
    @Setter
    private int sspType;

    @Getter
    @Setter
    private String desc;

    XunfeiActionType(int type, int sspType, String desc) {
        this.type = type;
        this.sspType = sspType;
        this.desc = desc;
    }

    public static XunfeiActionType getByType(Integer type){
        if(type == null){
            return NON_INREACTION;
        }
        for (XunfeiActionType advertType : values()) {
            if(advertType.getType() == type.intValue()){
                return advertType;
            }
        }
        return NON_INREACTION;
    }

}
