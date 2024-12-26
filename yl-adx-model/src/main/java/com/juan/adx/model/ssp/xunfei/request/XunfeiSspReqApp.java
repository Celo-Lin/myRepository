package com.juan.adx.model.ssp.xunfei.request;

import lombok.Data;

import java.util.List;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/22 9:32
 */
@Data
public class XunfeiSspReqApp {

    /**
     * app 唯一标识，由讯飞 ADX 生成
     */
    private String id;

    /**
     * app 的名称
     */
    private String name;

    /**
     * app 包名
     */
    private String bundle;

    /**
     * app 版本
     */
    private String version;
    /**
     * app 的分类
     */
    private List<String> cat;

    /**
     * app 相关标签，如果有多个标签则用英
     * 文逗号分隔。完整标签表请找讯飞索
     * 取。
     */
    private String tag;
    /**
     * 扩展字段（预留）
     */
    private Object ext;

}
