package com.juan.adx.api.converter;

import com.alibaba.fastjson2.JSON;
import com.juan.adx.api.converter.common.DspRequestParamConvert;
import com.juan.adx.api.converter.common.DspResponseParamConvert;
import com.juan.adx.api.converter.haoya.HaoYaDspRequestParamConvert;
import com.juan.adx.api.converter.haoya.HaoYaDspResponseParamConvert;
import com.juan.adx.api.converter.oppo.OppoDspRequestParamConvert;
import com.juan.adx.api.converter.oppo.OppoDspResponseParamConvert;
import com.juan.adx.api.converter.yidian.YiDianDspRequestParamConvert;
import com.juan.adx.api.converter.yidian.YiDianDspResponseParamConvert;
import com.juan.adx.api.converter.vivo.VivoDspRequestParamConvert;
import com.juan.adx.api.converter.vivo.VivoDspResponseParamConvert;
import com.juan.adx.api.converter.wifi.WifiDspRequestParamConvert;
import com.juan.adx.api.converter.wifi.WifiDspResponseParamConvert;
import com.juan.adx.api.converter.youdao.YouDaoDspRequestParamConvert;
import com.juan.adx.api.converter.youdao.YouDaoDspResponseParamConvert;
import com.juan.adx.api.converter.yueke.YueKeDspRequestParamConvert;
import com.juan.adx.api.converter.yueke.YueKeDspResponseParamConvert;
import com.juan.adx.common.utils.ParamUtils;
import com.juan.adx.model.dsp.DspRequestParam;
import com.juan.adx.model.dsp.DspResponseParam;
import com.juan.adx.model.dsp.haoya.request.HaoYaDspRequestParam;
import com.juan.adx.model.dsp.oppo.request.OppoDspRequestParam;
import com.juan.adx.model.dsp.yidian.request.YiDianDspRequestParam;
import com.juan.adx.model.dsp.vivo.request.VivoDspRequestParam;
import com.juan.adx.model.dsp.wifi.WifiDspProtobuf;
import com.juan.adx.model.dsp.youdao.request.YouDaoDspRequestParam;
import com.juan.adx.model.dsp.yueke.request.YueKeDspRequestParam;
import com.juan.adx.model.entity.api.ConvertDspParam;
import com.juan.adx.model.entity.api.ConvertSspParam;
import com.juan.adx.model.enums.Dsp;
import com.juan.adx.model.ssp.common.request.SspRequestParam;
import com.juan.adx.model.ssp.common.response.SspRespAdInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class DspParamConverterHelper {


	public static DspRequestParam convertRequest(ConvertSspParam convertSspParam) {
		DspRequestParam dspRequestParam = new DspRequestParam();
		Dsp dsp = convertSspParam.getDsp();
		Map<String, String> headersMap = new HashMap<String, String>();
		switch (dsp) {
			case YUE_KE:
				YueKeDspRequestParamConvert yuekeConvert = new YueKeDspRequestParamConvert(convertSspParam);
				YueKeDspRequestParam yuekeRequestParam = yuekeConvert.convert();
				dspRequestParam.setRequestParamJson(JSON.toJSONString(yuekeRequestParam));
				
				headersMap.put("Accept-Encoding", "gzip");
				headersMap.put("Content-Type", "application/json;charset=utf-8");
				headersMap.put("X-Forwarded-For", convertSspParam.getSspRequestParam().getNetwork().getIp());
				headersMap.put("User-Agent", convertSspParam.getSspRequestParam().getNetwork().getUserAgent());
				headersMap.put("Connection", "Keep-alive");
				dspRequestParam.setRequestHeaders(headersMap);
				break;
			case HAO_YA:
				HaoYaDspRequestParamConvert haoyaConvert = new HaoYaDspRequestParamConvert(convertSspParam);
				HaoYaDspRequestParam haoyaRequestParam = haoyaConvert.convert();
				dspRequestParam.setRequestParamJson(JSON.toJSONString(haoyaRequestParam));
				
				headersMap.put("Accept-Encoding", "gzip");
				headersMap.put("Content-Type", "application/json");
				dspRequestParam.setRequestHeaders(headersMap);
				break;
			case OPPO:
				OppoDspRequestParamConvert oppoConvert = new OppoDspRequestParamConvert(convertSspParam);
				OppoDspRequestParam oppoRequestParam = oppoConvert.convert();
				dspRequestParam.setRequestParamJson(Objects.nonNull(oppoRequestParam) ? JSON.toJSONString(oppoRequestParam) : null);
				headersMap.put("Content-Type", "application/json");
				dspRequestParam.setRequestHeaders(headersMap);
				break;
			case YOU_DAO:
				YouDaoDspRequestParamConvert youdaoConvert = new YouDaoDspRequestParamConvert(convertSspParam);
				YouDaoDspRequestParam youDaoDspRequestParam = youdaoConvert.convert();
				if(youDaoDspRequestParam !=null){
					dspRequestParam.setRequestParamMap(ParamUtils.beanToMap(youDaoDspRequestParam));
				}
				headersMap.put("Content-Type", "application/x-www-form-urlencoded");
				dspRequestParam.setRequestHeaders(headersMap);
				break;
			case YI_DIAN:
				YiDianDspRequestParamConvert yidianConvert = new YiDianDspRequestParamConvert(convertSspParam);
				YiDianDspRequestParam yiDianDspRequestParam = yidianConvert.convert();
				dspRequestParam.setRequestParamJson(Objects.nonNull(yiDianDspRequestParam) ? JSON.toJSONString(yiDianDspRequestParam) : null);
//				headersMap.put("Accept-Encoding", "gzip");
				headersMap.put("Content-Encoding", "gzip");
//				headersMap.put("Content-Type", "application/x-protobuf");
				headersMap.put("Content-Type", "application/json");
				dspRequestParam.setRequestHeaders(headersMap);
				break;
            case VIVO:
                VivoDspRequestParamConvert vivoConvert = new VivoDspRequestParamConvert(convertSspParam);
                VivoDspRequestParam vivoRequestParam = vivoConvert.convert();
                dspRequestParam.setRequestParamJson(Objects.nonNull(vivoRequestParam) ? JSON.toJSONString(vivoRequestParam) : null);
                headersMap.put("Content-Type", "application/json");
                dspRequestParam.setRequestHeaders(headersMap);
                break;
            case WIFI:
                WifiDspRequestParamConvert wifiConvert = new WifiDspRequestParamConvert(convertSspParam);
                WifiDspProtobuf.FlyingShuttleBidRequest flyingShuttleBidRequest = wifiConvert.convert();
                if(flyingShuttleBidRequest !=null){
                    dspRequestParam.setRequestParamBytes(flyingShuttleBidRequest.toByteArray());
                }
                headersMap.put("Content-Type", "application/x-protobuf");
                dspRequestParam.setRequestHeaders(headersMap);
                break;
			default:
				DspRequestParamConvert paramConvert = new DspRequestParamConvert(convertSspParam);
				SspRequestParam requestParam = paramConvert.convert();
				dspRequestParam.setRequestParamJson(JSON.toJSONString(requestParam));
				headersMap.put("Accept-Encoding", "gzip");
				headersMap.put("Content-Type", "application/json");
				dspRequestParam.setRequestHeaders(headersMap);
				break;
		}
		return dspRequestParam;
	}
	
	public static DspResponseParam convertResponse(ConvertDspParam convertDspParam) {
		DspResponseParam dspResponseParam = new DspResponseParam();
		Dsp dsp = convertDspParam.getDsp();
		switch (dsp) {
			case YUE_KE:
				YueKeDspResponseParamConvert yuekeConvert = new YueKeDspResponseParamConvert(convertDspParam);
				List<SspRespAdInfo> yuekeRespAdInfo = yuekeConvert.convert();
				dspResponseParam.setSspRespAdInfo(yuekeRespAdInfo);
				break;
			case HAO_YA:
				HaoYaDspResponseParamConvert haoyaConvert = new HaoYaDspResponseParamConvert(convertDspParam);
				List<SspRespAdInfo> haoyaRespAdInfo = haoyaConvert.convert();
				dspResponseParam.setSspRespAdInfo(haoyaRespAdInfo);
				break;
			case OPPO:
				OppoDspResponseParamConvert oppoConvert = new OppoDspResponseParamConvert(convertDspParam);
				List<SspRespAdInfo> oppoRespAdInfo = oppoConvert.convert();
				dspResponseParam.setSspRespAdInfo(oppoRespAdInfo);
				break;
			case YOU_DAO:
				YouDaoDspResponseParamConvert youDaoDspResponseParamConvert = new YouDaoDspResponseParamConvert(convertDspParam);
				List<SspRespAdInfo> youdaoRespAdInfo = youDaoDspResponseParamConvert.convert();
				dspResponseParam.setSspRespAdInfo(youdaoRespAdInfo);
				break;
			case YI_DIAN:
				YiDianDspResponseParamConvert yiDianParamConvert = new YiDianDspResponseParamConvert(convertDspParam);
				List<SspRespAdInfo> yidianRespAdInfo = yiDianParamConvert.convert();
				dspResponseParam.setSspRespAdInfo(yidianRespAdInfo);
				break;
            case VIVO:
                VivoDspResponseParamConvert vivoDspResponseParamConvert = new VivoDspResponseParamConvert(convertDspParam);
                List<SspRespAdInfo> vivoRespAdInfo = vivoDspResponseParamConvert.convert();
                dspResponseParam.setSspRespAdInfo(vivoRespAdInfo);
                break;
            case WIFI:
                WifiDspResponseParamConvert wifiDspResponseParamConvert = new WifiDspResponseParamConvert(convertDspParam);
                List<SspRespAdInfo> wifiRespAdInfo = wifiDspResponseParamConvert.convert();
                dspResponseParam.setSspRespAdInfo(wifiRespAdInfo);
                break;
			default:
				DspResponseParamConvert paramConvert = new DspResponseParamConvert(convertDspParam);
				List<SspRespAdInfo> respAdInfo = paramConvert.convert();
				dspResponseParam.setSspRespAdInfo(respAdInfo);
				break;
		}
		return dspResponseParam;	
		
		
	}






}
