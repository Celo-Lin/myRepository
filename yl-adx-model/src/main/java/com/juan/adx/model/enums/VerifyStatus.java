package com.juan.adx.model.enums;


public enum VerifyStatus {
	
	UNVERIFIED(0, "未核实"),
	
	VERIFIED(1, "已核实"),

	;
	
	private Integer status;
	
	private String desc;
	
	private VerifyStatus(Integer s, String d) {
		this.status = s;
		this.desc = d;
	}
	
	public static VerifyStatus valueOf(Integer s){
		if(s == null){
			return null;
		}
		for (VerifyStatus st : values()) {
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
