package com.juan.adx.api.converter.vivo;

import com.alibaba.fastjson2.JSON;
import com.juan.adx.api.converter.AbstractDspResponseParamConvert;
import com.juan.adx.common.alg.MD5Util;
import com.juan.adx.common.utils.LogPrintCheckUtils;
import com.juan.adx.model.dsp.vivo.enums.VivoAdEvent;
import com.juan.adx.model.dsp.vivo.enums.VivoAuctionLoss;
import com.juan.adx.model.dsp.vivo.enums.VivoDownloadStage;
import com.juan.adx.model.dsp.vivo.enums.VivoMaterialType;
import com.juan.adx.model.dsp.vivo.response.VivoAdDTO;
import com.juan.adx.model.dsp.vivo.response.VivoDspResponseParam;
import com.juan.adx.model.dsp.vivo.response.VivoImage;
import com.juan.adx.model.dsp.vivo.response.VivoTracking;
import com.juan.adx.model.dsp.youdao.enums.YouDaoYdAdType;
import com.juan.adx.model.dsp.youdao.response.YouDaoDownloadAppInfo;
import com.juan.adx.model.dsp.youdao.response.YouDaoDspResponseParam;
import com.juan.adx.model.dsp.youdao.response.YouDaoVideoPlaytrackers;
import com.juan.adx.model.dsp.yueke.enums.YueKeInteractionType;
import com.juan.adx.model.dsp.yueke.response.YueKeDspResponseParam;
import com.juan.adx.model.entity.api.ConvertDspParam;
import com.juan.adx.model.enums.DeeplinkType;
import com.juan.adx.model.enums.Dsp;
import com.juan.adx.model.enums.InteractionType;
import com.juan.adx.model.enums.MacroParameters;
import com.juan.adx.model.ssp.common.response.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.LongAdder;

import static com.juan.adx.model.dsp.vivo.enums.VivoAdEvent.*;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/25 16:45
 */
@Slf4j
public class VivoDspResponseParamConvert extends AbstractDspResponseParamConvert {

    private final Dsp DSP = this.dsp;
    private final static String MACRO_PRICE = "__WIN_PRICE__";
    private final static String DLD_PHASE = "__DLD_PHASE__";
    private final static String AUCTION_LOSS = "__AUCTION_LOSS_";
    public static LongAdder longAdder = new LongAdder();
    public VivoDspResponseParamConvert(ConvertDspParam convertDspParam) {
        super(convertDspParam);
    }

