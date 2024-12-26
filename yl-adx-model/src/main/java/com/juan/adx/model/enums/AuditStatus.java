package com.juan.adx.model.enums;

/**
 * 审核状态：1-待审核、2-审核通过、3-审核不通过
 */
public enum AuditStatus {
	
	UNAUDITED(1, "待审核"),
	
	PASS_AUDIT(2, "审核通过"),
	
	FAIL_AUDIT(3, "审核不通过"),

	;
	
	private Integer status;
	
	private String desc;
	
	private AuditStatus(Integer s, String d) {
		this.status = s;
		this.desc = d;
	}
	
	public static AuditStatus get(Integer s){
		if(s == null){
			return null;
		}
		for (AuditStatus st : values()) {
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
