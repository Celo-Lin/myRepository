package com.juan.adx.model.dsp.yidian.response;

import lombok.Data;

/**
 * @author caoliwu
 * @version 1.0
 * @ClassName YiDianMiniProgram
 * @description: TODO
 * @date 2024/5/28 11:33
 */
@Data
public class YiDianMiniProgram {
    /**
     * <pre>
     * 小程序名称    选填
     * </pre>
     */
    private String mpUserName;

    /**
     * <pre>
     * 小程序路径    选填
     * </pre>
     */
    private String mpPath;
}
