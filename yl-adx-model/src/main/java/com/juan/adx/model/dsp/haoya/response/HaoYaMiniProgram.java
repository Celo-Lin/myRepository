package com.juan.adx.model.dsp.haoya.response;
import lombok.Data;

@Data
public class HaoYaMiniProgram {
    /**
     * <pre>
     * appId: 移动应用的 ID
     * </pre>
     */
    private String appId;

    /**
     * <pre>
     * username: 所需跳转的小程序原始 ID（以"gh_"开头）
     * </pre>
     */
    private String username;

    /**
     * <pre>
     * path: 推荐，所需跳转的小程序内页面路径及参数，不填默认拉起小程序主页。
     * </pre>
     */
    private String path;

    /**
     * <pre>
     * minitype: 所需跳转的小程序类型，0=正式版、1=开发版、2=体验版
     * </pre>
     */
    private Integer minitype;
}
