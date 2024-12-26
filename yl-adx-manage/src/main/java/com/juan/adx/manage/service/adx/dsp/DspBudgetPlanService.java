package com.juan.adx.manage.service.adx.dsp;

import com.juan.adx.common.constants.Constants;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.common.utils.UserDetailsUtils;
import com.juan.adx.manage.dao.adx.dsp.DspBudgetPlanDao;
import com.yl.ad.dsp.model.dto.DspBudgetPlanDto;
import com.yl.ad.dsp.model.entity.DspBudgetPlan;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author: ChaoLong Lin
 * @CreateTime: 2024/12/26 14:30
 * @Description: 需求管理接口
 * @Version: V1.0
 */
@Service
public class DspBudgetPlanService {
    @Resource
    private DspBudgetPlanDao dspBudgetPlanDao;

    /**
     * 新增需求数据
     */
    @Transactional(value = "adxTransactionManager")
    public boolean addDspBudgetPlan (DspBudgetPlanDto dspBudgetPlanDto) {
        long nowSeconds = LocalDateUtils.getNowSeconds();
        DspBudgetPlan dspBudgetPlan = new DspBudgetPlan();
        //复制属性
        BeanUtils.copyProperties(dspBudgetPlanDto, dspBudgetPlan);
        dspBudgetPlan.setCreateDate(nowSeconds);
        dspBudgetPlan.setUpdateDate(nowSeconds);
        // 获取当前登录用户
        String currentUsername = UserDetailsUtils.getCurrentUsername();
        dspBudgetPlan.setCreateBy(currentUsername);
        dspBudgetPlan.setUpdateBy(currentUsername);
        dspBudgetPlan.setDeleted(Constants.Delete.UnDeleted);
        return this.addDspBudgetPlan(dspBudgetPlan) > 0;
    }

    public int addDspBudgetPlan (DspBudgetPlan dspBudgetPlan) {
        return dspBudgetPlanDao.saveDspBudgetPlan(dspBudgetPlan);
    }
}
