package com.juan.adx.common.model;

import com.juan.adx.common.exception.ExceptionEnum;

public class ManageResponse {

	protected int code;

	protected Object data;

	protected String msg;
	
	public ManageResponse() {
		this.code=200;
		this.data = "";
		this.msg = "success";
	}
	
	public ManageResponse(Object d) {
		this.data = d;
		this.code = 200;
		this.msg = "success";
	}
	
	public ManageResponse(Integer c, Object d) {
		this.data = d;
		this.code = c;
		this.msg = "";
	}
	public ManageResponse(Integer c, String m) {
		this.code = c;
		this.msg = m;
		this.data = "";
	}
	
	public ManageResponse(ExceptionEnum exceptionEnum) {
		this.code = exceptionEnum.getCode();
		this.msg = exceptionEnum.getMessage();
		this.data = "";
	}
	
	public ManageResponse(ExceptionEnum exceptionEnum, Object data) {
		this.code = exceptionEnum.getCode();
		this.msg = exceptionEnum.getMessage();
		this.data = data;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
