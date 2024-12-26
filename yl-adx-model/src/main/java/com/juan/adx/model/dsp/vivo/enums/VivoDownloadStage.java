package com.juan.adx.model.dsp.vivo.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/27 15:06
 */
public enum VivoDownloadStage {

    START_DOWNLOAD(1, "开始下载"),
    DOWNLOAD_COMPLETE(2, "下载完成"),
    START_INSTALL(3, "开始安装"),
    INSTALL_COMPLETE(4, "安装完成");

    @Getter
    @Setter
    private  int value;

    @Getter
    @Setter
    private  String description;

    VivoDownloadStage(int value, String description) {
        this.value = value;
        this.description = description;
    }


}
