package com.juan.adx.api.converter.haoya;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.juan.adx.api.context.TraceContext;
import com.juan.adx.api.converter.AbstractDspRequestParamConvert;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.model.dsp.haoya.enums.HaoYaAdvertType;
import com.juan.adx.model.dsp.haoya.enums.HaoYaCarrierType;
import com.juan.adx.model.dsp.haoya.enums.HaoYaDeviceType;
import com.juan.adx.model.dsp.haoya.enums.HaoYaGenderType;
import com.juan.adx.model.dsp.haoya.enums.HaoYaNetworkType;
import com.juan.adx.model.dsp.haoya.enums.HaoYaOrientationType;
import com.juan.adx.model.dsp.haoya.enums.HaoYaOsType;
import com.juan.adx.model.dsp.haoya.request.HaoYaAdSlot;
import com.juan.adx.model.dsp.haoya.request.HaoYaApp;
import com.juan.adx.model.dsp.haoya.request.HaoYaCaid;
import com.juan.adx.model.dsp.haoya.request.HaoYaDevice;
import com.juan.adx.model.dsp.haoya.request.HaoYaDspRequestParam;
import com.juan.adx.model.dsp.haoya.request.HaoYaGeo;
import com.juan.adx.model.dsp.haoya.request.HaoYaTemplate;
import com.juan.adx.model.dsp.haoya.request.HaoYaUser;
import com.juan.adx.model.entity.api.ConvertSspParam;
import com.juan.adx.model.enums.CooperationMode;

public class HaoYaDspRequestParamConvert extends AbstractDspRequestParamConvert  {

	public HaoYaDspRequestParamConvert(ConvertSspParam convertSspParam) {
		super(convertSspParam);
	}

	public HaoYaDspRequestParam convert() {
		HaoYaDspRequestParam dspRequestParam = new HaoYaDspRequestParam();
		
		List<HaoYaAdSlot> dspAdSlots = this.getDspAdSlots();
		HaoYaApp dspApp = this.getDspApp();
		HaoYaDevice dspDevice = this.getDspDevice();
		HaoYaUser dspUser = this.getDspUser();
		
		dspRequestParam.setReqid(TraceContext.getTraceIdByContext());
		dspRequestParam.setApiVersion("1.0.8");
		dspRequestParam.setAdSlotList(dspAdSlots);
		dspRequestParam.setApp(dspApp);
		dspRequestParam.setDevice(dspDevice);
		dspRequestParam.setSite(null);
		dspRequestParam.setUser(dspUser);
		dspRequestParam.setBcat(null);
		dspRequestParam.setBadv(null);
		dspRequestParam.setAppList(null);
		dspRequestParam.setExt(null);
		return dspRequestParam;
	}

	private HaoYaUser getDspUser() {
		
		HaoYaGenderType genderType = HaoYaGenderType.get(this.user.getGender());
		
		HaoYaUser dspUser = new HaoYaUser();
		dspUser.setUserId(TraceContext.getTraceIdByContext());
		dspUser.setGender(genderType.getDspType());
		dspUser.setAge(this.user.getAge());
		dspUser.setKeywords(null);
		return dspUser;
	}

