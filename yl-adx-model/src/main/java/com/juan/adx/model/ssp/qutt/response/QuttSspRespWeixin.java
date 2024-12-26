package com.juan.adx.model.ssp.qutt.response;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * @Author: Kevin.赵伟凯
 * @CreateTime: 2024-05-18 23:22
 * @Description:
 * @Version: 1.0
 */

@Data
public class QuttSspRespWeixin {

    /**
     * 否
     * 发布类型 0 正式 1 测试 2 预览
     */
    @JSONField(name = "release_type")
    private Integer releaseType;

    /**
     * 否
     * ⼩程序id
     */
    @JSONField(name = "program_id")
    private String programId;
    /**
     * 否
     * ⼩程序⻚⾯路径
     */
    @JSONField(name = "program_path")
    private String programPath;
}
