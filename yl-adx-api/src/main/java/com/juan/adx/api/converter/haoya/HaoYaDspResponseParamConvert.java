package com.juan.adx.api.converter.haoya;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.LongAdder;

import com.juan.adx.common.utils.LogPrintCheckUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson2.JSON;
import com.juan.adx.api.converter.AbstractDspResponseParamConvert;
import com.juan.adx.model.dsp.haoya.enums.HaoYaInteractionType;
import com.juan.adx.model.dsp.haoya.response.HaoYaAction;
import com.juan.adx.model.dsp.haoya.response.HaoYaAd;
import com.juan.adx.model.dsp.haoya.response.HaoYaAppInfo;
import com.juan.adx.model.dsp.haoya.response.HaoYaDspResponseParam;
import com.juan.adx.model.dsp.haoya.response.HaoYaImage;
import com.juan.adx.model.dsp.haoya.response.HaoYaMaterial;
import com.juan.adx.model.dsp.haoya.response.HaoYaMiniProgram;
import com.juan.adx.model.dsp.haoya.response.HaoYaTracking;
import com.juan.adx.model.dsp.haoya.response.HaoYaVideo;
import com.juan.adx.model.dsp.yueke.enums.YueKeOsType;
import com.juan.adx.model.entity.api.ConvertDspParam;
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
public class HaoYaDspResponseParamConvert extends AbstractDspResponseParamConvert {


    public HaoYaDspResponseParamConvert(ConvertDspParam convertDspParam) {
        super(convertDspParam);
    }

    public static LongAdder longAdder = new LongAdder();

