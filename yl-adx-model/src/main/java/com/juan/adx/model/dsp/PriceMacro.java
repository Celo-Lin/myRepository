package com.juan.adx.model.dsp;

import lombok.Data;

/**
 * @Author: cao fei
 * @Date: created in 3:57 下午 2022/6/13
 */
@Data
public class PriceMacro {
    private String macro;
    private boolean contains;

    private PriceMacro() {

    }

    public PriceMacro(boolean contains, String macro) {
        this.contains = contains;
        this.macro = macro;
    }

}
