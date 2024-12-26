package com.juan.adx.model.dsp.yueke.response;
import lombok.Data;

@Data
public class YueKeImg {

    /**
     * <pre>
     * 图片尺寸: 宽度
     * 是否必填: 否
     * 取值或描述: 图片的宽度
     * </pre>
     */
    private Integer w;

    /**
     * <pre>
     * 图片尺寸: 高度
     * 是否必填: 否
     * 取值或描述: 图片的高度
     * </pre>
     */
    private Integer h;

    /**
     * <pre>
     * 图片访问地址
     * 是否必填: 是
     * </pre>
     */
    private String iurl;

    /**
     * <pre>
     * 图片格式类型
     * 是否必填: 否
     * 取值或描述: 图片的 MIME 类型
     * </pre>
     */
    private String mimes;

    /**
     * <pre>
     * 图片用途类型
     * 是否必填: 是
     * 取值或描述: 1: 图标，2: 品牌，3: 主图
     * </pre>
     */
    private Integer type;
}
