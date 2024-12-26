package com.juan.adx.model.ssp.common.response;

import java.util.List;

import lombok.Data;

@Data
public class SspResponseParam {

	public Integer code;

	public List<SspRespAdInfo> data;
	
	public String msg;
}
