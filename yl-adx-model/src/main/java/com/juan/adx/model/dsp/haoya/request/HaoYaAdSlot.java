package com.juan.adx.model.dsp.haoya.request;
import lombok.Data;
import java.util.List;

@Data
public class HaoYaAdSlot {
    /**
     * <pre>
     * id: 2345ADX 广告平台的广告位 ID，与 2345ADX 商务沟通
     * </pre>
     */
    private String id;

    /**
     * <pre>
     * impId: 媒体侧自定义的提供的广告曝光唯一 ID
     * </pre>
     */
    private String impId;

    /**
     * <pre>
     * type: 广告位类型，详见：附录 4.1.1-广告类型
     * </pre>
     */
    private Integer type;

    /**
     * <pre>
     * template: 支持的广告样式，详见Template对象
     * </pre>
     */
    private List<HaoYaTemplate> template;

    /**
     * <pre>
     * floorPrice: 广告位底价，单位：分/cpm
     * </pre>
     */
    private Integer floorPrice;

    /**
     * <pre>
     * billType: 交易类型，0-cpm
     * </pre>
     */
    private Integer billType;

    /**
     * <pre>
     * bidType: 采买类型，0-RTB 1-PD，默认为 RTB
     * </pre>
     */
    private Integer bidType;

    /**
     * <pre>
     * width: 广告位宽，像素
     * </pre>
     */
    private Integer width;

    /**
     * <pre>
     * height: 广告位高，像素
     * </pre>
     */
    private Integer height;

    /**
     * <pre>
     * dealId: PDB、PD 交易订单号
     * </pre>
     */
    private String dealId;

    /**
     * <pre>
     * actionType: 点击广告后的交互类型，0：全部，1：H5，2：下载，3：唤醒，4：微信小程序
     * </pre>
     */
    private List<Integer> actionType;
}
