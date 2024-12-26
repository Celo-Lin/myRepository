package com.juan.adx.api.converter.yueke;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.juan.adx.api.context.TraceContext;
import com.juan.adx.api.converter.AbstractDspRequestParamConvert;
import com.juan.adx.common.alg.MD5Util;
import com.juan.adx.model.dsp.yueke.enums.YueKeCarrierType;
import com.juan.adx.model.dsp.yueke.enums.YueKeCoordinateType;
import com.juan.adx.model.dsp.yueke.enums.YueKeDeviceType;
import com.juan.adx.model.dsp.yueke.enums.YueKeNetworkType;
import com.juan.adx.model.dsp.yueke.enums.YueKeOrientationType;
import com.juan.adx.model.dsp.yueke.enums.YueKeOsType;
import com.juan.adx.model.dsp.yueke.request.YueKeApp;
import com.juan.adx.model.dsp.yueke.request.YueKeBanner;
import com.juan.adx.model.dsp.yueke.request.YueKeDevice;
import com.juan.adx.model.dsp.yueke.request.YueKeDspRequestParam;
import com.juan.adx.model.dsp.yueke.request.YueKeExt;
import com.juan.adx.model.dsp.yueke.request.YueKeGeo;
import com.juan.adx.model.dsp.yueke.request.YueKeImp;
import com.juan.adx.model.dsp.yueke.request.YueKeVideo;
import com.juan.adx.model.entity.api.ConvertSspParam;
import com.juan.adx.model.enums.AdvertType;

public class YueKeDspRequestParamConvert extends AbstractDspRequestParamConvert  {

	public YueKeDspRequestParamConvert(ConvertSspParam convertSspParam) {
		super(convertSspParam);
	}

	public YueKeDspRequestParam convert() {
		YueKeDspRequestParam dspRequestParam = new YueKeDspRequestParam();
		
		List<YueKeImp> dspImp = this.getDspImps();
		YueKeApp dspApp = this.getDspApp();
		YueKeDevice dspDevice = this.getDspDevice();
		YueKeExt dspExt = this.getDspExt();
		
		String id = MD5Util.getMD5String(TraceContext.getTraceIdByContext());
		
		
		dspRequestParam.setId(id);
		dspRequestParam.setVersion("2.0.6");
		dspRequestParam.setImp(dspImp);
		dspRequestParam.setSite(null);
		dspRequestParam.setApp(dspApp);
		dspRequestParam.setDevice(dspDevice);
		dspRequestParam.setUser(null);
		dspRequestParam.setAt(1);
		dspRequestParam.setTest(0);
		dspRequestParam.setTmax(null);
		dspRequestParam.setExt(dspExt);
		return dspRequestParam;
	}

	private YueKeExt getDspExt() {
		YueKeExt dspExt = new YueKeExt();
		dspExt.setRdt(-1);
		dspExt.setHttps(-1);
		dspExt.setDeepLink(-1);
		dspExt.setDownload(-1);
		dspExt.setAdmt(2);
		dspExt.setVech(2);
		dspExt.setVecv(2);
		return dspExt;
	}

