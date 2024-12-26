package com.juan.adx.model.enums;

/**
 * 对接方式：1-API、2-SDK
 */
public enum IntegrationMode {

	API(1, "API"),
	
	SDK(2, "SDK"),
	
	;
	
	private int mode;
	
	private String desc;
	
	private IntegrationMode(int mode, String desc) {
		this.mode = mode;
		this.desc = desc;
	}
	
	public static IntegrationMode get(Integer mode){
		if(mode == null){
			return null;
		}
		for (IntegrationMode integrationMode : values()) {
			if(integrationMode.getMode() == mode.intValue()){
				return integrationMode;
			}
		}
		return null;
	}

	
	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
