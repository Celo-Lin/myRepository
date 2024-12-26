package com.juan.adx.api.converter.yidian;

import com.juan.adx.api.context.TraceContext;
import com.juan.adx.api.converter.AbstractDspRequestParamConvert;
import com.juan.adx.model.dsp.yidian.enums.YiDianCarrierType;
import com.juan.adx.model.dsp.yidian.enums.YiDianDeviceType;
import com.juan.adx.model.dsp.yidian.enums.YiDianGenderType;
import com.juan.adx.model.dsp.yidian.enums.YiDianNetworkType;
import com.juan.adx.model.dsp.yidian.request.*;
import com.juan.adx.model.dsp.yueke.enums.YueKeOsType;
import com.juan.adx.model.entity.api.ConvertSspParam;

import java.util.ArrayList;
import java.util.List;

public class YiDianDspRequestParamConvert extends AbstractDspRequestParamConvert  {

	public YiDianDspRequestParamConvert(ConvertSspParam convertSspParam) {
		super(convertSspParam);
	}

	public YiDianDspRequestParam convert() {
		YiDianDspRequestParam yiDianDspRequestParam = new YiDianDspRequestParam();

		List<YiDianImp> dspAdSlots = this.getDspAdSlots();
		YiDianDevice dspDevice = this.getDspDevice();

		YiDianApp dspApp = this.getDspApp();
		YiDianUser dspUser = this.getDspUser();
		YiDianExt dspExt = this.getDspExt();

		yiDianDspRequestParam.setId(TraceContext.getTraceIdByContext());
		yiDianDspRequestParam.setImp(dspAdSlots);
		yiDianDspRequestParam.setDevice(dspDevice);
		yiDianDspRequestParam.setApp(dspApp);
		yiDianDspRequestParam.setUser(dspUser);
		//		yiDianDspRequestParam.setTmax(null);
		yiDianDspRequestParam.setTest(false);//生产改为false
		yiDianDspRequestParam.setExt(dspExt);
		return yiDianDspRequestParam;
	}

	private YiDianUser getDspUser() {

		YiDianGenderType genderType = YiDianGenderType.get(this.user.getGender());

		YiDianUser dspUser = new YiDianUser();

		if (this.user.getAge() != null) {
			dspUser.setAge(this.user.getAge());
		}
		dspUser.setGender(genderType.getDspType());

		return dspUser;
	}

