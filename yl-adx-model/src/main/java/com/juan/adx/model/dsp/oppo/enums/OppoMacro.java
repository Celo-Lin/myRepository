package com.juan.adx.model.dsp.oppo.enums;

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
public enum OppoMacro{
	
	WIDTH("__MF_WIDTH__", "$acw$", "实际广告位的宽度，物理像素，单位：像素"),
	HEIGHT("__MF_HEIGHT__", "$ach$", "实际广告位的高度，物理像素，单位：像素"),
	
	DOWN_X("__MF_ABS_DOWN_X__", "$dx$", "用户手指按下时的横坐标(绝对坐标：以屏幕左上角坐标为原点)"),
	DOWN_Y("__MF_ABS_DOWN_Y__", "$dy$", "用户手指按下时的纵坐标(绝对坐标：以屏幕左上角坐标为原点)"),
	UP_X("__MF_ABS_UP_X__", "$ux$", "用户手指抬起时的横坐标(绝对坐标：以屏幕左上角坐标为原点)"),
	UP_Y("__MF_ABS_UP_Y__", "$uy$", "用户手指抬起时的纵坐标(绝对坐标：以屏幕左上角坐标为原点)"),
	
	
	
	AIT("__MF_AIT__", "$itm$", "广告点击交互方式(Ad Interaction Type)：0-正常触屏点击、1-引导滑动点击、2-摇动点击(摇一摇)、3-自定义手势、4-正常触屏点击 或 滑动点击、5-扭动点击(扭一扭)、6-擦除"),
	X_MAX_ACC("__MF_X_MAX_ACC__", "$xma$", "SLD=2 时必填，用户摇动点击时 x 轴加速度峰值。Android 端乘以 100 取整， iOS 端乘以 980 取整"),
	Y_MAX_ACC("__MF_Y_MAX_ACC__", "$yma$", "SLD=2 时必填，用户摇动点击时 y 轴加速度峰值。Android 端乘以 100 取整，iOS 端乘以980 取整"),
	Z_MAX_ACC("__MF_Z_MAX_ACC__", "$zma$", "SLD=2 时必填，用户摇动点击时 z 轴加速度峰值。Android 端乘以 100 取整，iOS 端乘以980 取整"),
	
	
	VIDEO_PLAY_PROGRESS_MS("__MF_PLAY_PROGRESS_MS__", "$progress$", "视频已播放的时长, 单位：毫秒"),
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
        for (OppoMacro dspMacro : values()) {
            MacroParamterMapping mapping = new MacroParamterMapping();
            mapping.setMacro(dspMacro.getMacro());
            mapping.setPartnerMacro(dspMacro.getDspMacro());
            macroParamterMappings.add(mapping);
        }
    }

	private OppoMacro(String macro, String dspMacro, String desc) {
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