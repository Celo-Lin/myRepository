package com.juan.adx.model.dsp.oppo.response;
import lombok.Data;

import java.util.List;

@Data
public class OppoDspResponseParam {
    /**
     * <pre>
     * code: 响应结果编码，code=0 表示正常，见附录响应状态码
     * </pre>
     */
    private Integer code;

    /**
     * <pre>
     * msg: 响应结果描述信息
     * </pre>
     */
    private String msg;

    /**
     * <pre>
     * respId: 响应 ID，响应唯一标识
     * </pre>
     */
    private String respId;

    /**
     * <pre>
     * adList: 广告数组对象，见 UnionApiAdInfo Object
     * </pre>
     */
    private List<OppoUnionApiAdInfo> adList;

    /**
     * <pre>
     * reqInterval: 两次请求间隔最少时间，单位秒
     * </pre>
     */
    private Integer reqInterval;
}
