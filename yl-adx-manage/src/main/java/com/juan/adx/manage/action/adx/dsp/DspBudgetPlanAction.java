package com.juan.adx.manage.action.adx.dsp;

import com.juan.adx.common.model.ManageResponse;
import com.juan.adx.manage.service.adx.dsp.DspBudgetPlanService;
import com.yl.ad.dsp.model.dto.DspBudgetPlanDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @Author: ChaoLong Lin
 * @CreateTime: 2024/12/26 14:28
 * @Description: 需求管理接口
 * @Version: V1.0
 */
@Slf4j
@RestController
@RequestMapping("/adx/dsp/plan")
public class DspBudgetPlanAction {
    //新增了一个01
    //新增了一个02
    @Resource
    private DspBudgetPlanService dspBudgetPlanService;

    /**
     * 新增需求数据
     */
    @RequestMapping("/add")
    public ManageResponse add(@Validated @RequestBody DspBudgetPlanDto dspBudgetPlanDto) {
        this.dspBudgetPlanService.addDspBudgetPlan(dspBudgetPlanDto);
        return new ManageResponse();
    }
}
