package com.juan.adx.api.action;

import com.alibaba.fastjson2.JSON;
import com.juan.adx.api.config.ApiParameterConfig;
import com.juan.adx.api.context.TraceContext;
import com.juan.adx.api.service.AdxApiService;
import com.juan.adx.api.service.SlotService;
import com.juan.adx.common.cache.RedisTemplate;
import com.juan.adx.common.exception.ExceptionEnum;
import com.juan.adx.common.exception.ServiceRuntimeException;
import com.juan.adx.common.model.WebResponse;
import com.juan.adx.common.utils.UserAgentUtils;
import com.juan.adx.common.validator.ParamAssert;
import com.juan.adx.model.entity.api.AdSlotWrap;
import com.juan.adx.model.enums.OsType;
import com.juan.adx.model.ssp.common.request.SspRequestParam;
import com.juan.adx.model.ssp.common.response.SspRespAdInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@RestController
public class AdvertAction {


    @Resource
    private AdxApiService adxApiService;

    @Resource
    private SlotService slotService;

    @Resource
    private RedisTemplate redisTemplate;

    @PostMapping("/bid")
    public WebResponse getBidAd(@RequestBody SspRequestParam sspRequestParam, HttpServletResponse response, HttpServletRequest request) {
        if (ApiParameterConfig.printLogSspRequestSwitch) {
            //打印SSP请求接口响应日志
            Integer slotId = sspRequestParam.getSlot() != null ? sspRequestParam.getSlot().getAdSlotId() : null;
            log.info("ssp api request, traceId:{} | slotId:{} | requestParam:{}", TraceContext.getTraceIdByContext(), slotId, JSON.toJSONString(sspRequestParam));
        }

        checkRequestParam(sspRequestParam);

        List<SspRespAdInfo> retData = this.getad(sspRequestParam);
        return new WebResponse(retData);
    }


    public List<SspRespAdInfo> getad(SspRequestParam sspRequestParam) {
        //根据slotId查询广告主及广告位信息
        AdSlotWrap slotWrap = this.slotService.getAdSlotWrapById(sspRequestParam.getSlot().getAdSlotId());
        if (slotWrap == null) {
            throw new ServiceRuntimeException(ExceptionEnum.INVALID_SLOT_ID);
        }
        if (!slotWrap.getStatus()) {
            throw new ServiceRuntimeException(ExceptionEnum.ADVERT_SLOT_CLOSED);
        }
        if (slotWrap.getAdSlotBudgets() == null || slotWrap.getAdSlotBudgets().isEmpty()) {
            throw new ServiceRuntimeException(ExceptionEnum.NOT_CONFIG_BUDGET);
        }

        //获取广告数据
        List<SspRespAdInfo> retData = this.adxApiService.getDspAdvert(sspRequestParam, slotWrap);
        if (ApiParameterConfig.printLogSspRequestSwitch) {
            //打印SSP请求接口响应日志
            log.info("ssp api response, traceId:{} | slotId:{} | responseData :{}", TraceContext.getTraceIdByContext(), slotWrap.getSlotId(), JSON.toJSONString(retData));
        }
        return retData;
    }

