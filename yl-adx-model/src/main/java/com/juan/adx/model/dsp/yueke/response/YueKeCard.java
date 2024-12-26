package com.juan.adx.model.dsp.yueke.response;
import lombok.Data;

@Data
public class YueKeCard {

    /**
     * <pre>
     * 链接类型
     * 是否必填: 是
     * 取值或描述: 1: 图片链接, 2: 网页链接, 4: HTML代码
     * </pre>
     */
    private Integer type;

    /**
     * <pre>
     * 物料内容链接地址
     * 是否必填: 否
     * 取值或描述: 当链接类型不为 HTML 代码时使用
     * </pre>
     */
    private String url;

    /**
     * <pre>
     * HTML 代码片段
     * 是否必填: 否
     * 取值或描述: 当链接类型为 HTML 代码时使用
     * </pre>
     */
    private String html;

    /**
     * <pre>
     * 编码方式
     * 是否必填: 否
     * 取值或描述: 当链接类型为 HTML 代码时用于在 WebView 中渲染
     * </pre>
     */
    private String charset;

    /**
     * <pre>
     * 图标信息
     * 是否必填: 否
     * </pre>
     */
    private String icon;

    /**
     * <pre>
     * 卡片页面的标题信息
     * 是否必填: 否
     * </pre>
     */
    private String title;

    /**
     * <pre>
     * 卡片页面的详情描述文字信息
     * 是否必填: 否
     * </pre>
     */
    private String content;

    /**
     * <pre>
     * 评论总数
     * 是否必填: 否
     * </pre>
     */
    private Integer comments;

    /**
     * <pre>
     * 应用评分
     * 是否必填: 否
     * </pre>
     */
    private Integer endRating;

    /**
     * <pre>
     * 按钮信息
     * 是否必填: 否
     * </pre>
     */
    private YueKeButton button;
}
