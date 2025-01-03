package com.juan.adx.common.model;


public class HttpResponseEntity {

	/**
	 * http响应状态码
	 */
	private int statusCode;
	
	/**
	 * http响应体
	 */
	private String responseBody;

	
	public HttpResponseEntity() {
		super();
	}
	
	

	public HttpResponseEntity(int statusCode, String responseBody) {
		super();
		this.statusCode = statusCode;
		this.responseBody = responseBody;
	}



	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}
	
	
	
	
}