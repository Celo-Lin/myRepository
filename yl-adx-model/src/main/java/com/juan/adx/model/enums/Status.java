package com.juan.adx.model.enums;

/**
 * 数据记录状态：1-有效、2-无效
 */
public enum Status {

	VALID(1, "有效"),
	
	INVALID(2, "无效"),

	;
	
	private Integer status;
	
	private String desc;
	
	private Status(Integer s, String d) {
		this.status = s;
		this.desc = d;
	}
	
	public static Status get(Integer s){
		if(s == null){
			return null;
		}
		for (Status st : values()) {
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
