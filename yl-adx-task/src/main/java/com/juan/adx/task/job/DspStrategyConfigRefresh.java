package com.juan.adx.task.job;

import com.alibaba.fastjson2.JSON;
import com.juan.adx.common.cache.RedisKeyExpireTime;
import com.juan.adx.common.cache.RedisKeyUtil;
import com.juan.adx.common.cache.RedisTemplate;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.model.dto.manage.DspStrategyDto;
import com.juan.adx.model.entity.api.AdSlotBudgetSimple;
import com.juan.adx.model.entity.api.AdSlotBudgetWrap;
import com.juan.adx.model.entity.api.AdSlotWrap;
import com.juan.adx.model.entity.manage.*;
import com.juan.adx.model.enums.Status;
import com.juan.adx.task.mapper.adx.DspStrategyMapper;
import com.juan.adx.task.mapper.adx.SlotMapper;
import com.juan.adx.task.schedule.AbstractJob;
import com.juan.adx.task.service.AdvertSlotService;
import com.juan.adx.task.service.AppService;
import com.juan.adx.task.service.BudgetService;
import com.juan.adx.task.service.DspPartnerConfigService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * DSP流量转发策略配置刷新到Redis
 */
@Slf4j
@Component("dspStrategyConfigRefresh")
public class DspStrategyConfigRefresh extends AbstractJob {


    @Resource
    private DspStrategyMapper dspStrategyMapper;

    @Resource
    private RedisTemplate redisTemplate;


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.info("********* 刷新DSP流量转发策略配置  start *********");
        this.refreshStrategyData();
        stopWatch.stop();
        log.info("********* 刷新DSP流量转发策略配置 done ,cost: {} millis *********", stopWatch.getTotalTimeMillis());
    }

    private void refreshStrategyData() {
        List<DspStrategyDto> stgList = dspStrategyMapper.selectAllUsefulStrategyList();
        if (stgList == null || stgList.isEmpty()) {
            log.warn("刷新DSP流量转发策略配置 stop! 配置数据是空的 ");
            return;
        }

        Map<Integer, List<DspStrategyDto>> stg = stgList.stream().collect(Collectors.groupingBy(DspStrategyDto::getDspPartnerFid));
        for (Map.Entry<Integer, List<DspStrategyDto>> entry : stg.entrySet()) {
            String key = RedisKeyUtil.getDspStrategyConfigKey(entry.getKey());
            String ret = this.redisTemplate.STRINGS.setEx(key, RedisKeyExpireTime.DAY_1, JSON.toJSONString(entry.getValue()));
            log.info("********* DspStrategyConfigRefresh, 刷新数据：dspId: {}, data.size: {}", key, entry.getValue().size());
        }
    }
}
