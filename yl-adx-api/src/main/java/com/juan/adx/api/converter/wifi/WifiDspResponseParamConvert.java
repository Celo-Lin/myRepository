package com.juan.adx.api.converter.wifi;

import com.alibaba.fastjson2.JSON;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import com.juan.adx.api.converter.AbstractDspRequestParamConvert;
import com.juan.adx.api.converter.AbstractDspResponseParamConvert;
import com.juan.adx.common.alg.MD5Util;
import com.juan.adx.common.utils.LogPrintCheckUtils;
import com.juan.adx.model.dsp.oppo.response.OppoDspResponseParam;
import com.juan.adx.model.dsp.oppo.response.OppoUnionApiAdInfo;
import com.juan.adx.model.dsp.wifi.WifiDspProtobuf;
import com.juan.adx.model.dsp.wifi.enums.WifiAdType;
import com.juan.adx.model.dsp.yueke.enums.YueKeInteractionType;
import com.juan.adx.model.dsp.yueke.enums.YueKeOsType;
import com.juan.adx.model.dsp.yueke.response.*;
import com.juan.adx.model.entity.api.ConvertDspParam;
import com.juan.adx.model.entity.api.ConvertSspParam;
import com.juan.adx.model.enums.*;
import com.juan.adx.model.ssp.common.response.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/28 19:24
 */
@Slf4j
public class WifiDspResponseParamConvert extends AbstractDspResponseParamConvert {

    private final String DSP_NAME = this.dsp.getDesc();
    public static LongAdder longAdder = new LongAdder();

    public WifiDspResponseParamConvert(ConvertDspParam convertDspParam) {
        super(convertDspParam);
    }

    public List<SspRespAdInfo> convert() {

        if (StringUtils.isBlank(this.dspRespData)) {
            if (LogPrintCheckUtils.needPrintLog(longAdder, 100)) {
                log.warn("longAdder：{}，{} 响应数据为空，响应数据：{} ",longAdder.sum(), DSP_NAME, this.dspRespData);
            }
            return null;
        }

        WifiDspProtobuf.FlyingShuttleBidResponse flyingShuttleBidResponse = null;
        try {
            byte[] data = Base64.getDecoder().decode(this.dspRespData);
            flyingShuttleBidResponse = WifiDspProtobuf.FlyingShuttleBidResponse.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            log.warn("{} 响应数据转换Protobuf对象错误 ", DSP_NAME);
            return null;
        }

        try {
            String json = JsonFormat.printer().print(flyingShuttleBidResponse);
            if (!json.contains("\"ads\"")) {
                if (LogPrintCheckUtils.needPrintLog(longAdder, 100)) {
                    log.warn("longAdder：{}，{} 响应数据为空，响应数据：{} ",longAdder.sum(), DSP_NAME, json);
                }
                return null;
            }
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e);
        }

