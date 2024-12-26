package com.juan.adx.model.ssp.yoyo.enums;

import com.juan.adx.model.enums.OsType;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * 操作系统类型：
 * 	0：Unknown
 * 	1：Android
 * 	2：IOS
 * 	3：Windows
 * </pre>
 */
public enum YoYoOsType {

    UNKNOWN(OsType.UNKNOWN.getType(), 0, "Unknown"),

    IOS(OsType.IOS.getType(), 1, "IOS"),

    IOS_MAC(OsType.IOS.getType(), 4, "IOS"),

    ANDROID(OsType.ANDROID.getType(), 2, "Android"),

    WINDOWS(OsType.WINDOWS.getType(), 3, "Windows");

    @Getter
    @Setter
    private int type;

    @Getter
    @Setter
    private int sspType;

    @Getter
    @Setter
    private String desc;

    YoYoOsType(int type, int sspType, String desc) {
        this.type = type;
        this.sspType = sspType;
        this.desc = desc;
    }

    public static YoYoOsType getBySspType(String sspType) {
        if (sspType == null) {
            return YoYoOsType.UNKNOWN;
        }
        for (YoYoOsType osType : values()) {
            if (String.valueOf(osType.getSspType()).equals(sspType)) {
                return osType;
            }
        }
        return YoYoOsType.UNKNOWN;
    }


}
