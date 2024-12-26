package com.juan.adx.model.dsp.yueke.enums;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.juan.adx.model.entity.api.MacroParamterMapping;

import lombok.Getter;
import lombok.Setter;

/**
 * 广推 参数映射
 * EXAMPLE("Macro", "DSP Macro")
 * @MacroParameters
 */
public enum YueKeMacro{
	
	TS_MS("__MF_EVENT_TIME_MS__", "__TS__", "执行上报操作时客户端的时间戳, 单位：毫秒"),
	
	WIDTH("__MF_WIDTH__", "__AMVW__", "实际广告位的宽度，物理像素，单位：像素"),
	HEIGHT("__MF_HEIGHT__", "__AMVH__", "实际广告位的高度，物理像素，单位：像素"),
	
	DOWN_X("__MF_ABS_DOWN_X__", "__DSMX__", "用户手指按下时的横坐标(绝对坐标：以屏幕左上角坐标为原点)"),
	DOWN_Y("__MF_ABS_DOWN_Y__", "__DSMY__", "用户手指按下时的纵坐标(绝对坐标：以屏幕左上角坐标为原点)"),
	UP_X("__MF_ABS_UP_X__", "__DSCX__", "用户手指抬起时的横坐标(绝对坐标：以屏幕左上角坐标为原点)"),
	UP_Y("__MF_ABS_UP_Y__", "__DSCY__", "用户手指抬起时的纵坐标(绝对坐标：以屏幕左上角坐标为原点)"),
	
	POS_DOWN_X("__MF_POS_DOWN_X__", "__AZMX__", "用户手指按下时的横坐标(相对坐标：以广告位左上角坐标为原点)"),
	POS_DOWN_Y("__MF_POS_DOWN_Y__", "__AZMY__", "用户手指按下时的纵坐标(相对坐标：以广告位左上角坐标为原点)"),
	POS_UP_X("__MF_POS_UP_X__", "__AZCX__", "用户手指抬起时的横坐标(相对坐标：以广告位左上角坐标为原点)"),
	POS_UP_Y("__MF_POS_UP_Y__", "__AZCY__", "用户手指抬起时的纵坐标(相对坐标：以广告位左上角坐标为原点)"),
	
	
	
	VIDEO_PLAY_PROGRESS("__MF_PLAY_PROGRESS__", "__VD__", "视频已播放的时长, 单位：秒"),
	;

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
        for (YueKeMacro dspMacro : values()) {
            MacroParamterMapping mapping = new MacroParamterMapping();
            mapping.setMacro(dspMacro.getMacro());
            mapping.setPartnerMacro(dspMacro.getDspMacro());
            macroParamterMappings.add(mapping);
        }
    }

	private YueKeMacro(String macro, String dspMacro, String desc) {
        this.macro = macro;
        this.dspMacro = dspMacro;
        this.desc = desc;
    }

	/**
	 * 返回一个不可被修改的只读列表
	 */
	public static List<MacroParamterMapping> getMacroParamterMappings(){
		return Collections.unmodifiableList(macroParamterMappings);
	}

}