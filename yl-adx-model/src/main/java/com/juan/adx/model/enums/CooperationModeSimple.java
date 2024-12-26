package com.juan.adx.model.enums;

/**
 * 合作模式：1-PD、2-RTB
 */
public enum CooperationModeSimple {

	PD(1, "PD"),
	
	RTB(2, "RTB"),

	
	;
	
	private int mode;
	
	private String desc;
	
	private CooperationModeSimple(int mode, String desc) {
		this.mode = mode;
		this.desc = desc;
	}
	
	public static CooperationModeSimple get(Integer mode){
		if(mode == null){
			return null;
		}
		for (CooperationModeSimple cooperationMode : values()) {
			if(cooperationMode.getMode() == mode.intValue()){
				return cooperationMode;
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
