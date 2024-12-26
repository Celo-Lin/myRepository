package com.juan.adx.api.action;

import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Maps;
import com.juan.adx.api.config.ApiParameterConfig;
import com.juan.adx.api.context.TraceContext;
import com.juan.adx.api.service.AdxApiService;
import com.juan.adx.api.service.SlotService;
import com.juan.adx.common.cache.RedisTemplate;
import com.juan.adx.common.exception.ExceptionCode;
import com.juan.adx.common.exception.ExceptionEnum;
import com.juan.adx.common.exception.ServiceRuntimeException;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.common.validator.ParamAssert;
import com.juan.adx.model.dsp.yueke.enums.YueKeOsType;
import com.juan.adx.model.entity.api.AdSlotWrap;
import com.juan.adx.model.enums.AdvertType;
import com.juan.adx.model.enums.MacroParameters;
import com.juan.adx.model.enums.MaterialType;
import com.juan.adx.model.ssp.common.request.*;
import com.juan.adx.model.ssp.common.response.SspRespAdInfo;
import com.juan.adx.model.ssp.qutt.enums.*;
import com.juan.adx.model.ssp.qutt.request.*;
import com.juan.adx.model.ssp.qutt.response.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: Kevin.赵伟凯
 * @CreateTime: 2024-05-18 18:40
 * @Description: 趣头条ssp广告接口
 * @Version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("/ssp")
public class QuttAction {


    @Resource
    private AdxApiService adxApiService;

    @Resource
    private SlotService slotService;

    @Resource
    private AdvertAction advertAction;

    @Resource
    private RedisTemplate redisTemplate;

    @PostMapping("/qutt")
    public QuttSspResp quttGetAd(@RequestBody QuttSspReq bidReq, HttpServletResponse response, HttpServletRequest request) throws IOException {
        Long startTime = System.currentTimeMillis();
        if (ApiParameterConfig.printLogSspRequestSwitch) {
            log.info("qutt_ssp_origin_request, traceId:{} | requestData: {}", TraceContext.getTraceIdByContext(),  JSON.toJSONString(bidReq));
        }

        validateParameter(bidReq);
        QuttSspResp videoReqResp = videoFilterOut(bidReq);
        if(videoReqResp != null){
            return videoReqResp;
        }

        Map<String, List<QuttSspReqNative>> implNativeMapList = bidReq.getImpressions().stream().collect(Collectors.toMap(QuttSspReqImpression::getId, QuttSspReqImpression::getNatives));
        Map<String, List<SspRespAdInfo>> adDataMap = getad(bidReq);

        QuttSspResp resp = bulidSspRespone(bidReq, adDataMap, implNativeMapList, startTime);
        if (ApiParameterConfig.printLogSspRequestSwitch) {
            log.info("qutt_ssp_origin_response, traceId:{} | responsetData: {}", TraceContext.getTraceIdByContext(),  JSON.toJSONString(resp));
        }
        return resp;
    }

    /**
     *  //TODO 临时： 过滤掉视频物料请求  2024年5月30日14:34:36
     */
    private QuttSspResp videoFilterOut(QuttSspReq bidReq) {
        try {
            for (QuttSspReqImpression i : bidReq.getImpressions()) {
                if(i.getNatives() != null && !i.getNatives().isEmpty()){
                    // 7 视频， 8激励视频
                    if(i.getNatives().get(0).getType() != null && i.getNatives().get(0).getType() >= 7) {
                        QuttSspResp resp = new QuttSspResp();
                        resp.setRequestId(bidReq.getId());
                        resp.setCode(ExceptionCode.AdxApiCode.ADVERT_NOT_FILL);
                        log.trace("++++++qutt 过滤掉视频请求+++++++");
                        return resp;
                    }
                }
            }
        }catch (Exception e) {
            log.error("++++++qutt 过滤掉视频请求 Error +++++++ {}", e.getMessage());
        }
        return null;
    }


