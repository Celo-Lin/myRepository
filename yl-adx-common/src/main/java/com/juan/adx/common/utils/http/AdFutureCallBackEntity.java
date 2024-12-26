package com.juan.adx.common.utils.http;


import com.juan.adx.model.dsp.AdChannelResModel;
import com.juan.adx.model.ssp.common.request.SspRequestParam;

/**
 * @Author: Kevin.赵伟凯
 * @CreateTime: 2024-12-13 18:02
 * @Description: 像DSP  Proxy发起请求服务
 * @Version: 1.0
 */
public class AdFutureCallBackEntity implements AdFutureCallback {

    private boolean finished;
    private AdChannelResModel<SspRequestParam> response;

    @Override
    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @Override
    public AdChannelResModel getResponse() {
        return response;
    }

    public void setResponse(AdChannelResModel response) {
        this.response = response;
    }

}
