package com.juan.adx.model.ssp.qutt.request;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * @Author: Kevin.赵伟凯
 * @CreateTime: 2024-05-18 15:44
 * @Description:
 * @Version: 1.0
 */

@Data
public class QuttSspReqVideo {

    /*
   视频素材类型
     */
    private Integer type;

    /*
    视频宽⾼⽐
     */
    @JSONField(name = "aspect_ratio")
    private Integer aspectRatio;

}