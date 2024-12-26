package com.juan.adx.common.utils.http;


import com.juan.adx.model.dsp.AdChannelResModel;
import com.juan.adx.model.ssp.common.request.SspRequestParam;

/**
 * @Author: Kevin.赵伟凯
 * @CreateTime: 2024-12-13 18:02
 * @Description: 像DSP  Proxy发起请求服务
 * @Version: 1.0
 */
public interface AdFutureCallback {

    boolean isFinished();

    AdChannelResModel<SspRequestParam> getResponse();

}