        List<SspRespAdInfo> sspRespAdInfoList = new ArrayList<>();
        for (WifiDspProtobuf.Ad ad : flyingShuttleBidResponse.getAdsList()) {

            int materialType = MaterialType.UNKNOWN.getType();
            if (ad.getMaterialType().equals(WifiDspProtobuf.MaterialType.MATERIAL_TYPE_HORIZONTAL_PIC)
                    || ad.getMaterialType().equals(WifiDspProtobuf.MaterialType.MATERIAL_TYPE_VERTICAL_PIC)
                    || ad.getMaterialType().equals(WifiDspProtobuf.MaterialType.MATERIAL_TYPE_MULTI_PICS)) {
                materialType = MaterialType.IMAGE.getType();
            } else if (ad.getMaterialType().equals(WifiDspProtobuf.MaterialType.MATERIAL_TYPE_HORIZONTAL_VIDEO)
                    || ad.getMaterialType().equals(WifiDspProtobuf.MaterialType.MATERIAL_TYPE_VERTICAL_VIDEO)) {
                materialType = MaterialType.VIDEO.getType();
            }

            SspRespAdInfo respAdInfo = new SspRespAdInfo();
            respAdInfo.setRequestId(this.sspRequestId);
            respAdInfo.setAdType(this.sspRequestParam.getSlot().getType());
            respAdInfo.setMaterialType(materialType);//XXX
            respAdInfo.setInteractionType(WifiAdType.get(ad.getAdType()).getType());//XXX
            respAdInfo.setTitle(ad.getMaterial().getTitle());
            respAdInfo.setDesc(ad.getMaterial().getDesc());
            respAdInfo.setAdIcons(null);
            respAdInfo.setDeeplinkType(1);
            respAdInfo.setDeeplink(ad.getMaterial().getDeeplinkUrl());
            respAdInfo.setLandingPageUrl(ad.getMaterial().getLandingUrl());
            respAdInfo.setDownloadUrl(ad.getMaterial().getDownloadUrl());
            respAdInfo.setBidPrice(ad.getCpm());

            respAdInfo.setApp(getSspRespApp(ad));
            respAdInfo.setVideo(getSspRespVideo(ad));
            respAdInfo.setImages(getSspRespImages(ad));
            respAdInfo.setMiniProgram(getSspRespMiniProgram(ad));
            respAdInfo.setTrack(getSspRespTrack(respAdInfo, ad));
            //应用市场地址填充
//            if (ad.getAdType() == WifiDspProtobuf.AdType.AD_TYPE_MARKET) {
//                if (StringUtils.isBlank(respAdInfo.getLandingPageUrl())) {
//                    respAdInfo.setLandingPageUrl(ad.getMaterial().getMarketUrl());
//                }
//                if (StringUtils.isBlank(respAdInfo.getDeeplink())) {
//                    respAdInfo.setDeeplink(ad.getMaterial().getMarketUrl());
//                }
//            }
            sspRespAdInfoList.add(respAdInfo);
        }
        return sspRespAdInfoList;
    }

    private SspRespMiniProgram getSspRespMiniProgram(WifiDspProtobuf.Ad ad) {
        WifiDspProtobuf.WechatMiniApp wechatMiniApp = ad.getMaterial().getWechatMiniApp();
        SspRespMiniProgram sspRespMiniProgram = new SspRespMiniProgram();
        sspRespMiniProgram.setMiniProgramId(wechatMiniApp.getId());
        sspRespMiniProgram.setMiniProgramPath(wechatMiniApp.getPath());
        return sspRespMiniProgram;
    }


    private SspRespApp getSspRespApp(WifiDspProtobuf.Ad ad) {
        WifiDspProtobuf.TargetApp targetApp = ad.getMaterial().getApp();
        SspRespApp sspRespApp = new SspRespApp();
        sspRespApp.setName(ad.getMaterial().getApp().getName());
        sspRespApp.setVersion(targetApp.getVersionName());
        sspRespApp.setPkgName(targetApp.getPkgName());
        sspRespApp.setPkgMd5(MD5Util.getMD5String(targetApp.getPkgName()));
        sspRespApp.setIconUrl(targetApp.getIcon());
        sspRespApp.setSize((long) targetApp.getSize());
        sspRespApp.setCorporate(targetApp.getDeveloper());
        sspRespApp.setPrivacyPolicyUrl(targetApp.getPrivacy());
        sspRespApp.setPermissionUrl(targetApp.getPermissionUrl());
        return sspRespApp;
    }

    private SspRespVideo getSspRespVideo(WifiDspProtobuf.Ad ad) {

        WifiDspProtobuf.Video video = ad.getMaterial().getVideo();
        SspRespVideo sspRespVideo = new SspRespVideo();
        sspRespVideo.setUrl(video.getUrl());
        sspRespVideo.setCoverUrl(null);
        sspRespVideo.setDuration(video.getDuration());
        sspRespVideo.setForceDuration(null);
        sspRespVideo.setSize(video.getSize());
        sspRespVideo.setWidth(video.getWidth());
        sspRespVideo.setHeight(video.getHeight());
        sspRespVideo.setEndUrlType(null);
        sspRespVideo.setEndUrl(null);
        return sspRespVideo;
    }

    private List<SspRespImage> getSspRespImages(WifiDspProtobuf.Ad ad) {
        List<WifiDspProtobuf.Image> imageList = ad.getMaterial().getImagesList();
        List<SspRespImage> sspRespImages = new ArrayList<>();
        for (WifiDspProtobuf.Image image : imageList) {
            SspRespImage sspRespImage = new SspRespImage();
            sspRespImage.setHeight(image.getHeight());
            sspRespImage.setWidth(image.getWidth());
            sspRespImage.setUrl(image.getUrl());
            sspRespImages.add(sspRespImage);
        }
        return sspRespImages;
    }

    private List<String> replaceTrackingData(String data, List<String> urls) {
        if (StringUtils.isBlank(data)) {
            return urls;
        }
        List<String> newUrls = new ArrayList<>();
        for (String url : urls) {
            newUrls.add(url.replace("__TRACKING__", data));
        }
        return newUrls;
    }

    private SspRespTrack getSspRespTrack(SspRespAdInfo respAdInfo, WifiDspProtobuf.Ad ad) {

        WifiDspProtobuf.TrackingUrl trackingUrl = ad.getTrackingUrls();

        //解析DSP返回的监控链接，并将链接中的宏替换为平台宏
        List<String> showUrls = MacroParameters.macroParametersReplace(this.dsp, replaceTrackingData(ad.getTrackingData(), trackingUrl.getImpUrlsList()));
        List<String> clickUrls = MacroParameters.macroParametersReplace(this.dsp, replaceTrackingData(ad.getTrackingData(), trackingUrl.getClickUrlsList()));
        List<String> adLoadUrls = null;
        List<String> adSkipUrls = null;
        List<String> adCloseUrls = null;
        List<String> wechatOpenUrls = null;

        List<String> startDownloadUrls = MacroParameters.macroParametersReplace(this.dsp, replaceTrackingData(ad.getTrackingData(), trackingUrl.getDownloadStartUrlsList()));
        List<String> finishDownloadUrls = MacroParameters.macroParametersReplace(this.dsp, replaceTrackingData(ad.getTrackingData(), trackingUrl.getDownloadEndUrlsList()));
        List<String> startInstallUrls = null;
        List<String> finishInstallUrls = MacroParameters.macroParametersReplace(this.dsp, replaceTrackingData(ad.getTrackingData(), trackingUrl.getAppInstalledUrlsList()));
        List<String> activeAppUrls = null;
        List<String> deeplinkTryUrls = null;
        List<String> deeplinkSuccessUrls = MacroParameters.macroParametersReplace(this.dsp, replaceTrackingData(ad.getTrackingData(), trackingUrl.getDeeplinkAwokeUrlsList()));
        List<String> deeplinkFailureUrls = MacroParameters.macroParametersReplace(this.dsp, replaceTrackingData(ad.getTrackingData(), trackingUrl.getDeeplinkErrorUrlsList()));
        List<String> deeplinkClickUrls = MacroParameters.macroParametersReplace(this.dsp, replaceTrackingData(ad.getTrackingData(), trackingUrl.getDeeplinkClickUrlsList()));
        List<String> deeplinkInstalledkUrls = MacroParameters.macroParametersReplace(this.dsp, replaceTrackingData(ad.getTrackingData(), trackingUrl.getDeeplink5SUrlsList()));
        List<String> deeplinkUninstallkUrls = null;

        List<String> videoStartUrls = MacroParameters.macroParametersReplace(this.dsp, replaceTrackingData(ad.getTrackingData(), trackingUrl.getVideoStartUrlsList()));
        List<String> videoClickUrls = null;
        List<String> videoCompleteUrls = MacroParameters.macroParametersReplace(this.dsp, replaceTrackingData(ad.getTrackingData(), trackingUrl.getVideoEndUrlsList()));
        List<String> videoFailUrls = MacroParameters.macroParametersReplace(this.dsp, replaceTrackingData(ad.getTrackingData(), trackingUrl.getVideoErrorUrlsList()));
        List<String> videoCloseUrls = null;
        List<String> videoSkipUrls = null;
        List<String> videoPauseUrls = MacroParameters.macroParametersReplace(this.dsp, replaceTrackingData(ad.getTrackingData(), trackingUrl.getVideoPauseUrlsList()));
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

        List<WifiDspProtobuf.VideoProgressTracking> videoProgressTrackingList = trackingUrl.getVideoProcessPercentUrlsList();
        for (WifiDspProtobuf.VideoProgressTracking videoProgressTracking : videoProgressTrackingList) {
            if (videoProgressTracking.getProcess() == 2500) {
                videoQuartileUrls = MacroParameters.macroParametersReplace(this.dsp, replaceTrackingData(ad.getTrackingData(), videoProgressTracking.getUrlsList()));
            }
            if (videoProgressTracking.getProcess() == 5000) {
                videoHalfUrls = MacroParameters.macroParametersReplace(this.dsp, replaceTrackingData(ad.getTrackingData(), videoProgressTracking.getUrlsList()));
            }
            if (videoProgressTracking.getProcess() == 7500) {
                videoThreeQuartileUrls = MacroParameters.macroParametersReplace(this.dsp, replaceTrackingData(ad.getTrackingData(), videoProgressTracking.getUrlsList()));
            }
        }
        //竞胜url
        respAdInfo.setWinNoticeUrl(MacroParameters.macroParametersReplace(this.dsp, replaceTrackingData(ad.getTrackingData(), trackingUrl.getWinUrlsList())));

        //竞败url
        respAdInfo.setLossNoticeUrl(MacroParameters.macroParametersReplace(this.dsp, replaceTrackingData(ad.getTrackingData(), trackingUrl.getLoseUrlsList())));

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


}
