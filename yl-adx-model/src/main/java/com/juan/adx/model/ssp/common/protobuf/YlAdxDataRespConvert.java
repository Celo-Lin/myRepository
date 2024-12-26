package com.juan.adx.model.ssp.common.protobuf;

import com.google.common.collect.Lists;
import com.juan.adx.model.entity.api.SspRespBidWrap;
import com.juan.adx.model.ssp.common.request.SspRequestParam;
import com.juan.adx.model.ssp.common.response.*;
import com.yl.ad.protobuf.AdxDataProtobuf;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author： Kevin.zhao
 * @date： 2024/12/22 19:24
 * 将Protobuf对象转换为Bean对象, 不使用BeanUtil等方法,提高转换的效率
 */
public class YlAdxDataRespConvert {

    public SspRespBidWrap convert(AdxDataProtobuf.AdResp resp) {
        List<SspRespAdInfo> adInfos = Lists.newArrayList();
        SspRespBidWrap respWarp = new SspRespBidWrap();
        respWarp.setSspRespBids(adInfos);
        if (resp == null || resp.getDataList() == null || resp.getDataList().isEmpty()) {
            return respWarp;
        }
        for (AdxDataProtobuf.AdInfo adInfo : resp.getDataList()) {
            SspRespAdInfo b = new SspRespAdInfo();
            b.setRequestId(String.valueOf(adInfo.getRequestId()));
            b.setAdType(adInfo.getAdType());
            b.setMaterialType(adInfo.getMaterialType());
            b.setInteractionType(adInfo.getInteractionType());
            b.setTitle(adInfo.getTitle());
            b.setDesc(adInfo.getDesc());
            b.setAdIcons(adInfo.getAdIconsList());
            b.setDeeplink(adInfo.getDeeplink());
            b.setDeeplink(adInfo.getDeeplink());
            b.setLandingPageUrl(adInfo.getLandingPageUrl());
            b.setDownloadUrl(adInfo.getDownloadUrl());
            b.setBidPrice(adInfo.getBidPrice());
            b.setWinNoticeUrl(adInfo.getWinNoticeUrlList());
            b.setLossNoticeUrl(adInfo.getLossNoticeUrlList());
            b.setApp(convertApp(adInfo));
            b.setVideo(convertVideo(adInfo));
            b.setImages(convertImages(adInfo));
            b.setMiniProgram(convertMiniProgram(adInfo));
            b.setTrack(convertTrack(adInfo));
            adInfos.add(b);
        }

        return respWarp;
    }

    private SspRespTrack convertTrack(AdxDataProtobuf.AdInfo adInfo) {
        SspRespTrack track = new SspRespTrack();
        if (adInfo == null || adInfo.getTrace() == null) {
            return track;
        }
        AdxDataProtobuf.Trace tr = adInfo.getTrace();
        track.setShowUrls(Lists.newArrayList( tr.getShowUrlsList()) );
        track.setClickUrls(Lists.newArrayList( tr.getClickUrlsList()) );
        track.setAdLoadUrls(tr.getAdLoadUrlsList());
        track.setAdSkipUrls(tr.getAdSkipUrlsList());
        track.setAdCloseUrls(tr.getAdCloseUrlsList());
        track.setWechatOpenUrls(tr.getWechatOpenUrlsList());
        track.setStartDownloadUrls(tr.getStartDownloadUrlsList());
        track.setFinishDownloadUrls(Lists.newArrayList( tr.getFinishDownloadUrlsList()) );
        track.setStartInstallUrls(tr.getStartInstallUrlsList());
        track.setFinishInstallUrls(Lists.newArrayList( tr.getFinishInstallUrlsList()) );
        track.setActiveAppUrls(tr.getActiveAppUrlsList());
        track.setDeeplinkTryUrls(tr.getDeeplinkTryUrlsList());
        track.setDeeplinkSuccessUrls(Lists.newArrayList( tr.getDeeplinkSuccessUrlsList()) );
        track.setDeeplinkFailureUrls(tr.getDeeplinkFailureUrlsList());
        track.setDeeplinkClickUrls(tr.getClickUrlsList());
        track.setDeeplinkInstalledkUrls(tr.getDeeplinkInstalledUrlsList());
        track.setDeeplinkUninstallkUrls(tr.getDeeplinkUninstallUrlsList());

        /*------------------------------------ 分界线：视频类上报链接 ------------------------------------*/
        track.setVideoStartUrls(tr.getVideoStartUrlsList());
        track.setVideoClickUrls(tr.getVideoClickUrlsList());
        track.setVideoCompleteUrls(tr.getVideoCompleteUrlsList());
        track.setVideoFailUrls(tr.getVideoFailUrlsList());
        track.setVideoCloseUrls(tr.getVideoCloseUrlsList());
        track.setVideoSkipUrls(tr.getVideoSkipUrlsList());
        track.setVideoPauseUrls(tr.getVideoPauseUrlsList());
        track.setVideoResumeUrls(tr.getVideoResumeUrlsList());
        track.setVideoReplayUrls(tr.getVideoReplayUrlsList());
        track.setVideoMuteUrls(tr.getVideoMuteUrlsList());
        track.setVideoUnmuteUrls(tr.getVideoUnmuteUrlsList());
        track.setVideoFullscreenUrls(tr.getVideoExitFullscreenUrlsList());
        track.setVideoExitFullscreenUrls(tr.getVideoExitFullscreenUrlsList());
        track.setVideoUpscrollUrls(tr.getVideoUpscrollUrlsList());
        track.setVideoDownscrollUrls(tr.getVideoDownscrollUrlsList());
        track.setVideoQuartileUrls(tr.getVideoQuartileUrlsList());
        track.setVideoHalfUrls(tr.getVideoHalfUrlsList());
        track.setVideoThreeQuartileUrls(tr.getVideoThreeQuartileUrlsList());

        return track;
    }

