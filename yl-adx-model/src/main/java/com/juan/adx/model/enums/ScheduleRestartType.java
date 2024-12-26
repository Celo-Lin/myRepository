package com.juan.adx.model.enums;

public enum ScheduleRestartType {

	/**
	 * 无需重启
	 */
	NO_RESTART(0, "无需重启"),

	/**
	 * 需要重启
	 */
	RESTART(1, "需重启")
	
	;
	
	private ScheduleRestartType(int t, String d) {
		this.type = t;
		this.desc = d;
	}
	
	public static ScheduleRestartType getScheduleRestart(Integer type) {
		if(type == null) {
			return null;
		}
		for (ScheduleRestartType scheduleRestart : values()) {
			if(scheduleRestart.getType() == type.intValue()) {
				return scheduleRestart;
			}
		}
		return null;
	}
	
	private int type;
	
	private String desc;

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
