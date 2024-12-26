package com.juan.adx.model.ssp.yoyo.response;

import lombok.Data;

import java.util.List;

/**
 * @Author: Kevin.赵伟凯
 * @CreateTime: 2024-12-18 15:17
 * @Description: Yo Yo 广告请求响应实体根对象
 * @Version: 1.0
 */
@Data
public class YoYoBidResponse {
    /**
     * 对应请求ID
     */
    private String id;

    /**
     *YOYO创意列表
     */
    private List<BidBase> bidList;

    /**
     * 保留字段  JSON String
     */
    private String ext;
}
