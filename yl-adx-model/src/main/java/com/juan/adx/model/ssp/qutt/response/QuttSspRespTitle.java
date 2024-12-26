package com.juan.adx.model.ssp.qutt.response;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: Kevin.赵伟凯
 * @CreateTime: 2024-05-18 23:10
 * @Description:
 * @Version: 1.0
 */

@Data
public class QuttSspRespTitle {

    /**
     * 否
     * 标题
     */
    private String title;

    /**
     * 否
     * 子标题
     */
    @JSONField(name = "sub_title")
    private String subTitle;

    public  QuttSspRespTitle(String title) {
        this.title = title;
    }
}
