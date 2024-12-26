package com.juan.adx.model.dsp.yidian.request;

import lombok.Data;

/**
 * @author caoliwu
 * @version 1.0
 * @ClassName YiDianApp
 * @description: TODO
 * @date 2024/5/28 9:52
 */
@Data
public class YiDianApp {
    /**
     * <pre>
     * app 名称
     * </pre>
     */
    private String name;

    /**
     * <pre>
     * Android 包名，iOS 的 APP ID
     * </pre>
     */
    private String bundle;

    /**
     * <pre>
     * app 版本号
     * </pre>
     */
    private String ver;
}
