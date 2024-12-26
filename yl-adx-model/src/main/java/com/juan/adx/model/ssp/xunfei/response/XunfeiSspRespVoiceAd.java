package com.juan.adx.model.ssp.xunfei.response;

import lombok.Data;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/22 9:46
 */
@Data
public class XunfeiSspRespVoiceAd {

    /**
     * 语音互动广告地址
     */
    private String url;

    /**
     * 语音广告宽度
     */
    private Integer width;

    /**
     * 语音广告高度
     */
    private Integer height;

    private Object ext;

}
