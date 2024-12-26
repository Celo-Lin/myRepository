package com.juan.adx.model.ssp.qutt.response;

import lombok.Data;

/**
 * @Author: Kevin.赵伟凯
 * @CreateTime: 2024-05-18 15:44
 * @Description:
 * @Version: 1.0
 */

@Data
public class QuttSspRespImage {

    /**
     * 图⽚素材类型。⻅附录ImgType
     * 是
     */
    private Integer type;

    /*否
    图⽚宽，单位：像素
     */
    private Integer width;
    /*否
    图⽚⾼，单位：像素
     */
    private Integer height;

    /**
     * 是
     * 图⽚地址
     */
    private String src;

}
