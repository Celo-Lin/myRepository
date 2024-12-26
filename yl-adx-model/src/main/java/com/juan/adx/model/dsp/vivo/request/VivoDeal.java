package com.juan.adx.model.dsp.vivo.request;

import lombok.Data;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/25 16:05
 */
@Data
public class VivoDeal {
    /** PMP交易订单ID */
    private String id;

    /** PMP交易价格，单位：分/CPM */
    private Long bidFloor;
}
