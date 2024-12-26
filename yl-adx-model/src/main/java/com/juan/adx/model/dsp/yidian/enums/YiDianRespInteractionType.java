package com.juan.adx.model.dsp.yidian.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * 广告点击类型：
 * 1：跳转
 * 2：下载
 * </pre>
 */
public enum YiDianRespInteractionType {

	NON_INREACTION(0, 0, "无交互"),

	WEBVIEW (1, 1, "应用内WebView打开落地页地址 (落地页)"),

//	SYSTEM_BROWSER(2, 1, "使用系统浏览器打开落地页地址 (落地页)"),

	DEEPLINK(3, 1, "Deeplink 唤醒"),

	DOWNLOAD(4, 2, "点击后下载"),

	WECHAT_MINI_PROGRAM(5, 1, "打开微信小程序"),

//	GDT(6, 2, "广点通下载"),

	;

	@Getter
	@Setter
	private Integer type;

	@Getter
	@Setter
	private Integer dspType;

	@Getter
	@Setter
	private String desc;

	private YiDianRespInteractionType(Integer type, Integer dspType, String desc) {
		this.type = type;
		this.dspType = dspType;
		this.desc = desc;
	}
	
	public static YiDianRespInteractionType getByDspType(Integer dspType){
		if(dspType == null){
			return NON_INREACTION;
		}
		for (YiDianRespInteractionType interactionType : values()) {
			if(interactionType.getDspType().intValue() == dspType.intValue()){
				return interactionType;
			}
		}
		return NON_INREACTION;
	}
	
}
