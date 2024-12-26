package com.juan.adx.model.dsp.yueke.request;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class YueKeBanner {

    /**
     * <pre>
     * 广告位图片所要求的宽度
     * 是否必填: 是
     * 取值或描述: 单位为像素px
     * </pre>
     */
    private Integer w;

    /**
     * <pre>
     * 广告位图片所要求的高度
     * 是否必填: 是
     * 取值或描述: 单位为像素px
     * </pre>
     */
    private Integer h;

    /**
     * <pre>
     * 流量售卖位在设备屏幕上显示的位置编号
     * 是否必填: 否
     * 取值或描述: 默认为 0
     * </pre>
     */
    private Integer pos = 0;

    /**
     * <pre>
     * 图片类型编号
     * 是否必填: 否
     * 取值或描述: 主图默认为 "3"
     * </pre>
     */
    private Integer type;

    /**
     * <pre>
     * 图片的 MIME 类型列表
     * 是否必填: 否
     * 取值或描述: 默认填写 image/gif，image/jpeg，image/jpg，image/png
     * </pre>
     */
    private List<String> mimes = Arrays.asList("image/gif", "image/jpeg", "image/jpg", "image/png");
}
