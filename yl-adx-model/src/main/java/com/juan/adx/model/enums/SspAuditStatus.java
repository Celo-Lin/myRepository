package com.juan.adx.model.enums;

/**
 * SSP 应用、广告位审核状态：1-新增待审核、2-更新待审核、3-通过、4-不通过
 */
public enum SspAuditStatus {
	
	INSERT_UNAUDITED(1, "新增待审核"),
	
	UPDATE_UNAUDITED(2, "更新待审核"),
	
	PASS_AUDIT(3, "审核通过"),
	
	FAIL_AUDIT(4, "审核不通过"),

	;
	
	private Integer status;
	
	private String desc;
	
	private SspAuditStatus(Integer s, String d) {
		this.status = s;
		this.desc = d;
	}
	
	public static SspAuditStatus get(Integer s){
		if(s == null){
			return null;
		}
		for (SspAuditStatus st : values()) {
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
