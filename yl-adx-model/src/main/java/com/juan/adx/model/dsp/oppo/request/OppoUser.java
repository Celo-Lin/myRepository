package com.juan.adx.model.dsp.oppo.request;
import lombok.Data;
import java.util.List;

@Data
public class OppoUser {
    /**
     * <pre>
     * installedAppPkgList: 应用安装列表
     * </pre>
     */
    private List<String> installedAppPkgList;

    /**
     * <pre>
     * keyword: 搜索关键词（搜索场景字段，使用前与商务沟通）
     * </pre>
     */
    private String keyword;

    /**
     * <pre>
     * targetPkgName: 目标应用包名（使用前与商务沟通）
     * </pre>
     */
    private String targetPkgName;

    /**
     * <pre>
     * minTargetAppVersionCode: 目标应用最低版本号（使用前与商务沟通）
     * </pre>
     */
    private Long minTargetAppVersionCode;
}
