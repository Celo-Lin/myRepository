package com.juan.adx.task.job;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import com.juan.adx.task.schedule.AbstractJob;
import com.juan.adx.task.service.AdvertSlotService;
import com.juan.adx.task.service.BidRecordService;
import com.juan.adx.task.service.BudgetService;
import com.juan.adx.task.service.DataStatisticsSlotDailyRealtimeService;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.model.entity.manage.DataStatisticsSlotRealtime;
import com.juan.adx.model.entity.manage.DspBudget;
import com.juan.adx.model.entity.manage.SlotBudget;
import com.juan.adx.model.entity.manage.SspAdvertSlot;
import com.juan.adx.model.entity.task.StatisticsSlotIncomeDto;
import com.juan.adx.model.enums.CooperationModeSimple;

import lombok.extern.slf4j.Slf4j;

/**
 * 统计广告位时段收益数据
 * 执行频率：按小时（每小时过5分，执行一次）
 */
@Slf4j
@Component("statisticsIncomeRealtimeJob")
public class StatisticsIncomeRealtimeJob extends AbstractJob  {

	@Resource
	private AdvertSlotService advertSlotService;
	
	@Resource
	private BudgetService budgetService;
	
	@Resource
	private BidRecordService bidRecordService;
	
	@Resource
	private DataStatisticsSlotDailyRealtimeService slotDailyRealtimeService;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		LocalDateTime hoursLocalDateTime = LocalDateUtils.minusHoursGetLocalDateTime(1);
		long startTime = LocalDateUtils.getStartSecondsOfHours(hoursLocalDateTime);
		long endTime = LocalDateUtils.getEndSecondsOfHours(hoursLocalDateTime);
		this.statisticsIncomeRealtime(startTime, endTime);
	}
	
	
	private void statisticsIncomeRealtime(long startTime, long endTime) {
		List<StatisticsSlotIncomeDto> slotIncomeDtos = this.bidRecordService.statisticsIncomeRealtime(startTime, endTime);
		if(CollectionUtils.isEmpty(slotIncomeDtos)) {
			return;
		}
		for (StatisticsSlotIncomeDto slotIncomeDto : slotIncomeDtos) {
			DataStatisticsSlotRealtime dailyRealtime = new DataStatisticsSlotRealtime();
			dailyRealtime.setDspEstimateIncome(slotIncomeDto.getDspEstimateIncome());
			dailyRealtime.setSspEstimateIncome(slotIncomeDto.getSspEstimateIncome());
			dailyRealtime.setAdSlotId(slotIncomeDto.getSlotId());
			dailyRealtime.setBudgetId(slotIncomeDto.getBudgetId());
			dailyRealtime.setCooperationMode(CooperationModeSimple.RTB.getMode());
			dailyRealtime.setDate(startTime);
			boolean ret = this.slotDailyRealtimeService.updateSlotRealtimeIncome(dailyRealtime);
			if(!ret) {
				SspAdvertSlot slot = this.advertSlotService.getSlotById(slotIncomeDto.getSlotId());
				DspBudget budget = this.budgetService.getBudgetById(slotIncomeDto.getBudgetId());
				if(slot == null || budget == null) {
					log.warn("广告位或预算信息不存在, slotId:{} | budgetId:{} ", slotIncomeDto.getSlotId(), slotIncomeDto.getBudgetId());
					continue;
				}
				SlotBudget slotBudget = this.advertSlotService.getAdSlotBudgetBySlotIdWithBudgetId(slot.getId(), budget.getId());
				Integer maxRequests = slotBudget != null ? slotBudget.getMaxRequests() : 0;
				dailyRealtime.setSspPartnerId(slot.getSspPartnerId());
				dailyRealtime.setDspPartnerId(budget.getDspPartnerId());
				dailyRealtime.setAppId(slot.getAppId());
				dailyRealtime.setAdSlotType(slot.getType());
				dailyRealtime.setMaxRequests(maxRequests);
				dailyRealtime.setRequestCount(0);
				dailyRealtime.setFillCount(0);
				dailyRealtime.setDisplayCount(0);
				dailyRealtime.setClickCount(0);
				dailyRealtime.setDownloadCount(0);
				dailyRealtime.setInstallCount(0);
				dailyRealtime.setDeeplinkCount(0);
				this.slotDailyRealtimeService.saveSlotRealtimeIncome(dailyRealtime);
			}
		}
	}
	
}
