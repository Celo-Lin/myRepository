package com.juan.adx.common.utils.http;

import com.juan.adx.model.dsp.AdChannelResModel;
import com.juan.adx.model.ssp.common.request.SspRequestParam;

/**
 * @Author: Kevin.赵伟凯
 * @CreateTime: 2024-12-13 15:02
 * @Description: 像DSP  Proxy发起请求服务
 * @Version: 1.0
 */
public abstract class AdBaseRequest<RequestEntity> {

    private final RequestEntity requestEntity;
    private int timeout;
    private static final int defaultTimeout = 250;

    private AdBaseRequest() {
        this.requestEntity = null;
    }

    public AdBaseRequest(RequestEntity requestEntity) {
        this.requestEntity = requestEntity;
    }

    public RequestEntity getRequestEntity() {
        return requestEntity;
    }

    public abstract AdChannelResModel start();

    public abstract AdFutureCallback startAsync(Callable callable);

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getTimeout() {
        return timeout <= 0 ? defaultTimeout : timeout;
    }
}
