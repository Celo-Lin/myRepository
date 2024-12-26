package com.juan.adx.model.entity.api;

import com.juan.adx.model.enums.Rta;
import com.juan.adx.model.ssp.common.request.SspRequestParam;

import lombok.Data;

@Data
public class ConvertSspRtaParam {

	private SspRequestParam sspRequestParam;
	
	private AdSlotBudgetWrap adSlotBudgetWrap;
	
	private Rta rta;
	
	private ConvertSspRtaParam(ConvertSspRtaParamBuilder builder) {
        this.sspRequestParam = builder.sspRequestParam;
        this.adSlotBudgetWrap = builder.adSlotBudgetWrap;
        this.rta = builder.rta;
    }
	
	public static class ConvertSspRtaParamBuilder {
		
		private SspRequestParam sspRequestParam;

		private AdSlotBudgetWrap adSlotBudgetWrap;

		private Rta rta;

		public ConvertSspRtaParamBuilder() {
		}

		public ConvertSspRtaParamBuilder sspRequestParam(SspRequestParam sspRequestParam) {
			this.sspRequestParam = sspRequestParam;
			return this;
		}

		public ConvertSspRtaParamBuilder adSlotBudgetWrap(AdSlotBudgetWrap adSlotBudgetWrap) {
			this.adSlotBudgetWrap = adSlotBudgetWrap;
			return this;
		}
		
		public ConvertSspRtaParamBuilder rta(Rta rta) {
			this.rta = rta;
			return this;
		}

		public ConvertSspRtaParam build() {
			return new ConvertSspRtaParam(this);
		}
	}
}
