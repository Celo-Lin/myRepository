package com.juan.adx.model.dsp.vivo.request;

import lombok.Data;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/25 16:06
 */
@Data
public class VivoNetwork {

    /** 联网方式，参见附录-网络连接类型。不填将无广告返回；填未知，会严重影响流量变现效果。 */
    private Integer connectType;

    /** 运营商，参见附录-运营商类型。 */
    private Integer carrier;

    /** 运营商识别码 */
    private String mccmnc;

}
