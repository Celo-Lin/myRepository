package com.juan.adx.common.utils.http;


import com.juan.adx.model.dsp.AdChannelResModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Kevin.赵伟凯
 * @CreateTime: 2024-12-13 18:02
 * @Description: 像DSP  Proxy发起请求服务
 * @Version: 1.0
 */
public class AdBatchRequest<T extends AdBaseRequest> {

    private final List<T> baseRequests;

    public AdBatchRequest(List<T> baseRequests) {
        this.baseRequests = baseRequests;
    }

    public Map<T, AdChannelResModel> start() {
        if (baseRequests == null || baseRequests.size() == 0) {
            return null;
        }

        return batchRequestCountDown(baseRequests);
    }

    private Map<T, AdChannelResModel> batchRequestCountDown(List<T> baseRequests) {
        CountDownLatch countDownLatch = new CountDownLatch(baseRequests.size());
        int maxTimeout = 0;
        Map<T, AdFutureCallback> callbackMap = new HashMap<>();
        for (T baseRequest : baseRequests) {
            maxTimeout = Math.max(maxTimeout, baseRequest.getTimeout());
            AdFutureCallback callback = baseRequest.startAsync(countDownLatch::countDown);
            callbackMap.put(baseRequest, callback);
        }
        try {
            countDownLatch.await(maxTimeout, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        HashMap<T, AdChannelResModel> result = new HashMap<>();
        for (T baseRequest : callbackMap.keySet()) {
            AdFutureCallback callback = callbackMap.get(baseRequest);
            AdChannelResModel resModel = callback.getResponse();
            if (resModel == null) {
                resModel = new AdChannelResModel<>(AdChannelResModel.Status.timeout, "外部超时:" + maxTimeout, null);
            }
            result.put(baseRequest, resModel);
        }

        return result;
    }

}
