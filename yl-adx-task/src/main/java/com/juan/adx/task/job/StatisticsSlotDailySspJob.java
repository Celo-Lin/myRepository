package com.juan.adx.task.job;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson2.JSON;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.model.entity.manage.DataStatisticsSlotDailySsp;
import com.juan.adx.model.entity.task.StatisticsSlotIncomeDto;
import com.juan.adx.model.enums.AuditStatus;
import com.juan.adx.task.schedule.AbstractJob;
import com.juan.adx.task.service.BidRecordService;
import com.juan.adx.task.service.DataStatisticsSlotDailyRealtimeService;
import com.juan.adx.task.service.DataStatisticsSlotDailySspService;

import lombok.extern.slf4j.Slf4j;

/**
 * 渠道后台-统计广告位上报事件数据、收益数据
 * 执行频率：按天（每天凌晨执行一次，T+1）
 */
@Slf4j
@Component("statisticsSlotDailySspJob")
public class StatisticsSlotDailySspJob extends AbstractJob  {

	@Resource
	private DataStatisticsSlotDailyRealtimeService slotDailyRealtimeService;

	@Resource
	private DataStatisticsSlotDailySspService slotDailySspService;
	
	@Resource
	private BidRecordService bidRecordService;
	
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		LocalDate yesterDay = LocalDateUtils.minusDays(1);
		long startTime = LocalDateUtils.getStartSecondsByLocalDate(yesterDay);
		long endTime =LocalDateUtils.getEndSecondsByLocalDate(yesterDay);
		//统计渠道后台上报事件数据
		this.statisticsSlotDailySspData(startTime, endTime);
		//统计渠道后台广告位收益数据
		this.statisticsIncomeDailySsp(startTime, endTime);
	}
	
	
	/**
	 * 统计渠道的每日广告位收益数据
	 */
	private void statisticsIncomeDailySsp(long startTime, long endTime) {
		List<StatisticsSlotIncomeDto> slotIncomeDtos = this.bidRecordService.statisticsIncomeDailySsp(startTime, endTime);
		if(CollectionUtils.isEmpty(slotIncomeDtos)) {
			return;
		}
		for (StatisticsSlotIncomeDto slotIncomeDto : slotIncomeDtos) {
			DataStatisticsSlotDailySsp slotDailySsp = new DataStatisticsSlotDailySsp();
			slotDailySsp.setDate(startTime);
			slotDailySsp.setSspPartnerId(slotIncomeDto.getSspPartnerId());
			slotDailySsp.setAdSlotId(slotIncomeDto.getSlotId());
			slotDailySsp.setSspEstimateIncome(slotIncomeDto.getSspEstimateIncome());
			boolean ret = this.slotDailySspService.updateSlotDailySspIncome(slotDailySsp);
			if(!ret) {
				log.warn("没有更新到SSP的按天统计收益记录：", JSON.toJSONString(slotIncomeDto));
			}
		}
	}
	
	/**
	 * 统计渠道的每日请求、填充、展示、点击、下载、安装、dp数据
	 */
	private void statisticsSlotDailySspData(Long startTime, Long endTime) {
		
		List<DataStatisticsSlotDailySsp> slotDailySsps = this.slotDailyRealtimeService.getSlotDailySspStatisticsData(startTime, endTime);
		if(CollectionUtils.isEmpty(slotDailySsps)) {
			log.info("没有待持久化的SSP广告位每日数据统计, date：{}", LocalDateUtils.formatSecondsToString(startTime, LocalDateUtils.DATE_TIME_FORMATTER));
			return;
		}
		for (DataStatisticsSlotDailySsp slotDailySsp : slotDailySsps) {
			slotDailySsp.setDate(startTime);
			slotDailySsp.setAuditStatus(AuditStatus.UNAUDITED.getStatus());
			slotDailySsp.setSspEstimateIncome(0l);
			boolean ret = this.slotDailySspService.saveSlotDailySsp(slotDailySsp);
			if(!ret) {
				this.slotDailySspService.updateSlotDailySsp(slotDailySsp);
			}
		}
	}
}
