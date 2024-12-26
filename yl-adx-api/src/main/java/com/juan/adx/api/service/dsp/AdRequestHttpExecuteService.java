//package com.juan.adx.api.service.dsp;
//
//import com.juan.adx.common.utils.http.*;
//import com.juan.adx.model.dsp.AdChannelResModel;
//import com.juan.adx.model.ssp.common.protobuf.Constant;
//import com.juan.adx.model.ssp.common.protobuf.YlAdxDataReqConvert;
//import com.juan.adx.model.ssp.common.request.SspRequestParam;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.concurrent.FutureCallback;
//import org.apache.http.conn.ConnectTimeoutException;
//import org.apache.http.entity.ByteArrayEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
//
//import java.net.SocketTimeoutException;
//import java.util.concurrent.TimeoutException;
//
///**
// * @Author: Kevin.赵伟凯
// * @CreateTime: 2024-12-13 15:02
// * @Description: 向 DSP  Proxy发起请求服务
// * @Version: 1.0
// */
//@Slf4j
//public class AdRequestHttpExecuteService extends AdBaseRequest<SspRequestParam> {
//
//
//    private static final int MAX_CONN = 1000; // 最大连接数
//    private static final int MAX_PRE_ROUTE = 1000;
//    private static final int MAX_ROUTE = 1000;
//    private final static Object syncLock = new Object(); // 相当于线程锁,用于线程安全
//    private static final int IO_THREAD_COUNT = 2;
//    private final static Object nSyncLock = new Object(); // 相当于线程锁,用于线程安全
//    private static volatile HttpPoolClient httpClient;
//    private static volatile NHttpPoolClient nHttpClient;
//    public AdRequestHttpExecuteService(SspRequestParam adProxyReqModel) {
//        super(adProxyReqModel);
//    }
//
//    private static HttpPoolClient getHttpClient(String url) {
//        if (httpClient == null) {
//            //多线程下多个线程同时调用getHttpClient容易导致重复创建httpClient对象的问题,所以加上了同步锁
//            synchronized (syncLock) {
//                if (httpClient == null) {
//                    httpClient = new HttpPoolClient("AD-PROXY", url, MAX_CONN, MAX_PRE_ROUTE, MAX_ROUTE);
//                }
//            }
//        }
//        return httpClient;
//    }
//
//    private static NHttpPoolClient getNHttpClient(String url) {
//        if (nHttpClient == null) {
//            //多线程下多个线程同时调用getHttpClient容易导致重复创建httpClient对象的问题,所以加上了同步锁
//            synchronized (nSyncLock) {
//                if (nHttpClient == null) {
//                    nHttpClient = new NHttpPoolClient("AD-PROXY", url, MAX_CONN, MAX_PRE_ROUTE, MAX_ROUTE, IO_THREAD_COUNT);
//                }
//            }
//        }
//        return nHttpClient;
//    }
//
//    @Override
//    public AdFutureCallback startAsync(Callable callable) {
//        long startTime = System.nanoTime();
//        AdFutureCallBackEntity callBackEntity = new AdFutureCallBackEntity();
//
//        String remoteUrl = AdProxyConfiguration.getStaticRemoteUrl();
//        CloseableHttpAsyncClient client = getNHttpClient(remoteUrl).getClient();
//        HttpPost post = buildHttpPost(remoteUrl, getRequestEntity());
//
//        client.execute(post, new FutureCallback<HttpResponse>() {
//            @Override
//            public void completed(HttpResponse result) {
//                AdChannelResModel<YlAdxDataRespProtobuf.YlAdxDataResp> responseModel = dealResponse(result);
//
//                long costTime = (System.nanoTime() - startTime) / 1000000;
//                responseModel.setCostTime(costTime);
//                callBackEntity.setFinished(true);
//                callBackEntity.setResponse(responseModel);
//
//                if (callable != null) {
//                    callable.call();
//                }
//
//                // 关闭流
//                try {
//                    result.getEntity().getContent().close();
//                } catch (Exception ignore) {
//                }
//            }
//
//            @Override
//            public void failed(Exception ex) {
//                AdChannelResModel<YlAdxDataRespProtobuf.YlAdxDataResp> responseModel = dealExceptionResponse(ex);
//
//                long costTime = (System.nanoTime() - startTime) / 1000000;
//                responseModel.setCostTime(costTime);
//                callBackEntity.setFinished(true);
//                callBackEntity.setResponse(responseModel);
//
//                if (callable != null) {
//                    callable.call();
//                }
//            }
//
//            @Override
//            public void cancelled() {
//                AdChannelResModel<YlAdxDataProtobuf.AdReq> responseModel = new AdChannelResModel<>(AdChannelResModel.Status.fail, "失败 (请求被取消)", null);
//
//                long costTime = (System.nanoTime() - startTime) / 1000000;
//                responseModel.setCostTime(costTime);
//                callBackEntity.setFinished(true);
//                callBackEntity.setResponse(responseModel);
//
//                if (callable != null) {
//                    callable.call();
//                }
//            }
//        });
//
//        return callBackEntity;
//    }
//
//    @Override
//    public AdChannelResModel start() {
//        long startTime = System.nanoTime();
//
//        String remoteUrl = AdProxyConfiguration.getStaticRemoteUrl();
//        CloseableHttpClient client = getHttpClient(remoteUrl).getClient();
//        HttpPost post = buildHttpPost(remoteUrl, getRequestEntity());
//
//        CloseableHttpResponse response = null;
//        AdChannelResModel<YlAdxDataRespProtobuf.YlAdxDataResp> responseModel = null;
//        try {
//            response = client.execute(post);
//            responseModel = dealResponse(response);
//        } catch (Exception ex) {
//            responseModel = dealExceptionResponse(ex);
//        } finally {
//            if (responseModel == null) {
//                responseModel = new AdChannelResModel<>(AdChannelResModel.Status.fail, "失败 (异常兜底)", null);
//            }
//
//            try {
//                if (response != null) {
//                    response.getEntity().getContent().close();
//                }
//            } catch (Exception e) {
//                log.error("请求DSP异常， {}", e.getMessage());
//            }
//        }
//
//        long costTime = (System.nanoTime() - startTime) / 1000000;
//        responseModel.setCostTime(costTime);
//        return responseModel;
//    }
//
//    private HttpPost buildHttpPost(String url, SspRequestParam reqModel) {
//        HttpPost post = new HttpPost(url);
//        RequestConfig requestConfig = RequestConfig.custom()
//                .setConnectionRequestTimeout(50)
//                .setConnectTimeout(150)
//                .setSocketTimeout(getTimeout())
//                .build();
//        post.setConfig(requestConfig);
//        post.setHeader("Content-Type", "application/x-protobuf");
//        YlAdxDataReqConvert reqConvert = new YlAdxDataReqConvert();
//        YlAdxDataProtobuf.AdReq adReq = reqConvert.convert(reqModel);
//        ByteArrayEntity byteArrayEntity = new ByteArrayEntity(adReq.toByteArray());
//        post.setEntity(byteArrayEntity);
//        //        StringEntity postData = new StringEntity(JsonUtil.toJsonString(reqModel), "utf-8");
////        post.setEntity(postData);
//        return post;
//    }
//
//    public AdChannelResModel<YlAdxDataRespProtobuf.YlAdxDataResp> dealResponse(HttpResponse httpResponse) {
//        int statusCode = httpResponse.getStatusLine().getStatusCode();
//        HttpEntity entity = httpResponse.getEntity();
//
//        AdChannelResModel<YlAdxDataRespProtobuf.YlAdxDataResp> responseModel = null;
//        try {
//            if (statusCode == 200) {
//                YlAdxDataRespProtobuf.YlAdxDataResp protobufResponse = YlAdxDataRespProtobuf.YlAdxDataResp.parseFrom(entity.getContent());
//                if (protobufResponse == null) {
//                    responseModel = new AdChannelResModel<>(AdChannelResModel.Status.fail, "广告响应不存在", null);
//                } else if (protobufResponse.getCode() == Constant.ResultCode.noAd) {
//                    responseModel = new AdChannelResModel<>(AdChannelResModel.Status.noContent, "无填充", null);
//                } else if (protobufResponse.getCode() != Constant.ResultCode.success) {
//                    responseModel = new AdChannelResModel<>(AdChannelResModel.Status.fail, String.format("失败 (code:%d, msg:%s)", protobufResponse.getCode(), protobufResponse.getMsg()), null);
//                } else if (!protobufResponse.hasYlAdxData()) {
//                    responseModel = new AdChannelResModel<>(AdChannelResModel.Status.fail, "失败 (广告物料不存在)", null);
//                } else {
//                    responseModel = new AdChannelResModel<>(AdChannelResModel.Status.success, null, protobufResponse);
//
//                    // 广告主出价校验  TODO
////                    YlAdxDataProtobuf.YlAdxData  adData = protobufResponse.getYlAdxData();
////                    if (Constant.DockingWay.RTB.equals(adData.getDockingWay())) {
////                        AdProxyReqModel.BidInfo bidInfo = getRequestEntity().getBidInfo();
////                        if (bidInfo != null && bidInfo.getBidFloor() != null && bidInfo.getBidFloor().compareTo(data.getBidPrice()) > 0) {
////                            responseModel = new AdChannelResModel<>(AdChannelResModel.Status.fail, String.format("失败 (广告主出价异常, bidFloor: %d, bidPrice: %d)", bidInfo.getBidFloor(), data.getBidPrice()), null);
////                        }
////                    }
//                }
//            } else {
//                responseModel = new AdChannelResModel<>(AdChannelResModel.Status.fail, String.format("失败 (httpStatus:%s)", statusCode), null);
//            }
//        } catch (Exception e) {
//            responseModel = dealExceptionResponse(e);
//        } finally {
//            if (responseModel == null) {
//                responseModel = new AdChannelResModel<>(AdChannelResModel.Status.fail, "失败 (异常兜底)", null);
//            }
//        }
//        return responseModel;
//    }
//
//    private AdChannelResModel<YlAdxDataRespProtobuf.YlAdxDataResp> dealExceptionResponse(Exception ex) {
//        AdChannelResModel<YlAdxDataRespProtobuf.YlAdxDataResp> responseModel = null;
//        if (ex instanceof ConnectTimeoutException
//                || ex instanceof SocketTimeoutException
//                || ex instanceof TimeoutException) {
//            responseModel = new AdChannelResModel<>(AdChannelResModel.Status.timeout, ex.getMessage(), null);
//        } else {
//            responseModel = new AdChannelResModel<>(AdChannelResModel.Status.fail, ex.getMessage(), null);
//        }
//        return responseModel;
//    }
//
//}
