package com.juan.adx.api.service;

import com.alibaba.fastjson2.JSON;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import com.juan.adx.api.config.ApiParameterConfig;
import com.juan.adx.api.context.TraceContext;
import com.juan.adx.api.converter.BidPriceHelper;
import com.juan.adx.api.converter.DspParamConverterHelper;
import com.juan.adx.api.converter.RtaParamConverterHelper;
import com.juan.adx.api.service.dsp.AdRequestLogic;
import com.juan.adx.common.alg.base64.Base64Util;
import com.juan.adx.common.cache.RedisKeyUtil;
import com.juan.adx.common.cache.RedisTemplate;
import com.juan.adx.common.exception.ExceptionEnum;
import com.juan.adx.common.exception.ServiceRuntimeException;
import com.juan.adx.common.model.HttpResponseWrapper;
import com.juan.adx.common.utils.*;
import com.juan.adx.common.validator.ParamAssert;
import com.juan.adx.model.dsp.DspRequestParam;
import com.juan.adx.model.dsp.DspResponseParam;
import com.juan.adx.model.dsp.wifi.WifiDspProtobuf;
import com.juan.adx.model.dsp.youdao.enums.YouDaoResponseCode;
import com.juan.adx.model.dto.api.RequestLimitDto;
import com.juan.adx.model.entity.api.*;
import com.juan.adx.model.entity.api.ConvertDspParam.ConvertDspParamBuilder;
import com.juan.adx.model.entity.api.ConvertDspRtaParam.ConvertDspRtaParamBuilder;
import com.juan.adx.model.entity.api.ConvertSspParam.ConvertSspParamBuilder;
import com.juan.adx.model.entity.api.ConvertSspRtaParam.ConvertSspRtaParamBuilder;
import com.juan.adx.model.enums.*;
import com.juan.adx.model.ssp.common.protobuf.YlAdxDataReqConvert;
import com.juan.adx.model.ssp.common.protobuf.YlAdxDataRespConvert;
import com.juan.adx.model.ssp.common.request.SspReqDeal;
import com.juan.adx.model.ssp.common.request.SspRequestParam;
import com.juan.adx.model.ssp.common.response.SspRespAdInfo;
import com.yl.ad.protobuf.AdxDataProtobuf;
import com.yl.ad.protobuf.DomobGrpc;
import io.grpc.ManagedChannel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.LongAdder;

import static com.juan.adx.model.dsp.youdao.enums.YouDaoResponseCode.MULTI_ALL_SUCCESS;
import static com.juan.adx.model.dsp.youdao.enums.YouDaoResponseCode.SINGLE_SUCCESS;

@Slf4j
@Service
public class AdxApiService {

    public static LongAdder longAdder = new LongAdder();
    GrpcPool connectionPool = new GrpcPool("123.57.24.133", 8088, 50);
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private ReportService reportService;
    @Resource
    private RequestLimitService requestLimitService;
    @Autowired
    @Qualifier(value = "apiExecutor")
    private Executor apiExecutor;
    @Resource
    private AdRequestLogic adRequestLogic;

    public List<SspRespAdInfo> getDspAdvert(SspRequestParam sspRequestParam, AdSlotWrap slotWrap) {

        CooperationMode cooperationMode = CooperationMode.get(slotWrap.getSlotCooperationMode());
        if (cooperationMode == null) {
            throw new ServiceRuntimeException(ExceptionEnum.UNCLASSIFIED);
        }
        List<SspRespAdInfo> sspRespBids = null;
        switch (cooperationMode) {
            case PD:
                sspRespBids = this.pdMode(sspRequestParam, slotWrap);
                break;
            case RTB_AUTO:
                if (Objects.isNull(sspRequestParam.getDeal())
                        || Objects.isNull(sspRequestParam.getDeal().getBidfloor())
                        || sspRequestParam.getDeal().getBidfloor().intValue() <= 0) {
                    SspReqDeal deal = new SspReqDeal();
                    deal.setBidfloor(slotWrap.getSspBidPrice());
                    sspRequestParam.setDeal(deal);
                }
                //RTB-自动模式下，校验价格参数必填
                //ParamAssert.isTrue(sspRequestParam.getDeal() == null, "交易相关参数不能为空");
                ParamAssert.isTrue(sspRequestParam.getDeal().getBidfloor() == null || sspRequestParam.getDeal().getBidfloor().intValue() <= 0, "交易底价参数不能为空");
                sspRespBids = this.rtbMod(sspRequestParam, slotWrap, CooperationMode.RTB_AUTO);
                break;
            case RTB_MANUAL:
                sspRespBids = this.rtbMod(sspRequestParam, slotWrap, CooperationMode.RTB_MANUAL);
                break;
            default:
                break;
        }
        if (CollectionUtils.isEmpty(sspRespBids)) {
            throw new ServiceRuntimeException(ExceptionEnum.ADVERT_NOT_FILL);
        }
        return sspRespBids;
    }

    private void resetTraceId(String traceId) {
        TraceContext assertionContext = new TraceContext();
        assertionContext.setTraceId(traceId);
        TraceContext.initContext(assertionContext, true);
    }

    public List<SspRespAdInfo> rtbMod(SspRequestParam sspRequestParam, AdSlotWrap slotWrap, CooperationMode cooperationMode) {
        //批量请求DSP  ----------------------------------------
        List<AdSlotBudgetWrap> adSlotBudgetWraps = slotWrap.getAdSlotBudgets();
        List<SspRespBidWrap> rtbSspRespBidWraps = new ArrayList<SspRespBidWrap>();

        for (AdSlotBudgetWrap slotBudget : adSlotBudgetWraps) {
            SspRespBidWrap respBidWrap = grpcAsync(sspRequestParam, slotBudget, slotWrap, cooperationMode);
            rtbSspRespBidWraps.add(respBidWrap);
        }
        //批量请求DSP  ----------------------------------------
        // 比较价格,取最高价格的resp
        SspRespBidWrap sspRespBidWrap = null;
        if (CollectionUtils.isNotEmpty(rtbSspRespBidWraps)) {
            sspRespBidWrap = rtbSspRespBidWraps.stream()
                    .filter(Objects::nonNull)
                    .max(Comparator.comparingInt(b -> b.getBidRecord().getDspReturnPrice()))
                    .orElse(null);
        }
        List<SspRespAdInfo> resultList = sspRespBidWrap != null ? sspRespBidWrap.getSspRespBids() : null;
        //保存竞价记录
        if (CollectionUtils.isNotEmpty(resultList)) {
            this.saveBidRecord(sspRespBidWrap.getBidRecord());
        }
        return resultList;
        //-----------------------------------------------------------------------------------------
    }

