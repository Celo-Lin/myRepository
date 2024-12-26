package com.juan.adx.api.converter.yidian;

import com.alibaba.fastjson2.JSON;
import com.juan.adx.api.converter.AbstractDspResponseParamConvert;
import com.juan.adx.model.dsp.yidian.enums.YiDianRespInteractionType;
import com.juan.adx.model.dsp.yidian.enums.YiDianRespMaterialType;
import com.juan.adx.model.dsp.yidian.response.*;
import com.juan.adx.model.dsp.yueke.enums.YueKeOsType;
import com.juan.adx.model.entity.api.ConvertDspParam;
import com.juan.adx.model.enums.AdvertType;
import com.juan.adx.model.enums.CooperationMode;
import com.juan.adx.model.enums.DeeplinkType;
import com.juan.adx.model.enums.MacroParameters;
import com.juan.adx.model.ssp.common.response.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class YiDianDspResponseParamConvert extends AbstractDspResponseParamConvert {


	public YiDianDspResponseParamConvert(ConvertDspParam convertDspParam) {
		super(convertDspParam);
	}
	
	public List<SspRespAdInfo>  convert() {
		if (StringUtils.isBlank(this.dspRespData)) {
        	log.warn("{} 响应数据为空，响应数据：{} ", this.dsp.getDesc(), this.dspRespData);
            return null;
        }
		YiDianDspResponseParam dspResponseParam = JSON.parseObject(this.dspRespData, YiDianDspResponseParam.class);
		if(dspResponseParam == null) {
			log.warn("{} 响应的JSON数据转换为JAVA对象为空，响应数据：{} ", this.dsp.getDesc(), this.dspRespData);
			return null;
		}
//        if(!Objects.equals(0, dspResponseParam.getCode())) {
//        	log.warn("{} 响应异常状态码，响应数据：{} ", this.dsp.getDesc(), this.dspRespData);
//        	return null;
//        }

        List<YiDianSeatBid> dspSeatBids = dspResponseParam.getSeatBid();
        
        if(CollectionUtils.isEmpty(dspSeatBids)) {
        	log.warn("{} 响应json结构中SeatBids为空，响应数据：{} ", this.dsp.getDesc(), this.dspRespData);
        	return null;
        }
        
        List<SspRespAdInfo> adInfos = new ArrayList<SspRespAdInfo>();
        
    	for (YiDianSeatBid dspSeatBid : dspSeatBids) {

			List<YiDianBid> dspBids = dspSeatBid.getBid();
			if(CollectionUtils.isEmpty(dspBids)) {
				continue;
			}

			for (YiDianBid dspBid : dspBids) {
				SspRespApp sspRespApp = this.getSspRespApp(dspBid);
				SspRespVideo sspRespVideo = this.getSspRespVideo(dspBid);
				List<SspRespImage> sspRespImages = this.getSspRespImages(dspBid);
				SspRespTrack sspRespTrack = this.getSspRespTrack(dspBid);
				SspRespMiniProgram sspRespMiniProgram = this.getSspRespMiniProgram(dspBid);

				List<String> adIcons = this.getAdIcons(dspBid);

				String deeplinkUrl = dspBid.getDeeplinkurl();
				Integer actionType = dspBid.getCtype();

				YiDianRespInteractionType yiDianRespInteractionType = YiDianRespInteractionType.WEBVIEW;
				if(StringUtils.isNotBlank(deeplinkUrl)) {
					yiDianRespInteractionType = YiDianRespInteractionType.DEEPLINK;
				}else if(sspRespMiniProgram != null) {
					yiDianRespInteractionType = YiDianRespInteractionType.WECHAT_MINI_PROGRAM;
				}else if(actionType == 2) {
					yiDianRespInteractionType = YiDianRespInteractionType.DOWNLOAD;
				}

				YueKeOsType osType = YueKeOsType.get(this.device.getOsType());
				Integer deeplinkType = osType == YueKeOsType.IOS ? DeeplinkType.IOS_UNIVERSAL_LINK.getType() : DeeplinkType.DEEPLINK.getType();

				SspRespAdInfo respAdInfo = new SspRespAdInfo();
				respAdInfo.setRequestId(this.sspRequestId);
				respAdInfo.setAdType(this.adSlotBudgetWrap.getBudgetType());
				YiDianRespMaterialType materialType = YiDianRespMaterialType.get(dspBid.getTemplateid());
				respAdInfo.setMaterialType(materialType.getType());
				respAdInfo.setInteractionType(yiDianRespInteractionType.getType());
				respAdInfo.setTitle(dspBid.getTitle());
				respAdInfo.setDesc(null);
				respAdInfo.setAdIcons(adIcons);
				respAdInfo.setDeeplinkType(deeplinkType);
				respAdInfo.setDeeplink(deeplinkUrl);
				respAdInfo.setLandingPageUrl(dspBid.getCurl());
				respAdInfo.setDownloadUrl(dspBid.getDurl());
				respAdInfo.setBidPrice(dspBid.getPrice());
				List<String> list = new ArrayList<>();
				list.add(dspBid.getNurl());
				String encryptText = "id="+dspBid.getId()+"&adid="+dspBid.getAdid()+"&crid="+dspBid.getCrid()+"&price="+dspBid.getPrice()+"&t="+System.currentTimeMillis();
				list = showUrlReplace(encryptText, list);
				respAdInfo.setWinNoticeUrl(list);
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


	private SspRespMiniProgram getSspRespMiniProgram(YiDianBid dspBid) {
		YiDianMiniProgram wechat = dspBid.getMiniProgram();
		if(Objects.isNull(wechat)) {
			return null;
		}
		if (StringUtils.isEmpty(wechat.getMpPath())) {
			return null;
		}
		SspRespMiniProgram sspRespMiniProgram = new SspRespMiniProgram();
		sspRespMiniProgram.setMiniProgramId(wechat.getMpUserName());
		sspRespMiniProgram.setMiniProgramPath(wechat.getMpPath());
		return sspRespMiniProgram;
	}

	private List<String> getAdIcons(YiDianBid dspBid) {
		List<String> list = new ArrayList<>();
		YiDianVideo video =  Optional.ofNullable(dspBid.getVideo()).orElseGet(YiDianVideo::new);
		String logourl = video.getLogourl();
		if (logourl != null) {
			list.add(logourl);
		}
		return list;
	}

	private SspRespApp getSspRespApp(YiDianBid dspBid) {
		YiDianAppInfo dspApp = dspBid.getApp_info();
		if(Objects.isNull(dspApp)) {
			return null;
		}
		SspRespApp sspRespApp = new SspRespApp();
		sspRespApp.setName(dspApp.getApp_name());
		sspRespApp.setVersion(dspApp.getVersion_name());
		sspRespApp.setPkgName(dspBid.getDpkgname());
		sspRespApp.setPkgMd5(null);
		sspRespApp.setIconUrl(null);
		sspRespApp.setSize(null);
		sspRespApp.setCorporate(dspApp.getDeveloper_name());
		sspRespApp.setPrivacyPolicyUrl(dspApp.getPrivacy_policy_url());
		sspRespApp.setPermissionUrl(null);
		return sspRespApp;
	}

	private SspRespVideo getSspRespVideo(YiDianBid dspBid) {

		YiDianVideo dspVideo = Optional.ofNullable(dspBid.getVideo()).orElseGet(YiDianVideo::new);

		AdvertType advertType = AdvertType.get(this.slot.getType());
		if(Objects.isNull(advertType)) {
			return null;
		}

		SspRespVideo sspRespVideo = new SspRespVideo();
		sspRespVideo.setUrl(dspVideo.getVideourl());
		sspRespVideo.setCoverUrl(Optional.ofNullable(dspBid.getAurl()).map(list -> list.get(0)).orElse(null));
		sspRespVideo.setDuration(Math.toIntExact(Optional.ofNullable(dspVideo.getVideoduration()).orElse(0L)));
		sspRespVideo.setForceDuration(null);
		sspRespVideo.setSize(Math.toIntExact(Optional.ofNullable(dspVideo.getSize()).orElse(0L)));
		sspRespVideo.setWidth(dspVideo.getW());
		sspRespVideo.setHeight(dspVideo.getH());
		sspRespVideo.setEndUrlType(null);
		sspRespVideo.setEndUrl(null);


		return sspRespVideo;
	}

	private List<SspRespImage> getSspRespImages(YiDianBid dspBid){

		List<String> dspImages = dspBid.getAurl();
		if(CollectionUtils.isEmpty(dspImages)) {
			return null;
		}
		List<SspRespImage> sspRespImages = new ArrayList<SspRespImage>();
		for (String url : dspImages) {
			SspRespImage sspRespImage = new SspRespImage();
			sspRespImage.setHeight(dspBid.getH());
			sspRespImage.setWidth(dspBid.getW());
			sspRespImage.setUrl(url);
			sspRespImages.add(sspRespImage);
		}
		return sspRespImages;
	}

	private SspRespTrack getSspRespTrack(YiDianBid dspBid) {

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

		//解析DSP返回的监控链接，并将链接中的宏替换为平台宏
		showUrls = MacroParameters.macroParametersReplace(this.dsp, dspBid.getMurl());
		clickUrls = MacroParameters.macroParametersReplace(this.dsp, dspBid.getCmurl());
		startDownloadUrls = dspBid.getDmurl();
		finishDownloadUrls = dspBid.getDownsuccessurl();
		deeplinkSuccessUrls = dspBid.getDeeplinkmurl();
		videoStartUrls = dspBid.getPlayvideomurl();
		videoCompleteUrls = dspBid.getFinishvideomurl();

		//对曝光链接的宏进行替换
		String encryptText = "id="+dspBid.getId()+"&adid="+dspBid.getAdid()+"&crid="+dspBid.getCrid()+"&price="+dspBid.getPrice()+"&t="+System.currentTimeMillis();
		showUrls = showUrlReplace(encryptText, showUrls);

		//组装平台自己的事件上报链接
		showUrls = Optional.ofNullable(showUrls).orElseGet(ArrayList::new);
		clickUrls = Optional.ofNullable(clickUrls).orElseGet(ArrayList::new);
		finishDownloadUrls = Optional.ofNullable(finishDownloadUrls).orElseGet(ArrayList::new);
		finishInstallUrls = Optional.ofNullable(finishInstallUrls).orElseGet(ArrayList::new);
		deeplinkSuccessUrls = Optional.ofNullable(deeplinkSuccessUrls).orElseGet(ArrayList::new);
		this.fillReportUrls(showUrls, clickUrls, finishDownloadUrls, finishInstallUrls, deeplinkSuccessUrls);

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


	private final static String MACRO_PRICE = "%%WIN_PRICE%%";

	/**
	 * 上报链接价格宏替换
	 *
	 * @param encryptText
	 * @param showUrls
	 * @return
	 */
	private List<String> showUrlReplace(String encryptText, List<String> showUrls) {
		//非 RTB 广告也必须处理宏 将”%%WIN_PRICE%%“替换成 0
		if(this.mode != CooperationMode.RTB_AUTO && this.mode != CooperationMode.RTB_MANUAL) {
			List<String> resultWinUrls = new ArrayList<>();
			for (String url : showUrls) {
				String replaceWinUrl = StringUtils.replace(url, MACRO_PRICE, "0");
				resultWinUrls.add(replaceWinUrl);
			}
			return resultWinUrls;
		}
		String priceEncryptKey = this.adSlotBudgetWrap.getPriceEncryptKey();
		if (StringUtils.isBlank(priceEncryptKey) || priceEncryptKey.split(",").length != 2) {
			log.error("上报链接宏替换失败，一点资讯dsp加密密钥格式错误:{}", priceEncryptKey);
			return null;
		}
		String secretKey = priceEncryptKey.split(",")[0];
		String base64 = priceEncryptKey.split(",")[1];

		String priceEncryptStr = YiDianPriceEncrpt.encryptWinPrice(encryptText, secretKey, base64);
		if (StringUtils.isBlank(priceEncryptStr)) {
			return null;
		}
		List<String> resultWinUrls = new ArrayList<>();
		for (String url : showUrls) {
			String replaceWinUrl = StringUtils.replace(url, MACRO_PRICE, priceEncryptStr);
			resultWinUrls.add(replaceWinUrl);
		}
		return resultWinUrls;
	}
}
