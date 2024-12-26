package com.juan.adx.api.converter.common;

import com.juan.adx.api.context.TraceContext;
import com.juan.adx.api.converter.AbstractDspRequestParamConvert;
import com.juan.adx.model.entity.api.ConvertSspParam;
import com.juan.adx.model.ssp.common.request.SspRequestParam;

public class DspRequestParamConvert extends AbstractDspRequestParamConvert  {

	public DspRequestParamConvert(ConvertSspParam convertSspParam) {
		super(convertSspParam);
	}

	public SspRequestParam convert() {
		this.sspRequestParam.setRequestId(TraceContext.getTraceIdByContext());
		this.sspRequestParam.getSlot().setAdSlotId(null);
		this.sspRequestParam.getSlot().setSlotId(this.adSlotBudgetWrap.getDspSlotId());
		this.sspRequestParam.getApp().setAppId(this.adSlotBudgetWrap.getDspAppId());
		this.sspRequestParam.getApp().setPkgName(this.getPackageName());
		return this.sspRequestParam;
	}
	
	
	
	

}
