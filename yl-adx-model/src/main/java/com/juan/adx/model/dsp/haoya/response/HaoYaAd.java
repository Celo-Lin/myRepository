package com.juan.adx.model.dsp.haoya.response;
import lombok.Data;
import java.util.List;

@Data
public class HaoYaAd {
    /**
     * <pre>
     * id: 广告 ID，由 2345 广告平台生成
     * </pre>
     */
    private String id;

    /**
     * <pre>
     * impId: 对应广告请求中的 impId 字段
     *      必填
     * </pre>
     */
    private String impId;

    /**
     * <pre>
     * slotId: 广告位 ID
     *      必填
     * </pre>
     */
    private String slotId;

    /**
     * <pre>
     * creativeId: 创意 ID
     * </pre>
     */
    private String creativeId;

    /**
     * <pre>
     * templateId: 广告样式 ID，与请求Template.id一致
     * </pre>
     */
    private Integer templateId;

    /**
     * <pre>
     * price: 出价，单位：分/CPM
     *      必填
     * </pre>
     */
    private Integer price;

    /**
     * <pre>
     * material: 广告创意信息，详见Material对象
     * </pre>
     */
    private HaoYaMaterial material;

    /**
     * <pre>
     * landingPage: 落地页 URL
     *      必填
     * </pre>
     */
    private String landingPage;

    /**
     * <pre>
     * nurl: 竞胜通知，当素材竞价成功时，媒体侧需要上报此监测链接
     *      必填
     * </pre>
     */
    private List<String> nurl;

    /**
     * <pre>
     * lurl: 竞败通知，当素材竞价失败时，媒体侧需要上报此监测链接
     * </pre>
     */
    private List<String> lurl;

    /**
     * <pre>
     * tracking: 广告监测地址，详见Tracking对象
     *      必填
     * </pre>
     */
    private HaoYaTracking tracking;

    /**
     * <pre>
     * action: 广告点击后续信息，详见Action对象
     * </pre>
     */
    private HaoYaAction action;

    /**
     * <pre>
     * sourceLogo: 广告来源标示图，详见Image对象
     * </pre>
     */
    private HaoYaImage sourceLogo;

    /**
     * <pre>
     * dealId: PDB、PD 交易订单号
     * </pre>
     */
    private String dealId;

    /**
     * <pre>
     * seat: 广告主 ID
     * </pre>
     */
    private String seat;
}
