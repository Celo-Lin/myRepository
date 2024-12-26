package com.juan.adx.api.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.protobuf.util.JsonFormat;
import com.juan.adx.api.config.ApiParameterConfig;
import com.juan.adx.api.context.TraceContext;
import com.juan.adx.model.enums.InteractionType;
import com.juan.adx.model.enums.MacroParameters;
import com.juan.adx.model.ssp.common.request.SspReqApp;
import com.juan.adx.model.ssp.common.request.SspReqDeal;
import com.juan.adx.model.ssp.common.request.SspReqDevice;
import com.juan.adx.model.ssp.common.request.SspReqDeviceId;
import com.juan.adx.model.ssp.common.request.SspReqGeo;
import com.juan.adx.model.ssp.common.request.SspReqNetwork;
import com.juan.adx.model.ssp.common.request.SspReqSlot;
import com.juan.adx.model.ssp.common.request.SspReqUser;
import com.juan.adx.model.ssp.common.request.SspRequestParam;
import com.juan.adx.model.ssp.common.response.SspRespAdInfo;
import com.juan.adx.model.ssp.common.response.SspRespApp;
import com.juan.adx.model.ssp.common.response.SspRespImage;
import com.juan.adx.model.ssp.common.response.SspRespMiniProgram;
import com.juan.adx.model.ssp.common.response.SspRespTrack;
import com.juan.adx.model.ssp.common.response.SspRespVideo;
import com.juan.adx.model.ssp.wifi.WifiRtbProtobuf;
import com.juan.adx.model.ssp.wifi.WifiRtbProtobuf.BidRequest;
import com.juan.adx.model.ssp.wifi.enums.WifiAdvertType;
import com.juan.adx.model.ssp.wifi.enums.WifiCarrierType;
import com.juan.adx.model.ssp.wifi.enums.WifiDeviceType;
import com.juan.adx.model.ssp.wifi.enums.WifiNetworkType;
import com.juan.adx.model.ssp.wifi.enums.WifiOsType;

import lombok.extern.slf4j.Slf4j;

/**
 * WIFI万能钥匙SSP
 */
@Slf4j
@RestController
@RequestMapping("/ssp")
public class WifiAction {
	
