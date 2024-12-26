package com.juan.adx.model.dsp.yidian.enums;

import com.juan.adx.model.entity.api.MacroParamterMapping;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author caoliwu
 * @version 1.0
 * @ClassName YiDianMacro
 * @description: TODO
 * @date 2024/5/29 14:17
 */
public enum YiDianMacro {

    TS_MS("__MF_EVENT_TIME_MS__", "__TS__", "执行上报操作时客户端的时间戳, 单位：毫秒");

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
        for (YiDianMacro dspMacro : values()) {
            MacroParamterMapping mapping = new MacroParamterMapping();
            mapping.setMacro(dspMacro.getMacro());
            mapping.setPartnerMacro(dspMacro.getDspMacro());
            macroParamterMappings.add(mapping);
        }
    }

    YiDianMacro(String macro, String dspMacro, String desc) {
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
