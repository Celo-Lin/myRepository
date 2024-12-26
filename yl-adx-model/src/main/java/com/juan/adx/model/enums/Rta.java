package com.juan.adx.model.enums;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;

public enum Rta {

	BAIDU("Baidu", "百度"),
	
	KUAI_SHOU("KuaiShou", "快手"),

	
	;
	
	@Getter
	@Setter
	private String tag;
	
	@Getter
	@Setter
	private String desc;
	
	private Rta(String tag, String desc) {
		this.tag = tag;
		this.desc = desc;
	}
	
	public static Rta get(String tag) {
		if(tag == null || tag.isEmpty()){
			return null;
		}
		for (Rta dsp : values()) {
			if(StringUtils.equalsIgnoreCase(tag, dsp.getTag())){
				return dsp;
			}
		}
		return null;
	}
}
