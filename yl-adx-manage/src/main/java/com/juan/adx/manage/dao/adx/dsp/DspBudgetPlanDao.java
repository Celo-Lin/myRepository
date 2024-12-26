package com.juan.adx.manage.dao.adx.dsp;

import com.juan.adx.manage.mapper.adx.dsp.DspBudgetPlanMapper;
import com.yl.ad.dsp.model.dto.DspBudgetPlanDto;
import com.yl.ad.dsp.model.entity.DspBudgetPlan;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @Author: ChaoLong Lin
 * @CreateTime: 2024/12/26 14:33
 * @Description: 需求管理 Dao
 * @Version: V1.0
 */
@Repository
public class DspBudgetPlanDao {
    @Resource
    private DspBudgetPlanMapper dspBudgetPlanMapper;

    /**
     * 新增需求数据
     */
    public int saveDspBudgetPlan(DspBudgetPlan dspBudgetPlan) {
        return this.dspBudgetPlanMapper.saveDspBudgetPlan(dspBudgetPlan);
    }
}
