package com.yl.ad.dsp.model.entity;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: ChaoLong Lin
 * @CreateTime: 2024/12/26 15:04
 * @Description: dsp需求管理信息对象
 * @Version: V1.0
 */
@Data
public class DspBudgetPlan {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 需求方名称
     */
    private String dspName;

    /**
     * 是否启用，0：未启用，1：启用
     */
    private Integer status;

    /**
     * 交易类型，1：RTB，2：PD
     */
    private Integer transactionType;

    /**
     * 竞价类型，1：一价结算，2：二价结算
     */
    private Integer bidType;

    /**
     * 溢价比例
     */
    private Integer premiumRate;

    /**
     * EndPoint
     */
    private String endpoint;

    /**
     * QPS
     */
    private Integer qps;

    /**
     * 流量策略Id
     */
    private Long flowStrategyId;

    /**
     * 流量策略
     */
    private String flowStrategy;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Long createDate;

    /**
     * 最后的更新人
     */
    private String updateBy;

    /**
     * 最后的更新时间
     */
    private Long updateDate;

    /**
     * 逻辑删除字段，0：未删除，1：已删除
     */
    private Integer deleted;
}
