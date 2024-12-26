package com.juan.adx.model.ssp.qutt.request;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * @Author: Kevin.赵伟凯
 * @CreateTime: 2024-05-18 15:44
 * @Description: 标题相关条件
 * @Version: 1.0
 */

@Data
public class QuttSspReqTitleFormat {

    /*
    标题最⼤⻓度，单位：字节，0表示不需要，-1表示不限制
     */
    @JSONField(name = "title_max_len")
    private Integer titleMaxLen;

    /*
    ⼦标题最⼤⻓度，单位：字节，0表示不需要，-1表示不限制
     */
    @JSONField(name = "sub_title_max_len")
    private Integer subTitleMaxLen;

}
