package com.yl.ad.dsp.model.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Author: ChaoLong Lin
 * @CreateTime: 2024/12/26 15:07
 * @Description: dsp需求管理信息Dto对象
 * @Version: V1.0
 */
@Data
public class DspBudgetPlanDto {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 需求方名称
     */
    @NotBlank(message = "需求方名称不能为空")
    @Size(max = 50, message = "需求方名称长度不能超过50")
    private String dspName;

    /**
     * 是否启用，0：未启用，1：启用
     */
    @NotNull(message = "是否启用不能为空")
    private Integer status;

    /**
     * 交易类型，1：RTB，2：PD
     */
    @NotNull(message = "交易类型不能为空")
    private Integer transactionType;

    /**
     * 竞价类型，1：一价结算，2：二价结算
     */
    @NotNull(message = "竞价类型不能为空")
    private Integer bidType;

    /**
     * 溢价比例
     */
    @NotNull(message = "溢价比例不能为空")
    private Integer premiumRate;

    /**
     * EndPoint
     */
    @NotBlank(message = "EndPoint不能为空")
    private String endpoint;

    /**
     * QPS
     */
    @NotNull(message = "QPS不能为空")
    private Integer qps;

    /**
     * 流量策略Id
     */
    @NotNull(message = "流量策略Id不能为空")
    private Long flowStrategyId;

    /**
     * 流量策略
     */
    @NotBlank(message = "流量策略不能为空")
    private String flowStrategy;
}
