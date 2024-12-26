package com.juan.adx.model.enums;

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
public enum InteractionType {

	NON_INREACTION(0, "无交互"),
	
	WEBVIEW (1, "应用内WebView打开落地页地址 (落地页)"),
	
	SYSTEM_BROWSER(2, "使用系统浏览器打开落地页地址 (落地页)"),
	
	DEEPLINK(3, "Deeplink 唤醒"),
	
	DOWNLOAD(4, "点击后下载"),
	
	WECHAT_MINI_PROGRAM(5, "打开微信小程序"),
	
	GDT(6, "广点通下载"),
	
	;
	
	@Getter
	@Setter
	private Integer type;
	
	@Getter
	@Setter
	private String desc;
	
	private InteractionType(Integer type, String desc) {
		this.type = type;
		this.desc = desc;
	}
	
	public static InteractionType get(Integer type){
		if(type == null){
			return null;
		}
		for (InteractionType interactionType : values()) {
			if(interactionType.getType() == type.intValue()){
				return interactionType;
			}
		}
		return null;
	}
	
}
