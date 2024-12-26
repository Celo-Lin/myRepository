package com.juan.adx.model.ssp.qutt.request;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * @Author: Kevin.赵伟凯
 * @CreateTime: 2024-05-18 15:44
 * @Description: DescFormat
 * @Version: 1.0
 */

@Data
public class QutSspReqtDescFormat {

    /*
    描述最⼤⻓度，单位：字节，0表示不限制
     */
    @JSONField(name = "desc_max_len")
    private Integer descMaxLen;

    /*
    ⼦描述最⼤⻓度，单位：字节，0表示不限制
     */
    @JSONField(name = "sub_desc_max_len")
    private Integer subDescMaxLen;

}
