package com.juan.adx.api.service.yoyo;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.juan.adx.api.action.AdvertAction;
import com.juan.adx.api.service.SlotService;
import com.juan.adx.common.cache.RedisTemplate;
import com.juan.adx.common.exception.ExceptionEnum;
import com.juan.adx.common.exception.ServiceRuntimeException;
import com.juan.adx.model.entity.api.AdSlotWrap;
import com.juan.adx.model.enums.AdvertType;
import com.juan.adx.model.enums.CooperationMode;
import com.juan.adx.model.enums.MacroParameters;
import com.juan.adx.model.enums.MaterialType;
import com.juan.adx.model.ssp.common.request.*;
import com.juan.adx.model.ssp.common.response.SspRespAdInfo;
import com.juan.adx.model.ssp.qutt.enums.QuttCarrierType;
import com.juan.adx.model.ssp.qutt.enums.QuttDeviceType;
import com.juan.adx.model.ssp.qutt.enums.QuttNetworkType;
import com.juan.adx.model.ssp.yoyo.enums.YoYoInteractionType;
import com.juan.adx.model.ssp.yoyo.enums.YoYoMaterialTemplate;
import com.juan.adx.model.ssp.yoyo.enums.YoYoOsType;
import com.juan.adx.model.ssp.yoyo.request.*;
import com.juan.adx.model.ssp.yoyo.response.BidAdContent;
import com.juan.adx.model.ssp.yoyo.response.BidBase;
import com.juan.adx.model.ssp.yoyo.response.TrackBase;
import com.juan.adx.model.ssp.yoyo.response.YoYoBidResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

/**
 * @Author: Kevin.赵伟凯
 * @CreateTime: 2024-12-19 11:44
 * @Description: 悠悠互联广告service
 * @Version: 1.0
 */

@Service
@Slf4j
public class YoYoAdService {

    public static LongAdder longAdder = new LongAdder();
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private AdvertAction advertAction;
    @Resource
    private SlotService slotService;

    //todo 添加到配置中
    private static final Map<String, String> SSP_SLOT_MATCH = Maps.newHashMap();
    static {
        SSP_SLOT_MATCH.put("y.ygh.xxl.android1", "100701");
    }

    public YoYoBidResponse getYoYoBidAd(YoYoBidRequest bidReq) {
        Map<String, List<SspRespAdInfo>> slotMap = Maps.newHashMap();
        for (ImpBase imp : bidReq.getImp()) {
            List<SspRespAdInfo> soltAdResp = getYoYoBidAd(bidReq, imp);
            if (soltAdResp == null || soltAdResp.size() == 0) {
                continue;
            }
            slotMap.put(imp.getTagId(), soltAdResp);
        }
        if (slotMap.isEmpty()) {
            return null;
        }
        YoYoBidResponse resp = convertToYoYoResponse(bidReq, slotMap);
        return resp;
    }