    /**
     * 构造趣盟返回参数
     */
    private QuttSspResp bulidSspRespone(QuttSspReq bidReq, Map<String, List<SspRespAdInfo>> adDataMap, Map<String, List<QuttSspReqNative>> implNativeListMap, Long startTime) {
        QuttSspResp resp = new QuttSspResp();
        resp.setRequestId(bidReq.getId());
        if (adDataMap.isEmpty()) {
            resp.setCode(ExceptionCode.AdxApiCode.ADVERT_NOT_FILL);
            return resp;
        }

        resp.setCode(ExceptionCode.SUCCESS);
        List<QuttSspRespSeatBid> seatBidList = new ArrayList<>();
        for (Map.Entry<String, List<SspRespAdInfo>> entry : adDataMap.entrySet()) {
            String impId = entry.getKey();
            List<SspRespAdInfo> adList = entry.getValue();
            QuttSspRespSeatBid seatBid = new QuttSspRespSeatBid();
            seatBid.setImpressionId(impId);
            if (adList == null || adList.isEmpty()) {
                continue;
            }
            seatBid.setBids(new ArrayList<>(adList.size()));
            for (SspRespAdInfo retAd : adList) {
                QuttSspRespBid bid = new QuttSspRespBid();
                bid.setId(TraceContext.getTraceIdByContext());
                bid.setBidPrice(retAd.getBidPrice());
                //曝光，点击进行宏替换
                bid.setImpUrls(convertMacro(retAd.getTrack().getShowUrls()));
                bid.setClkUrls(convertMacro(retAd.getTrack().getClickUrls()));

                bid.setDbmUrls(retAd.getTrack().getStartDownloadUrls());
                bid.setDemUrls(retAd.getTrack().getFinishDownloadUrls());
                bid.setIbmUrls(retAd.getTrack().getStartInstallUrls());
                bid.setIemUrls(retAd.getTrack().getFinishInstallUrls());

                bid.setDpClks(convertMacro(retAd.getTrack().getDeeplinkSuccessUrls()));
                bid.setDpFailedClks(convertMacro(retAd.getTrack().getDeeplinkFailureUrls()));
                bid.setWinUrls(retAd.getWinNoticeUrl());
                bid.setLUrls(retAd.getLossNoticeUrl());

                // ------ Creative -----------
                QuttSspRespCreative creative = new QuttSspRespCreative();
                creative.setId(TraceContext.getTraceIdByContext());
                // 设置返回的物料类型
                List<QuttSspReqNative> nativeList = implNativeListMap.get(impId);
                if (nativeList != null && !nativeList.isEmpty()) {
                    //TODO 待检查和确认, 用请求的物料类型返回
                    creative.setType(nativeList.get(0).getType());
                } else {
                    creative.setType(convertMaterialType(retAd.getAdType()));
                }

                creative.setInteractionType(QuttInteractionType.getByType(retAd.getInteractionType()).getSspType());
                creative.setDeepLinkUrl(retAd.getDeeplink());
                creative.setLandingUrl(retAd.getLandingPageUrl());
                creative.setUniversalLink(retAd.getDeeplink());
                creative.setTitle(new QuttSspRespTitle(retAd.getTitle()));
                creative.setDesc(new QuttSspRespDesc(retAd.getDesc(), null));

                //Image
                if (retAd.getImages() != null && !retAd.getImages().isEmpty()) {
                    List<QuttSspRespImage> imageList = new ArrayList<>();
                    retAd.getImages().forEach(i -> {
                        QuttSspRespImage image = new QuttSspRespImage();
                        //TODO  图片类型
                        image.setType(0);
                        image.setSrc(i.getUrl());
                        image.setWidth(i.getWidth());
                        image.setHeight(i.getHeight());
                        imageList.add(image);
                    });
                    creative.setImages(imageList);
                }

                //Video
                if (retAd.getVideo() != null && StringUtils.isNotEmpty(retAd.getVideo().getUrl())) {
                    List<QuttSspRespVideo> videoList = new ArrayList<>();
                    QuttSspRespVideo video = new QuttSspRespVideo();
                    //TODO  视频类型
                    video.setType(0);
                    video.setWidth(retAd.getVideo().getWidth());
                    video.setHeight(retAd.getVideo().getHeight());
                    video.setDuration(retAd.getVideo().getDuration());
                    videoList.add(video);
                    creative.setVideos(videoList);
                }

                //App
                if (retAd.getApp() != null) {
                    QuttSspRespApp app = new QuttSspRespApp();
                    app.setAppName(retAd.getApp().getName());
                    app.setPackageName(retAd.getApp().getPkgName());
                    app.setPackageSize(retAd.getApp().getSize());
                    app.setAppVersion(retAd.getApp().getVersion());
                    app.setPrivacyProtocolUrl(retAd.getApp().getPrivacyPolicyUrl());
                    app.setPermissionProtocolUrl(retAd.getApp().getPermissionUrl());
                    app.setSrc(retAd.getDownloadUrl());
                    app.setDevelopers("");
                    creative.setApp(app);
                }

                //weixin
                if (retAd.getMiniProgram() != null) {
                    QuttSspRespWeixin weixin = new QuttSspRespWeixin();
                    weixin.setProgramId(retAd.getMiniProgram().getMiniProgramId());
                    weixin.setProgramPath(retAd.getMiniProgram().getMiniProgramPath());
                    weixin.setReleaseType(null);
                    creative.setWeixin(weixin);
                }

                bid.setCreative(creative);
                seatBid.getBids().add(bid);
            }
            seatBidList.add(seatBid);
        }

        resp.setSeatBids(seatBidList);
        resp.setProcessingTime(System.currentTimeMillis() - startTime);
        return resp;
    }

