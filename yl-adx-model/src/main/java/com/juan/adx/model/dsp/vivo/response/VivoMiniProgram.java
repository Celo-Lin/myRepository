package com.juan.adx.model.dsp.vivo.response;

import lombok.Data;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/25 16:36
 */
@Data
public class VivoMiniProgram {

    /** 小程序广告原始 id */
    private String originId;

    /** 拉起小程序页面的可带参数路径 */
    private String path;

}
