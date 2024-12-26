package com.juan.adx.model.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * 视频播放完成后，需要展示的资源类型：
 * 0：无
 * 1：需要展示的图片地址
 * 2：需要展示的落地页地址
 * 3：需要使用webview渲染的HTML代码
 */
public enum VideoEndType {

	NONE(0, "无"),
	
	IMAGE_URL(1, "图片地址"),

	LANDING_PAGE_URL(2, "落地页地址"),
	
    HTML_CODE(3, "webview渲染的HTML代码"),
    
	;
	
	@Getter
	@Setter
	private int type;
	
	@Getter
	@Setter
	private String desc;
	
	private VideoEndType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
	
	public static VideoEndType get(Integer type){
		if(type == null){
			return null;
		}
		for (VideoEndType endType : values()) {
			if(endType.getType() == type.intValue()){
				return endType;
			}
		}
		return null;
	}

}
