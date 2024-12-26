package com.juan.adx.model.dsp.haoya.request;
import java.util.List;

import lombok.Data;

@Data
public class HaoYaApp {
    /**
     * <pre>
     * appId: 媒体方 APP 的 ID
     * </pre>
     */
    private String appId;

    /**
     * <pre>
     * name: 媒体方 APP 的名称
     * </pre>
     */
    private String name;

    /**
     * <pre>
     * packageName: 应用包名
     * </pre>
     */
    private String packageName;

    /**
     * <pre>
     * version: 应用版本
     * </pre>
     */
    private String version;

    /**
     * <pre>
     * mkt: 应用商店
     * </pre>
     */
    private String mkt;

    /**
     * <pre>
     * mktSn: APP 在商店的编号
     * </pre>
     */
    private String mktSn;

    /**
     * <pre>
     * mktCat: APP 在商店的分类
     * </pre>
     */
    private String mktCat;

    /**
     * <pre>
     * mktTag: APP 在商店的标签
     * </pre>
     */
    private String mktTag;

    /**
     * <pre>
     * category: 所属行业
     * </pre>
     */
    private List<String> category;

    /**
     * <pre>
     * keywords: 关键词，例如"游戏"
     * </pre>
     */
    private String keywords;
}