    public List<SspRespAdInfo> convert() {
        if (StringUtils.isBlank(this.dspRespData)) {
            if (LogPrintCheckUtils.needPrintLog(longAdder, 100)) {
                log.warn("longAdder：{}，{} 响应数据为空，响应数据：{} ",longAdder.sum(), this.dsp.getDesc(), this.dspRespData);
            }
            return null;
        }

        VivoDspResponseParam dspResponseParam = JSON.parseObject(this.dspRespData, VivoDspResponseParam.class);
        if (dspResponseParam == null) {
            if (LogPrintCheckUtils.needPrintLog(longAdder, 100)) {
                log.warn("longAdder：{}，{} 响应的JSON数据转换为JAVA对象为空，响应数据：{} ",longAdder.sum(), this.dsp.getDesc(), this.dspRespData);
            }
            return null;
        }

        if (!Objects.equals(1, dspResponseParam.getCode())) {
            if (LogPrintCheckUtils.needPrintLog(longAdder, 100)) {
            log.warn("longAdder：{}，{} 响应异常状态码，响应数据：{} ",longAdder.sum(), this.dsp.getDesc(), this.dspRespData);
            }
            return null;
        }

        List<SspRespAdInfo> sspRespAdInfoList = new ArrayList<>();
        for (VivoAdDTO vivoAdDTO : dspResponseParam.getData()) {
            SspRespAdInfo respAdInfo = new SspRespAdInfo();
            respAdInfo.setRequestId(this.sspRequestId);
            respAdInfo.setAdType(this.adSlotBudgetWrap.getBudgetType());
            respAdInfo.setMaterialType(VivoMaterialType.getByDspType(vivoAdDTO.getMaterialType()).getType());

            InteractionType dspInteractionType = InteractionType.NON_INREACTION;
            if (StringUtils.isNotBlank(vivoAdDTO.getDeeplink())) {
                dspInteractionType = InteractionType.DEEPLINK;
            }else if(StringUtils.isNotBlank(vivoAdDTO.getTargetUrl())){
                dspInteractionType = InteractionType.WEBVIEW;
            }else if (vivoAdDTO.getMiniProgram() != null) {
                dspInteractionType = InteractionType.WECHAT_MINI_PROGRAM;
            } else if (StringUtils.isNotBlank(vivoAdDTO.getDownloadUrl())) {
                dspInteractionType = InteractionType.DOWNLOAD;
            }

            respAdInfo.setInteractionType(dspInteractionType.getType());
            respAdInfo.setTitle(vivoAdDTO.getTitle());
            respAdInfo.setDesc(vivoAdDTO.getDescription());
            if (StringUtils.isNotBlank(vivoAdDTO.getAdLogo())) {
                respAdInfo.setAdIcons(Collections.singletonList(vivoAdDTO.getAdLogo()));
            }
            respAdInfo.setDeeplinkType(1);
            respAdInfo.setDeeplink(vivoAdDTO.getDeeplink());
            respAdInfo.setLandingPageUrl(vivoAdDTO.getTargetUrl());
            respAdInfo.setDownloadUrl(vivoAdDTO.getDownloadUrl());
            if (vivoAdDTO.getPrice() != null) {
                respAdInfo.setBidPrice(Math.toIntExact(vivoAdDTO.getPrice()));
            }
            if (StringUtils.isNotBlank(vivoAdDTO.getNoticeUrl())) {
                String noticeUrl = vivoAdDTO.getNoticeUrl();
                //替换宏
                if (vivoAdDTO.getPrice() != null) {
                    noticeUrl = noticeUrl.replace(MACRO_PRICE, vivoAdDTO.getPrice().toString());
                }
                respAdInfo.setWinNoticeUrl(Collections.singletonList(noticeUrl));
                noticeUrl = noticeUrl.replace(AUCTION_LOSS, String.valueOf(VivoAuctionLoss.INSUFFICIENT_COMPETITIVENESS.getValue()));
                respAdInfo.setLossNoticeUrl(Collections.singletonList(noticeUrl));
            }
            respAdInfo.setApp(getSspRespApp(vivoAdDTO));
            respAdInfo.setVideo(getSspRespVideo(vivoAdDTO));
            respAdInfo.setImages(getSspRespImages(vivoAdDTO));
            respAdInfo.setMiniProgram(getSspRespMiniProgram(vivoAdDTO));
            respAdInfo.setTrack(getSspRespTrack(vivoAdDTO));
            sspRespAdInfoList.add(respAdInfo);
        }
        return sspRespAdInfoList;
    }

    private SspRespMiniProgram getSspRespMiniProgram(VivoAdDTO vivoAdDTO) {
        if (vivoAdDTO.getMiniProgram() == null) {
            return null;
        }
        SspRespMiniProgram sspRespMiniProgram = new SspRespMiniProgram();
        sspRespMiniProgram.setMiniProgramId(sspRespMiniProgram.getMiniProgramId());
        sspRespMiniProgram.setMiniProgramPath(sspRespMiniProgram.getMiniProgramPath());
        return sspRespMiniProgram;
    }

    private SspRespApp getSspRespApp(VivoAdDTO vivoAdDTO) {
        SspRespApp app = new SspRespApp();
        app.setName(vivoAdDTO.getAppName());
        app.setVersion(vivoAdDTO.getAppVersionName());
        app.setPkgName(vivoAdDTO.getAppPackage());
        if (vivoAdDTO.getAppPackage() != null) {
            app.setPkgMd5(MD5Util.getMD5String(vivoAdDTO.getAppPackage()));
        }
        app.setIconUrl(null);
        app.setSize(vivoAdDTO.getAppSize());
        app.setCorporate(vivoAdDTO.getAppDeveloper());
        app.setPrivacyPolicyUrl(vivoAdDTO.getAppPrivacyPolicyUrl());
        app.setPermissionUrl(null);//XXX
        return app;
    }