    /**
     * 将YoYo数据格式转换为 云联标准数据格式
     */
    private YoYoBidResponse convertToYoYoResponse(YoYoBidRequest bidReq, Map<String, List<SspRespAdInfo>> slotMap) {
        // 请求广告位Map
        Map<String, ImpBase> impMap = bidReq.getImp().stream().collect(Collectors.toMap(ImpBase::getTagId, v -> v, (v1, v2) -> v1));

        YoYoBidResponse resp = new YoYoBidResponse();
        resp.setId(bidReq.getId());
        List<BidBase> bidBaseList = Lists.newArrayList();
        resp.setBidList(bidBaseList);
        for (Map.Entry<String, List<SspRespAdInfo>> entry : slotMap.entrySet()) {
            String tagId = entry.getKey();
            SspRespAdInfo adInfo = entry.getValue().get(0);
            ImpBase reqImpBase = impMap.get(tagId);
            BidBase bidBase = new BidBase();
            bidBaseList.add(bidBase);

            bidBase.setId(UUID.randomUUID().toString());
            bidBase.setPrice(adInfo.getBidPrice());
            bidBase.setTagId(tagId);
            bidBase.setImpId(reqImpBase.getId());
            bidBase.setTargetType(YoYoInteractionType.getByType(adInfo.getInteractionType()).getSspType());
            bidBase.setIndustry("");

            //TODO, 用: 屏幕方向 + 广告类型 来判断支持的类型
            //暂时用请求的第一条模版id回填
            bidBase.setTemplateId(reqImpBase.getTemplateList().get(0).getYoyoTemplateId());

            BidAdContent adContent = new BidAdContent();
            bidBase.setBidAdContent(adContent);
            adContent.setTitle(adInfo.getTitle());
            adContent.setDescription(adInfo.getDesc());
            adContent.setSource("摇滚虎");
            adContent.setActionText(adInfo.getTitle());
            if (adInfo.getImages() != null && !adInfo.getImages().isEmpty()) {
                adContent.setImage(adInfo.getImages().get(0).getUrl());
                adContent.setImgUrlList(adInfo.getImages().stream().map(image -> image.getUrl()).collect(Collectors.toList()));
            }
            if (adInfo.getAdIcons() != null && !adInfo.getAdIcons().isEmpty()) {
                adContent.setIcon(adInfo.getAdIcons().get(0));
            }
            if (adInfo.getVideo() != null) {
                adContent.setVideo(adInfo.getVideo().getUrl());
                adContent.setDuration(adInfo.getVideo().getDuration());
                adContent.setVideoFileSize(adInfo.getVideo().getSize());
                adContent.setVideo(adInfo.getVideo().getEndUrl());
            }
            adContent.setLandingUrl(adInfo.getLandingPageUrl());
            adContent.setActionUrl(adInfo.getDownloadUrl());

            if (adInfo.getApp() != null) {
                adContent.setAppIcon(adInfo.getApp().getIconUrl());
                adContent.setAppName(adInfo.getApp().getName());
                adContent.setPkgName(adInfo.getApp().getPkgName());
                adContent.setAppVerName(adInfo.getApp().getVersion());
                adContent.setAppFileSize(adInfo.getApp().getSize().intValue());
                adContent.setAppPrivacy(adInfo.getApp().getPrivacyPolicyUrl());
                adContent.setAppPrivacyUrl(adInfo.getApp().getPrivacyPolicyUrl());
                adContent.setAppPermission(adInfo.getApp().getPermissionUrl());
                adContent.setAppPermissionUrl(adInfo.getApp().getPermissionUrl());
                adContent.setAppDeveloper(adInfo.getApp().getCorporate());
                adContent.setDescription(adInfo.getApp().getName());
                adContent.setAppDescriptionUrl(adInfo.getApp().getPermissionUrl());
                adContent.setIosAppid(adInfo.getApp().getPkgName());
            }

            adContent.setDeeplink(adInfo.getDeeplink());
            adContent.setUniversalLink(adInfo.getDeeplink());

            //微信小程序
            if (adInfo.getMiniProgram() != null) {
                adContent.setOriginId(adInfo.getMiniProgram().getMiniProgramId());
                adContent.setOriginPath(adInfo.getMiniProgram().getMiniProgramPath());
            }

            TrackBase trackBase = new TrackBase();
            bidBase.setTrack(trackBase);
            if (adInfo.getWinNoticeUrl() != null && !adInfo.getWinNoticeUrl().isEmpty()) {
                trackBase.setWinUrl(convertMacro(Lists.newArrayList(adInfo.getWinNoticeUrl())).get(0));
            }
            trackBase.setImp(convertMacro(adInfo.getTrack().getShowUrls()));
            trackBase.setClk(convertMacro(adInfo.getTrack().getClickUrls()));
            trackBase.setDel(convertMacro(adInfo.getTrack().getStartDownloadUrls()));
            trackBase.setFinishDle(convertMacro(adInfo.getTrack().getFinishDownloadUrls()));
            trackBase.setInstall(convertMacro(adInfo.getTrack().getStartInstallUrls()));
            trackBase.setFinishInstall(convertMacro(adInfo.getTrack().getFinishInstallUrls()));
            trackBase.setAppactiveurl(adInfo.getTrack().getActiveAppUrls());
            trackBase.setDeeplinkSuccess(convertMacro(adInfo.getTrack().getDeeplinkSuccessUrls()));
            trackBase.setDeeplinkFailure(adInfo.getTrack().getDeeplinkFailureUrls());
            trackBase.setDeeplinkFailInst(adInfo.getTrack().getDeeplinkFailureUrls());
            trackBase.setDeeplinkFailUninst(adInfo.getTrack().getDeeplinkUninstallkUrls());
        }

        return resp;
    }

    private List<SspRespAdInfo> getYoYoBidAd(YoYoBidRequest bidReq, ImpBase imp) {
        //TODO  yoyo的广告位和我们的广告位ID进行转换
        String ylSlotId = SSP_SLOT_MATCH.get(imp.getTagId());
        AdSlotWrap slotWrap = getAdSolt(ylSlotId);
        CooperationMode cooperationMode = CooperationMode.get(slotWrap.getSlotCooperationMode());
        if (cooperationMode == null) {
            log.error("YoYo合作模式找不到， reqId: {}", bidReq.getId());
            throw new ServiceRuntimeException(ExceptionEnum.UNCLASSIFIED);
        }

        SspRequestParam requestParam = convertToYlAdxData(bidReq, imp, slotWrap);
        List<SspRespAdInfo> respData = this.advertAction.getad(requestParam);
        log.info(">>>>>>>>>>>>>>>>>>: " + respData);
        return respData;
    }

