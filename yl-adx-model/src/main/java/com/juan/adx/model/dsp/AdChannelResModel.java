package com.juan.adx.model.dsp;

import lombok.Data;

/**
 * @Author: cao fei
 * @Date: created in 6:27 下午 2022/6/9
 */
@Data
public class AdChannelResModel<T> {
    /**
     * 业务后台广告状态
     */
    private Status status;
    /**
     * 业务后台广告提示消息
     */
    private String message;
    /**
     * 业务后台的请求耗时, 单位毫秒
     */
    private long costTime;
    /**
     * 业务后台返回的原始响应
     */
    private T rawResult;

    private AdChannelResModel() {

    }

    public AdChannelResModel(Status status, String message, T rawResult) {
        this.status = status;
        this.message = message;
        this.rawResult = rawResult;
    }

    public enum Status {
        /**
         * 超时
         */
        timeout,
        /**
         * 无填充
         */
        noContent,
        /**
         * 失败
         */
        fail,
        /**
         * 成功获取到物料
         */
        success
    }

}
