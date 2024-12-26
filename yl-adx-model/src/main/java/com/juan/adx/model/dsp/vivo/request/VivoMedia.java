package com.juan.adx.model.dsp.vivo.request;

import lombok.Data;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/25 16:06
 */
@Data
public class VivoMedia {

    /** 媒体 ID，在 vivo 联盟创建媒体时分配的 MediaID */
    private String mediaId;

    /** 应用包名，要与注册媒体时填写保持一致 */
    private String appPackage;

    /** 整型应用版本号 */
    private Integer appVersion;

}
