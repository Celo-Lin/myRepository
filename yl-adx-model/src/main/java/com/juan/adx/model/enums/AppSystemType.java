package com.juan.adx.model.enums;

/**
 * 应用系统平台：1-IOS、2-Android、3-快应用
 */
public enum AppSystemType {

	IOS(1, "IOS"),
	
	ANDROID(2, "Android"),
	
	FAST_APP(3, "快应用"),
	
	;
	
	private int type;
	
	private String desc;
	
	private AppSystemType(int type, String desc) {
		this.type = type;
		this.desc = desc;
	}
	
	public static AppSystemType get(Integer type){
		if(type == null){
			return null;
		}
		for (AppSystemType systemType : values()) {
			if(systemType.getType() == type.intValue()){
				return systemType;
			}
		}
		return null;
	}
	

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
