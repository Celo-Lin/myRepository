package com.juan.adx.model.dsp.vivo.enums;

import com.juan.adx.model.dsp.youdao.enums.YouDaoMacro;
import com.juan.adx.model.entity.api.MacroParamterMapping;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/27 11:14
 */
public enum VivoMacro {

    DPLINK_RESULT("__MF_DPLINK_RESULT__", "__DP_RESULT__", "deeplink 调起结果：0-成功、1-失败"),
    DPLINK_FAIL_RESULT("__MF_DPLINK_FAIL_RESULT__", "__DP_REASON__", "deeplink 调起失败原因：1-被拦截 2-应用未安装 3-调起异常"),
    EVENT_TIME_MS("__MF_EVENT_TIME_MS__", "__TS__", "执行上报操作时客户端的时间戳，单位：毫秒"),
    /*   WIDTH("", "__IP__", ""),*/
    AD_LT_X("-999", "__AD_LT_X__", "广告左上角坐标 x（注意宏首尾为双下划线）"),
    AD_LT_Y("-999", "__AD_LT_Y__", "广告左上角坐标 y（注意宏首尾为双下划线）"),
    AD_RB_X("-999", "__AD_RB_X__", "广告右下角坐标 x（注意宏首尾为双下划线）"),
    AD_RB_Y("-999", "__AD_RB_Y__", "广告右下角坐标 y（注意宏首尾为双下划线）"),
    CLICKAREA("__MF_CLICKAREA__", "__CLICKAREA__", "点击区域：1-广告素材、2-按钮"),
    POS_DP_DOWN_X("__MF_POS_DP_DOWN_X__", "__X__", "用户手指按下时的横坐标(相对坐标：以广告位左上角坐标为原点)"),
    POS_DP_DOWN_Y("__MF_POS_DP_DOWN_Y__", "__Y__", "用户手指按下时的纵坐标(相对坐标：以广告位左上角坐标为原点)"),
    PLAY_END_TIME("__MF_PLAY_END_TIME__", "__DURATION__", "视频播放结束时间，单位：秒。如果视频播放到结尾，则等于视频总时长"),
    CALL_UP_RESULT("", "__CALL_UP_RESULT__", "");

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
        macroParamterMappings = new ArrayList<>();
        for (VivoMacro dspMacro : values()) {
            MacroParamterMapping mapping = new MacroParamterMapping();
            mapping.setMacro(dspMacro.getMacro());
            mapping.setPartnerMacro(dspMacro.getDspMacro());
            macroParamterMappings.add(mapping);
        }
    }

    VivoMacro(String macro, String dspMacro, String desc) {
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