    /**
     * grpc 异步请求 DSP 接口(go)
     */
    public SspRespBidWrap grpcAsync(SspRequestParam sspRequestParam, AdSlotBudgetWrap slotBudget, AdSlotWrap slotWrap, CooperationMode cooperationMode) {
        //初始化竞价信息
        BidRecord bidRecord = new BidRecord();
        bidRecord.setTraceId(Long.parseLong(TraceContext.getTraceIdByContext()));
        bidRecord.setSspPartnerId(slotWrap.getSspPartnerId());
        bidRecord.setDspPartnerId(slotBudget.getDspPartnerId());
        bidRecord.setAppId(slotWrap.getAppId());
        bidRecord.setSlotId(slotWrap.getSlotId());
        bidRecord.setBudgetId(slotBudget.getBudgetId());
        bidRecord.setCooperationMode(CooperationModeSimple.RTB.getMode());
        bidRecord.setReportDisplayStatus(ReportStatus.UNREPORTED.getStatus());
        bidRecord.setSspFloorPriceSnapshot(slotBudget.getSspFloorPrice());
        bidRecord.setDspFloorPriceSnapshot(slotBudget.getDspFloorPrice());
        bidRecord.setSspPremiumRateSnapshot(slotBudget.getSspPremiumRate());
        bidRecord.setDspFloatingRateSnapshot(slotBudget.getDspFloatingRate());
        bidRecord.setCtime(LocalDateUtils.getNowSeconds());
        //计算并修改请求DSP的出价
        Integer sppBidFloor = sspRequestParam.getDeal().getBidfloor();
        int dspBidFloor = BidPriceHelper.calcRequestToDspPriceForRtbAuto(bidRecord, sppBidFloor, CooperationMode.RTB_AUTO);
        sspRequestParam.getDeal().setBidfloor(dspBidFloor);

        //TODO 多盟的requestId是number类型
        sspRequestParam.setRequestId(String.valueOf(System.currentTimeMillis()));
        YlAdxDataReqConvert reqConvert = new YlAdxDataReqConvert();
        AdxDataProtobuf.AdReq adReqProtobuf = reqConvert.convert(sspRequestParam);
        String reqJson = JSON.toJSONString(sspRequestParam);
        log.info("reqJson: {}", reqJson);

        //请求DSP广告
        ManagedChannel channel = connectionPool.borrowChannel();
        DomobGrpc.DomobBlockingStub stubBlocking = DomobGrpc.newBlockingStub(channel);
        AdxDataProtobuf.AdResp resp = stubBlocking.adGet(adReqProtobuf);
        if (resp == null || resp.getCode() != 200) {
            //TODO 记录错误日志等
            return null;
        }

        YlAdxDataRespConvert respConvert = new YlAdxDataRespConvert();
        SspRespBidWrap respBidWrap = respConvert.convert(resp);
        log.info(">>>>>>>>:" + JSON.toJSONString(respBidWrap));
        List<SspRespAdInfo> sspRespBids = respBidWrap.getSspRespBids();


        //DSP返回的价格 <= 0 OR DSP返回的价格为NULL，则丢弃DSP广告
        Integer dspReturnPrice = sspRespBids.get(0) != null ? sspRespBids.get(0).getBidPrice() : Integer.valueOf(0);
        if (dspReturnPrice == null || dspReturnPrice.intValue() <= 0) {
            return null;
        }
        //计算并修改返回给SSP的报价，价格为0则丢弃广告
        Integer sspReturnPrice = BidPriceHelper.calcReturnToSspPriceForRtbAuto(bidRecord, dspReturnPrice, CooperationMode.RTB_AUTO);
        if (sspReturnPrice == null || sspReturnPrice.intValue() <= 0) {
            return null;
        }
        for (SspRespAdInfo respBid : sspRespBids) {
            respBid.setBidPrice(sspReturnPrice);
        }
        dspRespDataHandler(sspRequestParam, slotBudget, slotWrap, sspRespBids, cooperationMode);

        SspRespBidWrap sspRespBid = new SspRespBidWrap();
        sspRespBid.setBidRecord(bidRecord);
        sspRespBid.setSspRespBids(sspRespBids);
        return sspRespBid;
    }

    /**
     * DSP 响应数据处理
     */
    public void dspRespDataHandler(SspRequestParam sspRequestParam, AdSlotBudgetWrap slotBudget, AdSlotWrap slotWrap,
                                   List<SspRespAdInfo> sspRespBids, CooperationMode cooperationMode) {
        // 添加自己平台的上报链接
        for (SspRespAdInfo adInfo : sspRespBids) {
            this.fillReportUrls(adInfo, slotWrap.getSlotId(), slotBudget.getBudgetId(), cooperationMode);
        }

        //保存广告位请求数统计
        this.reportService.saveReportEvent(ReportEventType.REQUEST, slotBudget.getSlotId(), slotBudget.getBudgetId(), cooperationMode.getMode(), null);
        //保存广告位填充数统计
        if (CollectionUtils.isNotEmpty(sspRespBids)) {
            this.reportService.saveReportEvent(ReportEventType.FILL, slotBudget.getSlotId(), slotBudget.getBudgetId(), cooperationMode.getMode(), null);
        }
        //统计DSP单设备每日最大请求计数
        RequestLimitDto requestLimitDto = new RequestLimitDto();
        requestLimitDto.setSlotId(slotBudget.getSlotId());
        requestLimitDto.setBudgetId(slotBudget.getBudgetId());
        requestLimitDto.setLimitType(slotBudget.getLimitType());
        requestLimitDto.setMaxRequests(slotBudget.getMaxRequests());
        requestLimitDto.setDeviceMaxRequests(slotBudget.getDeviceMaxRequests());
        requestLimitDto.setOsType(sspRequestParam.getDevice().getOsType());
        requestLimitDto.setSspReqDeviceId(sspRequestParam.getDeviceId());
        this.requestLimitService.requestDspByDeviceCounter(requestLimitDto);
    }