    private List<SspRespImage> convertImages(AdxDataProtobuf.AdInfo adInfo) {
        List<SspRespImage> images = new ArrayList<>(adInfo.getImagesCount());
        if (adInfo == null || adInfo.getVideo() == null) {
            return images;
        }
        for (AdxDataProtobuf.ImageInfo im : adInfo.getImagesList()){
            SspRespImage i = new SspRespImage();
            i.setUrl(im.getUrl());
            i.setWidth(im.getWidth());
            i.setHeight(im.getHeight());
            images.add(i);
        }
        return images;
    }

    private SspRespMiniProgram convertMiniProgram(AdxDataProtobuf.AdInfo adInfo) {
        SspRespMiniProgram m = new SspRespMiniProgram();
        if (adInfo == null || adInfo.getMiniProgram() == null) {
            return m;
        }
        m.setMiniProgramId(adInfo.getMiniProgram().getMiniProgramId());
        m.setMiniProgramPath(adInfo.getMiniProgram().getMiniProgramPath());
        return m;
    }
    private SspRespVideo convertVideo(AdxDataProtobuf.AdInfo adInfo) {
        SspRespVideo video = new SspRespVideo();
        if (adInfo == null || adInfo.getVideo() == null) {
            return video;
        }
        video.setUrl(adInfo.getVideo().getUrl());
        video.setCoverUrl(adInfo.getVideo().getCoverUrl());
        video.setDuration(adInfo.getVideo().getDuration());
        video.setForceDuration(adInfo.getVideo().getForceDuration());
        video.setSize(adInfo.getVideo().getSize());
        video.setWidth(adInfo.getVideo().getSize());
        video.setHeight(adInfo.getVideo().getHeight());
        video.setEndUrl(adInfo.getVideo().getEndUrl());
        video.setEndUrlType(adInfo.getVideo().getEndUrlType());
        return video;
    }

    private SspRespApp convertApp(AdxDataProtobuf.AdInfo adInfo) {
        SspRespApp app = new SspRespApp();
        if (adInfo == null || adInfo.getApp() == null) {
            return app;
        }
        app.setName(adInfo.getApp().getName());
        app.setPkgName(adInfo.getApp().getPkgName());
        app.setPkgMd5(adInfo.getApp().getPkgMd5());
        app.setIconUrl(adInfo.getApp().getIconUrl());
        app.setSize(adInfo.getApp().getSize());
        app.setCorporate(adInfo.getApp().getCorporate());
        app.setPrivacyPolicyUrl(adInfo.getApp().getPrivacyPolicyUrl());
        app.setPermissionUrl(adInfo.getApp().getPermissionUrl());
        return app;
    }