    private List<String> convertMacro(List<String> trackUrls) {
        List<String> replaceTrackUrls = new ArrayList<String>();
        if (CollectionUtils.isEmpty(trackUrls)) {
            return replaceTrackUrls;
        }
        for (String trackUrl : trackUrls) {
            trackUrl = StringUtils.replaceOnce(trackUrl, MacroParameters.DOWN_X.getMacro(), "${down_x}");
            trackUrl = StringUtils.replaceOnce(trackUrl, MacroParameters.DOWN_Y.getMacro(), "${down_y}");
            trackUrl = StringUtils.replaceOnce(trackUrl, MacroParameters.UP_X.getMacro(), "${up_x}");
            trackUrl = StringUtils.replaceOnce(trackUrl, MacroParameters.UP_Y.getMacro(), "${up_y}");

            trackUrl = StringUtils.replaceOnce(trackUrl, MacroParameters.WIDTH.getMacro(), "${ad_width}");
            trackUrl = StringUtils.replaceOnce(trackUrl, MacroParameters.HEIGHT.getMacro(), "${ad_height}");
            trackUrl = StringUtils.replaceOnce(trackUrl, MacroParameters.DP_WIDTH.getMacro(), "${dp_width}");
            trackUrl = StringUtils.replaceOnce(trackUrl, MacroParameters.DP_HEIGHT.getMacro(), "${dp_height}");

            trackUrl = StringUtils.replaceOnce(trackUrl, MacroParameters.DP_POS_DOWN_X.getMacro(), "${dp_down_x}");
            trackUrl = StringUtils.replaceOnce(trackUrl, MacroParameters.DP_POS_DOWN_Y.getMacro(), "${dp_down_y}");
            trackUrl = StringUtils.replaceOnce(trackUrl, MacroParameters.DP_POS_UP_X.getMacro(), "${dp_up_x}");
            trackUrl = StringUtils.replaceOnce(trackUrl, MacroParameters.DP_POS_UP_Y.getMacro(), "${dp_up_y}");
            trackUrl = StringUtils.replaceOnce(trackUrl, MacroParameters.AIT.getMacro(), "${sld}");

            replaceTrackUrls.add(trackUrl);
        }
        return replaceTrackUrls;
    }

    /**
     * 根据广告位类型匹配素材类型
     *
     * @param slotType
     * @return
     */
    private Integer convertMaterialType(Integer slotType) {
        //物料类型 TODO
        int materialType = 0;
        if (slotType.equals(AdvertType.SPLASH.getType())) {
            materialType = MaterialType.IMAGE_TEXT.getType();
        } else if (slotType.equals(AdvertType.BANNER.getType())) {
            materialType = MaterialType.IMAGE.getType();
        } else if (slotType.equals(AdvertType.INTERSTITIAL.getType())) {
            materialType = MaterialType.IMAGE.getType();
        } else if (slotType.equals(AdvertType.INFORMATION_STREAM.getType())) {
            materialType = MaterialType.IMAGE_TEXT.getType();
        } else if (slotType.equals(AdvertType.REWARDED_VIDEO.getType())) {
            materialType = MaterialType.VIDEO.getType();
        } else if (slotType.equals(AdvertType.NATIVE.getType())) {
            materialType = MaterialType.IMAGE_TEXT.getType();
        } else if (slotType.equals(AdvertType.PATCH.getType())) {
            materialType = MaterialType.IMAGE_TEXT.getType();
        }
        return materialType;
    }

    public Map<String, List<SspRespAdInfo>> getad(QuttSspReq bidReq) {
        Map<String, List<SspRespAdInfo>> retMap = Maps.newHashMap();
        bidReq.getImpressions().forEach(i -> {
            AdSlotWrap slotWrap = getAdSolt(i.getAdslotId());

            //获取广告数据
            List<SspRespAdInfo> retData = this.advertAction.getad(buildSspRequestParam(bidReq, i, slotWrap));
            //List<SspRespAdInfo> retData = this.adxApiService.getDspAdvert(buildSspRequestParam(bidReq, i, slotWrap), slotWrap);
            if (ApiParameterConfig.printLogSspRequestSwitch) {
                log.info("ssp qutt api response, traceId:{} | slotId:{} | responseData :{}", TraceContext.getTraceIdByContext(), slotWrap.getSlotId(), JSON.toJSONString(retData));
            }
            if (retData != null && !retData.isEmpty()) {
                retMap.put(i.getId(), retData);
            }
        });

        return retMap;
    }

