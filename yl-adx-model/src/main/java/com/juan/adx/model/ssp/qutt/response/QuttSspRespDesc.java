package com.juan.adx.model.ssp.qutt.response;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: Kevin.赵伟凯
 * @CreateTime: 2024-05-18 15:44
 * @Description: DescFormat
 * @Version: 1.0
 */

@Data
@AllArgsConstructor
public class QuttSspRespDesc  {

    /**
     * 否
     * 描述
     */
    private String desc;

    /**
     * 否
     * 子描述
     */
    @JSONField(name = "sub_desc")
    private String subDesc;

}
