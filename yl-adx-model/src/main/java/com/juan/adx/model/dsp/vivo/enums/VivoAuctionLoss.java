package com.juan.adx.model.dsp.vivo.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/27 15:25
 */
public enum VivoAuctionLoss {

    INSUFFICIENT_COMPETITIVENESS(1, "竞争力不足"),
    TIMEOUT(2, "返回超时"),
    INVALID_RESPONSE(3, "报文不符要求"),
    OTHER(10001, "其他");

    @Getter
    private  int value;

    @Getter
    private  String description;

    VivoAuctionLoss(int value, String description) {
        this.value = value;
        this.description = description;
    }

}