    private SspRequestParam buildSspRequestParam(QuttSspReq bidReq, QuttSspReqImpression impression, AdSlotWrap slotWrap) {
        SspReqSlot slot = new SspReqSlot();
        //用我们ADX定义的广告位广告类型
        slot.setType(slotWrap.getSlotType());
        slot.setMaterialType(convertMaterialType(slotWrap.getSlotType()));
//        slot.setMaterialType(3);
        slot.setAdSlotId(Integer.valueOf(impression.getAdslotId()));
        slot.setWidth(0);
        slot.setHeight(0);

        SspReqApp app = null;
        QuttSspReqApp reqApp = bidReq.getApp();
        if (reqApp != null) {
            app = new SspReqApp();
            app.setName(reqApp.getAppName());
            app.setVerName(reqApp.getAppVersion());
            app.setPkgName(reqApp.getAppBundleId());
            app.setAppStoreVersion(reqApp.getAppBundleUid());
        }

        SspReqUser user = null;
        QuttSspReqUser reqUser = bidReq.getUser();
        if (reqUser != null) {
            user = new SspReqUser();
            user.setGender(reqUser.getGender());
            user.setAge(reqUser.getAge());
            user.setInterest(null);
            user.setInstalled(null);
        }

        SspReqDevice device = null;
        SspReqNetwork network = null;
        SspReqDeviceId deviceId = null;
        QuttSspReqDevice reqDevice = bidReq.getDevice();
        if (reqDevice != null) {
            device = new SspReqDevice();
            device.setOsType(QuttOsType.getBySspType(reqDevice.getOs()).getType());
            device.setType(QuttDeviceType.getBySspType(reqDevice.getDeviceType()).getType());
            device.setOsVersion(Optional.ofNullable(reqDevice.getOsVersion()).orElse(""));
            device.setOsUiVersion(null);
            device.setAndroidApiLevel(null);
            device.setLanguage(Optional.ofNullable(reqDevice.getLanguage()).orElse(""));
            device.setTimeZone(Optional.ofNullable(reqDevice.getLocalTzTime()).orElse(""));
            device.setSysCompilingTime(Optional.ofNullable(reqDevice.getSysCompilingTime()).orElse("0"));
            device.setSysUpdateTime(Optional.ofNullable(reqDevice.getMbTime()).orElse("0"));
            device.setSysStartupTime(Optional.ofNullable(String.valueOf(reqDevice.getElapseTime())).orElse("0"));
            device.setBirthMark(null);
            device.setBootMark(reqDevice.getBootMark());
            device.setUpdateMark(reqDevice.getUpdateMark());
            device.setRomVersion(reqDevice.getOsVersion());
            device.setDeviceName(reqDevice.getDeviceName());
            device.setDeviceNameMd5(reqDevice.getDeviceName());
            device.setSysMemorySize(Optional.ofNullable(reqDevice.getMemTotal()).orElse(0L));
            device.setSysDiskSize(Optional.ofNullable(reqDevice.getDiskTotal()).orElse(0L));
            device.setCpuNum(1);
            device.setModel(Optional.ofNullable(reqDevice.getModel()).orElse(""));
            device.setHardwareModel(null);
            device.setHmsVersion(reqDevice.getHuaweiVerCodeOfHms());
            device.setHarmonyOsVersion(null);
            device.setHagVersion(null);
            device.setSupportDeeplink(1);
            device.setSupportUniversal(1);
            device.setMake(Optional.ofNullable(reqDevice.getBrand()).orElse(""));
            device.setBrand(Optional.ofNullable(reqDevice.getBrand()).orElse(""));
            device.setImsi(null);
            device.setWidth(reqDevice.getScreenWidth() == null ? 0 : reqDevice.getScreenWidth());
            device.setHeight(reqDevice.getScreenHeight() == null ? 0 : reqDevice.getScreenHeight());
            device.setDensity(null);
            device.setDpi(reqDevice.getDpi());
            device.setPpi(Objects.nonNull(reqDevice.getPpi()) ? (int) reqDevice.getPpi() : null);
            device.setOrientation(0);
            device.setScreenSize(null);
            device.setSerialno(null);

            network = new SspReqNetwork();
            network.setUserAgent(StringUtils.isEmpty(reqDevice.getUserAgent()) ? reqDevice.getBrowserUserAgent() : reqDevice.getUserAgent());
            network.setIp(reqDevice.getIp());
            network.setIpv6(null);
            network.setMac(reqDevice.getMac());
            network.setMacMd5(reqDevice.getMacMd5());
            network.setMacSha1(null);
            network.setSsid(null);
            network.setBssid(null);
            network.setCarrier(QuttCarrierType.getBySspType(reqDevice.getCarrier()).getType());
            network.setNetworkType(QuttNetworkType.getBySspType(reqDevice.getConnectionType()).getType());
            network.setMcc(null);
            network.setMnc(null);
            network.setCountry(reqDevice.getCountryCode());

            deviceId = new SspReqDeviceId();
            deviceId.setImei(reqDevice.getImei());
            deviceId.setImeiMd5(reqDevice.getImeiMd5());
            deviceId.setOaid(reqDevice.getOaid());
            deviceId.setOaidMd5(reqDevice.getOaidMd5());
            deviceId.setAndroidId(reqDevice.getAndroidId());
            deviceId.setAndroidIdMd5(reqDevice.getAndroidIdMd5());
            deviceId.setAndroidIdSha1(null);
            deviceId.setIdfa(reqDevice.getIdfa());
            deviceId.setIdfaMd5(reqDevice.getIdfaMd5());
            deviceId.setIdfv(null);
            deviceId.setOpenUdid(null);
        }

        SspReqGeo geo = null;
        QuttSspReqGeo reqGeo = bidReq.getGeo();
        if (reqGeo != null) {
            geo = new SspReqGeo();
            geo.setCoordinateType(null);
            geo.setLatitude(reqGeo.getLat());
            geo.setLongitude(reqGeo.getLon());
            geo.setTimestamp(reqGeo.getLocTime());
        }

        SspReqDeal deal = new SspReqDeal();
        deal.setBidfloor(impression.getBidFloor());

        SspRequestParam sspRequestParam = new SspRequestParam();
        sspRequestParam.setRequestId(bidReq.getId());
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


    /**
     * 根据slotId查询广告主及广告位信息
     */
    private AdSlotWrap getAdSolt(String slotId) {
        AdSlotWrap slotWrap = this.slotService.getAdSlotWrapById(Integer.valueOf(slotId));
        if (slotWrap == null) {
            throw new ServiceRuntimeException(ExceptionEnum.INVALID_SLOT_ID);
        }
        if (!slotWrap.getStatus()) {
            throw new ServiceRuntimeException(ExceptionEnum.ADVERT_SLOT_CLOSED);
        }
        if (slotWrap.getAdSlotBudgets() == null || slotWrap.getAdSlotBudgets().isEmpty()) {
            throw new ServiceRuntimeException(ExceptionEnum.NOT_CONFIG_BUDGET);
        }
        return slotWrap;
    }

    private void validateImpressionParameter(List<QuttSspReqImpression> impressions) {
        impressions.forEach(i -> {
            ParamAssert.isTrue(StringUtils.isEmpty(i.getId()), "impressions对象中的id不能为空");
            ParamAssert.isTrue(StringUtils.isEmpty(i.getAdslotId()), "impressions对象中的adslot_id不能为空(ADX提供的⼴告位id)");
            ParamAssert.isTrue(i.getBidFloor() == null, "impressions对象中的bid_ﬂoor不能为空(⼴告位的CPM底价)");
            if (i.getNatives() != null && !i.getNatives().isEmpty()) {
                i.getNatives().forEach(n -> {
                    ParamAssert.isTrue(n.getType() == null, "natives对象中的type不能为空(请求的物料类型)");
                    if (n.getImgs() != null && !n.getImgs().isEmpty()) {
                        n.getImgs().forEach(im -> {
                            ParamAssert.isTrue(im.getType() == null, "images对象中的type不能为空(图⽚素材类型)");
                        });
                    }
                    if (n.getVideos() != null && !n.getVideos().isEmpty()) {
                        n.getVideos().forEach(im -> {
                            ParamAssert.isTrue(im.getType() == null, "images对象中的type不能为空(图⽚素材类型)");
                        });
                    }
                });
            }
        });
    }

    private void validateParameter(QuttSspReq bidReq) {
        //基础参数校验
        ParamAssert.isTrue(bidReq == null, "无效参数");
        ParamAssert.isTrue(bidReq.getImpressions() == null || bidReq.getImpressions().isEmpty(), "impressions信息不能为空");
        ParamAssert.isTrue(bidReq.getApp() == null, "App信息不能为空");
        ParamAssert.isTrue(bidReq.getDevice() == null, "Device信息不能为空");
    }
}