    private SspRespVideo getSspRespVideo(VivoAdDTO vivoAdDTO) {
        if (vivoAdDTO.getVideo() == null) {
            return null;
        }
        SspRespVideo video = new SspRespVideo();
        video.setUrl(vivoAdDTO.getVideo().getVideoUrl());
        video.setCoverUrl(vivoAdDTO.getVideo().getPreviewImgUrl());
        video.setDuration(vivoAdDTO.getVideo().getDuration());
        video.setForceDuration(null);
        video.setSize(vivoAdDTO.getVideo().getSize() / 1024);
        video.setWidth(vivoAdDTO.getVideo().getWidth());
        video.setHeight(vivoAdDTO.getVideo().getHeight());
        video.setEndUrlType(null);
        video.setEndUrl(null);
        return video;
    }


    private List<SspRespImage> getSspRespImages(VivoAdDTO vivoAdDTO) {
        if (vivoAdDTO.getImageList() != null && !vivoAdDTO.getImageList().isEmpty()) {
            List<SspRespImage> imageList = new ArrayList<>();
            for (VivoImage vivoImage : vivoAdDTO.getImageList()) {
                SspRespImage sspRespImage = new SspRespImage();
                sspRespImage.setHeight(vivoImage.getHeight());
                sspRespImage.setWidth(vivoImage.getWidth());
                sspRespImage.setUrl(vivoImage.getUrl());
                imageList.add(sspRespImage);
            }
            return imageList;
        }
        if (vivoAdDTO.getImage() != null) {
            SspRespImage sspRespImage = new SspRespImage();
            sspRespImage.setHeight(vivoAdDTO.getImage().getHeight());
            sspRespImage.setWidth(vivoAdDTO.getImage().getWidth());
            sspRespImage.setUrl(vivoAdDTO.getImage().getUrl());
            return Collections.singletonList(sspRespImage);
        }
        return null;
    }

