package com.juan.adx.model.enums;

/**
 * 合作模式：1-PD、2-RTB(自动)、3-RTB(手动)
 */
public enum CooperationMode {

	PD(1, "PD"),
	
	RTB_AUTO(2, "RTB自动"),
	
	RTB_MANUAL(3, "RTB手动"),
	
	;
	
	private int mode;
	
	private String desc;
	
	private CooperationMode(int mode, String desc) {
		this.mode = mode;
		this.desc = desc;
	}
	
	public static CooperationMode get(Integer mode){
		if(mode == null){
			return null;
		}
		for (CooperationMode cooperationMode : values()) {
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
