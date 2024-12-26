package com.juan.adx.api.service.dsp;


import com.juan.adx.api.context.TraceContext;
import com.juan.adx.common.utils.http.AdBaseRequest;
import com.juan.adx.common.utils.http.AdBatchRequest;
import com.juan.adx.model.dsp.AdChannelResModel;
import com.juan.adx.model.entity.api.AdSlotBudgetWrap;
import com.juan.adx.model.entity.api.AdSlotWrap;
import com.juan.adx.model.entity.api.SspRespBidWrap;
import com.juan.adx.model.enums.CooperationMode;
import com.juan.adx.model.ssp.common.request.SspRequestParam;
import com.juan.adx.model.ssp.common.response.SspRespAdInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @Author: Kevin.赵伟凯
 * @CreateTime: 2024-12-16 11:02
 * @Description: 向 DSP  Proxy发起请求服务
 * @Version: 1.0
 */
@Slf4j
@Service
public class AdRequestLogic {

    public List<SspRespAdInfo> rtbAdRequest(SspRequestParam sspRequestParam, AdSlotWrap slotWrap, CooperationMode cooperationMode) {
        List<CompletableFuture<SspRespBidWrap>> futures = new ArrayList<CompletableFuture<SspRespBidWrap>>();
        Integer appId = slotWrap.getAppId();
        Integer sspPartnerId = slotWrap.getSspPartnerId();
        String traceId = TraceContext.getTraceIdByContext();
		/*AtomicInteger requestExceedCount = new AtomicInteger(0);
		AtomicInteger deviceRequestExceedCount = new AtomicInteger(0);*/

        Map<String, AdBaseRequest> requests = new HashMap<>();
        for (AdSlotBudgetWrap slotBudget : slotWrap.getAdSlotBudgets()) {
            Map<String, PriceContext> priceContextMap = new HashMap<>();
            PriceContext priceContext = new PriceContext();

//            AdProxyReqModel adProxyReqModel = AdProxyRequest.requestParser.parse(adRequestBo, priceContext, sourceChannel);
//            AdRequestHttpExecuteService adProxyRequest = new AdRequestHttpExecuteService(sspRequestParam);
//            //TODO  需要改成配置，不同的预算方有不同的超时时间配置
//            adProxyRequest.setTimeout(250000);
//            requests.put(slotBudget.getDspSlotId(), adProxyRequest);
        }

        // 批量请求并获取结果
        AdBatchRequest adBatchRequest = new AdBatchRequest<>(new ArrayList<>(requests.values()));
        Map<AdBaseRequest, AdChannelResModel> resultMap = adBatchRequest.start();

        //遍历返回的结果
        List<SspRespBidWrap> rtaSspRespBidWraps = new ArrayList<SspRespBidWrap>();
        List<SspRespBidWrap> rtbSspRespBidWraps = new ArrayList<SspRespBidWrap>();
        for (Map.Entry<AdBaseRequest, AdChannelResModel> entry : resultMap.entrySet()) {
            AdBaseRequest adBaseRequest = entry.getKey();
            AdChannelResModel adChannelResModel = entry.getValue();
            switch (adChannelResModel.getStatus()) {
                case timeout:
                    log.error("DSP请求超时：{}", adBaseRequest.getRequestEntity().toString());
                    break;
                case noContent:
                    log.error("DSP无广告内容：{}", adBaseRequest.getRequestEntity().toString());
                    break;
                case fail:
                    String memo = adChannelResModel.getMessage();
                    log.error("DSP请求失败：{}: des: {}", memo, adBaseRequest.getRequestEntity().toString());
                    break;
                case success: {
                    log.info(">>>>>>>>>>>>>>: " + adChannelResModel.getRawResult());
                }
            }


        }
        return  null;
    }
}