    /**
     * 将YoYo数据格式转换为 云联标准数据格式
     */
    private SspRequestParam convertToYlAdxData(YoYoBidRequest bidReq, ImpBase imp, AdSlotWrap slotWrap) {
        SspReqSlot slot = new SspReqSlot();
        //用我们ADX定义的广告位广告类型
        YoYoMaterialTemplate template = YoYoMaterialTemplate.getByTemplateId(imp.getTemplateList().get(0).getYoyoTemplateId());
        if (template == null) {
            log.error("YoYo广告请求失败，TemplateBase的yoyoTemplateId不能匹配到广告位和类型，{}", bidReq);
            throw new ServiceRuntimeException(ExceptionEnum.INVALID_PARAM);
        }
        slot.setType(template.getAdType());
        slot.setMaterialType(convertMaterialType(template));
        slot.setAdSlotId(slotWrap.getSlotId());
        slot.setSlotId(imp.getTagId());
        slot.setWidth(0);
        slot.setHeight(0);

        SspReqApp app = new SspReqApp();
        app.setName(bidReq.getApp().getName());
        app.setVerName(bidReq.getApp().getVersion());
        app.setPkgName(bidReq.getApp().getBundle());

        SspReqUser user = null;
        UserBase reqUser = bidReq.getUser();
        if (reqUser != null) {
            user = new SspReqUser();
            user.setGender(reqUser.getGender());
            user.setAge(reqUser.getAge());
            user.setInterest(reqUser.getKeywordList());
            user.setInstalled(bidReq.getInstallAppList());
        }

        SspReqDevice device = new SspReqDevice();
        SspReqNetwork network = null;
        SspReqDeviceId deviceId = null;
        DeviceBase reqDevice = bidReq.getDevice();
        device.setOsType(YoYoOsType.getBySspType(reqDevice.getOs()).getSspType());
        device.setType(QuttDeviceType.getBySspType(reqDevice.getDeviceType()).getType());
        device.setOsVersion(Optional.ofNullable(reqDevice.getOsv()).orElse(""));
        device.setOsUiVersion(null);
        device.setAndroidApiLevel(null);
        device.setLanguage(Optional.ofNullable(reqDevice.getLanguage()).orElse(""));
        device.setTimeZone("");
        device.setSysCompilingTime(Optional.ofNullable(reqDevice.getFileInitTime()).orElse("0"));
        device.setSysUpdateTime(Optional.ofNullable(reqDevice.getFileInitTime()).orElse("0"));
        device.setSysStartupTime(Optional.ofNullable(String.valueOf(reqDevice.getFileInitTime())).orElse("0"));
        device.setBirthMark(null);
        device.setBootMark(reqDevice.getBootMark());
        device.setUpdateMark(reqDevice.getUpdateMark());
        device.setRomVersion(reqDevice.getOsv());
        device.setDeviceName(reqDevice.getMake());
        device.setDeviceNameMd5(reqDevice.getMake());
        device.setSysMemorySize(0L);
        device.setSysDiskSize(0L);
        device.setCpuNum(1);
        device.setModel(Optional.ofNullable(reqDevice.getModel()).orElse(""));
        device.setHardwareModel(reqDevice.getModel());
        device.setHmsVersion(reqDevice.getHmsCoreVersion());
        device.setHarmonyOsVersion(reqDevice.getHmsCoreVersion());
        device.setHagVersion(reqDevice.getAppStoreVersion());
        device.setSupportDeeplink(1);
        device.setSupportUniversal(1);
        device.setMake(Optional.ofNullable(reqDevice.getMake()).orElse(""));
        device.setBrand(Optional.ofNullable(reqDevice.getMake()).orElse(""));
        device.setImsi(reqDevice.getImsi());
        device.setWidth(Optional.ofNullable(reqDevice.getW()).orElse(0));
        device.setHeight(Optional.ofNullable(reqDevice.getH()).orElse(0));
        device.setDensity(null);
        device.setDpi(0);
        device.setPpi(Objects.nonNull(reqDevice.getPpi()) ? reqDevice.getPpi() : null);
        device.setOrientation(0);
        device.setScreenSize(null);
        device.setSerialno(null);

        network = new SspReqNetwork();
        network.setUserAgent(reqDevice.getUa());
        network.setIp(reqDevice.getIp());
        network.setIpv6(reqDevice.getIpv6());
        network.setMac(reqDevice.getMac());
        network.setMacMd5(reqDevice.getMacMd5());
        network.setMacSha1(null);
        network.setSsid(null);
        network.setBssid(null);
        network.setCarrier(QuttCarrierType.getBySspType(reqDevice.getCarrier()).getType());
        network.setNetworkType(QuttNetworkType.getBySspType(reqDevice.getConnectionType()).getType());
        network.setMcc(null);
        network.setMnc(null);

        deviceId = new SspReqDeviceId();
        deviceId.setImei(reqDevice.getDid());
        deviceId.setImeiMd5(reqDevice.getDidMd5());
        deviceId.setOaid(reqDevice.getOaId());
        deviceId.setOaidMd5(reqDevice.getOaIdMd5());
        deviceId.setAndroidId(reqDevice.getDpId());
        deviceId.setAndroidIdMd5(reqDevice.getDpIdMd5());
        deviceId.setAndroidIdSha1(null);
        deviceId.setIdfa(reqDevice.getIdfa());
        deviceId.setIdfaMd5(reqDevice.getIdfaMd5());
        deviceId.setIdfv(null);
        deviceId.setOpenUdid(null);

        SspReqGeo geo = null;
        GeoBase reqGeo = reqDevice.getGeo();
        if (reqGeo != null) {
            network.setCountry(reqGeo.getCountry());
            geo = new SspReqGeo();
            geo.setCoordinateType(null);
            geo.setLatitude(reqGeo.getLat());
            geo.setLongitude(reqGeo.getLon());
            geo.setTimestamp(0L);
        }

        List<BidInfoBase> bidInfoList = imp.getBidInfoList();
        SspReqDeal deal = new SspReqDeal();
        deal.setBidfloor(bidInfoList.get(0).getBidFloor().intValue());
        deal.setChargetype(bidInfoList.get(0).getBillingType());

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
     * 根据广告位类型匹配素材类型
     */
    private Integer convertMaterialType(YoYoMaterialTemplate template) {
        //物料类型 TODO
        int materialType = 0;
        if (template.getAdType().equals(AdvertType.SPLASH.getType())) {
            materialType = MaterialType.IMAGE_TEXT.getType();
        } else if (template.getAdType().equals(AdvertType.BANNER.getType())) {
            materialType = MaterialType.IMAGE.getType();
        } else if (template.getAdType().equals(AdvertType.INTERSTITIAL.getType())) {
            materialType = MaterialType.IMAGE.getType();
        } else if (template.getAdType().equals(AdvertType.INFORMATION_STREAM.getType())) {
            materialType = MaterialType.IMAGE_TEXT.getType();
        } else if (template.getAdType().equals(AdvertType.REWARDED_VIDEO.getType())) {
            materialType = MaterialType.VIDEO.getType();
        } else if (template.getAdType().equals(AdvertType.NATIVE.getType())) {
            materialType = MaterialType.IMAGE_TEXT.getType();
        } else if (template.getAdType().equals(AdvertType.PATCH.getType())) {
            materialType = MaterialType.IMAGE_TEXT.getType();
        }
        return materialType;
    }

    /**
     * 根据slotId查询广告主及广告位信息
     */
    private AdSlotWrap getAdSolt(String slotId) {
        if (slotId == null){
            throw new ServiceRuntimeException(ExceptionEnum.INVALID_SLOT_ID);
        }
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

    private List<String> convertMacro(List<String> trackUrls) {
        List<String> replaceTrackUrls = new ArrayList<String>();
        if (CollectionUtils.isEmpty(trackUrls)) {
            return replaceTrackUrls;
        }
        for (String trackUrl : trackUrls) {
            trackUrl = StringUtils.replaceOnce(trackUrl, MacroParameters.DOWN_X.getMacro(), "__DOWNX__");
            trackUrl = StringUtils.replaceOnce(trackUrl, MacroParameters.DOWN_Y.getMacro(), "__DOWNY__");
            trackUrl = StringUtils.replaceOnce(trackUrl, MacroParameters.UP_X.getMacro(), "__UPX__");
            trackUrl = StringUtils.replaceOnce(trackUrl, MacroParameters.UP_Y.getMacro(), "__UPY__");

            trackUrl = StringUtils.replaceOnce(trackUrl, MacroParameters.WIDTH.getMacro(), "__WIDTH__");
            trackUrl = StringUtils.replaceOnce(trackUrl, MacroParameters.HEIGHT.getMacro(), "__HEIGHT__");

            trackUrl = StringUtils.replaceOnce(trackUrl, MacroParameters.TS_MS.getMacro(), "__TS__");
            trackUrl = StringUtils.replaceOnce(trackUrl, MacroParameters.AIT.getMacro(), "__SLD__");

            trackUrl = StringUtils.replaceOnce(trackUrl, MacroParameters.TS_MS.getMacro(), "__AZMTS__");
            trackUrl = StringUtils.replaceOnce(trackUrl, MacroParameters.EVENT_END_TIME_MS.getMacro(), "_AZCTS__");
            trackUrl = StringUtils.replaceOnce(trackUrl, MacroParameters.PRICE.getMacro(), "{price}");

            replaceTrackUrls.add(trackUrl);
        }
        return replaceTrackUrls;
    }

}