	@Resource
	private AdvertAction advertAction;
	
//	@RequestMapping(path = "/wifi",  consumes = {"application/octet-stream;charset=UTF-8"}, produces = {"application/octet-stream;charset=UTF-8"})
//	public WifiRtbProtobuf.BidResponse processData(@RequestBody WifiRtbProtobuf.BidRequest wifiRequestParam,
//			HttpServletRequest request, HttpServletResponse response) {
    @PostMapping(value = "/wifi", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public byte[] processData(@RequestBody byte[] protobufData,
				HttpServletRequest request, HttpServletResponse response) {
    	
		try {
			WifiRtbProtobuf.BidRequest wifiRequestParam = WifiRtbProtobuf.BidRequest.parseFrom(protobufData);
			
			String slotId = wifiRequestParam.getSlot().getId();
			if (ApiParameterConfig.printLogSspRequestSwitch) {
				String requestParamJson = JsonFormat.printer().print(wifiRequestParam);
				log.info("wifi ssp api request, traceId:{} | slotId:{} | requestParam:{}", TraceContext.getTraceIdByContext(), slotId, requestParamJson);
			}
			
			WifiRtbProtobuf.BidResponse responseParam = this.process(wifiRequestParam);
			
			if (ApiParameterConfig.printLogSspRequestSwitch) {
				String responseParamJson = JsonFormat.printer().print(responseParam);
				log.info("wifi ssp api response, traceId:{} | slotId:{} | responseParam:{}", TraceContext.getTraceIdByContext(), slotId, responseParamJson);
			}
			
			return responseParam.toByteArray();
		} catch (Exception e) {
			/*if (ApiParameterConfig.printLogSspRequestSwitch) {
				log.error("wifi ssp api error. ", e);
			}*/
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			return null;
		}
		
    }

	private WifiRtbProtobuf.BidResponse process(BidRequest wifiRequestParam) {
		
		SspRequestParam sspRequestParam = this.buildSspRequestParam(wifiRequestParam);
		
		List<SspRespAdInfo> adInfos = this.advertAction.getad(sspRequestParam);
		
		WifiRtbProtobuf.BidResponse bidResponse = this.buildSspResponseParam(adInfos, wifiRequestParam);
		return bidResponse;
	}
	
	private WifiRtbProtobuf.BidResponse buildSspResponseParam(List<SspRespAdInfo> adInfos, BidRequest wifiRequestParam){
		WifiRtbProtobuf.BidResponse.Builder bidResponseBuilder = WifiRtbProtobuf.BidResponse.newBuilder();
		
		WifiRtbProtobuf.Slot wifiSlot = wifiRequestParam.getSlot();
		
		bidResponseBuilder.setSlotId(Optional.ofNullable(wifiSlot).map( WifiRtbProtobuf.Slot::getId).orElse(""));
		bidResponseBuilder.setRequestId(wifiRequestParam.getRequestId());
		for (SspRespAdInfo sspRespAdInfo : adInfos) {
			
			InteractionType interactionType = InteractionType.get(sspRespAdInfo.getInteractionType());
			if(InteractionType.DOWNLOAD == interactionType) {
				continue;
			}
			
			WifiRtbProtobuf.Ad.Builder adBuilder = WifiRtbProtobuf.Ad.newBuilder();
			
			List<WifiRtbProtobuf.Image> images = new ArrayList<WifiRtbProtobuf.Image>();
			List<SspRespImage> sspRespImages = CollectionUtils.isNotEmpty( sspRespAdInfo.getImages()) ? sspRespAdInfo.getImages() : new ArrayList<SspRespImage>();
			for (SspRespImage sspRespImage : sspRespImages) {
				WifiRtbProtobuf.Image.Builder imageBuilder = WifiRtbProtobuf.Image.newBuilder(); 
				imageBuilder.setUrl(sspRespImage.getUrl());
				imageBuilder.setWidth(sspRespImage.getWidth());
				imageBuilder.setHeight(sspRespImage.getHeight());
				imageBuilder.setMd5("");
				images.add(imageBuilder.build());
			}
			
			SspRespApp sspRespApp = sspRespAdInfo.getApp();
			WifiRtbProtobuf.TargetApp.Builder targetAppBuilder = WifiRtbProtobuf.TargetApp.newBuilder();
			targetAppBuilder.setId("");
			targetAppBuilder.setName(Optional.ofNullable(sspRespApp).map(v -> v.getName()).orElse(""));
			targetAppBuilder.setVersion(0);
			targetAppBuilder.setVersionName(Optional.ofNullable(sspRespApp).map(v -> v.getVersion()).orElse(""));
			targetAppBuilder.setMarket("");
			targetAppBuilder.setPkgName(Optional.ofNullable(sspRespApp).map(v -> v.getPkgName()).orElse(""));
			targetAppBuilder.setIcon(Optional.ofNullable(sspRespApp).map(v -> v.getIconUrl()).orElse(""));
			targetAppBuilder.setSize(Optional.ofNullable(sspRespApp).map(v -> v.getSize()).map(Long::intValue).orElse(0));
			targetAppBuilder.setMd5(Optional.ofNullable(sspRespApp).map(v -> v.getPkgMd5()).orElse(""));
			targetAppBuilder.setDeveloper(Optional.ofNullable(sspRespApp).map(v -> v.getCorporate()).orElse(""));
			targetAppBuilder.setPrivacy(Optional.ofNullable(sspRespApp).map(v -> v.getPrivacyPolicyUrl()).orElse(""));
			targetAppBuilder.setPermissionUrl(Optional.ofNullable(sspRespApp).map(v -> v.getPermissionUrl()).orElse(""));
			targetAppBuilder.setIntroductionUrl("");
			
			
			SspRespVideo sspRespVideo = sspRespAdInfo.getVideo();
			WifiRtbProtobuf.Video.Builder videoBuilder = WifiRtbProtobuf.Video.newBuilder();
			if(sspRespVideo != null) {
				videoBuilder.setUrl(Optional.ofNullable(sspRespVideo.getUrl()).orElse(""));
				videoBuilder.setDuration(Optional.ofNullable(sspRespVideo.getDuration()).orElse(0));
				videoBuilder.setSize(Optional.ofNullable(sspRespVideo.getSize()).orElse(0));
				videoBuilder.setWidth(Optional.ofNullable(sspRespVideo.getWidth()).orElse(0));
				videoBuilder.setHeight(Optional.ofNullable(sspRespVideo.getHeight()).orElse(0));
			}
			
			SspRespMiniProgram sspRespMiniProgram = sspRespAdInfo.getMiniProgram();
			
			WifiRtbProtobuf.Material.Builder materialBuilder = WifiRtbProtobuf.Material.newBuilder();
			materialBuilder.setIdeaId("");
			materialBuilder.setIdeaGroupId("");
			materialBuilder.setUnitId("");
			materialBuilder.setPlanId("");
			materialBuilder.setUserId("");
			materialBuilder.setLandingUrl(Optional.ofNullable(sspRespAdInfo.getLandingPageUrl()).orElse(""));
			materialBuilder.setDownloadUrl(Optional.ofNullable(sspRespAdInfo.getDownloadUrl()).orElse(""));
			materialBuilder.setDeeplinkUrl(Optional.ofNullable(sspRespAdInfo.getDeeplink()).orElse(""));
			materialBuilder.setMarketUrl("");
			materialBuilder.setTitle(Optional.ofNullable(sspRespAdInfo.getTitle()).orElse(""));
			materialBuilder.setDesc(Optional.ofNullable(sspRespAdInfo.getDesc()).orElse(""));
			materialBuilder.addAllImages(images);
			materialBuilder.setApp(targetAppBuilder.build());
			materialBuilder.setVideo(videoBuilder.build());
			if(sspRespMiniProgram != null) {
				materialBuilder.setMiniprogramAppid(Optional.ofNullable(sspRespMiniProgram.getMiniProgramId()).orElse(""));
				materialBuilder.setMiniprogramPagePath(Optional.ofNullable(sspRespMiniProgram.getMiniProgramPath()).orElse(""));
			}
			
			SspRespTrack sspRespTrack = sspRespAdInfo.getTrack();
			
			List<String> impUrls = this.getSspTrackUrls(sspRespTrack.getShowUrls());
			List<String> clickUrls = this.getSspTrackUrls(sspRespTrack.getClickUrls());
			List<String> videoStartUrls = this.getSspTrackUrls(sspRespTrack.getVideoStartUrls());
			List<String> videoEndUrls = this.getSspTrackUrls(sspRespTrack.getVideoCompleteUrls());
			List<String> videoPauseUrls = this.getSspTrackUrls(sspRespTrack.getVideoPauseUrls());
			List<String> videoErrorUrls = this.getSspTrackUrls(sspRespTrack.getVideoFailUrls());
			List<String> videoQuartileUrls = this.getSspTrackUrls(sspRespTrack.getVideoQuartileUrls());
			List<String> videoHalfUrls = this.getSspTrackUrls(sspRespTrack.getVideoHalfUrls());
			List<String> videoThreeQuartileUrls = this.getSspTrackUrls(sspRespTrack.getVideoThreeQuartileUrls());
			
			List<WifiRtbProtobuf.VideoProgressTracking> videoProcessPercentUrls = this.getProcessPercentUrls(videoQuartileUrls, 
					videoHalfUrls, videoThreeQuartileUrls);
			
			List<WifiRtbProtobuf.VideoProgressTracking> videoProcessDurationUrls = new ArrayList<WifiRtbProtobuf.VideoProgressTracking>();
			
			WifiRtbProtobuf.TrackingList.Builder trackingListBuilder = WifiRtbProtobuf.TrackingList.newBuilder();
			trackingListBuilder.addAllImpUrls(impUrls);
			trackingListBuilder.addAllClickUrls(clickUrls);
			trackingListBuilder.addAllVideoStartUrls(videoStartUrls);
			trackingListBuilder.addAllVideoEndUrls(videoEndUrls);
			trackingListBuilder.addAllVideoPauseUrls(videoPauseUrls);
			trackingListBuilder.addAllVideoErrorUrls(videoErrorUrls);
			trackingListBuilder.addAllVideoProcessPercentUrls(videoProcessPercentUrls);
			trackingListBuilder.addAllVideoProcessDurationUrls(videoProcessDurationUrls);
			
			adBuilder.setMaterial(materialBuilder.build());
			adBuilder.setTrackingList(trackingListBuilder.build());
			adBuilder.setCpm(sspRespAdInfo.getBidPrice());
//			adBuilder.setCpm(RandomUtils.nextInt(1000, 10000));
			bidResponseBuilder.addAds(adBuilder.build());
		}
		
		return bidResponseBuilder.build();
	}
	
	private List<WifiRtbProtobuf.VideoProgressTracking> getProcessPercentUrls(List<String> videoQuartileUrls,
			List<String> videoHalfUrls, List<String> videoThreeQuartileUrls) {
		List<WifiRtbProtobuf.VideoProgressTracking> videoProcessPercentUrls = new ArrayList<WifiRtbProtobuf.VideoProgressTracking>();
		
		if(CollectionUtils.isNotEmpty(videoQuartileUrls)) {
			WifiRtbProtobuf.VideoProgressTracking.Builder videoProgressTrackingBuilder = WifiRtbProtobuf.VideoProgressTracking.newBuilder();
			videoProgressTrackingBuilder.setProcess(Double.valueOf(0.25 * 10000).intValue());
			videoProgressTrackingBuilder.addAllUrls(videoQuartileUrls);
			videoProcessPercentUrls.add(videoProgressTrackingBuilder.build());
		}
		if(CollectionUtils.isNotEmpty(videoHalfUrls)) {
			WifiRtbProtobuf.VideoProgressTracking.Builder videoProgressTrackingBuilder = WifiRtbProtobuf.VideoProgressTracking.newBuilder();
			videoProgressTrackingBuilder.setProcess(Double.valueOf(0.5 * 10000).intValue());
			videoProgressTrackingBuilder.addAllUrls(videoHalfUrls);
			videoProcessPercentUrls.add(videoProgressTrackingBuilder.build());
		}
		if(CollectionUtils.isNotEmpty(videoThreeQuartileUrls)) {
			WifiRtbProtobuf.VideoProgressTracking.Builder videoProgressTrackingBuilder = WifiRtbProtobuf.VideoProgressTracking.newBuilder();
			videoProgressTrackingBuilder.setProcess(Double.valueOf(0.75 * 10000).intValue());
			videoProgressTrackingBuilder.addAllUrls(videoThreeQuartileUrls);
			videoProcessPercentUrls.add(videoProgressTrackingBuilder.build());
		}
		return videoProcessPercentUrls;
	}
	
	private List<String> getSspTrackUrls(List<String> trackUrls){
		List<String> replaceTrackUrls = new ArrayList<String>();
		if(CollectionUtils.isEmpty(trackUrls)) {
			return replaceTrackUrls;
		}
		for (String trackUrl : trackUrls) {
			trackUrl = StringUtils.replaceOnce(trackUrl, MacroParameters.DOWN_X.getMacro(), "__DOWN_X__");
			trackUrl = StringUtils.replaceOnce(trackUrl, MacroParameters.DOWN_Y.getMacro(), "__DOWN_Y__");
			trackUrl = StringUtils.replaceOnce(trackUrl, MacroParameters.UP_X.getMacro(), "__UP_X__");
			trackUrl = StringUtils.replaceOnce(trackUrl, MacroParameters.UP_Y.getMacro(), "__UP_Y__");
			trackUrl = StringUtils.replaceOnce(trackUrl, MacroParameters.TS_S.getMacro(), "__STIME__");
			replaceTrackUrls.add(trackUrl);
		}
		return replaceTrackUrls;
	}
	
	
	private SspRequestParam buildSspRequestParam(BidRequest wifiRequestParam) {
		
		
		SspReqSlot slot = null;
		WifiRtbProtobuf.Slot wifiSlot = wifiRequestParam.getSlot();
		if(wifiSlot != null) {
			WifiRtbProtobuf.SlotType wifiSlotType = wifiSlot.getType();
			WifiAdvertType wifiAdvertType = WifiAdvertType.getBySspType(wifiSlotType.getNumber());
			
			Integer slotId = wifiSlot.getId() != null ? Integer.valueOf(wifiSlot.getId()) : null;
			slot = new SspReqSlot();
			slot.setAdSlotId(slotId);
			slot.setType(wifiAdvertType.getType());
			slot.setWidth(0);
			slot.setHeight(0);
			slot.setMaterialType(null);
		}
		
		SspReqDeal deal = null;
		
		SspReqApp app = null;
		WifiRtbProtobuf.SourceApp wifiApp = wifiRequestParam.getSourceApp();
		if(wifiApp != null) {
			app = new SspReqApp();
			app.setName(wifiApp.getName());
			app.setVerName(null);
			app.setPkgName(wifiApp.getPkgName());
			app.setAppStoreVersion(null);
		}
		
		SspReqUser user = null;
		WifiRtbProtobuf.UserData wifiUser = wifiRequestParam.getUser();
		if(wifiUser != null) {
			user = new SspReqUser();
			user.setGender(null);
			user.setAge(null);
			user.setInterest(null);
			user.setInstalled(wifiUser.getInstalledAppsList());
		}
		
		SspReqDevice device = null;
		SspReqNetwork network = null;
		SspReqDeviceId deviceId = null;
		WifiRtbProtobuf.Device wifiDevice = wifiRequestParam.getDevice();
		WifiRtbProtobuf.DeviceID wifiDeviceId = wifiDevice.getDeviceId();
		WifiRtbProtobuf.Network wifiNetwork = wifiRequestParam.getNetwork();
		if(wifiDevice != null) {
			WifiOsType wifiOsType = WifiOsType.getBySspType(wifiDevice.getOs().getNumber());
			WifiDeviceType wifiDeviceType = WifiDeviceType.getBySspType(wifiDevice.getType().getNumber());
			
			device = new SspReqDevice();
			device.setOsType(wifiOsType.getType());
			device.setType(wifiDeviceType.getType());
			device.setOsVersion(wifiDevice.getOsVersion());
			device.setOsUiVersion(null);
			device.setAndroidApiLevel(wifiDevice.getApiLevel());
			device.setLanguage(null);
			device.setTimeZone(null);
			device.setSysCompilingTime(null);
			device.setSysUpdateTime(wifiDevice.getSysUpdateTime());
			device.setSysStartupTime(wifiDevice.getBootTime());
			device.setBirthMark(null);
			device.setBootMark(wifiDevice.getBootMark());
			device.setUpdateMark(wifiDevice.getUpdateMark());
			device.setRomVersion(null);
			device.setDeviceName(null);
			device.setDeviceNameMd5(null);
			device.setSysMemorySize(null);
			device.setSysDiskSize(null);
			device.setCpuNum(null);
			device.setModel(wifiDevice.getModel());
			device.setHardwareModel(null);
			device.setHmsVersion(wifiDevice.getHwHmsVerCode());
			device.setHarmonyOsVersion(null);
			device.setHagVersion(wifiDevice.getHwAgVerCode());
			device.setSupportDeeplink(1);
			device.setSupportUniversal(1);
			device.setMake(wifiDevice.getVendor());
			device.setBrand(wifiDevice.getVendor());
			device.setImsi(null);
			device.setWidth(wifiDevice.getScreenWidth());
			device.setHeight(wifiDevice.getScreenHeight());
			device.setDensity(null);
			device.setDpi(wifiDevice.getScreenDpi());
			device.setPpi(Objects.nonNull(wifiDevice.getScreenPpi()) ? (int) wifiDevice.getScreenPpi() : null );
			device.setOrientation(0);
			device.setScreenSize(null);
			device.setSerialno(null);
			
			Integer netType= Optional.ofNullable(wifiNetwork.getType()).map(WifiRtbProtobuf.NetType :: getNumber).orElse(null);
			WifiNetworkType wifiNetworkType = WifiNetworkType.getBySspType(netType);
			
			Integer carrierType = Optional.ofNullable(wifiNetwork.getCarrier()).map(WifiRtbProtobuf.Carrier :: getNumber).orElse(null);
			WifiCarrierType wifiCarrierType = WifiCarrierType.getBySspType(carrierType);
			
			network = new SspReqNetwork();
			network.setUserAgent(wifiDevice.getUserAgent());
			network.setIp(Optional.ofNullable(wifiNetwork.getIpv4()).orElse(null));
			network.setIpv6(null);
			network.setMac(Optional.ofNullable(wifiDeviceId.getMac()).orElse(null));
			network.setMacMd5(null);
			network.setMacSha1(null);
			network.setSsid(null);
			network.setBssid(null);
			network.setCarrier(wifiCarrierType.getType());
			network.setNetworkType(wifiNetworkType.getType());
			network.setMcc(null);
			network.setMnc(null);
			network.setCountry(null);
			
			
			deviceId = new SspReqDeviceId();
			deviceId.setImei(Optional.ofNullable(wifiDeviceId.getImei()).orElse(null));
			deviceId.setImeiMd5(null);
			deviceId.setOaid(Optional.ofNullable(wifiDeviceId.getOaid()).orElse(null));
			deviceId.setOaidMd5(null);
			deviceId.setAndroidId(Optional.ofNullable(wifiDeviceId.getAndroidId()).orElse(null));
			deviceId.setAndroidIdMd5(null);
			deviceId.setAndroidIdSha1(null);
			deviceId.setIdfa(Optional.ofNullable(wifiDeviceId.getIdfa()).orElse(null));
			deviceId.setIdfaMd5(null);
			deviceId.setIdfv(null);
			deviceId.setOpenUdid(null);
		}
		
		SspReqGeo geo = null;
		WifiRtbProtobuf.Geo wifiGeo = wifiRequestParam.getGeo();
		if(wifiGeo != null) {
			geo = new SspReqGeo();
			geo.setCoordinateType(null);
			geo.setLatitude(wifiGeo.getLatitude());
			geo.setLongitude(wifiGeo.getLongitude());
			geo.setTimestamp(null);
		}
		
		
		SspRequestParam sspRequestParam = new SspRequestParam();
		sspRequestParam.setRequestId(wifiRequestParam.getRequestId());
		sspRequestParam.setSlot(slot);
		sspRequestParam.setDeal(deal);
		sspRequestParam.setApp(app);
		sspRequestParam.setUser(user);
		sspRequestParam.setDevice(device);
		sspRequestParam.setDeviceId(deviceId);
		sspRequestParam.setCaId(null);
		sspRequestParam.setNetwork(network);
		sspRequestParam.setGeo(geo);
		sspRequestParam.setExt(null);
		return sspRequestParam;
	}
	
	
}
