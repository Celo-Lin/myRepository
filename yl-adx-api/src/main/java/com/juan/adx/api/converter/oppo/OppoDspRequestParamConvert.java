package com.juan.adx.api.converter.oppo;

import org.apache.commons.lang3.StringUtils;

import com.juan.adx.api.converter.AbstractDspRequestParamConvert;
import com.juan.adx.common.utils.NumberUtils;
import com.juan.adx.model.dsp.oppo.enums.OppoAdvertType;
import com.juan.adx.model.dsp.oppo.enums.OppoCarrierType;
import com.juan.adx.model.dsp.oppo.enums.OppoNetworkType;
import com.juan.adx.model.dsp.oppo.request.OppoAppInfo;
import com.juan.adx.model.dsp.oppo.request.OppoDeviceInfo;
import com.juan.adx.model.dsp.oppo.request.OppoDspRequestParam;
import com.juan.adx.model.dsp.oppo.request.OppoGpsInfo;
import com.juan.adx.model.dsp.oppo.request.OppoPosInfo;
import com.juan.adx.model.dsp.oppo.request.OppoUser;
import com.juan.adx.model.entity.api.ConvertSspParam;

public class OppoDspRequestParamConvert extends AbstractDspRequestParamConvert  {

	public OppoDspRequestParamConvert(ConvertSspParam convertSspParam) {
		super(convertSspParam);
	}

	public OppoDspRequestParam convert() {
		
		//跑OPPO预算时需要过滤掉手机品牌非oppo、realme、oneplus的流量
		String deviceBrand = this.device.getBrand();
		if(StringUtils.isBlank(deviceBrand) 
				|| (!StringUtils.equalsIgnoreCase("oppo", deviceBrand) 
						&& !StringUtils.equalsIgnoreCase("realme", deviceBrand)
						&& !StringUtils.equalsIgnoreCase("oneplus", deviceBrand)) ) {
			return null;
		}
		
		OppoDspRequestParam dspRequestParam = new OppoDspRequestParam();
		
		OppoAppInfo dspApp = this.getDspApp();
		OppoPosInfo dspPos = this.getDspPos();
		OppoDeviceInfo dspDevice = this.getDspDevice();
		OppoUser dspUser = this.getDspUser();
		
		dspRequestParam.setApiVersion(1);
		dspRequestParam.setApiVc(108);
		dspRequestParam.setAppStoreVc(NumberUtils.toInt(this.app.getAppStoreVersion()));
		dspRequestParam.setAppInfo(dspApp);
		dspRequestParam.setPosInfo(dspPos);
		dspRequestParam.setDevInfo(dspDevice);
		dspRequestParam.setUser(dspUser);
		return dspRequestParam;
	}

	private OppoUser getDspUser() {
		OppoUser dspUser = new OppoUser();
		dspUser.setInstalledAppPkgList(this.user.getInstalled());
		dspUser.setKeyword(null);
		dspUser.setTargetPkgName(null);
		dspUser.setMinTargetAppVersionCode(null);
		return dspUser;
	}

	private OppoDeviceInfo getDspDevice() {
		OppoNetworkType networkType = OppoNetworkType.get(this.network.getNetworkType());
		OppoCarrierType carrierType = OppoCarrierType.get(this.network.getCarrier());
		OppoDeviceInfo dspDevice = new OppoDeviceInfo();
		dspDevice.setImei(this.deviceId.getImei());
		dspDevice.setImeiMd5(this.deviceId.getImeiMd5());
		dspDevice.setOaId(this.deviceId.getOaid());
		dspDevice.setVaId(this.deviceId.getAndroidId());
		dspDevice.setIp(this.network.getIp());
		dspDevice.setUa(this.network.getUserAgent());
		dspDevice.setMac(this.network.getMac());
		dspDevice.setMacMd5(this.network.getMacMd5());
		dspDevice.setAnId(this.deviceId.getAndroidId());
		dspDevice.setColorOsv(null);
		dspDevice.setRomv(this.device.getRomVersion());
		dspDevice.setAnVer(this.device.getOsVersion());
		dspDevice.setH(this.device.getHeight());
		dspDevice.setW(this.device.getWidth());
		dspDevice.setDensity(this.device.getDensity());
		dspDevice.setConnectionType(networkType.getDspType());
		dspDevice.setCarrier(carrierType.getDspType());
		dspDevice.setOri(null);
		
		OppoGpsInfo gpsInfo = new OppoGpsInfo();
		gpsInfo.setLat(this.geo.getLatitude());
		gpsInfo.setLon(this.geo.getLongitude());
		gpsInfo.setTimestamp(this.geo.getTimestamp());
		dspDevice.setGpsInfo(gpsInfo);
		dspDevice.setLinkSpeed(null);
		dspDevice.setBrand(this.device.getBrand());
		dspDevice.setModel(this.device.getModel());
		dspDevice.setBootMark(this.device.getBootMark());
		dspDevice.setUpdateMark(this.device.getUpdateMark());
		return dspDevice;
	}

	private OppoPosInfo getDspPos() {
		OppoAdvertType advertType = OppoAdvertType.get(this.slot.getType());
		OppoPosInfo dspPos = new OppoPosInfo();
		dspPos.setId(this.adSlotBudgetWrap.getDspSlotId());
		dspPos.setPosType(advertType.getDspType());
		dspPos.setW(this.slot.getWidth());
		dspPos.setH(this.slot.getHeight());
		return dspPos;
	}

	private OppoAppInfo getDspApp() {
		OppoAppInfo dspApp = new OppoAppInfo();
		dspApp.setAppId(this.adSlotBudgetWrap.getDspAppId());
		dspApp.setPkgname(this.getPackageName());
		dspApp.setVerName(this.app.getVerName());
		return dspApp;
	}
	
	

}
