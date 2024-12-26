package com.juan.adx.model.dsp.haoya.response;
import lombok.Data;

@Data
public class HaoYaAction {
    /**
     * <pre>
     * type: 点击广告后的交互类型，1-H5、2-下载、3-唤醒、4-微信小程序
     * </pre>
     */
    private Integer type;

    /**
     * <pre>
     * app: 下载类广告推荐填写，详见AppInfo对象
     * </pre>
     */
    private HaoYaAppInfo app;

    /**
     * <pre>
     * deeplink: 应用内地址唤醒
     * </pre>
     */
    private String deeplink;

    /**
     * <pre>
     * universalLink: iOS universalLink
     * </pre>
     */
    private String universalLink;

    /**
     * <pre>
     * miniProgram: 调起小程序，详见MiniProgram对象
     * </pre>
     */
    private HaoYaMiniProgram miniProgram;
}
