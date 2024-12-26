package com.juan.adx.model.dsp.oppo.request;
import lombok.Data;

@Data
public class OppoAppInfo {
    /**
     * <pre>
     * appId: 联盟应用 ID.
     * </pre>
     */
    private String appId;

    /**
     * <pre>
     * pkgname: 应用唯一标示（即包名）
     * </pre>
     */
    private String pkgname;

    /**
     * <pre>
     * verName: 应用版本名称(接入应用的版本名称)
     * </pre>
     */
    private String verName;
}