	private YiDianDevice getDspDevice() {
		YiDianDevice dspDevice = new YiDianDevice();

		YiDianGeo yiDianGeo = new YiDianGeo();
		if (this.geo.getLatitude() != null) {
			yiDianGeo.setLat(this.geo.getLatitude());
		}
		if (this.geo.getLongitude() != null) {
			yiDianGeo.setLon(this.geo.getLongitude());
		}
		dspDevice.setGeo(yiDianGeo);

		dspDevice.setUa(this.network.getUserAgent());
		dspDevice.setIp(this.network.getIp());

		if (this.network.getIpv6() != null) {
			dspDevice.setIpv6(this.network.getIpv6());
		}

		YiDianDeviceType deviceType = YiDianDeviceType.get(this.device.getType());
		dspDevice.setDevicetype(deviceType.getDspType());

		YueKeOsType osType = YueKeOsType.get(this.device.getOsType());
		dspDevice.setOs(osType.getDspType());
		dspDevice.setOsv(this.device.getOsVersion());

		if (this.device.getHeight() != null) {
			dspDevice.setH(this.device.getHeight());
		}
		if (this.device.getWidth() != null) {
			dspDevice.setW(this.device.getWidth());
		}
		dspDevice.setModel(this.device.getModel());
		dspDevice.setMake(this.device.getMake());
		if (this.device.getPpi() != null) {
			dspDevice.setPpi(this.device.getPpi());
		}
		if (this.device.getDensity() != null) {
			dspDevice.setPxrate(this.device.getDensity());
		}

		YiDianNetworkType networkType = YiDianNetworkType.get(this.network.getNetworkType());
		dspDevice.setConnectiontype(networkType.getDspType());

		YiDianCarrierType carrierType = YiDianCarrierType.get(this.network.getCarrier());
		dspDevice.setOperatortype(carrierType.getDspType());

		if (this.device.getLanguage() != null) {
			dspDevice.setLanguage(this.device.getLanguage());
		}
//		dspDevice.addAllAppinstalled(null);
//		dspDevice.addAllAppuninstalled(null);

		if ("android".equals(dspDevice.getOs())) {
			if (this.deviceId.getImei() != null) {
				dspDevice.setDid(this.deviceId.getImei());
			}
			if (this.deviceId.getImeiMd5() != null) {
				dspDevice.setDidmd5(this.deviceId.getImeiMd5());
			}
			if (this.deviceId.getAndroidId() != null) {
				dspDevice.setDpid(this.deviceId.getAndroidId());
			}
			if (this.deviceId.getAndroidIdMd5() != null) {
				dspDevice.setDpidmd5(this.deviceId.getAndroidIdMd5());
			}
		} else if ("ios".equals(dspDevice.getOs())){
			if (this.deviceId.getIdfa() != null) {
				dspDevice.setDid(this.deviceId.getIdfa());
			}
			if (this.deviceId.getIdfaMd5() != null) {
				dspDevice.setDidmd5(this.deviceId.getIdfaMd5());
			}
			if (this.deviceId.getIdfv() != null) {
				dspDevice.setDpid(this.deviceId.getIdfv());
			}
//			dspDevice.setDpidmd5(null);
		}else {
//			dspDevice.setDid(null);
//			dspDevice.setDidmd5(null);
//			dspDevice.setDpid(null);
//			dspDevice.setDpidmd5(null);
		}
//		dspDevice.setDpidsha1(null);
//		dspDevice.setOaidsha1(null);
		if (this.deviceId.getOaid() != null) {
			dspDevice.setOaid(this.deviceId.getOaid());
		}
		if (this.deviceId.getOaidMd5() != null) {
			dspDevice.setOaidmd5(this.deviceId.getOaidMd5());
		}
//		dspDevice.setOaidsha1(null);
		if (this.network.getMac() != null) {
			dspDevice.setMac(this.network.getMac());
		}
//		dspDevice.setMacsha1(null);
		if (this.network.getMacMd5() != null) {
			dspDevice.setMacmd5(this.network.getMacMd5());
		}
		if (this.device.getBootMark() != null) {
			dspDevice.setBootMark(this.device.getBootMark());
		}
		if (this.device.getUpdateMark() != null) {
			dspDevice.setUpdateMark(this.device.getUpdateMark());
		}
		if (this.device.getHagVersion() != null) {
			dspDevice.setVercodeofag(this.device.getHagVersion());
		}
		if (this.device.getHmsVersion() != null) {
			dspDevice.setVercodeofhms(this.device.getHmsVersion());
		}

		if (this.device.getDeviceNameMd5() != null) {
			dspDevice.setDevicenamemd5(this.device.getDeviceNameMd5());
		}
		if (this.device.getModel() != null) {
			dspDevice.setHardwaremachine(this.device.getModel());
		}
		if (this.device.getSysMemorySize() != null) {
			dspDevice.setPhysicalmemorysize(this.device.getSysMemorySize());
		}
		if (this.device.getSysDiskSize() != null) {
			dspDevice.setHarddisksize(this.device.getSysDiskSize());
		}
		if (this.device.getSysUpdateTime() != null) {
			dspDevice.setSystemupdatetime(this.device.getSysUpdateTime());
		}
		if (this.network.getCountry() != null) {
			dspDevice.setCountrycode(this.network.getCountry());
		}
		if (this.device.getTimeZone() != null) {
			dspDevice.setTimezone(this.device.getTimeZone());
		}
		if (this.device.getSysCompilingTime() != null) {
			dspDevice.setDeviceinitializetime(this.device.getSysCompilingTime());
		}
//		dspDevice.setT2(null);
//		dspDevice.setT8(null);
//		dspDevice.setKid(null);
		if (this.caId.getCaid() != null) {
			dspDevice.setCaid(this.caId.getCaid());
		}
		return dspDevice;
	}

	private YiDianApp getDspApp() {
		YiDianApp dspApp = new YiDianApp();
		dspApp.setName(this.app.getName());
		dspApp.setBundle(this.getPackageName());
		dspApp.setVer(this.app.getVerName());
		return dspApp;
	}

	private List<YiDianImp> getDspAdSlots() {
		List<YiDianImp> dspAdSlots = new ArrayList<YiDianImp>();

		YiDianImp dspAdSlot = new YiDianImp();
		dspAdSlot.setSlot_id(this.adSlotBudgetWrap.getDspSlotId());
		if (this.deal.getBidfloor() != null) {
			dspAdSlot.setBidfloor(this.deal.getBidfloor());
		}
//		dspAdSlot.setDealIds();
		
		dspAdSlots.add(dspAdSlot);
		return dspAdSlots;
	}

	private YiDianExt getDspExt() {
		YiDianExt dspExt = new YiDianExt();
		dspExt.setDeeplinkSupported(true);
		return dspExt;
	}

}