	private HaoYaDevice getDspDevice() {
		HaoYaDevice dspDevice = new HaoYaDevice();
		
		dspDevice.setUa(this.network.getUserAgent());
		
		HaoYaDeviceType deviceType = HaoYaDeviceType.get(this.device.getType());
		dspDevice.setType(deviceType.getDspType());
		dspDevice.setBrand(this.device.getBrand());
		dspDevice.setModel(this.device.getModel());
		dspDevice.setMake(this.device.getMake());
		dspDevice.setHmsVersion(this.device.getHmsVersion());
		dspDevice.setAsVersion(null);
		
		HaoYaOsType osType = HaoYaOsType.get(this.device.getOsType());
		dspDevice.setOs(osType.getDspType());
		dspDevice.setOsVersion(this.device.getOsVersion());
		dspDevice.setDensity(null);
		dspDevice.setPpi(this.device.getPpi());
		dspDevice.setPxRatio(this.device.getDensity());
		dspDevice.setWidth(this.device.getWidth());
		dspDevice.setHeight(this.device.getHeight());
		
		HaoYaCarrierType carrierType = HaoYaCarrierType.get(this.network.getCarrier());
		dspDevice.setCarrier(carrierType.getDspType());
		
		HaoYaNetworkType networkType = HaoYaNetworkType.get(this.network.getNetworkType());
		dspDevice.setNetwork(networkType.getDspType());
		
		HaoYaOrientationType orientationType = HaoYaOrientationType.get(this.device.getOrientation());
		dspDevice.setOrientation(orientationType.getDspType());
		dspDevice.setIp(this.network.getIp());
		dspDevice.setIpv6(this.network.getIpv6());
		dspDevice.setImei(this.deviceId.getImei());
		dspDevice.setImeiMD5(this.deviceId.getImeiMd5());
		dspDevice.setOaid(this.deviceId.getOaid());
		dspDevice.setOaidMD5(this.deviceId.getOaidMd5());
		dspDevice.setDpid(this.deviceId.getAndroidId());
		dspDevice.setDpidMD5(this.deviceId.getAndroidIdMd5());
		dspDevice.setMac(this.network.getMac());
		dspDevice.setMacMD5(this.network.getMacMd5());
		dspDevice.setIdfa(this.deviceId.getIdfa());
		dspDevice.setIdfaMD5(this.deviceId.getIdfaMd5());
		dspDevice.setBootMark(this.device.getBootMark());
		dspDevice.setUpdateMark(this.device.getUpdateMark());
		dspDevice.setBirthTime(this.device.getSysCompilingTime());
		dspDevice.setBootTime(this.device.getSysStartupTime());
		dspDevice.setUpdateTime(this.device.getSysUpdateTime());
		dspDevice.setLanguage(this.device.getLanguage());
		
		HaoYaGeo geo = new HaoYaGeo();
		geo.setLat(this.geo.getLatitude());
		geo.setLon(this.geo.getLongitude());
		geo.setCountry(null);
		geo.setProvince(null);
		geo.setCity(null);
		dspDevice.setGeo(geo);
		dspDevice.setPaid(this.ext.getPaid());
		
		HaoYaCaid caid = new HaoYaCaid();
		caid.setId(this.caId.getCaid());
		caid.setVersion(this.caId.getVersion());
		caid.setBootTimeInSec(null);
		caid.setCountryCode(this.network.getCountry());
		caid.setLanguage(this.device.getLanguage());
		caid.setDeviceName(this.device.getDeviceName());
		caid.setModel(this.device.getHardwareModel());
		caid.setSystemVersion(this.device.getOsVersion());
		caid.setMachine(this.device.getModel());
		caid.setCarrierInfo(null);
		caid.setMemory(Optional.ofNullable(this.device.getSysMemorySize()).map(v -> v.toString()).orElse(""));
		caid.setDisk(Optional.ofNullable(this.device.getSysDiskSize()).map(v -> v.toString()).orElse(""));
		caid.setSysFileTime(null);
		caid.setTimeZone(this.device.getTimeZone());
		caid.setInitTime(null);
		
		dspDevice.setCaid(caid);
		dspDevice.setAaid(null);
		dspDevice.setElapseTime(null);
		dspDevice.setSysCpuNum(this.device.getCpuNum());
		dspDevice.setBatteryState(null);
		dspDevice.setBattery(null);
		dspDevice.setRomVersion(this.device.getRomVersion());
		dspDevice.setSdFreeSpace(null);
		return dspDevice;
	}

	private HaoYaApp getDspApp() {
		HaoYaApp dspApp = new HaoYaApp();
		dspApp.setAppId(this.app.getName());
		dspApp.setName(this.app.getName());
		dspApp.setPackageName(this.getPackageName());
		dspApp.setVersion(this.app.getVerName());
		dspApp.setMkt(null);
		dspApp.setMktSn(null);
		dspApp.setMktCat(null);
		dspApp.setMktTag(null);
		dspApp.setCategory(null);
		dspApp.setKeywords(null);
		return dspApp;
	}

	private List<HaoYaAdSlot> getDspAdSlots() {
		List<HaoYaAdSlot> dspAdSlots = new ArrayList<HaoYaAdSlot>();
		
		HaoYaAdvertType advertType = HaoYaAdvertType.get(this.slot.getType());
		
		HaoYaAdSlot dspAdSlot = new HaoYaAdSlot();
		dspAdSlot.setId(this.adSlotBudgetWrap.getDspSlotId());
		dspAdSlot.setImpId(String.format("%s%d", TraceContext.getTraceIdByContext(), LocalDateUtils.getNowMilliseconds()));
		dspAdSlot.setType(advertType.getDspType());
		dspAdSlot.setTemplate(this.getTemplates());
		dspAdSlot.setFloorPrice(this.deal.getBidfloor());
		dspAdSlot.setBillType(0);
		dspAdSlot.setBidType(CooperationMode.PD == this.mode ? 1 : 0);
		dspAdSlot.setWidth(this.slot.getWidth());
		dspAdSlot.setHeight(this.slot.getHeight());
		dspAdSlot.setDealId(null);
		dspAdSlot.setActionType(Arrays.asList(0));
		
		dspAdSlots.add(dspAdSlot);
		return dspAdSlots;
	}
	
	private List<HaoYaTemplate> getTemplates() {
		List<HaoYaTemplate> templates = new ArrayList<HaoYaTemplate>();
		for (int i = 1; i <= 7; i++) {
			HaoYaTemplate haoYaTemplate = new HaoYaTemplate();
			haoYaTemplate.setId(i);
			templates.add(haoYaTemplate);
		}
		return templates;
	}
	
	

}
