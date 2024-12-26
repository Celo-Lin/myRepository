package com.juan.adx.model.dsp.oppo.request;
import lombok.Data;

@Data
public class OppoDspRequestParam {
    /**
     * <pre>
     * apiVersion: 协议版本，必填：1
     * </pre>
     */
    private Integer apiVersion;

    /**
     * <pre>
     * apiVc: 联盟 API 版本号，作兼容使用(由 OPPO 这边分配)
     * </pre>
     */
    private Integer apiVc;

    /**
     * <pre>
     * appStoreVc: 商店版本号
     * </pre>
     */
    private Integer appStoreVc;

    /**
     * <pre>
     * appInfo: 应用信息，见 APPInfo Object
     * </pre>
     */
    private OppoAppInfo appInfo;

    /**
     * <pre>
     * posInfo: 广告位信息，见 PosInfo Object
     * </pre>
     */
    private OppoPosInfo posInfo;

    /**
     * <pre>
     * devInfo: 设备信息，见 DevInfo Object
     * </pre>
     */
    private OppoDeviceInfo devInfo;

    /**
     * <pre>
     * user: 用户信息，见 User Object
     * </pre>
     */
    private OppoUser user;
}
