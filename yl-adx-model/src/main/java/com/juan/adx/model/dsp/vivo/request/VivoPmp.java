package com.juan.adx.model.dsp.vivo.request;

import lombok.Data;

import java.util.List;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/25 16:05
 */
@Data
public class VivoPmp {

    /**
     * PMP 交易的具体订单信息，详情见 Deal。订单信息请
     * 事先找 vivo 联盟运营沟通，请勿单方变更订单信息，否
     * 则，导致停投或者数据 gap 由媒体侧承担。
     */
    private List<VivoDeal> deals;

}
