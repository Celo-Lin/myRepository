package com.juan.adx.model.dsp.vivo.response;

import lombok.Data;

import java.util.List;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/25 16:28
 */
@Data
public class VivoDspResponseParam {
    /** 返回码 */
    private Integer code;

    /** 解释code返回码含义 */
    private String msg;

    /** 广告数据数组 */
    private List<VivoAdDTO> data;

}
