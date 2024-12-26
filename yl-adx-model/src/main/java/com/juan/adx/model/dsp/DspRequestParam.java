package com.juan.adx.model.dsp;

import java.util.Map;

import lombok.Data;

@Data
public class DspRequestParam {

	private String requestParamJson;
	
	private Map<String, String> requestParamMap;

	private byte[] requestParamBytes;
	
	private Map<String, String> requestHeaders;
}
