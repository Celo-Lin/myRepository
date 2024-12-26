package com.juan.adx.model.enums;

/**
 * 上报状态：0-未上报、1-已上报
 */
public enum DisplayReportStatus {
	
	UNREPORTED(0, "未上报"),
	
	REPORTED(1, "已上报"),
	
	;
	
	private Integer status;
	
	private String desc;
	
	private DisplayReportStatus(Integer s, String d) {
		this.status = s;
		this.desc = d;
	}
	
	public static DisplayReportStatus get(Integer s){
		if(s == null){
			return null;
		}
		for (DisplayReportStatus st : values()) {
			if(st.getStatus().intValue() == s.intValue()){
				return st;
			}
		}
		return null;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
