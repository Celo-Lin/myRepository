package com.juan.adx.model.ssp.xunfei.request;

import lombok.Data;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/22 9:38
 */
@Data
public class XunfeiSspReqCaid {

    /**
     * 广协版本号，如：20220111
     */
    private String ver;

    /**
     * 广协 caid，如：
     * 37b0aa6964ac38586ae105792b44c0ae
     */
    private String caid;

    private Object ext;

}
