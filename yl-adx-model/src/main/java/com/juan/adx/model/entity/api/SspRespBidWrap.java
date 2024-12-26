package com.juan.adx.model.entity.api;

import java.util.List;

import com.juan.adx.model.ssp.common.response.SspRespAdInfo;

import lombok.Data;

@Data
public class SspRespBidWrap {

	private List<SspRespAdInfo> sspRespBids;
	
	private BidRecord bidRecord;
	
	private boolean hasRta;
	
	private Integer rtaPriorityValue;
}
