package com.juan.adx.model.entity.api;

import com.juan.adx.model.enums.CooperationMode;
import com.juan.adx.model.enums.Dsp;
import com.juan.adx.model.ssp.common.request.SspRequestParam;

import lombok.Data;

@Data
public class ConvertSspParam {

	private SspRequestParam sspRequestParam;
	
	private AdSlotBudgetWrap adSlotBudgetWrap;
	
	private CooperationMode mode;
	
	private Dsp dsp;
	
	private ConvertSspParam(ConvertSspParamBuilder builder) {
        this.sspRequestParam = builder.sspRequestParam;
        this.adSlotBudgetWrap = builder.adSlotBudgetWrap;
        this.mode = builder.mode;
        this.dsp = builder.dsp;
    }
	
	public static class ConvertSspParamBuilder {
		
		private SspRequestParam sspRequestParam;

		private AdSlotBudgetWrap adSlotBudgetWrap;
		
		private CooperationMode mode;

		private Dsp dsp;
		
		public ConvertSspParamBuilder() {
		}

		public ConvertSspParamBuilder sspRequestParam(SspRequestParam sspRequestParam) {
			this.sspRequestParam = sspRequestParam;
			return this;
		}

		public ConvertSspParamBuilder adSlotBudgetWrap(AdSlotBudgetWrap adSlotBudgetWrap) {
			this.adSlotBudgetWrap = adSlotBudgetWrap;
			return this;
		}
		
		public ConvertSspParamBuilder mod(CooperationMode mode) {
			this.mode = mode;
			return this;
		}
		
		public ConvertSspParamBuilder dsp(Dsp dsp) {
			this.dsp = dsp;
			return this;
		}

		public ConvertSspParam build() {
			return new ConvertSspParam(this);
		}
	}
}