    private SspRespTrack getSspRespTrack(VivoAdDTO vivoAdDTO) {

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

        for (VivoTracking vivoTracking : vivoAdDTO.getTrackingList()) {
            if (vivoTracking.getTrackingEvent().equals(AD_IMPRESSION.getValue())) {
                if (showUrls == null) {
                    showUrls = new ArrayList<>();
                }
                showUrls.addAll(vivoTracking.getTrackUrls());
            }
            if (vivoTracking.getTrackingEvent().equals(AD_CLICK.getValue())) {
                if (clickUrls == null) {
                    clickUrls = new ArrayList<>();
                }
                clickUrls.addAll(vivoTracking.getTrackUrls());
            }
            if (vivoTracking.getTrackingEvent().equals(VIDEO_AD_START.getValue())) {
                if (videoStartUrls == null) {
                    videoStartUrls = new ArrayList<>();
                }
                videoStartUrls.addAll(vivoTracking.getTrackUrls());
            }
            if (vivoTracking.getTrackingEvent().equals(VIDEO_AD_PLAY_25.getValue())) {
                if (videoQuartileUrls == null) {
                    videoQuartileUrls = new ArrayList<>();
                }
                videoQuartileUrls.addAll(vivoTracking.getTrackUrls());
            }
            if (vivoTracking.getTrackingEvent().equals(VIDEO_AD_PLAY_50.getValue())) {
                if (videoHalfUrls == null) {
                    videoHalfUrls = new ArrayList<>();
                }
                videoHalfUrls.addAll(vivoTracking.getTrackUrls());
            }
            if (vivoTracking.getTrackingEvent().equals(VIDEO_AD_PLAY_75.getValue())) {
                if (videoThreeQuartileUrls == null) {
                    videoThreeQuartileUrls = new ArrayList<>();
                }
                videoThreeQuartileUrls.addAll(vivoTracking.getTrackUrls());
            }
            if (vivoTracking.getTrackingEvent().equals(VIDEO_AD_COMPLETE.getValue())) {
                if (videoCompleteUrls == null) {
                    videoCompleteUrls = new ArrayList<>();
                }
                videoCompleteUrls.addAll(vivoTracking.getTrackUrls());
            }
            if (vivoTracking.getTrackingEvent().equals(DEEPLINK_TRIGGER.getValue())) {
                if (deeplinkTryUrls == null) {
                    deeplinkTryUrls = new ArrayList<>();
                }
                deeplinkTryUrls.addAll(vivoTracking.getTrackUrls());
            }
            if (vivoTracking.getTrackingEvent().equals(DOWNLOAD_EVENT.getValue())) {
                if (startDownloadUrls == null) {
                    startDownloadUrls = new ArrayList<>();
                }
                if (finishDownloadUrls == null) {
                    finishDownloadUrls = new ArrayList<>();
                }
                if (startInstallUrls == null) {
                    startInstallUrls = new ArrayList<>();
                }
                if (finishInstallUrls == null) {
                    finishInstallUrls = new ArrayList<>();
                }
                startDownloadUrls.addAll(vivoTracking.getTrackUrls());
                finishDownloadUrls.addAll(vivoTracking.getTrackUrls());
                startInstallUrls.addAll(vivoTracking.getTrackUrls());
                finishInstallUrls.addAll(vivoTracking.getTrackUrls());
            }
            if (vivoTracking.getTrackingEvent().equals(MINI_PROGRAM_TRIGGER.getValue())) {
                if (wechatOpenUrls == null) {
                    wechatOpenUrls = new ArrayList<>();
                }
                wechatOpenUrls.addAll(vivoTracking.getTrackUrls());
            }
        }
        showUrls = MacroParameters.macroParametersReplace(DSP, showUrls);
        //曝光链接替换价格宏
        if (vivoAdDTO.getPrice() != null) {
            List<String> newShowUrls = new ArrayList<>();
            for (String url : showUrls) {
                newShowUrls.add(url.replace(MACRO_PRICE, vivoAdDTO.getPrice().toString()));
            }
            showUrls = newShowUrls;
        }

        clickUrls = MacroParameters.macroParametersReplace(DSP, clickUrls);
        deeplinkTryUrls = MacroParameters.macroParametersReplace(DSP, deeplinkTryUrls);
        /*deeplinkSuccessUrls = MacroParameters.macroParametersReplace(DSP, deeplinkSuccessUrls);
        deeplinkInstalledkUrls = MacroParameters.macroParametersReplace(DSP, deeplinkInstalledkUrls);
        deeplinkUninstallkUrls = MacroParameters.macroParametersReplace(DSP, deeplinkUninstallkUrls);
        deeplinkFailureUrls = MacroParameters.macroParametersReplace(DSP, deeplinkFailureUrls);*/

        startDownloadUrls = MacroParameters.macroParametersReplace(DSP, startDownloadUrls);
        finishDownloadUrls = MacroParameters.macroParametersReplace(DSP, finishDownloadUrls);
        startInstallUrls = MacroParameters.macroParametersReplace(DSP, startInstallUrls);
        finishInstallUrls = MacroParameters.macroParametersReplace(DSP, finishInstallUrls);

        wechatOpenUrls = MacroParameters.macroParametersReplace(DSP, wechatOpenUrls);
        videoStartUrls = MacroParameters.macroParametersReplace(DSP, videoStartUrls);
        /*  videoFailUrls = MacroParameters.macroParametersReplace(DSP, videoFailUrls);*/

        //替换下载阶段宏
        startDownloadUrls = replaceUrls(startDownloadUrls, DLD_PHASE, String.valueOf(VivoDownloadStage.START_DOWNLOAD.getValue()));
        finishDownloadUrls = replaceUrls(finishDownloadUrls, DLD_PHASE, String.valueOf(VivoDownloadStage.DOWNLOAD_COMPLETE.getValue()));
        startInstallUrls = replaceUrls(startInstallUrls, DLD_PHASE, String.valueOf(VivoDownloadStage.START_INSTALL.getValue()));
        finishInstallUrls = replaceUrls(finishInstallUrls, DLD_PHASE, String.valueOf(VivoDownloadStage.INSTALL_COMPLETE.getValue()));

        videoQuartileUrls = MacroParameters.macroParametersReplace(DSP, videoQuartileUrls);
        videoHalfUrls = MacroParameters.macroParametersReplace(DSP, videoHalfUrls);
        videoThreeQuartileUrls = MacroParameters.macroParametersReplace(DSP, videoThreeQuartileUrls);
        videoStartUrls = MacroParameters.macroParametersReplace(DSP, videoStartUrls);
        videoCompleteUrls = MacroParameters.macroParametersReplace(DSP, videoCompleteUrls);

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

    /**
     * 将url中的宏替换成实际值
     *
     * @param urls
     * @param macro
     * @param replaceStr
     * @return
     */
    private List<String> replaceUrls(List<String> urls, String macro, String replaceStr) {
        if (urls == null || urls.isEmpty()) {
            return urls;
        }
        List<String> newUrls = new ArrayList<>();
        for (String url : urls) {
            newUrls.add(url.replace(macro, replaceStr));
        }
        return newUrls;
    }
}
