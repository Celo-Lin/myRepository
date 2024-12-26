package com.juan.adx.model.dsp.yidian.request;

import lombok.Data;

/**
 * @author caoliwu
 * @version 1.0
 * @ClassName YiDianExt
 * @description: TODO
 * @date 2024/5/28 9:53
 */
@Data
public class YiDianExt {
    /**
     * <pre>
     * 媒体方是否支持 deeplink 吊起 true 支持，false 不支持，默认不支持  选填
     * </pre>
     */
    private Boolean deeplinkSupported;
}
