package com.juan.adx.api.converter;

import java.util.HashMap;
import java.util.Map;

import com.juan.adx.model.dsp.DspRequestParam;
import com.juan.adx.model.dsp.DspResponseParam;
import com.juan.adx.model.entity.api.ConvertDspRtaParam;
import com.juan.adx.model.entity.api.ConvertSspRtaParam;
import com.juan.adx.model.enums.Rta;


public class RtaParamConverterHelper {


	public static DspRequestParam convertRequest(ConvertSspRtaParam convertSspRtaParam) {
		DspRequestParam dspRequestParam = new DspRequestParam();
		Rta rta = convertSspRtaParam.getRta();
		Map<String, String> headersMap = new HashMap<String, String>();
		switch (rta) {
			default:
				break;
		}
		return dspRequestParam;
	}
	
	public static DspResponseParam convertResponse(ConvertDspRtaParam convertDspRtaParam) {
		DspResponseParam dspResponseParam = new DspResponseParam();
		Rta rta = convertDspRtaParam.getRta();
		switch (rta) {
			default:
				break;
		}
		return dspResponseParam;	
		
		
	}
	
	
	


	
}