    protected void fillReportUrls(SspRespAdInfo adInfo, Integer slotId, Integer budgetId, CooperationMode cooperationMode) {
        String traceId = TraceContext.getTraceIdByContext();
        ReportLinkParam reportLinkParam = new ReportLinkParam();
        reportLinkParam.setTraceId(Long.parseLong(traceId));
        reportLinkParam.setSlotId(slotId);
        reportLinkParam.setBudgetId(budgetId);
        reportLinkParam.setMod(cooperationMode.getMode());
        reportLinkParam.setWinUrl(null);
        String baseData = JSON.toJSONString(reportLinkParam);
        String reportParamStr = Base64Util.encodeWithKeyUrlSafe(baseData, ApiParameterConfig.base64EncryptKey);

        String adxShowUrl = String.format(ApiParameterConfig.adxReportDisplayUrl, reportParamStr, slotId);
        String adxClickUrl = String.format(ApiParameterConfig.adxReportClickUrl, reportParamStr, slotId);
        String adxDownloadUrl = String.format(ApiParameterConfig.adxReportDownloadUrl, reportParamStr, slotId);
        String adxInstallUrl = String.format(ApiParameterConfig.adxReportInstallUrl, reportParamStr, slotId);
        String adxDeeplinkUrl = String.format(ApiParameterConfig.adxReportDpUrl, reportParamStr, slotId);

        List<String> showUrls = Optional.ofNullable(adInfo.getTrack().getShowUrls()).orElseGet(ArrayList::new);
        List<String> clickUrls = Optional.ofNullable(adInfo.getTrack().getClickUrls()).orElseGet(ArrayList::new);
        List<String> finishDownloadUrls = Optional.ofNullable(adInfo.getTrack().getFinishDownloadUrls()).orElseGet(ArrayList::new);
        List<String> finishInstallUrls = Optional.ofNullable(adInfo.getTrack().getFinishInstallUrls()).orElseGet(ArrayList::new);
        List<String> deeplinkSuccessUrls = Optional.ofNullable(adInfo.getTrack().getDeeplinkSuccessUrls()).orElseGet(ArrayList::new);

        showUrls.add(adxShowUrl);
        clickUrls.add(adxClickUrl);
        finishDownloadUrls.add(adxDownloadUrl);
        finishInstallUrls.add(adxInstallUrl);
        deeplinkSuccessUrls.add(adxDeeplinkUrl);
    }

