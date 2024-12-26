package com.juan.adx.model.dsp.vivo.enums;

import com.juan.adx.model.dsp.oppo.enums.OppoCarrierType;
import lombok.Getter;
import lombok.Setter;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/25 17:37
 */
public enum VivoGender {

    M(1, "M", "男"),

    F(2, "F", "女"),

    O(0, "O", "其他");


    @Getter
    @Setter
    private int type;

    @Getter
    @Setter
    private String dspType;

    @Getter
    @Setter
    private String desc;

    VivoGender(int type, String dspType, String desc) {
        this.type = type;
        this.dspType = dspType;
        this.desc = desc;
    }

    public static VivoGender get(Integer type){
        if(type == null){
            return O;
        }
        for (VivoGender carrierType : values()) {
            if(carrierType.getType() == type.intValue()){
                return carrierType;
            }
        }
        return O;
    }

}