    public AdxDataProtobuf.App convertApp(SspRequestParam s) {
        AdxDataProtobuf.App.Builder a = AdxDataProtobuf.App.newBuilder();
        Optional.ofNullable(s.getApp().getAppStoreVersion()).ifPresent(a::setAppStoreVersion);
        Optional.ofNullable(s.getApp().getName()).ifPresent(a::setName);
        Optional.ofNullable(s.getApp().getVerName()).ifPresent(a::setVerName);
        Optional.ofNullable(s.getApp().getPkgName()).ifPresent(a::setPkgName);

        //TODO  appId
        return a.build();
    }

    public AdxDataProtobuf.Device convertDevice(SspRequestParam s) {
        AdxDataProtobuf.Device.Builder d = AdxDataProtobuf.Device.newBuilder();
        Optional.ofNullable(s.getDevice().getBrand()).ifPresent(d::setBrand);
        Optional.ofNullable(s.getDevice().getType()).ifPresent(d::setType);
        Optional.ofNullable(s.getDevice().getMake()).ifPresent(d::setMake);
        Optional.ofNullable(s.getDevice().getBirthMark()).ifPresent(d::setBirthMark);
        Optional.ofNullable(s.getDevice().getAndroidApiLevel()).ifPresent(d::setAndroidApiLevel);
        Optional.ofNullable(s.getDevice().getDpi()).ifPresent(d::setDpi);
        Optional.ofNullable(s.getDevice().getCpuNum()).ifPresent(d::setCpuNum);
        Optional.ofNullable(s.getDevice().getPpi()).ifPresent(d::setPpi);
        Optional.ofNullable(s.getDevice().getWidth()).ifPresent(d::setWidth);
        Optional.ofNullable(s.getDevice().getHeight()).ifPresent(d::setHeight);
        Optional.ofNullable(s.getDevice().getBootMark()).ifPresent(d::setBootMark);
        Optional.ofNullable(s.getDevice().getDensity()).ifPresent(d::setDensity);
        Optional.ofNullable(s.getDevice().getDeviceName()).ifPresent(d::setDeviceName);
        Optional.ofNullable(s.getDevice().getDeviceNameMd5()).ifPresent(d::setDeviceNameMd5);
        Optional.ofNullable(s.getDevice().getHagVersion()).ifPresent(d::setHagVersion);
        Optional.ofNullable(s.getDevice().getHmsVersion()).ifPresent(d::setHmsVersion);
        Optional.ofNullable(s.getDevice().getRomVersion()).ifPresent(d::setRomVersion);
        Optional.ofNullable(s.getDevice().getOsVersion()).ifPresent(d::setOsVersion);
        Optional.ofNullable(s.getDevice().getOsUiVersion()).ifPresent(d::setOsUiVersion);
        Optional.ofNullable(s.getDevice().getOsType()).ifPresent(d::setOsType);
        Optional.ofNullable(s.getDevice().getUpdateMark()).ifPresent(d::setUpdateMark);
        Optional.ofNullable(s.getDevice().getTimeZone()).ifPresent(d::setTimeZone);
        Optional.ofNullable(s.getDevice().getSysMemorySize()).ifPresent(d::setSysMemorySize);
        Optional.ofNullable(s.getDevice().getSysDiskSize()).ifPresent(d::setSysDiskSize);
        Optional.ofNullable(s.getDevice().getHardwareModel()).ifPresent(d::setHardwareModel);
        Optional.ofNullable(s.getDevice().getHarmonyOsVersion()).ifPresent(d::setHarmonyOsVersion);
        Optional.ofNullable(s.getDevice().getSupportDeeplink()).ifPresent(d::setSupportDeeplink);
        Optional.ofNullable(s.getDevice().getSupportUniversal()).ifPresent(d::setSupportUniversal);
        Optional.ofNullable(s.getDevice().getSerialno()).ifPresent(d::setSerialno);
        Optional.ofNullable(s.getDevice().getOrientation()).ifPresent(d::setOrientation);
        Optional.ofNullable(s.getDevice().getScreenSize()).ifPresent(d::setScreenSize);
        Optional.ofNullable(s.getDevice().getSysCompilingTime()).ifPresent(d::setSysCompilingTime);
        Optional.ofNullable(s.getDevice().getSysStartupTime()).ifPresent(d::setSysStartupTime);
        Optional.ofNullable(s.getDevice().getSysUpdateTime()).ifPresent(d::setSysUpdateTime);
        Optional.ofNullable(s.getDevice().getImsi()).ifPresent(d::setImsi);
        Optional.ofNullable(s.getDevice().getModel()).ifPresent(d::setModel);
        Optional.ofNullable(s.getDevice().getLanguage()).ifPresent(d::setLanguage);

        return d.build();
    }

