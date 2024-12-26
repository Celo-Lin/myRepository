package com.juan.adx.api.action;

import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Maps;
import com.juan.adx.api.config.ApiParameterConfig;
import com.juan.adx.api.context.TraceContext;
import com.juan.adx.api.service.AdxApiService;
import com.juan.adx.api.service.SlotService;
import com.juan.adx.api.service.yoyo.YoYoAdService;
import com.juan.adx.common.cache.RedisTemplate;
import com.juan.adx.common.exception.ExceptionCode;
import com.juan.adx.common.exception.ExceptionEnum;
import com.juan.adx.common.exception.ServiceRuntimeException;
import com.juan.adx.common.validator.ParamAssert;
import com.juan.adx.model.entity.api.AdSlotWrap;
import com.juan.adx.model.enums.AdvertType;
import com.juan.adx.model.enums.MacroParameters;
import com.juan.adx.model.enums.MaterialType;
import com.juan.adx.model.ssp.common.request.*;
import com.juan.adx.model.ssp.common.response.SspRespAdInfo;
import com.juan.adx.model.ssp.qutt.enums.*;
import com.juan.adx.model.ssp.qutt.request.*;
import com.juan.adx.model.ssp.qutt.response.*;
import com.juan.adx.model.ssp.yoyo.request.ImpBase;
import com.juan.adx.model.ssp.yoyo.request.YoYoBidRequest;
import com.juan.adx.model.ssp.yoyo.response.YoYoBidResponse;
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
 * @CreateTime: 2024年12月19日09:53:49
 * @Description: 优优互联ssp广告接口
 * @Version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("/yoyo")
public class YoYoController {

    @Resource
    private YoYoAdService yoYoAdService;

    @Resource
    private SlotService slotService;

    @Resource
    private AdvertAction advertAction;

    @Resource
    private RedisTemplate redisTemplate;

    @PostMapping("/bid")
    public YoYoBidResponse yoyoGetAd(@RequestBody YoYoBidRequest bidReq, HttpServletResponse response, HttpServletRequest request) throws IOException {
        Long startTime = System.currentTimeMillis();
        if (ApiParameterConfig.printLogSspRequestSwitch) {
            log.info("yoyo_ssp_origin_request, traceId:{} | requestData: {}", TraceContext.getTraceIdByContext(), JSON.toJSONString(bidReq));
        }
        validateParameter(bidReq);

        YoYoBidResponse resp = yoYoAdService.getYoYoBidAd(bidReq);
        return resp;
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

    private void validateParameter(YoYoBidRequest bidReq) {
        //基础参数校验
        ParamAssert.isTrue(bidReq == null, "请求参数不能为空");
        ParamAssert.isTrue(StringUtils.isEmpty(bidReq.getId()), "请求唯一ID不能为空");
        //Imp， ImpBase
        ParamAssert.isTrue(bidReq.getImp() == null, "imp曝光信息不能为空");
        bidReq.getImp().forEach(imp -> {

            ParamAssert.isTrue(StringUtils.isEmpty(imp.getId()), "ImpBase的广告曝光imp唯一ID不能为空");
            ParamAssert.isTrue(StringUtils.isEmpty(imp.getTagId()), "ImpBase的广告位ID标识不能为空");
            ParamAssert.isTrue(imp.getTemplateList() == null || imp.getTemplateList().isEmpty(), "ImpBase的templateList广告位资源文档广告位支持的模板不能为空");
            imp.getTemplateList().forEach(r -> {
                ParamAssert.isTrue(StringUtils.isEmpty(r.getYoyoTemplateId()), "TemplateBase的yoyoTemplateId不能为空");
                ParamAssert.isTrue(StringUtils.isEmpty(r.getTargetTypeStr()), "TemplateBase的targetTypeStr不能为空");
            });

            // BidInfoBase
            ParamAssert.isTrue(imp.getBidInfoList() == null || imp.getBidInfoList().isEmpty(), "ImpBase的bidInfoList支持的出价方式类型和底价不能为空");
            imp.getBidInfoList().forEach(r -> {
                ParamAssert.isTrue(r.getBillingType() == null || r.getBillingType() <= 0 || r.getBillingType() > 3, "BidInfoBase的billingType不能为空或者值不正确");
                ParamAssert.isTrue(r.getBillingType() == null || r.getBillingType() <= 0, "BidInfoBase的bidFloor不能为空或者值不正确");
            });

            ParamAssert.isTrue(imp.getAdsCount() == null || imp.getAdsCount() <= 0, "ImpBase的广告条数不能为空或者不正确");
        });

        //AppBase
        ParamAssert.isTrue(bidReq.getApp() == null, "App信息不能为空");
        ParamAssert.isTrue(StringUtils.isEmpty(bidReq.getApp().getBundle()), "AppBase的bundle不能为空");

        ParamAssert.isTrue(StringUtils.isEmpty(bidReq.getAt()), "at计费模式不能为空");
        //Device
        ParamAssert.isTrue(bidReq.getDevice() == null, "Device信息不能为空");
        ParamAssert.isTrue(StringUtils.isEmpty(bidReq.getDevice().getUa()), "DeviceBase的ua不能为空");
        ParamAssert.isTrue(StringUtils.isEmpty(bidReq.getDevice().getIp()), "DeviceBase的ip不能为空");
        ParamAssert.isTrue(bidReq.getDevice().getDeviceType() == null || bidReq.getDevice().getDeviceType() < 0, "DeviceBase的deviceType不能为空");
        ParamAssert.isTrue(StringUtils.isEmpty(bidReq.getDevice().getMake()), "DeviceBase的make不能为空");
        ParamAssert.isTrue(StringUtils.isEmpty(bidReq.getDevice().getModel()), "DeviceBase的model不能为空");
        ParamAssert.isTrue(StringUtils.isEmpty(bidReq.getDevice().getMobileModel()), "DeviceBase的mobileModel不能为空");
        ParamAssert.isTrue(StringUtils.isEmpty(bidReq.getDevice().getOs()), "DeviceBase的os不能为空");
        ParamAssert.isTrue(StringUtils.isEmpty(bidReq.getDevice().getOsv()), "DeviceBase的osv不能为空");
        ParamAssert.isTrue(bidReq.getDevice().getCarrier() == null, "DeviceBase的carrier不能为空");
        ParamAssert.isTrue(bidReq.getDevice().getConnectionType() == null, "DeviceBase的connectionType不能为空");
    }
}

