package com.juan.adx.model.dsp.yueke.request;

import lombok.Data;

import java.util.List;

@Data
public class YueKeApp {
    /**
     * Y
     * 媒体ID，该字段由智友广告交易平台提供
     */
    private String id;

    /**
     * Y
     * 流量应用名称
     */
    private String name;

    /**
     * Y
     * 流量应用包名，例如：“com.xxx.xxxx”
     */
    private String bundle;

    /**
     * Y
     * 流量应用版本，例如: 6.6.2
     */
    private String ver;

    /**
     * N
     * 是否是付费应用，1:是，0:否
     */
    private Integer paid;

    /**
     * N
     * 流量应用分类描述信息，逗号分隔。例如: "军事,汽车,萌宠" 等
     */
    private String keywords;

    /**
     * N
     * 媒体类型
     */
    private List<String> cat;
}