    public AdxDataProtobuf.DeviceId convertDeviceId(SspRequestParam s) {
        AdxDataProtobuf.DeviceId.Builder d = AdxDataProtobuf.DeviceId.newBuilder();
        Optional.ofNullable(s.getDeviceId().getAndroidId()).ifPresent(d::setAndroidId);
        Optional.ofNullable(s.getDeviceId().getAndroidIdMd5()).ifPresent(d::setAndroidIdMd5);
        Optional.ofNullable(s.getDeviceId().getAndroidIdSha1()).ifPresent(d::setAndroidIdSha1);
        Optional.ofNullable(s.getDeviceId().getIdfa()).ifPresent(d::setIdfa);
        Optional.ofNullable(s.getDeviceId().getIdfaMd5()).ifPresent(d::setIdfaMd5);
        Optional.ofNullable(s.getDeviceId().getIdfv()).ifPresent(d::setIdfv);
        Optional.ofNullable(s.getDeviceId().getOaid()).ifPresent(d::setOaid);
        Optional.ofNullable(s.getDeviceId().getOaidMd5()).ifPresent(d::setOaidMd5);
        Optional.ofNullable(s.getDeviceId().getImei()).ifPresent(d::setImei);
        Optional.ofNullable(s.getDeviceId().getImeiMd5()).ifPresent(d::setImeiMd5);
        Optional.ofNullable(s.getDeviceId().getOpenUdid()).ifPresent(d::setOpenUdid);

        return d.build();
    }

    public AdxDataProtobuf.Slot convertSlot(SspRequestParam sspRequestParam) {
        AdxDataProtobuf.Slot.Builder slot = AdxDataProtobuf.Slot.newBuilder();
        slot.setAdSlotId(sspRequestParam.getSlot().getAdSlotId().toString());
        slot.setType(sspRequestParam.getSlot().getType());
        slot.setHeight(sspRequestParam.getSlot().getHeight());
        slot.setWidth(sspRequestParam.getSlot().getWidth());
        slot.setMaterialType(sspRequestParam.getSlot().getMaterialType());
        return slot.build();
    }


    public AdxDataProtobuf.Network convertNetwork(SspRequestParam s) {
        AdxDataProtobuf.Network.Builder network = AdxDataProtobuf.Network.newBuilder();
        Optional.ofNullable(s.getNetwork().getBssid()).ifPresent(network::setBssid);
        Optional.ofNullable(s.getNetwork().getSsid()).ifPresent(network::setSsid);
        Optional.ofNullable(s.getNetwork().getIp()).ifPresent(network::setIp);
        Optional.ofNullable(s.getNetwork().getCountry()).ifPresent(network::setCountry);
        Optional.ofNullable(s.getNetwork().getCarrier()).ifPresent(network::setCarrier);
        Optional.ofNullable(s.getNetwork().getIpv6()).ifPresent(network::setIpv6);
        Optional.ofNullable(s.getNetwork().getMac()).ifPresent(network::setMac);
        Optional.ofNullable(s.getNetwork().getMacMd5()).ifPresent(network::setMacMd5);
        Optional.ofNullable(s.getNetwork().getMacSha1()).ifPresent(network::setMacSha1);
        Optional.ofNullable(s.getNetwork().getUserAgent()).ifPresent(network::setUserAgent);
        Optional.ofNullable(s.getNetwork().getNetworkType()).ifPresent(network::setNetworkType);
        Optional.ofNullable(s.getNetwork().getMnc()).ifPresent(network::setMnc);
        Optional.ofNullable(s.getNetwork().getMcc()).ifPresent(network::setMcc);
        return network.build();
    }

}