    public List<SspRespAdInfo> convert() {
        if (StringUtils.isBlank(this.dspRespData)) {
            if (LogPrintCheckUtils.needPrintLog(longAdder, 100)) {
                log.warn("longAdder：{}，{} 响应数据为空，响应数据：{} ", longAdder.sum(),this.dsp.getDesc(), this.dspRespData);
            }
            return null;
        }

        HaoYaDspResponseParam dspResponseParam = JSON.parseObject(this.dspRespData, HaoYaDspResponseParam.class);
        if (dspResponseParam == null) {
            if (LogPrintCheckUtils.needPrintLog(longAdder, 100)) {
                log.warn("longAdder：{}，{} 响应的JSON数据转换为JAVA对象为空，响应数据：{} ",longAdder.sum(), this.dsp.getDesc(), this.dspRespData);
            }
            return null;
        }

        if (!Objects.equals(0, dspResponseParam.getCode())) {
            if (LogPrintCheckUtils.needPrintLog(longAdder, 100)) {
                log.warn("longAdder：{}，{} 响应异常状态码，响应数据：{} ", longAdder.sum(),this.dsp.getDesc(), this.dspRespData);
            }
            return null;
        }

        List<HaoYaAd> dspAds = dspResponseParam.getAds();

        if (CollectionUtils.isEmpty(dspAds)) {
            if (LogPrintCheckUtils.needPrintLog(longAdder, 100)) {
                log.warn("longAdder：{}，{} 响应json结构中dspAds为空，响应数据：{} ",longAdder.sum(), this.dsp.getDesc(), this.dspRespData);
            }
            return null;
        }

        List<SspRespAdInfo> adInfos = new ArrayList<SspRespAdInfo>();

        for (HaoYaAd dspAd : dspAds) {

            SspRespApp sspRespApp = this.getSspRespApp(dspAd);
            SspRespVideo sspRespVideo = this.getSspRespVideo(dspAd);
            List<SspRespImage> sspRespImages = this.getSspRespImages(dspAd);
            SspRespTrack sspRespTrack = this.getSspRespTrack(dspAd);
            SspRespMiniProgram sspRespMiniProgram = this.getSspRespMiniProgram(dspAd);
            List<String> adIcons = this.getAdIcons(dspAd);

            HaoYaAction dspAction = Optional.ofNullable(dspAd.getAction()).orElseGet(HaoYaAction::new);

            HaoYaInteractionType dspInteractionType = HaoYaInteractionType.getByDspType(dspAction.getType());
            String downloadUrl = null;
            if (dspInteractionType == HaoYaInteractionType.DOWNLOAD) {
                downloadUrl = Optional.ofNullable(dspAction.getApp()).map(v -> v.getDownloadUrl()).orElse(null);
            }

            YueKeOsType osType = YueKeOsType.get(this.device.getOsType());
            Integer deeplinkType = osType == YueKeOsType.IOS ? DeeplinkType.IOS_UNIVERSAL_LINK.getType() : DeeplinkType.DEEPLINK.getType();
            String deeplinkUrl = osType == YueKeOsType.IOS ? dspAction.getUniversalLink() : dspAction.getDeeplink();

            SspRespAdInfo respAdInfo = new SspRespAdInfo();
            respAdInfo.setRequestId(this.sspRequestId);
            respAdInfo.setAdType(this.adSlotBudgetWrap.getBudgetType());
            respAdInfo.setMaterialType(null);
            respAdInfo.setInteractionType(dspInteractionType.getType());
            respAdInfo.setTitle(Optional.ofNullable(dspAd.getMaterial()).map(v -> v.getTitle()).orElse(null));
            respAdInfo.setDesc(Optional.ofNullable(dspAd.getMaterial()).map(v -> v.getDescription()).orElse(null));
            respAdInfo.setAdIcons(adIcons);
            respAdInfo.setDeeplinkType(deeplinkType);
            respAdInfo.setDeeplink(deeplinkUrl);
            respAdInfo.setLandingPageUrl(dspAd.getLandingPage());
            respAdInfo.setDownloadUrl(downloadUrl);
            respAdInfo.setBidPrice(dspAd.getPrice());
            respAdInfo.setWinNoticeUrl(null);
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


    private SspRespMiniProgram getSspRespMiniProgram(HaoYaAd dspAd) {
        HaoYaAction dspAction = dspAd.getAction();
        if (Objects.isNull(dspAction)) {
            return null;
        }
        HaoYaMiniProgram miniProgram = dspAction.getMiniProgram();
        if (Objects.isNull(miniProgram)) {
            return null;
        }
        SspRespMiniProgram sspRespMiniProgram = new SspRespMiniProgram();
        sspRespMiniProgram.setMiniProgramId(miniProgram.getUsername());
        sspRespMiniProgram.setMiniProgramPath(miniProgram.getPath());
        return sspRespMiniProgram;
    }

    private List<String> getAdIcons(HaoYaAd dspAd) {
        HaoYaImage sourceLogo = dspAd.getSourceLogo();
        if (Objects.isNull(sourceLogo)) {
            return null;
        }
        List<String> adIcons = new ArrayList<String>();
        adIcons.add(sourceLogo.getUrl());
        return adIcons;
    }

    private SspRespApp getSspRespApp(HaoYaAd dspAd) {

        HaoYaAction dspAction = dspAd.getAction();
        if (Objects.isNull(dspAction)) {
            return null;
        }
        HaoYaAppInfo dspApp = dspAction.getApp();
        if (Objects.isNull(dspApp)) {
            return null;
        }
        HaoYaImage image = dspApp.getLogo();

        SspRespApp sspRespApp = new SspRespApp();
        sspRespApp.setName(dspApp.getName());
        sspRespApp.setVersion(dspApp.getVersion());
        sspRespApp.setPkgName(dspApp.getPackageName());
        sspRespApp.setPkgMd5(null);
        sspRespApp.setIconUrl(Optional.ofNullable(image).map(v -> v.getUrl()).orElse(null));
        sspRespApp.setSize(Optional.ofNullable(dspApp.getSize()).map(Integer::longValue).orElse(null));
        sspRespApp.setCorporate(dspApp.getDeveloper());
        sspRespApp.setPrivacyPolicyUrl(dspApp.getPrivacy());
        sspRespApp.setPermissionUrl(dspApp.getPermissionUrl());
        return sspRespApp;
    }

    private SspRespVideo getSspRespVideo(HaoYaAd dspAd) {

        HaoYaMaterial dspMaterial = dspAd.getMaterial();
        if (Objects.isNull(dspMaterial)) {
            return null;
        }
        HaoYaVideo dspVideo = dspMaterial.getVideo();
        if (Objects.isNull(dspVideo)) {
            return null;
        }

        SspRespVideo sspRespVideo = new SspRespVideo();
        sspRespVideo.setUrl(dspVideo.getUrl());
        sspRespVideo.setCoverUrl(Optional.ofNullable(dspVideo.getCover()).map(v -> v.getUrl()).orElse(null));
        sspRespVideo.setDuration(dspVideo.getDuration());
        sspRespVideo.setForceDuration(null);
        sspRespVideo.setSize(Optional.ofNullable(dspVideo.getSize()).map(v -> v * 1024).orElse(null));
        sspRespVideo.setWidth(null);
        sspRespVideo.setHeight(null);
        sspRespVideo.setEndUrlType(null);
        sspRespVideo.setEndUrl(null);
        return sspRespVideo;
    }

    private List<SspRespImage> getSspRespImages(HaoYaAd dspAd) {

        HaoYaMaterial dspMaterial = dspAd.getMaterial();
        if (Objects.isNull(dspMaterial)) {
            return null;
        }
        List<HaoYaImage> dspImages = dspMaterial.getImages();
        if (CollectionUtils.isEmpty(dspImages)) {
            return null;
        }
        List<SspRespImage> sspRespImages = new ArrayList<SspRespImage>();
        for (HaoYaImage dspImage : dspImages) {
            SspRespImage sspRespImage = new SspRespImage();
            sspRespImage.setHeight(dspImage.getHeight());
            sspRespImage.setWidth(dspImage.getWidth());
            sspRespImage.setUrl(dspImage.getUrl());
            sspRespImages.add(sspRespImage);
        }
        return sspRespImages;
    }


    //	private final static String EKEY = "5c7afc442382d1efa21a69523ab909cb";//加密密钥
//	private final static String IKEY = "9d02fd7a94bb81b661dcfdd482f3d78a";//校验密钥
    private final static String MACRO_AUCTION_PRICE = "__AUCTION_PRICE__";
    private final static String MACRO_HIGH_PRICE = "__HIGH_PRICE__";

    private List<String> winUrl(HaoYaAd dspAd) {
        //只处理RTB自动模式的广告竞胜URL
        if (this.mode != CooperationMode.RTB_AUTO) {
            return null;
        }
        List<String> winUrls = dspAd.getNurl();
        if (CollectionUtils.isEmpty(winUrls)) {
            return null;
        }
        return replacePriceMacro(winUrls, dspAd);
    }

    private List<String> replacePriceMacro(List<String> urls, HaoYaAd dspAd) {
        if (urls == null) {
            return null;
        }
        String priceEncryptKey = this.adSlotBudgetWrap.getPriceEncryptKey();
        String[] keyArray = StringUtils.split(priceEncryptKey, ",");
        String EKEY = keyArray[0];    //加密密钥
        String IKEY = keyArray[1];    //校验密钥
        String priceEncryptStr = HaoYaPriceEncrypt.encodePrice(String.valueOf(dspAd.getPrice()), EKEY, IKEY);
        if (StringUtils.isBlank(priceEncryptStr)) {
            return urls;
        }
        List<String> newUrls = new ArrayList<>();
        for (String url : urls) {
            String replaceWinUrl = StringUtils.replaceIgnoreCase(url, MACRO_AUCTION_PRICE, priceEncryptStr);
            replaceWinUrl = StringUtils.replaceIgnoreCase(replaceWinUrl, MACRO_HIGH_PRICE, priceEncryptStr);
            newUrls.add(replaceWinUrl);
        }
        return newUrls;
    }

    private SspRespTrack getSspRespTrack(HaoYaAd dspAd) {

        HaoYaTracking dspTracking = Optional.ofNullable(dspAd.getTracking()).orElseGet(HaoYaTracking::new);

        //解析DSP返回的监控链接，并将链接中的宏替换为平台宏
        List<String> showUrls = MacroParameters.macroParametersReplace(this.dsp, replacePriceMacro(dspTracking.getImpUrls(), dspAd));
        List<String> clickUrls = MacroParameters.macroParametersReplace(this.dsp, replacePriceMacro(dspTracking.getClickUrls(), dspAd));
        List<String> adLoadUrls = null;
        List<String> adSkipUrls = null;
        List<String> adCloseUrls = null;
        List<String> wechatOpenUrls = MacroParameters.macroParametersReplace(this.dsp, replacePriceMacro(dspTracking.getMiniSuccessUrls(), dspAd));

        List<String> startDownloadUrls = MacroParameters.macroParametersReplace(this.dsp, replacePriceMacro(dspTracking.getDownStartUrls(), dspAd));
        List<String> finishDownloadUrls = MacroParameters.macroParametersReplace(this.dsp, replacePriceMacro(dspTracking.getDownFinUrls(), dspAd));
        List<String> startInstallUrls = MacroParameters.macroParametersReplace(this.dsp, replacePriceMacro(dspTracking.getDownInstallStartUrls(), dspAd));
        List<String> finishInstallUrls = MacroParameters.macroParametersReplace(this.dsp, replacePriceMacro(dspTracking.getDownInstallUrls(), dspAd));
        List<String> activeAppUrls = null;
        List<String> deeplinkTryUrls = null;
        List<String> deeplinkSuccessUrls = MacroParameters.macroParametersReplace(this.dsp, replacePriceMacro(dspTracking.getDpSuccessUrls(), dspAd));
        List<String> deeplinkFailureUrls = MacroParameters.macroParametersReplace(this.dsp, replacePriceMacro(dspTracking.getDpFailedUrls(), dspAd));
        List<String> deeplinkClickUrls = null;
        List<String> deeplinkInstalledkUrls = null;
        List<String> deeplinkUninstallkUrls = null;

        List<String> videoStartUrls = MacroParameters.macroParametersReplace(this.dsp, replacePriceMacro(dspTracking.getVideoStartUrls(), dspAd));
        List<String> videoClickUrls = null;
        List<String> videoCompleteUrls = MacroParameters.macroParametersReplace(this.dsp, replacePriceMacro(dspTracking.getVideoFinUrls(), dspAd));
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
        List<String> videoQuartileUrls = MacroParameters.macroParametersReplace(this.dsp, replacePriceMacro(dspTracking.getVideoFirstQUrls(), dspAd));
        List<String> videoHalfUrls = MacroParameters.macroParametersReplace(this.dsp, replacePriceMacro(dspTracking.getVideoMidUrls(), dspAd));
        List<String> videoThreeQuartileUrls = MacroParameters.macroParametersReplace(this.dsp, replacePriceMacro(dspTracking.getVideoThirdQUrls(), dspAd));

        //组装平台自己的事件上报链接
        showUrls = Optional.ofNullable(showUrls).orElseGet(ArrayList::new);
        clickUrls = Optional.ofNullable(clickUrls).orElseGet(ArrayList::new);
        finishDownloadUrls = Optional.ofNullable(finishDownloadUrls).orElseGet(ArrayList::new);
        finishInstallUrls = Optional.ofNullable(finishInstallUrls).orElseGet(ArrayList::new);
        deeplinkSuccessUrls = Optional.ofNullable(deeplinkSuccessUrls).orElseGet(ArrayList::new);
        this.fillReportUrls(showUrls, clickUrls, finishDownloadUrls, finishInstallUrls, deeplinkSuccessUrls);

        List<String> winUrls = this.winUrl(dspAd);
        if (CollectionUtils.isNotEmpty(winUrls)) {
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
