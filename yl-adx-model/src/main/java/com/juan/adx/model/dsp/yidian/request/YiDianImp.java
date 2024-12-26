package com.juan.adx.model.dsp.yidian.request;

import lombok.Data;

import java.util.List;

/**
 * @author caoliwu
 * @version 1.0
 * @ClassName YiDianImp
 * @description: TODO
 * @date 2024/5/28 9:54
 */
@Data
public class YiDianImp {
    /**
     * <pre>
     * 广告位的唯一标识符，由一点提供
     * </pre>
     */
    private String slot_id;

    /**
     * <pre>
     * 媒体底价，单位：分 ,选填
     * </pre>
     */
    private Integer bidfloor;

    /**
     * <pre>
     * 直接交易标识 ID；由交易平台和DSP 提前约定 ,选填
     * </pre>
     */
    private List<String> dealIds;
}
