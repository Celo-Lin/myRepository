package com.juan.adx.model.ssp.xunfei.request;

import lombok.Data;

import java.util.List;
import java.util.Objects;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/22 9:00
 */
@Data
public class XunfeiSspReqElement {

    /**
     * 指明响应的时候该元素是否必填。
     * （必填元素响应时不能为空）
     */
    private Boolean required;
    /**
     * 元素名称
     */
    private String name;
    /**
     * 文字的最小长度（文本元素必填）
     */
    private Integer min_text_num;
    /**
     * 文字的最大长度（文本元素必填）
     */
    private Integer max_text_num;

    /**
     * 音频/视频最小时长（单位：秒，音
     * 频/视频元素必填）
     */
    private Integer min_duration;
    /**
     * 音频/视频最大时长（单位：秒，音
     * 频/视频元素必填）
     */
    private Integer max_duration;
    /**
     * 宽度（图片/视频元素必填）
     */
    private Integer width;
    /**
     * 高度（图片/视频元素必填）
     */
    private Integer height;

    /**
     * 文件大小，单位 KB（非文本元素必
     * 填）
     */
    private Integer file_size;
    /**
     * 素材文件支持的扩展名(非文本元素
     * 必填),
     */
    private List<String>  ext_type;
    /**
     * 扩展字段（预留）
     */
    private Object ext;
}
