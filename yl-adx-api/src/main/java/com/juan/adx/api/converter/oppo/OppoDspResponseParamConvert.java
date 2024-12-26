package com.juan.adx.api.converter.oppo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.LongAdder;

import com.juan.adx.common.utils.LogPrintCheckUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson2.JSON;
import com.juan.adx.api.converter.AbstractDspResponseParamConvert;
import com.juan.adx.model.dsp.oppo.enums.OppoCarrierType;
import com.juan.adx.model.dsp.oppo.enums.OppoNetworkType;
import com.juan.adx.model.dsp.oppo.response.OppoApp;
import com.juan.adx.model.dsp.oppo.response.OppoDspResponseParam;
import com.juan.adx.model.dsp.oppo.response.OppoMaterialFile;
import com.juan.adx.model.dsp.oppo.response.OppoTracking;
import com.juan.adx.model.dsp.oppo.response.OppoUnionApiAdInfo;
import com.juan.adx.model.dsp.yueke.enums.YueKeOsType;
import com.juan.adx.model.entity.api.ConvertDspParam;
import com.juan.adx.model.enums.CooperationMode;
import com.juan.adx.model.enums.DeeplinkType;
import com.juan.adx.model.enums.Dsp;
import com.juan.adx.model.enums.InteractionType;
import com.juan.adx.model.enums.MacroParameters;
import com.juan.adx.model.ssp.common.response.SspRespAdInfo;
import com.juan.adx.model.ssp.common.response.SspRespApp;
import com.juan.adx.model.ssp.common.response.SspRespImage;
import com.juan.adx.model.ssp.common.response.SspRespMiniProgram;
import com.juan.adx.model.ssp.common.response.SspRespTrack;
import com.juan.adx.model.ssp.common.response.SspRespVideo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OppoDspResponseParamConvert extends AbstractDspResponseParamConvert {

	private final String DSP_NAME = this.dsp.getDesc();
	private final Dsp DSP = this.dsp;
	public static LongAdder longAdder = new LongAdder();
	
	public OppoDspResponseParamConvert(ConvertDspParam convertDspParam) {
		super(convertDspParam);
	}
	
	public List<SspRespAdInfo>  convert() {
		if (StringUtils.isBlank(this.dspRespData)) {
			if (LogPrintCheckUtils.needPrintLog(longAdder, 100)) {
				log.warn("longAdder：{}，{} 响应数据为空，响应数据：{} ",longAdder.sum(), DSP_NAME, this.dspRespData);
			}
            return null;
        }
		
		OppoDspResponseParam dspResponseParam = JSON.parseObject(this.dspRespData, OppoDspResponseParam.class);
        if(dspResponseParam == null) {
			if (LogPrintCheckUtils.needPrintLog(longAdder, 100)) {
				log.warn("longAdder：{}，{} 响应的JSON数据转换为JAVA对象为空，响应数据：{} ",longAdder.sum(), DSP_NAME, this.dspRespData);
			}
        	return null;
        }
        
        if(!Objects.equals(0, dspResponseParam.getCode())) {
			if (LogPrintCheckUtils.needPrintLog(longAdder, 100)) {
				log.warn("longAdder：{}，{} 响应异常状态码，响应数据：{} ",longAdder.sum(), DSP_NAME, this.dspRespData);
			}
        	return null;
        }
        
        List<OppoUnionApiAdInfo> dspAds = dspResponseParam.getAdList();
        
        if(CollectionUtils.isEmpty(dspAds)) {
			if (LogPrintCheckUtils.needPrintLog(longAdder, 100)) {
				log.warn("longAdder：{}，{} 响应json结构中dspAds为空，响应数据：{} ",longAdder.sum(), DSP_NAME, this.dspRespData);
			}
        	return null;
        }
        
        List<SspRespAdInfo> adInfos = new ArrayList<SspRespAdInfo>();
        
    	for (OppoUnionApiAdInfo dspAd : dspAds) {
    		
    		SspRespApp sspRespApp = this.getSspRespApp(dspAd);
			SspRespVideo sspRespVideo = this.getSspRespVideo(dspAd);
			List<SspRespImage> sspRespImages = this.getSspRespImages(dspAd);
			SspRespTrack sspRespTrack = this.getSspRespTrack(dspAd);
			SspRespMiniProgram sspRespMiniProgram = this.getSspRespMiniProgram(dspAd);
			List<String> adIcons = this.getAdIcons(dspAd);
			
			//过滤掉OPPO返回的视频素材
//			if(Objects.nonNull(sspRespVideo)) {
//				continue;
//			}

			
			InteractionType dspInteractionType = InteractionType.NON_INREACTION;
			String downloadUrl = Optional.ofNullable(dspAd.getApp()).map(v -> v.getDownloadUrl()).orElse(null);
			
			YueKeOsType osType = YueKeOsType.get(this.device.getOsType());
			Integer deeplinkType = osType == YueKeOsType.IOS ? DeeplinkType.IOS_UNIVERSAL_LINK.getType() : DeeplinkType.DEEPLINK.getType();
			
			
			if(StringUtils.isNotBlank(dspAd.getDeepLink())) {
				dspInteractionType = InteractionType.DEEPLINK;
			}else if(StringUtils.isNotBlank(dspAd.getTargetUrl())) {
				dspInteractionType = InteractionType.WEBVIEW;
			}else {
				dspInteractionType = InteractionType.DOWNLOAD;
			}
			
			SspRespAdInfo respAdInfo = new SspRespAdInfo();
			respAdInfo.setRequestId(this.sspRequestId);
			respAdInfo.setAdType(this.adSlotBudgetWrap.getBudgetType());
			respAdInfo.setMaterialType(null);
			respAdInfo.setInteractionType(dspInteractionType.getType());
			respAdInfo.setTitle(dspAd.getTitle());
			respAdInfo.setDesc(dspAd.getDesc());
			respAdInfo.setAdIcons(adIcons);
			respAdInfo.setDeeplinkType(deeplinkType);
			respAdInfo.setDeeplink(dspAd.getDeepLink());
			respAdInfo.setLandingPageUrl(dspAd.getTargetUrl());
			respAdInfo.setDownloadUrl(downloadUrl);
			respAdInfo.setBidPrice(Optional.ofNullable(dspAd.getPrice()).map(Long::intValue).orElse(null));
			respAdInfo.setWinNoticeUrl(getWinUrl(dspAd));
			respAdInfo.setLossNoticeUrl(null);
			
			respAdInfo.setApp(sspRespApp);
			respAdInfo.setVideo(sspRespVideo);
			respAdInfo.setImages(sspRespImages);
			respAdInfo.setMiniProgram(sspRespMiniProgram);
			respAdInfo.setTrack(sspRespTrack);
			
			adInfos.add(respAdInfo);
		}
        
        return adInfos;
	}
	
	
	private SspRespMiniProgram getSspRespMiniProgram(OppoUnionApiAdInfo dspAd) {
		return null;
	}

	private List<String> getAdIcons(OppoUnionApiAdInfo dspAd) {
		
		OppoMaterialFile dspMaterial = dspAd.getLogoFile();
		if(Objects.isNull(dspMaterial)) {
			return null;
		}
		List<String> adIcons = new ArrayList<String>();
		adIcons.add(dspMaterial.getUrl());
		return adIcons;
	}

	private SspRespApp getSspRespApp(OppoUnionApiAdInfo dspAd) {
		
		OppoApp dspApp = dspAd.getApp();
		if(Objects.isNull(dspApp)) {
			return null;
		}
		SspRespApp sspRespApp = new SspRespApp();
		sspRespApp.setName(dspApp.getAppName());
		sspRespApp.setVersion(dspApp.getVersionName());
		sspRespApp.setPkgName(dspApp.getAppPackage());
		sspRespApp.setPkgMd5(dspApp.getApkMd5());
		sspRespApp.setIconUrl(dspApp.getAppIconUrl());
		sspRespApp.setSize(dspApp.getApkSize());
		sspRespApp.setCorporate(dspApp.getDeveloperName());
		sspRespApp.setPrivacyPolicyUrl(dspApp.getPrivacyUrl());
		sspRespApp.setPermissionUrl(dspApp.getPermissionUrl());
		return sspRespApp;
	}
	
	private SspRespVideo getSspRespVideo(OppoUnionApiAdInfo dspAd) {
		
		List<OppoMaterialFile> dspMaterial = dspAd.getFileList();
		if(CollectionUtils.isEmpty(dspMaterial)) {
			return null;
		}
		OppoMaterialFile dspMaterialFile = null;
		for (OppoMaterialFile oppoMaterialFile : dspMaterial) {
			if(Objects.equals(2, oppoMaterialFile.getFileType())) {
				dspMaterialFile = oppoMaterialFile;
				break;
			}
		}
		if(dspMaterialFile == null) {
			return null;
		}
		SspRespVideo sspRespVideo = new SspRespVideo();
		sspRespVideo.setUrl(dspMaterialFile.getUrl());
		sspRespVideo.setCoverUrl(null);
		sspRespVideo.setDuration(Optional.ofNullable(dspAd.getVideoDuration()).map(v -> v / 1024).map(Long::intValue).orElse(null));
		sspRespVideo.setForceDuration(null);
		sspRespVideo.setSize(null);
		sspRespVideo.setWidth(null);
		sspRespVideo.setHeight(null);
		sspRespVideo.setEndUrlType(null);
		sspRespVideo.setEndUrl(null);
		return sspRespVideo;
	}

	private List<SspRespImage> getSspRespImages(OppoUnionApiAdInfo dspAd){
		
		List<OppoMaterialFile> dspMaterial = dspAd.getFileList();
		if(CollectionUtils.isEmpty(dspMaterial)) {
			return null;
		}
		
		List<SspRespImage> sspRespImages = new ArrayList<SspRespImage>();
		
		for (OppoMaterialFile oppoMaterialFile : dspMaterial) {
			if(!Objects.equals(1, oppoMaterialFile.getFileType())) {
				continue;
			}
			SspRespImage sspRespImage = new SspRespImage();
			sspRespImage.setHeight(null);
			sspRespImage.setWidth(null);
			sspRespImage.setUrl(oppoMaterialFile.getUrl());
			sspRespImages.add(sspRespImage);
		}
		return sspRespImages;
	}
	
	
	private final static String MACRO_PRICE = "$swp$";
    
    @SuppressWarnings("unused")
	private List<String> getWinUrl(OppoUnionApiAdInfo dspAd ) {
    	//只处理RTB自动模式的广告竞胜URL
    	if(this.mode == CooperationMode.PD) {
    		return null;
    	}

		List<OppoTracking> dspTrackings = Optional.ofNullable(dspAd.getTrackingList()).orElseGet(ArrayList::new);
    	if(CollectionUtils.isEmpty(dspTrackings)) {
			return null;
		}

		String priceEncryptKey = this.adSlotBudgetWrap.getPriceEncryptKey();
		String priceEncryptStr = OppoPriceEncrypt.encrypt(String.valueOf(dspAd.getPrice()), priceEncryptKey);
		List<String> winTrackUrlList = Lists.newArrayList();
		for (OppoTracking dspTrack : dspTrackings) {
			Integer dspTrackType = dspTrack.getTrackingEvent();
			List<String> replaceDspTrackUrls = MacroParameters.macroParametersReplace(DSP, dspTrack.getTrackUrls());
			if(Objects.equals(dspTrackType, 5)) {
				return this.macroParametersReplace(replaceDspTrackUrls, priceEncryptStr);
			}
		}
		return null;
    }
    
    private String getEncrypPrice(OppoUnionApiAdInfo dspAd) {
    	String priceEncryptKey = this.adSlotBudgetWrap.getPriceEncryptKey();
    	String priceEncryptStr = OppoPriceEncrypt.encrypt(String.valueOf(dspAd.getPrice()), priceEncryptKey); 
    	if(StringUtils.isBlank(priceEncryptStr)) {
    		return null;
    	}
    	return priceEncryptStr;
    }

	
	private SspRespTrack getSspRespTrack(OppoUnionApiAdInfo dspAd) {
		
		//解析DSP返回的监控链接，并将链接中的宏替换为平台宏
        List<String> showUrls = null;
        List<String> clickUrls = null;
        List<String> adLoadUrls = null;
        List<String> adSkipUrls = null;
        List<String> adCloseUrls = null;
        List<String> wechatOpenUrls = null;
        
        List<String> startDownloadUrls = null;
        List<String> finishDownloadUrls = null;
        List<String> startInstallUrls = null;
        List<String> finishInstallUrls = null;
        List<String> activeAppUrls = null;
        List<String> deeplinkTryUrls = null;
        List<String> deeplinkSuccessUrls = null;
        List<String> deeplinkFailureUrls = null;
        List<String> deeplinkClickUrls = null;
        List<String> deeplinkInstalledkUrls = null;
        List<String> deeplinkUninstallkUrls = null;
        
        List<String> videoStartUrls = null;
        List<String> videoClickUrls = null;
        List<String> videoCompleteUrls = null;
        List<String> videoFailUrls = null;
        List<String> videoCloseUrls = null;
        List<String> videoSkipUrls = null;
        List<String> videoPauseUrls = null;
        List<String> videoResumeUrls = null;
        List<String> videoReplayUrls = null;
        List<String> videoMuteUrls = null;
        List<String> videoUnmuteUrls = null;
        List<String> videoFullscreenUrls = null;
        List<String> videoExitFullscreenUrls = null;
        List<String> videoUpscrollUrls = null;
        List<String> videoDownscrollUrls = null;
        List<String> videoQuartileUrls = null;
        List<String> videoHalfUrls = null;
        List<String> videoThreeQuartileUrls = null;
        
        String encrypPriceStr = this.getEncrypPrice(dspAd);
        
        List<String> winTrackUrls = null;
        
        List<OppoTracking> dspTrackings = Optional.ofNullable(dspAd.getTrackingList()).orElseGet(ArrayList::new);
        for (OppoTracking dspTrack : dspTrackings) {
		   	
        	Integer dspTrackType = dspTrack.getTrackingEvent();
        	List<String> replaceDspTrackUrls = MacroParameters.macroParametersReplace(DSP, dspTrack.getTrackUrls());
        	
        	replaceDspTrackUrls = this.macroParametersReplace(replaceDspTrackUrls, encrypPriceStr);
        	
			if(Objects.equals(dspTrackType, 1)) {
				clickUrls = replaceDspTrackUrls;
			}else if(Objects.equals(dspTrackType, 2)) {
				showUrls = replaceDspTrackUrls;
			}else if(Objects.equals(dspTrackType, 3)) {
				adCloseUrls = replaceDspTrackUrls;
			}else if(Objects.equals(dspTrackType, 5)) {
				winTrackUrls = replaceDspTrackUrls;
			}else if(Objects.equals(dspTrackType, 10010)) {
				videoStartUrls = replaceDspTrackUrls;
			}else if(Objects.equals(dspTrackType, 10011)) {
				videoCompleteUrls = replaceDspTrackUrls;
			}else if(Objects.equals(dspTrackType, 10012)) {
				videoQuartileUrls = replaceDspTrackUrls;
			}else if(Objects.equals(dspTrackType, 10013)) {
				videoHalfUrls = replaceDspTrackUrls;
			}else if(Objects.equals(dspTrackType, 10014)) {
				videoThreeQuartileUrls = replaceDspTrackUrls;
			}
		}

        //组装平台自己的事件上报链接
        showUrls = Optional.ofNullable(showUrls).orElseGet(ArrayList::new);
        clickUrls = Optional.ofNullable(clickUrls).orElseGet(ArrayList::new);
        finishDownloadUrls = Optional.ofNullable(finishDownloadUrls).orElseGet(ArrayList::new);
        finishInstallUrls = Optional.ofNullable(finishInstallUrls).orElseGet(ArrayList::new);
        deeplinkSuccessUrls = Optional.ofNullable(deeplinkSuccessUrls).orElseGet(ArrayList::new);
        this.fillReportUrls(showUrls, clickUrls, finishDownloadUrls, finishInstallUrls, deeplinkSuccessUrls);
        
		/*List<String> winUrls = this.winUrl(dspAd, winTrackUrls);
		if(CollectionUtils.isNotEmpty(winUrls)) {
		    showUrls.addAll(winUrls);
		}*/
        
        if(CollectionUtils.isNotEmpty(winTrackUrls)) {
        	showUrls.addAll(winTrackUrls);
        }
        
		SspRespTrack sspRespTrack = new SspRespTrack();
		sspRespTrack.setShowUrls(showUrls);
		sspRespTrack.setClickUrls(clickUrls);
		sspRespTrack.setAdLoadUrls(adLoadUrls);
		sspRespTrack.setAdSkipUrls(adSkipUrls);
		sspRespTrack.setAdCloseUrls(adCloseUrls);
		sspRespTrack.setWechatOpenUrls(wechatOpenUrls);
		
		sspRespTrack.setStartDownloadUrls(startDownloadUrls);
		sspRespTrack.setFinishDownloadUrls(finishDownloadUrls);
		sspRespTrack.setStartInstallUrls(startInstallUrls);
		sspRespTrack.setFinishInstallUrls(finishInstallUrls);
		sspRespTrack.setActiveAppUrls(activeAppUrls);
		sspRespTrack.setDeeplinkTryUrls(deeplinkTryUrls);
		sspRespTrack.setDeeplinkSuccessUrls(deeplinkSuccessUrls);
		sspRespTrack.setDeeplinkFailureUrls(deeplinkFailureUrls);
		sspRespTrack.setDeeplinkClickUrls(deeplinkClickUrls);
		sspRespTrack.setDeeplinkInstalledkUrls(deeplinkInstalledkUrls);
		sspRespTrack.setDeeplinkUninstallkUrls(deeplinkUninstallkUrls);
		
		sspRespTrack.setVideoStartUrls(videoStartUrls);
		sspRespTrack.setVideoClickUrls(videoClickUrls);
		sspRespTrack.setVideoCompleteUrls(videoCompleteUrls);
		sspRespTrack.setVideoFailUrls(videoFailUrls);
		sspRespTrack.setVideoCloseUrls(videoCloseUrls);
		sspRespTrack.setVideoSkipUrls(videoSkipUrls);
		sspRespTrack.setVideoPauseUrls(videoPauseUrls);
		sspRespTrack.setVideoResumeUrls(videoResumeUrls);
		sspRespTrack.setVideoReplayUrls(videoReplayUrls);
		sspRespTrack.setVideoMuteUrls(videoMuteUrls);
		sspRespTrack.setVideoUnmuteUrls(videoUnmuteUrls);
		sspRespTrack.setVideoFullscreenUrls(videoFullscreenUrls);
		sspRespTrack.setVideoExitFullscreenUrls(videoExitFullscreenUrls);
		sspRespTrack.setVideoUpscrollUrls(videoUpscrollUrls);
		sspRespTrack.setVideoDownscrollUrls(videoDownscrollUrls);
		sspRespTrack.setVideoQuartileUrls(videoQuartileUrls);
		sspRespTrack.setVideoHalfUrls(videoHalfUrls);
		sspRespTrack.setVideoThreeQuartileUrls(videoThreeQuartileUrls);
		return sspRespTrack;
	}
	
	public List<String> macroParametersReplace(List<String> urls, String priceEncryptStr){
		if(CollectionUtils.isEmpty(urls)){
			return null;
		}
		List<String> resultList = new ArrayList<String>();
		urls.stream().forEach(url -> {
			
			String netType = "$nt$";
            if (StringUtils.containsIgnoreCase(url, netType)) {
            	OppoNetworkType networkType = OppoNetworkType.get(this.network.getNetworkType());
            	String value = networkType.getDspType();
                url = StringUtils.replaceIgnoreCase(url, netType, value);
            }
            
            String carrier = "$ca$";
            if (StringUtils.containsIgnoreCase(url, carrier)) {
            	OppoCarrierType carrierType = OppoCarrierType.get(this.network.getCarrier());
            	String value = String.valueOf(carrierType.getDspType());
                url = StringUtils.replaceIgnoreCase(url, carrier, value);
            }
            
            String bidResult = "$br$";
            if (StringUtils.containsIgnoreCase(url, bidResult)) {
            	url = StringUtils.replaceIgnoreCase(url, bidResult, "0");
            }
            
            String sspWinPrice = "$swp$";
            if (StringUtils.containsIgnoreCase(url, sspWinPrice)) {
                url = StringUtils.replaceIgnoreCase(url, sspWinPrice, priceEncryptStr);
            }
            resultList.add(url);
		});
		return resultList;
	}
}
