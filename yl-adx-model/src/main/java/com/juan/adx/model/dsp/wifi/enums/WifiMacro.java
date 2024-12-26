package com.juan.adx.model.dsp.wifi.enums;

import com.juan.adx.model.entity.api.MacroParamterMapping;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/15 16:51
 */
public enum WifiMacro {

    POS_DOWN_X("__MF_POS_DOWN_X__", "__DOWN_X__", ""),
    POS_DOWN_Y("__MF_POS_DOWN_Y__", "__DOWN_Y__", ""),
    POS_UP_X("__MF_POS_UP_X__", "__UP_X__", ""),
    POS_UP_Y("__MF_POS_UP_Y__", "__UP_Y__", ""),
    EVENT_TIME_MS("__MF_EVENT_TIME_MS__", "__STIME__", ""),
    SLD("__MF_AIT__", "__SLD__", "");


    @Getter
    @Setter
    private String macro;

    @Getter
    @Setter
    private String dspMacro;

    @Getter
    @Setter
    private String desc;


    private static List<MacroParamterMapping> macroParamterMappings;

    static {
        macroParamterMappings = new ArrayList<MacroParamterMapping>();
        for (WifiMacro dspMacro : values()) {
            MacroParamterMapping mapping = new MacroParamterMapping();
            mapping.setMacro(dspMacro.getMacro());
            mapping.setPartnerMacro(dspMacro.getDspMacro());
            macroParamterMappings.add(mapping);
        }
    }

    WifiMacro(String macro, String dspMacro, String desc) {
        this.macro = macro;
        this.dspMacro = dspMacro;
        this.desc = desc;
    }

    /**
     * 返回一个不可被修改的只读列表
     */
    public static List<MacroParamterMapping> getMacroParamterMappings() {
        return Collections.unmodifiableList(macroParamterMappings);
    }


}
