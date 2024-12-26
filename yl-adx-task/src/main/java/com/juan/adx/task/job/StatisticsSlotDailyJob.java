package com.juan.adx.task.job;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson2.JSON;
import com.juan.adx.task.schedule.AbstractJob;
import com.juan.adx.task.service.BidRecordService;
import com.juan.adx.task.service.DataStatisticsSlotDailyRealtimeService;
import com.juan.adx.task.service.DataStatisticsSlotDailyService;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.model.entity.manage.DataStatisticsSlotDaily;
import com.juan.adx.model.entity.task.StatisticsSlotIncomeDto;

import lombok.extern.slf4j.Slf4j;

/**
 * 内部后台-统计广告位上报事件数据、收益数据
 * 执行频率：按天（每天凌晨执行一次，T+1）
 */
@Slf4j
@Component("statisticsSlotDailyJob")
public class StatisticsSlotDailyJob extends AbstractJob  {

	@Resource
	private DataStatisticsSlotDailyRealtimeService slotDailyRealtimeService;

	@Resource
	private DataStatisticsSlotDailyService slotDailyService;
	
	@Resource
	private BidRecordService bidRecordService;

	
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		LocalDate yesterDay = LocalDateUtils.minusDays(1);
		long startTime = LocalDateUtils.getStartSecondsByLocalDate(yesterDay);
		long endTime =LocalDateUtils.getEndSecondsByLocalDate(yesterDay);
		//统计内部后台上报事件数据
		this.statisticsSlotDailyData(startTime, endTime);
		//统计内部后台广告位收益数据
		this.statisticsIncomeDaily(startTime, endTime);
	}
	
	/**
	 * 统计每日广告位收益数据
	 */
	private void statisticsIncomeDaily(long startTime, long endTime) {
		List<StatisticsSlotIncomeDto> slotIncomeDtos = this.bidRecordService.statisticsIncomeDaily(startTime, endTime);
		if(CollectionUtils.isEmpty(slotIncomeDtos)) {
			return;
		}
		for (StatisticsSlotIncomeDto slotIncomeDto : slotIncomeDtos) {
			DataStatisticsSlotDaily slotDaily = new DataStatisticsSlotDaily();
			slotDaily.setDate(startTime);
			slotDaily.setAdSlotId(slotIncomeDto.getSlotId());
			slotDaily.setBudgetId(slotIncomeDto.getBudgetId());
			slotDaily.setSspEstimateIncome(slotIncomeDto.getSspEstimateIncome());
			slotDaily.setDspEstimateIncome(slotIncomeDto.getDspEstimateIncome());
			boolean ret = this.slotDailyService.updateSlotDailyIncome(slotDaily);
			if(!ret) {
				log.warn("没有更新到按天统计收益记录：", JSON.toJSONString(slotIncomeDto));
			}
		}
	}
	
	/**
	 * 统计每日请求、填充、展示、点击、下载、安装、dp数据
	 */
	private void statisticsSlotDailyData(Long startTime, Long endTime) {
		
		List<DataStatisticsSlotDaily> statisticsSlotDailies = this.slotDailyRealtimeService.getSlotDailyStatisticsData(startTime, endTime);
		if(CollectionUtils.isEmpty(statisticsSlotDailies)) {
			log.info("没有待持久化的广告位每日数据统计, date：{}", LocalDateUtils.formatSecondsToString(startTime, LocalDateUtils.DATE_TIME_FORMATTER));
			return;
		}
		for (DataStatisticsSlotDaily dataStatisticsSlotDaily : statisticsSlotDailies) {
			dataStatisticsSlotDaily.setDate(startTime);
			dataStatisticsSlotDaily.setSspEstimateIncome(0l);
			dataStatisticsSlotDaily.setDspEstimateIncome(0l);
			boolean ret = this.slotDailyService.saveSlotDaily(dataStatisticsSlotDaily);
			if(!ret) {
				this.slotDailyService.updateSlotDaily(dataStatisticsSlotDaily);
			}
		}
	}
}
