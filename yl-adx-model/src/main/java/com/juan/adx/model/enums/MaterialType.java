package com.juan.adx.model.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * 广告物料类型：
 * 0：未知
 * 1：纯文字广告
 * 2：纯图片广告
 * 3：图文广告
 * 4：HTML广告
 * 5：视频广告
 * 6：音频广告
 */
public enum MaterialType {
	
	UNKNOWN(0, "未知"),
	
	TEXT(1, "纯文字广告"),
	
	IMAGE(2, "纯图片广告"),
	
	IMAGE_TEXT(3, "图文广告"),
	
	HTML(4, "HTML广告"),
	
	VIDEO(5, "视频广告"),
	
	VOICE(6, "音频广告")
	;

	@Getter
	@Setter
	private Integer type;
	
	@Getter
	@Setter
	private String desc;
	
	private MaterialType(Integer type, String desc) {
		this.type = type;
		this.desc = desc;
	}
	
	public static MaterialType get(Integer type){
		if(type == null){
			return null;
		}
		for (MaterialType materialType : values()) {
			if(materialType.getType() == type.intValue()){
				return materialType;
			}
		}
		return null;
	}
}
