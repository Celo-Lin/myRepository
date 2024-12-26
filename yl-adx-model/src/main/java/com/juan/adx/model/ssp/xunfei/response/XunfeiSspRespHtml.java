package com.juan.adx.model.ssp.xunfei.response;

import lombok.Data;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/22 9:46
 */
@Data
public class XunfeiSspRespHtml {

    /**
     * 完整的 Html 内容
     */
    private String adm;

    /**
     * HTML 宽度
     */
    private Integer width;

    /**
     * HTML 高度
     */
    private Integer height;

    private Object ext;
}
