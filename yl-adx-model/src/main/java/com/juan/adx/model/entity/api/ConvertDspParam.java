package com.juan.adx.model.entity.api;

import com.juan.adx.model.enums.CooperationMode;
import com.juan.adx.model.enums.CooperationModeSimple;
import com.juan.adx.model.enums.Dsp;
import com.juan.adx.model.ssp.common.request.SspRequestParam;

import lombok.Data;

@Data
public class ConvertDspParam {
	
	private SspRequestParam sspRequestParam;
	
	private String dspRespData;
	
	private AdSlotBudgetWrap adSlotBudgetWrap;
	
	private String sspRequestId;
	
	private Dsp dsp;
	
	private CooperationMode mode;
	
	private CooperationModeSimple modeSimple;
	
	private ConvertDspParam(ConvertDspParamBuilder builder) {
		this.sspRequestParam = builder.sspRequestParam;
        this.dspRespData = builder.dspRespData;
        this.adSlotBudgetWrap = builder.adSlotBudgetWrap;
        this.sspRequestId = builder.sspRequestId;
        this.dsp = builder.dsp;
        this.mode = builder.mode;
        this.modeSimple = builder.modeSimple;
    }
	
	
	public static class ConvertDspParamBuilder {
		
		private SspRequestParam sspRequestParam;
		
		private String dspRespData;
		
		private AdSlotBudgetWrap adSlotBudgetWrap;
		
		private String sspRequestId;
		
		private Dsp dsp;
		
		private CooperationMode mode;
		
		private CooperationModeSimple modeSimple;

		public ConvertDspParamBuilder() {
		}

		public ConvertDspParamBuilder sspRequestParam(SspRequestParam sspRequestParam) {
			this.sspRequestParam = sspRequestParam;
			return this;
		}
		
		public ConvertDspParamBuilder dspRespData(String dspRespData) {
			this.dspRespData = dspRespData;
			return this;
		}

		public ConvertDspParamBuilder adSlotBudgetWrap(AdSlotBudgetWrap adSlotBudgetWrap) {
			this.adSlotBudgetWrap = adSlotBudgetWrap;
			return this;
		}

		public ConvertDspParamBuilder sspRequestId(String sspRequestId) {
			this.sspRequestId = sspRequestId;
			return this;
		}
		
		public ConvertDspParamBuilder dsp(Dsp dsp) {
			this.dsp = dsp;
			return this;
		}
		
		public ConvertDspParamBuilder mod(CooperationMode mode) {
			this.mode = mode;
			return this;
		}
		
		public ConvertDspParamBuilder modeSimple(CooperationModeSimple modeSimple) {
			this.modeSimple = modeSimple;
			return this;
		}

		public ConvertDspParam build() {
			return new ConvertDspParam(this);
		}
	}	
}
