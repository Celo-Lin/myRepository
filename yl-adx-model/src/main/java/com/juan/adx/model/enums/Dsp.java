package com.juan.adx.model.enums;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;

public enum Dsp {
	
	YUE_KE("YueKe", "阅客"),
	
	HAO_YA("HaoYa", "好压2345"),
	
	OPPO("OPPO", "OPPO"),
	
	YING_SHI("YingShi", "萤石"),

	FANG_GE("FangGe", "方歌"),

	HAI_NA("HaiNa", "海纳春秋"),

	YOU_DAO("YouDao", "网易有道"),

	YI_DIAN("YiDian", "一点资讯"),

	VIVO("VIVO", "VIVO"),

	LeYou("LeYou", "华夏乐游"),

    WIFI("WIFI", "wifi");
	
	@Getter
	@Setter
	private String tag;
	
	@Getter
	@Setter
	private String desc;
	
	private Dsp(String tag, String desc) {
		this.tag = tag;
		this.desc = desc;
	}
	
	public static Dsp get(String tag) {
		if(tag == null || tag.isEmpty()){
			return null;
		}
		for (Dsp dsp : values()) {
			if(StringUtils.equalsIgnoreCase(tag, dsp.getTag())){
				return dsp;
			}
		}
		return null;
	}
}
