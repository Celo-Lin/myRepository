package com.juan.adx.model.ssp.xunfei.request;

import lombok.Data;

import java.util.List;
import java.util.Objects;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/22 8:59
 */
@Data
public class XunfeiSspReqCreativeTemplate {

    /**
     * 广告位支持的创意 id
     */
    private Integer id;
    /**
     * 广告位模板 id（线下提供）
     */
    private Integer template_id;
    /**
     * 广告位创意中包含的元素内容
     */
    private List<XunfeiSspReqElement> elements;
    /**
     * 支持的交互类型
     */
    private List<Integer> interbehavior;
    /**
     * 扩展字段（预留）
     */
    private Object ext;
}