    public List<SspRespAdInfo> rtbMod_old(SspRequestParam sspRequestParam, AdSlotWrap slotWrap, CooperationMode cooperationMode) {
        List<CompletableFuture<SspRespBidWrap>> futures = new ArrayList<CompletableFuture<SspRespBidWrap>>();
        List<AdSlotBudgetWrap> adSlotBudgetWraps = slotWrap.getAdSlotBudgets();
        Integer appId = slotWrap.getAppId();
        Integer sspPartnerId = slotWrap.getSspPartnerId();
        String traceId = TraceContext.getTraceIdByContext();
		/*AtomicInteger requestExceedCount = new AtomicInteger(0);
		AtomicInteger deviceRequestExceedCount = new AtomicInteger(0);*/
        for (AdSlotBudgetWrap slotBudget : adSlotBudgetWraps) {
            //并发请求DSP接口
            CompletableFuture<SspRespBidWrap> future = CompletableFuture.supplyAsync(() -> {

                resetTraceId(traceId);
                SspRespBidWrap sspRespBidWrap = null;
                if (cooperationMode == CooperationMode.RTB_AUTO && slotBudget.isHasRta()) {
                    sspRespBidWrap = this.rta(sspRequestParam, slotBudget, slotWrap.getSlotId(), appId, sspPartnerId);
                } else if (cooperationMode == CooperationMode.RTB_AUTO) {
                    sspRespBidWrap = this.rtbAuto(sspRequestParam, slotBudget, slotWrap.getSlotId(), appId, sspPartnerId);
                } else if (cooperationMode == CooperationMode.RTB_MANUAL) {
                    sspRespBidWrap = this.rtbManual(sspRequestParam, slotBudget, slotWrap.getSlotId(), appId, sspPartnerId);
                }
                return sspRespBidWrap;
            }, apiExecutor).exceptionally(exception -> {
                if (exception.getCause() instanceof ServiceRuntimeException) {
                    ServiceRuntimeException serviceRuntimeException = (ServiceRuntimeException) exception.getCause();
                    if (LogPrintCheckUtils.needPrintLog(longAdder, 100)) {
                        log.error("广告位：{}，longAdder：{},RTB模式，异步任务处理过程中发生业务异常, message: {} ", slotWrap.getSlotId(), longAdder.sum(), serviceRuntimeException.getMessage());
                    }
				    		
							/*if(ExceptionCode.AdxApiCode.DSP_REQUEST_EXCEED_MAX_LIMIT == serviceRuntimeException.getCode().intValue()) {
								requestExceedCount.incrementAndGet();
							}
							if(ExceptionCode.AdxApiCode.DSP_DEVICE_REQUEST_EXCEED_MAX_LIMIT == serviceRuntimeException.getCode().intValue()) {
								deviceRequestExceedCount.incrementAndGet();
							}*/
                } else {
                    StringBuilder stackTraceString = new StringBuilder();
                    stackTraceString.append("\n");
                    stackTraceString.append(exception.getMessage()).append("\n");
                    Throwable causeThrowable = exception.getCause();
                    StackTraceElement[] stackTrace = causeThrowable != null ? causeThrowable.getStackTrace() : exception.getStackTrace();
                    int depth = stackTrace != null && stackTrace.length > 5 ? 5 : stackTrace.length; // 限制输出的堆栈深度为5
                    for (int j = 0; j < depth; j++) {
                        stackTraceString.append(stackTrace[j]).append("\n");
                    }

                    if (ApiParameterConfig.printLogDspRequestSwitch) {
                        log.error("广告位：{}，longAdder：{},RTB模式，异步任务处理过程中发生其它异常：{}", slotWrap.getSlotId(), longAdder.sum(), JsonUtils.toJson(stackTrace));
                    } else if (LogPrintCheckUtils.needPrintLog(longAdder, 100)) {
                        log.error("广告位：{}，longAdder：{},RTB模式，异步任务处理过程中发生其它异常：{}", slotWrap.getSlotId(), longAdder.sum(), stackTraceString);
                    }
                }
                return null;
            }).handle((result, ex) -> {
                // 在回调中 reset TraceContext
                TraceContext.resetContext();
                return result;// 保留原始结果
            });

            futures.add(future);
        }

        List<CompletableFuture<SspRespBidWrap>> completedFutures = new ArrayList<CompletableFuture<SspRespBidWrap>>();
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
        try {
            allFutures.get(ApiParameterConfig.rtbWaitingChildThreadTimeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            log.error("RTB模式，获取任务处理结果发生异常, message: {} ", e.toString());
        }

        for (CompletableFuture<SspRespBidWrap> future : futures) {
            if (future.isDone() && !future.isCompletedExceptionally()) {
                completedFutures.add(future);
            }
        }

        List<SspRespBidWrap> rtaSspRespBidWraps = new ArrayList<SspRespBidWrap>();
        List<SspRespBidWrap> rtbSspRespBidWraps = new ArrayList<SspRespBidWrap>();
        for (CompletableFuture<SspRespBidWrap> completableFuture : completedFutures) {

            SspRespBidWrap sspRespBidWrap = completableFuture.join();
            if (sspRespBidWrap == null) {
                continue;
            }
            if (sspRespBidWrap.isHasRta()) {
                rtaSspRespBidWraps.add(sspRespBidWrap);
            } else {
                rtbSspRespBidWraps.add(sspRespBidWrap);
            }
        }

        SspRespBidWrap sspRespBidWrap = null;
        if (CollectionUtils.isNotEmpty(rtbSspRespBidWraps)) {
            sspRespBidWrap = rtbSspRespBidWraps.stream()
                    .filter(Objects::nonNull)
                    .max(Comparator.comparingInt(b -> b.getBidRecord().getDspReturnPrice()))
                    .orElse(null);
        }

        if (CollectionUtils.isEmpty(rtbSspRespBidWraps) && CollectionUtils.isNotEmpty(rtaSspRespBidWraps)) {
            sspRespBidWrap = rtaSspRespBidWraps.stream()
                    .filter(Objects::nonNull)
                    .max(Comparator.comparingInt(b -> {
                                Integer priorityValue = b.getRtaPriorityValue();
                                return priorityValue != null ? priorityValue : Integer.MIN_VALUE;
                            })
                    )
                    .orElse(null);
        }

        List<SspRespAdInfo> resultList = sspRespBidWrap != null ? sspRespBidWrap.getSspRespBids() : null;
        //保存竞价记录
        if (CollectionUtils.isNotEmpty(resultList)) {
            this.saveBidRecord(sspRespBidWrap.getBidRecord());
        }
        return resultList;
    }

    public SspRespBidWrap rta(SspRequestParam sspRequestParam, AdSlotBudgetWrap slotBudget, Integer slotId, Integer
            appId, Integer sspPartnerId) {
        Integer sppBidFloor = sspRequestParam.getDeal().getBidfloor();
        Integer sspReturnPrice = slotBudget.getRtaSspFloorPrice();
        if (sspReturnPrice == null || sspReturnPrice.intValue() <= 0) {
            return null;
        }
        //初始化竞价信息
        BidRecord bidRecord = new BidRecord();
        bidRecord.setTraceId(Long.parseLong(TraceContext.getTraceIdByContext()));
        bidRecord.setSspPartnerId(sspPartnerId);
        bidRecord.setDspPartnerId(slotBudget.getDspPartnerId());
        bidRecord.setAppId(appId);
        bidRecord.setSlotId(slotId);
        bidRecord.setBudgetId(slotBudget.getBudgetId());
        bidRecord.setCooperationMode(CooperationModeSimple.RTB.getMode());
        bidRecord.setReportDisplayStatus(ReportStatus.UNREPORTED.getStatus());
        bidRecord.setSspBidPrice(sppBidFloor);
        bidRecord.setSspReturnPrice(sspReturnPrice);
        bidRecord.setDspBidPrice(0);
        bidRecord.setDspReturnPrice(0);
        bidRecord.setSspFloorPriceSnapshot(slotBudget.getSspFloorPrice());
        bidRecord.setDspFloorPriceSnapshot(slotBudget.getDspFloorPrice());
        bidRecord.setSspPremiumRateSnapshot(slotBudget.getSspPremiumRate());
        bidRecord.setDspFloatingRateSnapshot(slotBudget.getDspFloatingRate());
        bidRecord.setCtime(LocalDateUtils.getNowSeconds());

        Integer budgetId = slotBudget.getBudgetId();

        RequestLimitDto requestLimitDto = new RequestLimitDto();
        requestLimitDto.setSlotId(slotId);
        requestLimitDto.setBudgetId(budgetId);
        requestLimitDto.setLimitType(slotBudget.getLimitType());
        requestLimitDto.setMaxRequests(slotBudget.getMaxRequests());
        requestLimitDto.setDeviceMaxRequests(slotBudget.getDeviceMaxRequests());
        requestLimitDto.setOsType(sspRequestParam.getDevice().getOsType());
        requestLimitDto.setSspReqDeviceId(sspRequestParam.getDeviceId());

        //检查DSP每日最大请求数(展示，点击，下载+dp，安装)限制
        boolean allowRequestDsp = this.requestLimitService.allowRequestDsp(requestLimitDto);
        if (!allowRequestDsp) {
            throw new ServiceRuntimeException(ExceptionEnum.DSP_REQUEST_EXCEED_MAX_LIMIT);
        }

        //检查DSP单设备每日最大请求数限制
        boolean allowRequestDspByDevice = this.requestLimitService.allowRequestDspByDevice(requestLimitDto);
        if (!allowRequestDspByDevice) {
            throw new ServiceRuntimeException(ExceptionEnum.DSP_DEVICE_REQUEST_EXCEED_MAX_LIMIT);
        }
        //获取RTA枚举信息
        Rta rta = Rta.get(slotBudget.getPinyinName());
        if (rta == null) {
            throw new ServiceRuntimeException(ExceptionEnum.NOT_CONFIG_DSP_MAPPING);
        }
        String dspTag = slotBudget.getPinyinName();
        String rtaApiUrl = slotBudget.getRtaApiUrl();
        CooperationModeSimple modeSimple = CooperationModeSimple.RTB;
        //Step-1：DSP接口请求参数转换 | Step-2：请求DSP接口获取广告数据 | Step-3：DSP接口响应参数转换
        ConvertSspRtaParam convertSspParam = new ConvertSspRtaParamBuilder()
                .sspRequestParam(sspRequestParam)
                .adSlotBudgetWrap(slotBudget)
                .rta(rta)
                .build();
        DspRequestParam dspRequestParam = RtaParamConverterHelper.convertRequest(convertSspParam);

        String dspResponseStr = this.requestRta(rtaApiUrl, dspRequestParam.getRequestParamMap(), dspRequestParam.getRequestHeaders(), dspTag, slotId);

        String requestUniqueId = sspRequestParam.getRequestId();
        ConvertDspRtaParam convertDspParam = new ConvertDspRtaParamBuilder()
                .dspRespData(dspResponseStr)
                .adSlotBudgetWrap(slotBudget)
                .requestUniqueId(requestUniqueId)
                .rta(rta)
                .modeSimple(modeSimple)
                .build();
        DspResponseParam dspResponseParam = RtaParamConverterHelper.convertResponse(convertDspParam);
        List<SspRespAdInfo> respAdInfos = dspResponseParam.getSspRespAdInfo();
        //保存广告位请求数统计
        this.reportService.saveReportEvent(ReportEventType.REQUEST, slotId, budgetId, modeSimple.getMode(), null);
        //保存广告位填充数统计
        if (CollectionUtils.isNotEmpty(respAdInfos)) {
            this.reportService.saveReportEvent(ReportEventType.FILL, slotId, budgetId, modeSimple.getMode(), null);
        }
        //统计DSP API单设备每日最大请求计数
        this.requestLimitService.requestDspByDeviceCounter(requestLimitDto);

        SspRespBidWrap sspRespBid = new SspRespBidWrap();
        sspRespBid.setSspRespBids(respAdInfos);
        sspRespBid.setBidRecord(bidRecord);
        sspRespBid.setHasRta(Boolean.TRUE);
        sspRespBid.setRtaPriorityValue(slotBudget.getRtaPriorityValue());
        return sspRespBid;

    }

    public SspRespBidWrap rtbAuto(SspRequestParam sspRequestParam, AdSlotBudgetWrap slotBudget, Integer
            slotId, Integer appId, Integer sspPartnerId) {
        //初始化竞价信息
        BidRecord bidRecord = new BidRecord();
        bidRecord.setTraceId(Long.parseLong(TraceContext.getTraceIdByContext()));
        bidRecord.setSspPartnerId(sspPartnerId);
        bidRecord.setDspPartnerId(slotBudget.getDspPartnerId());
        bidRecord.setAppId(appId);
        bidRecord.setSlotId(slotId);
        bidRecord.setBudgetId(slotBudget.getBudgetId());
        bidRecord.setCooperationMode(CooperationModeSimple.RTB.getMode());
        bidRecord.setReportDisplayStatus(ReportStatus.UNREPORTED.getStatus());
        bidRecord.setSspFloorPriceSnapshot(slotBudget.getSspFloorPrice());
        bidRecord.setDspFloorPriceSnapshot(slotBudget.getDspFloorPrice());
        bidRecord.setSspPremiumRateSnapshot(slotBudget.getSspPremiumRate());
        bidRecord.setDspFloatingRateSnapshot(slotBudget.getDspFloatingRate());
        bidRecord.setCtime(LocalDateUtils.getNowSeconds());
        //计算并修改请求DSP的出价
        Integer sppBidFloor = sspRequestParam.getDeal().getBidfloor();
        int dspBidFloor = BidPriceHelper.calcRequestToDspPriceForRtbAuto(bidRecord, sppBidFloor, CooperationMode.RTB_AUTO);
        sspRequestParam.getDeal().setBidfloor(dspBidFloor);
        //请求DSP广告
        List<SspRespAdInfo> sspRespBids = this.sendRequestToDsp(sspRequestParam, slotBudget, CooperationMode.RTB_AUTO);
        if (CollectionUtils.isEmpty(sspRespBids)) {
            return null;
        }
        //DSP返回的价格 <= 0 OR DSP返回的价格为NULL，则丢弃DSP广告
        Integer dspReturnPrice = sspRespBids.get(0) != null ? sspRespBids.get(0).getBidPrice() : Integer.valueOf(0);
        if (dspReturnPrice == null || dspReturnPrice.intValue() <= 0) {
            return null;
        }
        //计算并修改返回给SSP的报价，价格为0则丢弃广告
        Integer sspReturnPrice = BidPriceHelper.calcReturnToSspPriceForRtbAuto(bidRecord, dspReturnPrice, CooperationMode.RTB_AUTO);
        if (sspReturnPrice == null || sspReturnPrice.intValue() <= 0) {
            return null;
        }
        for (SspRespAdInfo respBid : sspRespBids) {
            respBid.setBidPrice(sspReturnPrice);
        }
        SspRespBidWrap sspRespBid = new SspRespBidWrap();
        sspRespBid.setBidRecord(bidRecord);
        sspRespBid.setSspRespBids(sspRespBids);
        return sspRespBid;
    }

    public SspRespBidWrap rtbManual(SspRequestParam sspRequestParam, AdSlotBudgetWrap slotBudget, Integer
            slotId, Integer appId, Integer sspPartnerId) {
        //初始化竞价信息
        BidRecord bidRecord = new BidRecord();
        bidRecord.setTraceId(Long.parseLong(TraceContext.getTraceIdByContext()));
        bidRecord.setSspPartnerId(sspPartnerId);
        bidRecord.setDspPartnerId(slotBudget.getDspPartnerId());
        bidRecord.setAppId(appId);
        bidRecord.setSlotId(slotId);
        bidRecord.setBudgetId(slotBudget.getBudgetId());
        bidRecord.setCooperationMode(CooperationModeSimple.RTB.getMode());
        bidRecord.setReportDisplayStatus(ReportStatus.UNREPORTED.getStatus());
        bidRecord.setSspFloorPriceSnapshot(slotBudget.getSspFloorPrice());
        bidRecord.setDspFloorPriceSnapshot(slotBudget.getDspFloorPrice());
        bidRecord.setSspPremiumRateSnapshot(slotBudget.getSspPremiumRate());
        bidRecord.setDspFloatingRateSnapshot(slotBudget.getDspFloatingRate());
        bidRecord.setCtime(LocalDateUtils.getNowSeconds());
        //计算并修改请求DSP的出价
        SspReqDeal deal = sspRequestParam.getDeal() != null ? sspRequestParam.getDeal() : new SspReqDeal();
        Integer sppBidFloor = deal.getBidfloor();
        int dspFloor = BidPriceHelper.calcRequestToDspPriceForRtbAuto(bidRecord, sppBidFloor, CooperationMode.RTB_MANUAL);
        deal.setBidfloor(dspFloor);
        sspRequestParam.setDeal(deal);
        //请求DSP广告
        List<SspRespAdInfo> sspRespBids = this.sendRequestToDsp(sspRequestParam, slotBudget, CooperationMode.RTB_MANUAL);
        if (CollectionUtils.isEmpty(sspRespBids)) {
            return null;
        }
        //DSP返回的价格 <= 0 OR DSP返回的价格为NULL，则丢弃DSP广告
        Integer dspReturnPrice = sspRespBids.get(0) != null ? sspRespBids.get(0).getBidPrice() : Integer.valueOf(0);
        if (dspReturnPrice == null || dspReturnPrice.intValue() <= 0) {
            return null;
        }
        //RTB（手动）模式，DSP返回的价格 <= 媒体底价，则丢弃DSP广告
        if (dspReturnPrice.intValue() <= bidRecord.getSspFloorPriceSnapshot().intValue()) {
            return null;
        }
        //计算并修改返回给SSP的报价
        Integer sspReturnPrice = BidPriceHelper.calcReturnToSspPriceForRtbAuto(bidRecord, dspReturnPrice, CooperationMode.RTB_MANUAL);
        for (SspRespAdInfo respBid : sspRespBids) {
            respBid.setBidPrice(sspReturnPrice);
        }
        SspRespBidWrap sspRespBid = new SspRespBidWrap();
        sspRespBid.setBidRecord(bidRecord);
        sspRespBid.setSspRespBids(sspRespBids);
        return sspRespBid;
    }

    public List<SspRespAdInfo> pdMode(SspRequestParam sspRequestParam, AdSlotWrap slotWrap) {
        AdSlotBudgetWrap adSlotBudgetWrap = slotWrap.getAdSlotBudgets().get(0);
        List<SspRespAdInfo> respBids = this.sendRequestToDsp(sspRequestParam, adSlotBudgetWrap, CooperationMode.PD);
        if (CollectionUtils.isEmpty(respBids)) {
            return null;
        }
        //PD模式不返回价格
        for (SspRespAdInfo respBid : respBids) {
            respBid.setBidPrice(0);
        }
        return respBids;
    }

    /**
     * 请求DSP限制配置检查
     *
     * @return
     */
    private Dsp checkDspRequestLimit(RequestLimitDto requestLimitDto, AdSlotBudgetWrap slotBudget) {
        //检查DSP每日最大请求数限制
        boolean allowRequestDsp = this.requestLimitService.allowRequestDsp(requestLimitDto);
        if (!allowRequestDsp) {
            throw new ServiceRuntimeException(ExceptionEnum.DSP_REQUEST_EXCEED_MAX_LIMIT);
        }
        //检查DSP单设备每日最大请求数限制
        boolean allowRequestDspByDevice = this.requestLimitService.allowRequestDspByDevice(requestLimitDto);
        if (!allowRequestDspByDevice) {
            throw new ServiceRuntimeException(ExceptionEnum.DSP_DEVICE_REQUEST_EXCEED_MAX_LIMIT);
        }
        //获取DSP枚举信息
        Dsp dsp = Dsp.get(slotBudget.getPinyinName());
        if (dsp == null) {
            throw new ServiceRuntimeException(ExceptionEnum.NOT_CONFIG_DSP_MAPPING);
        }
        return dsp;
    }

    private List<SspRespAdInfo> sendRequestToDsp(SspRequestParam sspRequestParam, AdSlotBudgetWrap slotBudget, CooperationMode mode) {
        RequestLimitDto requestLimitDto = new RequestLimitDto();
        requestLimitDto.setSlotId(slotBudget.getSlotId());
        requestLimitDto.setBudgetId(slotBudget.getBudgetId());
        requestLimitDto.setLimitType(slotBudget.getLimitType());
        requestLimitDto.setMaxRequests(slotBudget.getMaxRequests());
        requestLimitDto.setDeviceMaxRequests(slotBudget.getDeviceMaxRequests());
        requestLimitDto.setOsType(sspRequestParam.getDevice().getOsType());
        requestLimitDto.setSspReqDeviceId(sspRequestParam.getDeviceId());

        Dsp dsp = checkDspRequestLimit(requestLimitDto, slotBudget);
        String dspTag = slotBudget.getPinyinName();
        CooperationModeSimple modeSimple = mode == CooperationMode.PD ? CooperationModeSimple.PD : CooperationModeSimple.RTB;
        //Step-1：DSP接口请求参数转换 | Step-2：请求DSP接口获取广告数据 | Step-3：DSP接口响应参数转换
        ConvertSspParam convertSspParam = new ConvertSspParamBuilder()
                .sspRequestParam(sspRequestParam)
                .adSlotBudgetWrap(slotBudget)
                .mod(mode)
                .dsp(dsp)
                .build();
        DspRequestParam dspRequestParam = DspParamConverterHelper.convertRequest(convertSspParam);
        String dspApiUrl = slotBudget.getApiUrl();

        String dspResponseStr = this.requestDspSelector(dsp, dspApiUrl, dspRequestParam.getRequestParamJson(),
                dspRequestParam.getRequestParamMap(), dspRequestParam.getRequestParamBytes(), dspRequestParam.getRequestHeaders(), dspTag, slotBudget.getSlotId());

        String sspRequestId = sspRequestParam.getRequestId();
        ConvertDspParam convertDspParam = new ConvertDspParamBuilder()
                .sspRequestParam(sspRequestParam)
                .dspRespData(dspResponseStr)
                .adSlotBudgetWrap(slotBudget)
                .sspRequestId(sspRequestId)
                .dsp(dsp)
                .mod(mode)
                .modeSimple(modeSimple)
                .build();
        DspResponseParam dspResponseParam = DspParamConverterHelper.convertResponse(convertDspParam);
        List<SspRespAdInfo> respAdInfos = dspResponseParam.getSspRespAdInfo();
        //保存广告位请求数统计
        this.reportService.saveReportEvent(ReportEventType.REQUEST, slotBudget.getSlotId(), slotBudget.getBudgetId(), modeSimple.getMode(), null);
        //保存广告位填充数统计
        if (CollectionUtils.isNotEmpty(respAdInfos)) {
            this.reportService.saveReportEvent(ReportEventType.FILL, slotBudget.getSlotId(), slotBudget.getBudgetId(), modeSimple.getMode(), null);
        }
        //统计DSP单设备每日最大请求计数
        this.requestLimitService.requestDspByDeviceCounter(requestLimitDto);

        return respAdInfos;
    }

    /**
     * 根据不同dsp选择不同请求方式
     *
     * @param dsp
     * @param apiUrl
     * @param jsonParams
     * @param requestParam
     * @param headersMap
     * @param dspTag
     * @param slotId
     * @return
     */
    private String requestDspSelector(Dsp dsp, String apiUrl, String
            jsonParams, Map<String, String> requestParam, byte[] requestParamBytes, Map<
            String, String> headersMap, String dspTag, Integer slotId) {
        switch (dsp) {
            case YOU_DAO:
                return this.requestDspForYouDao(apiUrl, requestParam, headersMap, dspTag, slotId);
            case YI_DIAN:
                return this.requestDspYiDian(apiUrl, jsonParams, headersMap, dspTag, slotId);
            case WIFI:
                return this.requestDspForWifi(apiUrl, requestParamBytes, headersMap, dspTag, slotId);
            default:
                return this.requestDsp(apiUrl, jsonParams, headersMap, dspTag, slotId);
        }
    }

    /**
     * 请求wifidsp广告数据
     *
     * @param apiUrl
     * @param requestParam
     * @param headersMap
     * @param dspTag
     * @param slotId
     * @return
     */
    private String requestDspForWifi(String apiUrl, byte[] requestParam, Map<String, String> headersMap, String
            dspTag, Integer slotId) {
        headersMap.put("Content-Type", "application/x-protobuf");
        String dspResponseStr = StringUtils.EMPTY;
        String code = StringUtils.EMPTY;
        String reason = StringUtils.EMPTY;
        try {
            HttpResponseWrapper responseWrapper = HttpsUtil.httpPostProtobuf(apiUrl, requestParam, headersMap, ApiParameterConfig.requestDspApiTimeout);
            if (ApiParameterConfig.printLogDspRequestSwitch) {
                try {
                    //打印dsp接口请求日志
                    WifiDspProtobuf.FlyingShuttleBidRequest flyingShuttleBidRequest = WifiDspProtobuf.FlyingShuttleBidRequest.parseFrom(requestParam);
                    log.info("dsp api request, traceId:{} | slotId:{} | dsp:{} | requestParams :{} ",
                            TraceContext.getTraceIdByContext(), slotId, dspTag, JsonFormat.printer().print(flyingShuttleBidRequest));
                } catch (InvalidProtocolBufferException e) {
                    log.warn("{} 请求数据转换Protobuf对象错误", "WIFI");
                    return null;
                }
            }
            if (responseWrapper != null && responseWrapper.getHeaders() != null && responseWrapper.getStatusCode() == HttpStatus.SC_OK) {
                if (ApiParameterConfig.printLogDspRequestSwitch) {
                    try {
                        WifiDspProtobuf.FlyingShuttleBidResponse flyingShuttleBidResponse = WifiDspProtobuf.FlyingShuttleBidResponse.parseFrom(responseWrapper.getByteBody());
                        //打印dsp接口请求响应日志
                        log.info("dsp api response, traceId:{} | slotId:{} | dsp:{} | responseData :{}| statusCode:{} | reason:{}",
                                TraceContext.getTraceIdByContext(), slotId, dspTag, JsonFormat.printer().print(flyingShuttleBidResponse), code, reason);
                    } catch (InvalidProtocolBufferException e) {
                        log.warn("{} 响应数据转换Protobuf对象错误", "WIFI");
                        return null;
                    }
                }
                return Base64.getEncoder().encodeToString(responseWrapper.getByteBody());
            } else {
                reason = responseWrapper != null ? responseWrapper.getReason() : null;
            }

        } catch (Exception e) {
            String errorInfo = "HTTP请求DSP接口异常. ";
            log.error(errorInfo, e);
        }
        if (ApiParameterConfig.printLogDspRequestSwitch) {
            //打印dsp接口请求响应日志
            log.info("dsp api response, traceId:{} | slotId:{} | dsp:{} | responseData :{}| statusCode:{} | reason:{}",
                    TraceContext.getTraceIdByContext(), slotId, dspTag, dspResponseStr, code, reason);
        }
        return dspResponseStr;
    }


    /**
     * 请求有道dsp广告数据
     *
     * @param apiUrl
     * @param requestParam
     * @param headersMap
     * @param dspTag
     * @param slotId
     * @return
     */
    private String requestDspForYouDao(String
                                               apiUrl, Map<String, String> requestParam, Map<String, String> headersMap, String dspTag, Integer slotId) {
        if (requestParam == null || requestParam.keySet().isEmpty()) {
            return null;
        }
        String dspResponseStr = StringUtils.EMPTY;
        String code = StringUtils.EMPTY;
        String reason = StringUtils.EMPTY;
        try {
            if (ApiParameterConfig.printLogDspRequestSwitch) {
                //打印dsp接口请求日志
                log.info("dsp api request, traceId:{} | slotId:{} | dsp:{} | requestParams :{} ",
                        TraceContext.getTraceIdByContext(), slotId, dspTag, JSON.toJSONString(requestParam));
            }
            //有道 application/x-www-form-urlencoded
            HttpResponseWrapper responseWrapper = HttpsUtil.httpPost(apiUrl, requestParam, headersMap, ApiParameterConfig.requestDspApiTimeout);
            if (responseWrapper != null && responseWrapper.getHeaders() != null && responseWrapper.getStatusCode() == HttpStatus.SC_OK) {
                code = JSON.parseObject(responseWrapper.getHeaders().get("X-Adstate")).get("code").toString();
                YouDaoResponseCode youDaoResponseCode = YouDaoResponseCode.get(code);
                if (SINGLE_SUCCESS.getCode().equals(code) || MULTI_ALL_SUCCESS.getCode().equals(code)) {
                    dspResponseStr = responseWrapper.getBody();
                } else {
                    reason = youDaoResponseCode.getReason();
                }
            } else {
                reason = responseWrapper != null ? responseWrapper.getReason() : null;
            }

        } catch (Exception e) {
            String errorInfo = "HTTP请求DSP接口异常. ";
            log.error(errorInfo, e);
        }
        if (ApiParameterConfig.printLogDspRequestSwitch) {
            //打印dsp接口请求响应日志
            log.info("dsp api response, traceId:{} | slotId:{} | dsp:{} | responseData :{}| statusCode:{} | reason:{}",
                    TraceContext.getTraceIdByContext(), slotId, dspTag, dspResponseStr, code, reason);
        }
        return dspResponseStr;
    }

    /**
     * 请求DSP接口获取广告数据
     */
    private String requestDsp(String apiUrl, String jsonParams, Map<String, String> headersMap, String
            dspTag, Integer slotId) {
        if (StringUtils.isBlank(jsonParams)) {
            return null;
        }
        String dspResponseStr = StringUtils.EMPTY;
        try {
            if (ApiParameterConfig.printLogDspRequestSwitch) {
                //打印dsp接口请求日志
                log.info("dsp api request, traceId:{} | slotId:{} | dsp:{} | requestParams :{} ",
                        TraceContext.getTraceIdByContext(), slotId, dspTag, jsonParams);
            }

            HttpResponseWrapper responseWrapper = HttpsUtil.httpPost(apiUrl, jsonParams, headersMap, ApiParameterConfig.requestDspApiTimeout);
            if (responseWrapper != null && responseWrapper.getStatusCode() == HttpStatus.SC_OK) {
                dspResponseStr = responseWrapper.getBody();
            } else {
                Integer statusCode = responseWrapper != null ? responseWrapper.getStatusCode() : null;
                String reason = responseWrapper != null ? responseWrapper.getReason() : null;
                String body = responseWrapper != null ? responseWrapper.getBody() : null;
                //打印dsp接口异常响应日志
                if (ApiParameterConfig.printLogDspRequestSwitch) {
                    log.info("dsp api response error, traceId:{} | slotId:{} | dsp:{} | statusCode:{} | reason:{} | body:{}",
                            TraceContext.getTraceIdByContext(), slotId, dspTag, statusCode, reason, body);
                }
            }
        } catch (Exception e) {
            String errorInfo = "HTTP请求DSP接口异常. ";
            log.error(errorInfo, e);
        }
        if (ApiParameterConfig.printLogDspRequestSwitch) {
            //打印dsp接口请求响应日志
            log.info("dsp api response, traceId:{} | slotId:{} | dsp:{} | responseData :{}",
                    TraceContext.getTraceIdByContext(), slotId, dspTag, dspResponseStr);
        }
        return dspResponseStr;
    }

    /**
     * 请求DSP接口获取广告数据
     */
    private String requestDspYiDian(String apiUrl, String jsonParams, Map<String, String> headersMap, String dspTag, Integer slotId) {
        if (StringUtils.isBlank(jsonParams)) {
            return null;
        }
        String dspResponseStr = StringUtils.EMPTY;
        try {
            if (ApiParameterConfig.printLogDspRequestSwitch) {
                //打印dsp接口请求日志
                log.info("dsp api request, traceId:{} | slotId:{} | dsp:{} | requestParams :{} ",
                        TraceContext.getTraceIdByContext(), slotId, dspTag, jsonParams);
            }

            HttpResponseWrapper responseWrapper = HttpsUtil.httpPostCompress(apiUrl, jsonParams, headersMap, ApiParameterConfig.requestDspApiTimeout);
            if (responseWrapper != null && responseWrapper.getStatusCode() == HttpStatus.SC_OK) {
                dspResponseStr = responseWrapper.getBody();
            } else {
                Integer statusCode = responseWrapper != null ? responseWrapper.getStatusCode() : null;
                String reason = responseWrapper != null ? responseWrapper.getReason() : null;
                String body = responseWrapper != null ? responseWrapper.getBody() : null;
                //打印dsp接口异常响应日志
                log.info("dsp api response error, traceId:{} | slotId:{} | dsp:{} | statusCode:{} | reason:{} | body:{}",
                        TraceContext.getTraceIdByContext(), slotId, dspTag, statusCode, reason, body);
            }
        } catch (Exception e) {
            String errorInfo = "HTTP请求DSP接口异常. ";
            log.error(errorInfo, e);
        }
        if (ApiParameterConfig.printLogDspRequestSwitch) {
            //打印dsp接口请求响应日志
            log.info("dsp api response, traceId:{} | slotId:{} | dsp:{} | responseData :{}",
                    TraceContext.getTraceIdByContext(), slotId, dspTag, dspResponseStr);
        }
        return dspResponseStr;
    }


    /**
     * 请求RTA接口
     */
    private String requestRta(String
                                      apiUrl, Map<String, String> paramMap, Map<String, String> headersMap, String dspTag, Integer slotId) {
        String dspResponseStr = StringUtils.EMPTY;
        try {
            if (ApiParameterConfig.printLogDspRequestSwitch) {
                //打印rta接口请求日志
                log.info("rta api request, traceId:{} | slotId:{} | dsp:{} | url:{} | requestParams :{}",
                        TraceContext.getTraceIdByContext(), slotId, dspTag, apiUrl, paramMap);
            }
            HttpResponseWrapper responseWrapper = HttpsUtil.httpGet(apiUrl, headersMap, paramMap, ApiParameterConfig.requestDspApiTimeout);
            if (responseWrapper != null && responseWrapper.getStatusCode() == HttpStatus.SC_OK) {
                dspResponseStr = responseWrapper.getBody();
            } else {
                Integer statusCode = responseWrapper != null ? responseWrapper.getStatusCode() : null;
                String reason = responseWrapper != null ? responseWrapper.getReason() : null;
                String body = responseWrapper != null ? responseWrapper.getBody() : null;
                //打印rta接口异常响应日志
                log.warn("rta api response error, traceId:{} | slotId:{} | dsp:{} | statusCode:{} | reason:{} | body:{}",
                        TraceContext.getTraceIdByContext(), slotId, dspTag, statusCode, reason, body);
            }
        } catch (Exception e) {
            String errorInfo = "";
            log.error(errorInfo, e);
        }
        if (ApiParameterConfig.printLogDspRequestSwitch) {
            //打印rta接口请求响应日志
            log.info("rta api response, traceId:{} | slotId:{} | dsp:{} | responseData :{}",
                    TraceContext.getTraceIdByContext(), slotId, dspTag, dspResponseStr);
        }
        return dspResponseStr;
    }


    /**
     * 保存广告出价记录
     */
    public void saveBidRecord(BidRecord bidRecord) {
        String todayStr = LocalDateUtils.getNowDate(LocalDateUtils.DATE_PLAIN_FORMATTER);
        String bidRecordValue = JSON.toJSONString(bidRecord);
        String bidRecordKey = RedisKeyUtil.getAdBidRecordKey(todayStr);
        //广告出价记录KEY的过期时间由定时任务刷入DB时设置
        this.redisTemplate.LISTS.lpush(bidRecordKey, bidRecordValue);
    }

    /**
     * 保存DSP请求记录（此方案暂时没启用）
     */
    @Deprecated
    public void saveRequestRecord(AdSlotBudgetWrap adSlotBudgetWrap, int mod) {
        String todayStr = LocalDateUtils.getNowDate(LocalDateUtils.DATE_PLAIN_FORMATTER);
        DspRequestRecord dspRequestRecord = new DspRequestRecord();
        dspRequestRecord.setSlotId(adSlotBudgetWrap.getSlotId());
        dspRequestRecord.setBudgetId(adSlotBudgetWrap.getBudgetId());
        dspRequestRecord.setCooperationMode(mod);
        dspRequestRecord.setTimeSeconds(LocalDateUtils.getNowSeconds());
        String requestRecordValue = JSON.toJSONString(dspRequestRecord);
        String requestRecordKey = RedisKeyUtil.getRequestRecordKey(todayStr);
        this.redisTemplate.LISTS.lpush(requestRecordKey, requestRecordValue);
    }

}
