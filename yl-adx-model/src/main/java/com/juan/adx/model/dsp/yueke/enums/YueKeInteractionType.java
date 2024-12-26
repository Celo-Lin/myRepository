package com.juan.adx.model.dsp.yueke.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * 广告操作行为：
 *  0：无交互
 *  1：应用内WebView打开落地页地址 (落地页)
 *  2：使用系统浏览器打开落地页地址 (落地页)
 *  3：Deeplink 唤醒
 *  4：点击后下载，仅出现在Android设备 (下载)
 *  5：打开微信小程序
 *  6：广点通下载
 * </pre>
 */
public enum YueKeInteractionType {

	NON_INREACTION(0, -1, "无交互"),
	
	WEBVIEW (1, 1, "应用内WebView打开落地页地址 (落地页)"),
	
//	SYSTEM_BROWSER(2, 1, "使用系统浏览器打开落地页地址 (落地页)"),
	
	DEEPLINK(3, 1, "Deeplink 唤醒"),
	
	DOWNLOAD(4, 2, "点击后下载"),
	
	WECHAT_MINI_PROGRAM(5, 1, "打开微信小程序"),
	
	GDT(6, 2, "广点通下载"),
	
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
	
	private YueKeInteractionType(Integer type, Integer dspType, String desc) {
		this.type = type;
		this.dspType = dspType;
		this.desc = desc;
	}
	
	public static YueKeInteractionType getByDspType(Integer dspType){
		if(dspType == null){
			return NON_INREACTION;
		}
		for (YueKeInteractionType interactionType : values()) {
			if(interactionType.getDspType().intValue() == dspType.intValue()){
				return interactionType;
			}
		}
		return NON_INREACTION;
	}
	
}
