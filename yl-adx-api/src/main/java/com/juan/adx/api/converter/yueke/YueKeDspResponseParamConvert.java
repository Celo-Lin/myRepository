package com.juan.adx.api.converter.yueke;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

import com.juan.adx.common.utils.LogPrintCheckUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson2.JSON;
import com.juan.adx.api.converter.AbstractDspResponseParamConvert;
import com.juan.adx.model.dsp.yueke.enums.YueKeInteractionType;
import com.juan.adx.model.dsp.yueke.enums.YueKeOsType;
import com.juan.adx.model.dsp.yueke.response.YueKeApp;
import com.juan.adx.model.dsp.yueke.response.YueKeAssets;
import com.juan.adx.model.dsp.yueke.response.YueKeBanner;
import com.juan.adx.model.dsp.yueke.response.YueKeBid;
import com.juan.adx.model.dsp.yueke.response.YueKeDspResponseParam;
import com.juan.adx.model.dsp.yueke.response.YueKeEvents;
import com.juan.adx.model.dsp.yueke.response.YueKeImg;
import com.juan.adx.model.dsp.yueke.response.YueKeNative;
import com.juan.adx.model.dsp.yueke.response.YueKeSeatBid;
import com.juan.adx.model.dsp.yueke.response.YueKeVideo;
import com.juan.adx.model.dsp.yueke.response.YueKeWechat;
import com.juan.adx.model.entity.api.ConvertDspParam;
import com.juan.adx.model.enums.AdvertType;
import com.juan.adx.model.enums.CooperationMode;
import com.juan.adx.model.enums.DeeplinkType;
import com.juan.adx.model.enums.MacroParameters;
import com.juan.adx.model.ssp.common.response.SspRespAdInfo;
import com.juan.adx.model.ssp.common.response.SspRespApp;
import com.juan.adx.model.ssp.common.response.SspRespImage;
import com.juan.adx.model.ssp.common.response.SspRespMiniProgram;
import com.juan.adx.model.ssp.common.response.SspRespTrack;
import com.juan.adx.model.ssp.common.response.SspRespVideo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class YueKeDspResponseParamConvert extends AbstractDspResponseParamConvert {

	public static LongAdder longAdder = new LongAdder();
	public YueKeDspResponseParamConvert(ConvertDspParam convertDspParam) {
		super(convertDspParam);
	}
	
	public List<SspRespAdInfo>  convert() {
		if (StringUtils.isBlank(this.dspRespData)) {
			if (LogPrintCheckUtils.needPrintLog(longAdder, 100)) {
				log.warn("longAdder：{}，{} 响应数据为空，响应数据：{} ",longAdder.sum(), this.dsp.getDesc(), this.dspRespData);
			}
            return null;
        }
		
		YueKeDspResponseParam dspResponseParam = JSON.parseObject(this.dspRespData, YueKeDspResponseParam.class);
        if(dspResponseParam == null) {
			if (LogPrintCheckUtils.needPrintLog(longAdder, 100)) {
				log.warn("longAdder：{}，{} 响应的JSON数据转换为JAVA对象为空，响应数据：{} ",longAdder.sum(), this.dsp.getDesc(), this.dspRespData);
			}
        	return null;
        }
        
        if(!Objects.equals("10000", dspResponseParam.getCode())) {
			if (LogPrintCheckUtils.needPrintLog(longAdder, 100)) {
				log.warn("longAdder：{}，{} 响应异常状态码，响应数据：{} ",longAdder.sum(), this.dsp.getDesc(), this.dspRespData);
			}
        	return null;
        }
        
        List<YueKeSeatBid> dspSeatBids = dspResponseParam.getSeatbid();
        
        if(CollectionUtils.isEmpty(dspSeatBids)) {
			if (LogPrintCheckUtils.needPrintLog(longAdder, 100)) {
				log.warn("longAdder：{}，{} 响应json结构中seatbid为空，响应数据：{} ",longAdder.sum(), this.dsp.getDesc(), this.dspRespData);
			}
        	return null;
        }
        
        List<SspRespAdInfo> adInfos = new ArrayList<SspRespAdInfo>();
        
    	for (YueKeSeatBid dspSeatBid : dspSeatBids) {
    		
    		List<YueKeBid> dspBids = dspSeatBid.getBid();
    		if(CollectionUtils.isEmpty(dspBids)) {
    			continue;
    		}
    		
    		for (YueKeBid dspBid : dspBids) {
    			
				SspRespApp sspRespApp = this.getSspRespApp(dspBid);
				SspRespVideo sspRespVideo = this.getSspRespVideo(dspBid);
				List<SspRespImage> sspRespImages = this.getSspRespImages(dspBid);
				SspRespTrack sspRespTrack = this.getSspRespTrack(dspBid);
				SspRespMiniProgram sspRespMiniProgram = this.getSspRespMiniProgram(dspBid);
				
				List<String> adIcons = this.getAdIcons(dspBid);
				
				String deeplinkUrl = dspBid.getDeepLink();
				String actionType = dspBid.getActionType();
				String demand = dspBid.getDemand();
				
				YueKeInteractionType yueKeInteractionType = YueKeInteractionType.WEBVIEW;
				if(StringUtils.isNotBlank(deeplinkUrl)) {
					yueKeInteractionType = YueKeInteractionType.DEEPLINK;
				}else if(StringUtils.equals(actionType, "2") 
						&& StringUtils.equalsIgnoreCase(demand, "GDT")) {
					yueKeInteractionType = YueKeInteractionType.GDT;
				}else if(sspRespMiniProgram != null) {
					yueKeInteractionType = YueKeInteractionType.WECHAT_MINI_PROGRAM;
				}else if(StringUtils.equals(actionType, "2") 
						&& StringUtils.isNotBlank(dspBid.getTarget())) {
					yueKeInteractionType = YueKeInteractionType.DOWNLOAD;
				}
				
				
				YueKeOsType osType = YueKeOsType.get(this.device.getOsType());
				Integer deeplinkType = osType == YueKeOsType.IOS ? DeeplinkType.IOS_UNIVERSAL_LINK.getType() : DeeplinkType.DEEPLINK.getType();
				
				SspRespAdInfo respAdInfo = new SspRespAdInfo();
				respAdInfo.setRequestId(this.sspRequestId);
				respAdInfo.setAdType(this.adSlotBudgetWrap.getBudgetType());
				respAdInfo.setMaterialType(null);
				respAdInfo.setInteractionType(yueKeInteractionType.getType());
				respAdInfo.setTitle(null);
				respAdInfo.setDesc(null);
				respAdInfo.setAdIcons(adIcons);
				respAdInfo.setDeeplinkType(deeplinkType);
				respAdInfo.setDeeplink(deeplinkUrl);
				respAdInfo.setLandingPageUrl(dspBid.getTarget());
				respAdInfo.setDownloadUrl(dspBid.getTarget());
				respAdInfo.setBidPrice(Optional.ofNullable(dspBid.getPrice()).map(Float::intValue).orElseGet(()->0));
				respAdInfo.setWinNoticeUrl(null);
				respAdInfo.setLossNoticeUrl(null);
				
				respAdInfo.setApp(sspRespApp);
				respAdInfo.setVideo(sspRespVideo);
				respAdInfo.setImages(sspRespImages);
				respAdInfo.setMiniProgram(sspRespMiniProgram);
				respAdInfo.setTrack(sspRespTrack);
				
				adInfos.add(respAdInfo);
			}
    		
		}
			
        
        return adInfos;
	}
	
	
	private SspRespMiniProgram getSspRespMiniProgram(YueKeBid dspBid) {
		YueKeWechat wechat = dspBid.getWechat();
		if(Objects.isNull(wechat)) {
			return null;
		}
		SspRespMiniProgram sspRespMiniProgram = new SspRespMiniProgram();
		sspRespMiniProgram.setMiniProgramId(wechat.getProgramId());
		sspRespMiniProgram.setMiniProgramPath(wechat.getProgramPath());
		return sspRespMiniProgram;
	}

	private List<String> getAdIcons(YueKeBid dspBid) {
		return null;
	}

	private SspRespApp getSspRespApp(YueKeBid dspBid) {
		
		YueKeApp dspApp = dspBid.getApp();
		if(Objects.isNull(dspApp)) {
			return null;
		}
		SspRespApp sspRespApp = new SspRespApp();
		sspRespApp.setName(dspApp.getName());
		sspRespApp.setVersion(dspApp.getVers());
		sspRespApp.setPkgName(dspApp.getPack());
		sspRespApp.setPkgMd5(dspApp.getMd5());
		sspRespApp.setIconUrl(dspApp.getIcon());
		sspRespApp.setSize(dspApp.getSize());
		sspRespApp.setCorporate(dspApp.getDev());
		sspRespApp.setPrivacyPolicyUrl(dspApp.getPrivacy());
		sspRespApp.setPermissionUrl(dspApp.getPermission());
		return sspRespApp;
	}
	
	private SspRespVideo getSspRespVideo(YueKeBid dspBid) {
		
		YueKeVideo dspVideo = Optional.ofNullable(dspBid.getVideo()).orElseGet(YueKeVideo::new);
		
		AdvertType advertType = AdvertType.get(this.slot.getType());
		if(Objects.isNull(advertType)) {
			return null;
		}
		
		SspRespVideo sspRespVideo = new SspRespVideo();
		if(advertType == AdvertType.SPLASH) {
			YueKeBanner dspBanner = Optional.ofNullable(dspBid.getBanner()).orElseGet(YueKeBanner::new);
			sspRespVideo.setUrl(dspBanner.getVideoUrl());
			sspRespVideo.setCoverUrl(null);
			sspRespVideo.setDuration(dspBanner.getDuration());
			sspRespVideo.setForceDuration(dspBanner.getSkipMinTime());
			sspRespVideo.setSize(null);
			sspRespVideo.setWidth(dspBanner.getW());
			sspRespVideo.setHeight(dspBanner.getH());
			sspRespVideo.setEndUrlType(null);
			sspRespVideo.setEndUrl(null);
		}else {
			YueKeImg dspCover = dspVideo.getCover();
			sspRespVideo.setUrl(dspVideo.getIurl());
			sspRespVideo.setCoverUrl(Optional.ofNullable(dspCover).map(YueKeImg :: getIurl).orElse(null));
			sspRespVideo.setDuration(dspVideo.getDuration());
			sspRespVideo.setForceDuration(dspVideo.getSkipMinTime());
			sspRespVideo.setSize(Optional.ofNullable(dspVideo.getSize()).map(size -> size * 1024).orElse(null));
			sspRespVideo.setWidth(dspVideo.getW());
			sspRespVideo.setHeight(dspVideo.getH());
			sspRespVideo.setEndUrlType(null);
			sspRespVideo.setEndUrl(null);
		}
		
		return sspRespVideo;
	}

	private List<SspRespImage> getSspRespImages(YueKeBid dspBid){
		
		AdvertType advertType = AdvertType.get(this.slot.getType());
		if(Objects.isNull(advertType)) {
			return null;
		}
		
		List<SspRespImage> sspRespImages = new ArrayList<SspRespImage>();
		
		if((advertType == AdvertType.SPLASH 
				|| advertType == AdvertType.INFORMATION_STREAM
				|| advertType == AdvertType.BANNER)) {
			
			YueKeBanner dspBanner = Optional.ofNullable(dspBid.getBanner()).orElseGet(YueKeBanner::new);
			
			SspRespImage sspRespImage = new SspRespImage();
			sspRespImage.setHeight(dspBanner.getH());
			sspRespImage.setWidth(dspBanner.getW());
			sspRespImage.setUrl(dspBanner.getIurl());
			sspRespImages.add(sspRespImage);
		}else if(advertType == AdvertType.INFORMATION_STREAM 
				|| advertType == AdvertType.NATIVE ) {
			YueKeNative dspNative = dspBid.getNativeMaterial();
			List<YueKeAssets> assets = Optional.ofNullable(dspNative).map(YueKeNative::getAssets).orElse(null);
			if(CollectionUtils.isEmpty(assets)) {
				return null;
			}
			for (YueKeAssets dspAssets : assets) {
				YueKeImg dspImg = dspAssets.getImg();
				SspRespImage sspRespImage = new SspRespImage();
				sspRespImage.setHeight(dspImg.getH());
				sspRespImage.setWidth(dspImg.getW());
				sspRespImage.setUrl(dspImg.getIurl());
				sspRespImages.add(sspRespImage);
			}
			
		}
		return sspRespImages;
	}
	
	
	private final static String MACRO_PRICE = "__WIN_PRICE__";
    
    private List<String> winUrl(YueKeBid dspBid) {
    	//只处理RTB自动模式的广告竞胜URL
    	if(this.mode != CooperationMode.RTB_AUTO) {
    		return null;
    	}
    	String winUrl = dspBid.getNurl();
    	if(StringUtils.isBlank(winUrl)) {
    		return null;
    	}
    	String priceEncryptStr = YueKePriceEncrypt.encrypt(String.valueOf(dspBid.getPrice()), this.adSlotBudgetWrap.getPriceEncryptKey());
    	if(StringUtils.isBlank(priceEncryptStr)) {
    		return null;
    	}
    	
    	List<String> resultWinUrls = new ArrayList<String>();
    	String replaceWinUrl = StringUtils.replace(winUrl, MACRO_PRICE, priceEncryptStr);
    	resultWinUrls.add(replaceWinUrl);
    	return resultWinUrls;
    }
	
	private SspRespTrack getSspRespTrack(YueKeBid dspBid) {
		
		YueKeEvents dspTracking = Optional.ofNullable(dspBid.getEvents()).orElseGet(YueKeEvents::new);
		
		//解析DSP返回的监控链接，并将链接中的宏替换为平台宏
        List<String> showUrls = MacroParameters.macroParametersReplace(this.dsp, dspTracking.getEls());
        List<String> clickUrls = MacroParameters.macroParametersReplace(this.dsp, dspTracking.getCls());
        List<String> adLoadUrls = null;
        List<String> adSkipUrls = null;
        List<String> adCloseUrls = null;
        List<String> wechatOpenUrls = null;
        
        List<String> startDownloadUrls = MacroParameters.macroParametersReplace(this.dsp, dspTracking.getSdls());
        List<String> finishDownloadUrls = MacroParameters.macroParametersReplace(this.dsp, dspTracking.getEdls());
        List<String> startInstallUrls = MacroParameters.macroParametersReplace(this.dsp, dspTracking.getSils());
        List<String> finishInstallUrls = MacroParameters.macroParametersReplace(this.dsp, dspTracking.getEils());
        List<String> activeAppUrls = MacroParameters.macroParametersReplace(this.dsp, dspTracking.getIals());
        List<String> deeplinkTryUrls = null;
        List<String> deeplinkSuccessUrls = MacroParameters.macroParametersReplace(this.dsp, dspTracking.getDcls());
        List<String> deeplinkFailureUrls = null;
        List<String> deeplinkClickUrls = null;
        List<String> deeplinkInstalledkUrls = null;
        List<String> deeplinkUninstallkUrls = null;
        
        List<String> videoStartUrls = MacroParameters.macroParametersReplace(this.dsp, dspTracking.getSpls());
        List<String> videoClickUrls = null;
        List<String> videoCompleteUrls = MacroParameters.macroParametersReplace(this.dsp, dspTracking.getEpls());
        List<String> videoFailUrls = null;
        List<String> videoCloseUrls = MacroParameters.macroParametersReplace(this.dsp, dspTracking.getCpls());
        List<String> videoSkipUrls = MacroParameters.macroParametersReplace(this.dsp, dspTracking.getSkls());
        List<String> videoPauseUrls = MacroParameters.macroParametersReplace(this.dsp, dspTracking.getPpls());
        List<String> videoResumeUrls = MacroParameters.macroParametersReplace(this.dsp, dspTracking.getGpls());
        List<String> videoReplayUrls = null;
        List<String> videoMuteUrls = MacroParameters.macroParametersReplace(this.dsp, dspTracking.getMpls());
        List<String> videoUnmuteUrls = null;
        List<String> videoFullscreenUrls = null;
        List<String> videoExitFullscreenUrls = null;
        List<String> videoUpscrollUrls = null;
        List<String> videoDownscrollUrls = null;
        
        Map<String, Set<String>> fpls = dspTracking.getFpls();
        Set<String> fpls25 = Optional.ofNullable(fpls).map(fp -> fp.get("25")).orElse(null);
        Set<String> fpls50 = Optional.ofNullable(fpls).map(fp -> fp.get("50")).orElse(null);
        Set<String> fpls75 = Optional.ofNullable(fpls).map(fp -> fp.get("75")).orElse(null);
        
        List<String> videoQuartileUrls = MacroParameters.macroParametersReplace(this.dsp, Optional.ofNullable(fpls25).map(s -> s.stream().collect(Collectors.toList())).orElse(null));
        List<String> videoHalfUrls = MacroParameters.macroParametersReplace(this.dsp, Optional.ofNullable(fpls50).map(s -> s.stream().collect(Collectors.toList())).orElse(null));
        List<String> videoThreeQuartileUrls = MacroParameters.macroParametersReplace(this.dsp, Optional.ofNullable(fpls75).map(s -> s.stream().collect(Collectors.toList())).orElse(null));

        //组装平台自己的事件上报链接
        showUrls = Optional.ofNullable(showUrls).orElseGet(ArrayList::new);
        clickUrls = Optional.ofNullable(clickUrls).orElseGet(ArrayList::new);
        finishDownloadUrls = Optional.ofNullable(finishDownloadUrls).orElseGet(ArrayList::new);
        finishInstallUrls = Optional.ofNullable(finishInstallUrls).orElseGet(ArrayList::new);
        deeplinkSuccessUrls = Optional.ofNullable(deeplinkSuccessUrls).orElseGet(ArrayList::new);
        this.fillReportUrls(showUrls, clickUrls, finishDownloadUrls, finishInstallUrls, deeplinkSuccessUrls);
        
        List<String> winUrls = this.winUrl(dspBid);
        if(CollectionUtils.isNotEmpty(winUrls)) {
            showUrls.addAll(winUrls);
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
}