	private YueKeDevice getDspDevice() {
		
		YueKeOrientationType orientationType = YueKeOrientationType.get(this.device.getOrientation());
		YueKeNetworkType networkType = YueKeNetworkType.get(this.network.getNetworkType());
		YueKeCarrierType carrierType = YueKeCarrierType.get(this.network.getCarrier());
		YueKeDeviceType deviceType = YueKeDeviceType.get(this.device.getType());
		YueKeCoordinateType coordinateType = YueKeCoordinateType.get(this.geo.getCoordinateType());
		YueKeOsType osType = YueKeOsType.get(this.device.getOsType());
		
		YueKeGeo dspGeo = new YueKeGeo();
		dspGeo.setLat(this.geo.getLatitude());
		dspGeo.setLon(this.geo.getLongitude());
		dspGeo.setCoordinate(coordinateType.getDspType());
		dspGeo.setTimestamp(this.geo.getTimestamp());
		dspGeo.setAccu(null);
		
		YueKeDevice dspDevice = new YueKeDevice();
		dspDevice.setUa(this.network.getUserAgent());
		dspDevice.setGeo(dspGeo);
		dspDevice.setIp(this.network.getIp());
		dspDevice.setIpv6(this.network.getIpv6());
		dspDevice.setDevicetype(deviceType.getDspType());
		dspDevice.setMake(this.device.getMake());
		dspDevice.setModel(this.device.getModel());
		dspDevice.setOs(osType.getDspType());
		dspDevice.setOsv(this.device.getOsVersion());
		dspDevice.setHwv(this.device.getHardwareModel());
		dspDevice.setH(this.device.getHeight());
		dspDevice.setW(this.device.getWidth());
		dspDevice.setSw(this.device.getWidth());
		dspDevice.setSh(this.device.getHeight());
		dspDevice.setPpi(this.device.getPpi());
		dspDevice.setDpi(this.device.getDpi());
		dspDevice.setCaid(this.caId.getCaid());
		dspDevice.setCaidVer(this.caId.getVersion());
		dspDevice.setIfa(this.deviceId.getIdfa());
		dspDevice.setIfamd5(this.deviceId.getIdfaMd5());
		dspDevice.setIfv(this.deviceId.getIdfv());
		dspDevice.setIfvmd5(null);
		dspDevice.setUdid(this.deviceId.getOpenUdid());
		dspDevice.setUdidmd5(null);
		dspDevice.setDid(this.deviceId.getImei());
		dspDevice.setDidmd5(this.deviceId.getImeiMd5());
		dspDevice.setDpid(this.deviceId.getAndroidId());
		dspDevice.setDpidmd5(this.deviceId.getAndroidIdMd5());
		dspDevice.setOaId(this.deviceId.getOaid());
		dspDevice.setMac(this.network.getMac());
		dspDevice.setMacmd5(this.network.getMacMd5());
		dspDevice.setCarrier(carrierType.getDspType());
		dspDevice.setConnectiontype(networkType.getDspType());
		dspDevice.setImsi(this.device.getImsi());
		dspDevice.setOrientation(orientationType.getDspType());
		dspDevice.setApps(this.user.getInstalled());
		dspDevice.setAppstore(this.app.getAppStoreVersion());
		dspDevice.setHms(this.device.getHmsVersion());
		dspDevice.setStartupTime(this.device.getSysStartupTime());
		dspDevice.setUpdateTime(this.device.getSysUpdateTime());
		dspDevice.setCountry(this.network.getCountry());
		dspDevice.setLanguage(this.device.getLanguage());
		dspDevice.setTimeZone(this.device.getTimeZone());
		dspDevice.setMemorySize(this.device.getSysMemorySize());
		dspDevice.setDiskSize(this.device.getSysDiskSize());
		dspDevice.setPhoneNameMd5(this.device.getDeviceNameMd5());
		dspDevice.setIdfaPolicy(null);
		dspDevice.setBoot_mark(this.device.getBootMark());
		dspDevice.setUpdate_mark(this.device.getUpdateMark());
		dspDevice.setPaid(this.ext.getPaid());
		dspDevice.setAaid(null);
		dspDevice.setHardware_model(this.device.getHardwareModel());
		dspDevice.setHardware_mechine(this.device.getModel());
		dspDevice.setBirthTime(this.device.getBirthMark());
		return dspDevice;
	}

	private YueKeApp getDspApp() {
		YueKeApp dspApp = new YueKeApp();
		dspApp.setId(this.adSlotBudgetWrap.getDspAppId());
		dspApp.setName(this.app.getName());
		dspApp.setBundle(this.getPackageName());
		dspApp.setVer(this.app.getVerName());
		dspApp.setPaid(null);
		dspApp.setKeywords(null);
		dspApp.setCat(null);
		return dspApp;
	}

	private List<YueKeImp> getDspImps() {
		List<YueKeImp> dspImps = new ArrayList<YueKeImp>();
		
		AdvertType advertType = AdvertType.get(this.slot.getType());
		
		YueKeBanner dspBanner = null;
		if(advertType != null && (advertType == AdvertType.SPLASH 
				|| advertType == AdvertType.INTERSTITIAL 
				|| advertType == AdvertType.BANNER ) ) {
			
			dspBanner = new YueKeBanner();
			dspBanner.setW(this.slot.getWidth());
			dspBanner.setH(this.slot.getHeight());
		}
		
		YueKeVideo dspVideo = null;
		if(advertType != null && (advertType == AdvertType.REWARDED_VIDEO 
				|| advertType == AdvertType.NATIVE) ) {
			
			dspVideo = new YueKeVideo();
			dspVideo.setW(this.slot.getWidth());
			dspVideo.setH(this.slot.getHeight());
			dspVideo.setType(1);
			dspVideo.setMinduration(null);
			dspVideo.setMaxduration(null);
			dspVideo.setStartdelay(null);
			dspVideo.setLinearity(null);
		}
		
		
		YueKeImp dspImp = new YueKeImp();
		dspImp.setAw(this.slot.getWidth());
		dspImp.setAh(this.slot.getHeight());
		dspImp.setTagid(this.adSlotBudgetWrap.getDspSlotId());
		dspImp.setBidfloor(Optional.ofNullable(this.deal.getBidfloor()).map(Integer::floatValue).orElse(0f));
		dspImp.setBanner(dspBanner);
		dspImp.setVideo(dspVideo);
		dspImp.setNativeAd(null);
		dspImp.setPmp(null);
		dspImp.setMts(null);
		
		dspImps.add(dspImp);
		return dspImps;
	}


	
	
	
	
	

}
