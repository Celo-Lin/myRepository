package com.juan.adx.model.ssp.yoyo.request;

import lombok.Data;

import java.util.List;

/**
* @Author: ChaoLong Lin
* @CreateTime: 2024/12/19 9:53
* @Description: YoYo 请求曝光信息对象
* @version: V1.0
*/
@Data
public class ImpBase {
    /**
     * 广告曝光imp唯一ID
     */
    private String id;

    /**
     * 广告位ID标识，广告位见运营侧提供的广告位资源文档，每个曝光位只有一个tagid
     */
    private String tagId;

    /**
     *广告位资源文档广告位支持的模板 , 每个tagId下的模板都是确定的 , 单个tagid支持多个模板
     * 模版表详见：https://docs.qq.com/sheet/DRXZMRUZVb3RjdW5F?tab=000003
     * 广告位与模板关系见运营侧提供的资源文档
     */
    private List<TemplateBase> templateList;

    /**
     * 支持的出价方式类型和底价
     */
    private List<BidInfoBase> bidInfoList;

    /**
     * 广告条数 , 默认是1 , 一个tagid可能要多条广告
     */
    private Integer adsCount;

    /**
     * 搜索关键词 , 部分搜索广告位的请求会传这个值
     */
    private String searchKeyword;

    /**
     * 限制召回的应用包名集合 , (如果该集合不为空, 需要严格按照此包名集合进行召回)
     */
    private List<String> pkgWhitelist;

    /**
     * 流量包ID，部分业务会传，使用流量包ID时需要在返回体中回传该值
     */
    private List<Integer> flowPkgIds;
}
