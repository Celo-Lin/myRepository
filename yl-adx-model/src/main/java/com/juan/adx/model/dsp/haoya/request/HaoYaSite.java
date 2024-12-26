package com.juan.adx.model.dsp.haoya.request;
import lombok.Data;

@Data
public class HaoYaSite {
    /**
     * <pre>
     * siteId: 媒体侧提供的 website ID
     * </pre>
     */
    private String siteId;

    /**
     * <pre>
     * name: 网站名称
     * </pre>
     */
    private String name;

    /**
     * <pre>
     * domain: 网站域名
     * </pre>
     */
    private String domain;

    /**
     * <pre>
     * url: 网址
     * </pre>
     */
    private String url;

    /**
     * <pre>
     * referer: 网站 referer
     * </pre>
     */
    private String referer;

    /**
     * <pre>
     * keywords: 关键词，例如"页游"
     * </pre>
     */
    private String keywords;
}