    /**
     * 参数校验
     *
     * @param sspRequestParam
     */
    private void checkRequestParam(SspRequestParam sspRequestParam) {
        //基础参数校验
        ParamAssert.isTrue(sspRequestParam == null, "无效参数");
        ParamAssert.isTrue(sspRequestParam.getApp() == null, "App信息不能为空");
        ParamAssert.isTrue(sspRequestParam.getSlot() == null, "Slot信息不能为空");
        ParamAssert.isTrue(sspRequestParam.getDevice() == null, "Device信息不能为空");
        ParamAssert.isTrue(sspRequestParam.getNetwork() == null, "Network信息不能为空");
        ParamAssert.isTrue(sspRequestParam.getDeviceId() == null, "DeviceId信息不能为空");

        ParamAssert.isBlank(sspRequestParam.getApp().getPkgName(), "App@pkgName参数不能为空");
        //ParamAssert.isBlank(sspRequestParam.getApp().getAppVersion(), "App@appVersion参数不能为空");

        ParamAssert.isTrue(sspRequestParam.getSlot().getAdSlotId() == null, "Solt@adSlotId参数不能为空");
        ParamAssert.isTrue(sspRequestParam.getSlot().getType() == null, "Solt@type参数不能为空");
        ParamAssert.isTrue(sspRequestParam.getSlot().getWidth() == null, "Solt@width参数不能为空");
        ParamAssert.isTrue(sspRequestParam.getSlot().getHeight() == null, "Solt@height参数不能为空");

        ParamAssert.isTrue(sspRequestParam.getDevice().getOsType() == null, "Device@osType参数不能为空");
        ParamAssert.isTrue(sspRequestParam.getDevice().getSupportDeeplink() == null, "Device@supportDeeplink参数不能为空");
        ParamAssert.isTrue(sspRequestParam.getDevice().getSupportUniversal() == null, "Device@supportUniversal参数不能为空");
        ParamAssert.isTrue(sspRequestParam.getDevice().getType() == null, "Device@Type参数不能为空");
        ParamAssert.isTrue(sspRequestParam.getDevice().getWidth() == null, "Device@width参数不能为空");
        ParamAssert.isTrue(sspRequestParam.getDevice().getHeight() == null, "Device@height参数不能为空");
//        ParamAssert.isTrue(sspRequestParam.getDevice().getDensity() == null, "Device@density参数不能为空");
//        ParamAssert.isTrue(sspRequestParam.getDevice().getDpi() == null, "Device@dpi参数不能为空");
        ParamAssert.isTrue(sspRequestParam.getDevice().getOrientation() == null, "Device@orientation参数不能为空");
        ParamAssert.isBlank(sspRequestParam.getDevice().getOsVersion(), "Device@osVersion参数不能为空");
        ParamAssert.isBlank(sspRequestParam.getDevice().getModel(), "Device@model参数不能为空");
        ParamAssert.isBlank(sspRequestParam.getDevice().getMake(), "Device@make参数不能为空");
        ParamAssert.isBlank(sspRequestParam.getDevice().getBrand(), "Device@mbrand参数不能为空");
//        ParamAssert.isBlank(sspRequestParam.getDevice().getRomVersion(), "Device@romVersion参数不能为空");
//        ParamAssert.isBlank(sspRequestParam.getDevice().getLanguage(), "Device@language参数不能为空");
//        ParamAssert.isBlank(sspRequestParam.getDevice().getScreenSize(), "Device@screenSize参数不能为空");
//        ParamAssert.isBlank(sspRequestParam.getDevice().getSerialno(), "Device@serialno参数不能为空");

        ParamAssert.isBlank(sspRequestParam.getNetwork().getUserAgent(), "Network@userAgent参数不能为空");
        ParamAssert.isBlank(sspRequestParam.getNetwork().getIp(), "Network@ip参数不能为空");
//        ParamAssert.isBlank(sspRequestParam.getNetwork().getMac(), "Network@mac参数不能为空");
        ParamAssert.isTrue(sspRequestParam.getNetwork().getCarrier() == null, "Device@Carrier参数不能为空");
        ParamAssert.isTrue(sspRequestParam.getNetwork().getNetworkType() == null, "Device@networkType参数不能为空");

        OsType systemType = OsType.get(sspRequestParam.getDevice().getOsType());
        ParamAssert.isTrue(systemType == null, "Device@osType参数无效");

        //ip、ipv6 必传一个
        boolean ipIsNull = StringUtils.isBlank(sspRequestParam.getNetwork().getIp())
                && StringUtils.isBlank(sspRequestParam.getNetwork().getIpv6());
        ParamAssert.isTrue(ipIsNull, "Network@ip、ipv6不能同时为空");

        //Android 系统 imei  imeiMd5  oaid  oaidMd5 必传一个
        //IOS系统 idfa  idfv 必传一个
        if (systemType == OsType.ANDROID) {
            boolean deviceIdIsNull = StringUtils.isBlank(sspRequestParam.getDeviceId().getImei())
                    && StringUtils.isBlank(sspRequestParam.getDeviceId().getImeiMd5())
                    && StringUtils.isBlank(sspRequestParam.getDeviceId().getOaid())
                    && StringUtils.isBlank(sspRequestParam.getDeviceId().getOaidMd5());
            ParamAssert.isTrue(deviceIdIsNull, "android device id 不能为空");
        } else if (systemType == OsType.IOS) {
            boolean deviceIdIsNull = StringUtils.isBlank(sspRequestParam.getDeviceId().getIdfa())
                    && StringUtils.isBlank(sspRequestParam.getDeviceId().getIdfv());
            ParamAssert.isTrue(deviceIdIsNull, "ios device id 不能为空");
        }

        //校验入参ua（User-Agent）中的系统版本号与入参osVersion的系统版本号是否一致
//        String userAgentOsVersion = UserAgentParser.parseOsVersion(sspRequestParam.getNetwork().getUa(), systemType);
//        ParamAssert.isFalse(userAgentOsVersion.equals(sspRequestParam.getDevice().getOsVersion()), "系统版本不匹配");
        boolean flag = UserAgentUtils.osVerisonEqualstoUa(sspRequestParam.getDevice().getOsVersion(), sspRequestParam.getNetwork().getUserAgent(), systemType);
        ParamAssert.isFalse(flag, "系统版本不匹配");
    }


}
