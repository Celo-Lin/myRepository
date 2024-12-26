package com.juan.adx.model.entity.api;

import com.juan.adx.model.enums.CooperationModeSimple;
import com.juan.adx.model.enums.Rta;

import lombok.Data;

@Data
public class ConvertDspRtaParam {
	
	private String dspRespData;
	
	private AdSlotBudgetWrap adSlotBudgetWrap;
	
	private String requestUniqueId;
	
	private Rta rta;
	
	private CooperationModeSimple modeSimple;
	
	private ConvertDspRtaParam(ConvertDspRtaParamBuilder builder) {
        this.dspRespData = builder.dspRespData;
        this.adSlotBudgetWrap = builder.adSlotBudgetWrap;
        this.requestUniqueId = builder.requestUniqueId;
        this.rta = builder.rta;
        this.modeSimple = builder.modeSimple;
    }
	
	
	public static class ConvertDspRtaParamBuilder {
		
		private String dspRespData;
		
		private AdSlotBudgetWrap adSlotBudgetWrap;
		
		private String requestUniqueId;
		
		private Rta rta;
		
		private CooperationModeSimple modeSimple;

		public ConvertDspRtaParamBuilder() {
		}

		public ConvertDspRtaParamBuilder dspRespData(String dspRespData) {
			this.dspRespData = dspRespData;
			return this;
		}

		public ConvertDspRtaParamBuilder adSlotBudgetWrap(AdSlotBudgetWrap adSlotBudgetWrap) {
			this.adSlotBudgetWrap = adSlotBudgetWrap;
			return this;
		}

		public ConvertDspRtaParamBuilder requestUniqueId(String requestUniqueId) {
			this.requestUniqueId = requestUniqueId;
			return this;
		}
		
		public ConvertDspRtaParamBuilder rta(Rta rta) {
			this.rta = rta;
			return this;
		}
		
		public ConvertDspRtaParamBuilder modeSimple(CooperationModeSimple modeSimple) {
			this.modeSimple = modeSimple;
			return this;
		}

		public ConvertDspRtaParam build() {
			return new ConvertDspRtaParam(this);
		}
	}	
}
