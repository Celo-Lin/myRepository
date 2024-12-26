package com.juan.adx.model.ssp.xunfei.request;

import lombok.Data;

import java.util.List;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/22 9:35
 */
@Data
public class XunfeiSspReqUser {

    /**
     * 用户标签
     */
    private List<String> tags;

    private Object ext;
}
