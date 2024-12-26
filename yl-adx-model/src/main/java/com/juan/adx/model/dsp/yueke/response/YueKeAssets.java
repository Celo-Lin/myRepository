package com.juan.adx.model.dsp.yueke.response;
import lombok.Data;

@Data
public class YueKeAssets {

    /**
     * <pre>
     * 采买编号，资产自增长编号信息，通常从1开始自增
     * 是否必填: 是
     * 取值或描述: 通常从1开始进行自增长
     * </pre>
     */
    private Integer id;

    /**
     * <pre>
     * 标题信息
     * 是否必填: 否
     * </pre>
     */
    private YueKeTitle title;

    /**
     * <pre>
     * 品牌名称、描述等数据
     * 是否必填: 否
     * </pre>
     */
    private Data data;

    /**
     * <pre>
     * 图片信息
     * 是否必填: 否
     * </pre>
     */
    private YueKeImg img;

    /**
     * <pre>
     * 视频信息
     * 是否必填: 否
     * </pre>
     */
    private YueKeVideo video;
}
