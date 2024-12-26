package com.juan.adx.model.dsp.youdao.response;

import lombok.Data;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/16 9:46
 */
@Data
public class YouDaoWxMiniProgram {

    /**
     * 应用 appid
     */
    private String appid;

    /**
     * 小程序原始 id
     */
    private String originid;

    /**
     * 小程序路径
     */
    private String path;

}
