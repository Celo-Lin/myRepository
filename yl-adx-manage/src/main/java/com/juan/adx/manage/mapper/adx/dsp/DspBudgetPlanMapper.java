package com.juan.adx.manage.mapper.adx.dsp;

import com.yl.ad.dsp.model.entity.DspBudgetPlan;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: ChaoLong Lin
 * @CreateTime: 2024/12/26 14:35
 * @Description: 需求管理 Mapper
 * @Version: V1.0
 */
@Mapper
public interface DspBudgetPlanMapper {

    String SQL_COLUMN = "	id AS id,"
            + "				dsp_name AS dspName,"
            + "				status AS status,"
            + "				transaction_type AS transactionType,"
            + "				bid_type AS bidType,"
            + "				premium_rate AS premiumRate,"
            + "				endpoint AS endpoint,"
            + "				qps AS qps,"
            + "				flow_strategy_id AS flowStrategyId,"
            + "				flow_strategy AS flowStrategy,"
            + "				create_by AS createBy,"
            + "				create_date AS createDate,"
            + "				update_by AS updateBy,"
            + "				update_date AS updateDate,"
            + "				is_deleted AS deleted";


    /**
     * 新增需求管理数据
     */
    @Insert("INSERT INTO dsp_budget_plan ("
            + "    dsp_name,"
            + "    status,"
            + "    transaction_type,"
            + "    bid_type,"
            + "    premium_rate,"
            + "    endpoint,"
            + "    qps,"
            + "    flow_strategy_id,"
            + "    flow_strategy,"
            + "    create_by,"
            + "    create_date,"
            + "    update_by,"
            + "    update_date,"
            + "    is_deleted"
            + ") VALUES ("
            + "    #{dspBudgetPlan.dspName},"
            + "    #{dspBudgetPlan.status},"
            + "    #{dspBudgetPlan.transactionType},"
            + "    #{dspBudgetPlan.bidType},"
            + "    #{dspBudgetPlan.premiumRate},"
            + "    #{dspBudgetPlan.endpoint},"
            + "    #{dspBudgetPlan.qps},"
            + "    #{dspBudgetPlan.flowStrategyId},"
            + "    #{dspBudgetPlan.flowStrategy},"
            + "    #{dspBudgetPlan.createBy},"
            + "    #{dspBudgetPlan.createDate},"
            + "    #{dspBudgetPlan.updateBy},"
            + "    #{dspBudgetPlan.updateDate},"
            + "    #{dspBudgetPlan.deleted}"
            + ")")
    @Options(useGeneratedKeys = true, keyProperty = "dspBudgetPlan.id", keyColumn = "id")
    int saveDspBudgetPlan(@Param("dspBudgetPlan")DspBudgetPlan dspBudgetPlan);
}
