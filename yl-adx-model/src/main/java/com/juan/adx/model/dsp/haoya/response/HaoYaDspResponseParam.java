package com.juan.adx.model.dsp.haoya.response;
import lombok.Data;
import java.util.List;

@Data
public class HaoYaDspResponseParam {
    /**
     * <pre>
     * respId: 广告响应 ID，与 AdRequest.reqId 一致
     *      必填
     * </pre>
     */
    private String respId;

    /**
     * <pre>
     * ads: 广告素材信息，当有广告返回时，至少包含一个Ad对象
     * </pre>
     */
    private List<HaoYaAd> ads;

    /**
     * <pre>
     * code: 响应结果码，详见：附录 4.2.2-code码说明
     *      必填
     * </pre>
     */
    private Integer code;
}
