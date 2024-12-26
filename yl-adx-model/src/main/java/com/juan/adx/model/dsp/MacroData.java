package com.juan.adx.model.dsp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: cao fei
 * @Date: created in 09:53 2023/9/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MacroData {
    /**
     * 广告请求链路id, url安全参数
     */
    private String urlSafeTraceId;
    /**
     * 广告请求包名, url安全参数
     */
    private String urlSafePackageName;
    /**
     * 天目广告位id, url安全参数
     */
    private String urlSafeAdPositionId;
}
