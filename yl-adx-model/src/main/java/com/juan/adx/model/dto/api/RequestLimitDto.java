package com.juan.adx.model.dto.api;

import com.juan.adx.model.ssp.common.request.SspReqDeviceId;

import lombok.Data;

@Data
public class RequestLimitDto {

	private Integer slotId;
	private Integer budgetId;
	private Integer limitType;
	private Integer maxRequests;
	private Integer deviceMaxRequests;
	private Integer osType;
	private SspReqDeviceId sspReqDeviceId;
}
