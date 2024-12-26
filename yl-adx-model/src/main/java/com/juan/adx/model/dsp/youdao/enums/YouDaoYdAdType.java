package com.juan.adx.model.dsp.youdao.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/17 11:03
 */

public enum YouDaoYdAdType {

    WEBVIEW(0, "落地页广告"),
    DOWNLOAD(1, "下载类型广告");

    @Getter
    @Setter
    private int type;

    @Getter
    @Setter
    private String desc;

    YouDaoYdAdType(int type,  String desc) {
        this.type = type;
        this.desc = desc;
    }

}
