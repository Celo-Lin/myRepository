package com.juan.adx.task.job;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.juan.adx.task.schedule.AbstractJob;
import com.juan.adx.task.service.DataStatisticsSlotDailyRealtimeService;
import com.juan.adx.task.service.DataStatisticsSlotMonitoringService;
import com.juan.adx.common.cache.RedisKeyUtil;
import com.juan.adx.common.cache.RedisTemplate;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.model.entity.manage.DataStatisticsSlotMonitoring;
import com.juan.adx.model.enums.ReportEventType;

import lombok.extern.slf4j.Slf4j;

/**
 * 内部后台-统计广告位监控数据
 * 执行频率：按分钟（每分钟执行一次）
 */
@Slf4j
@Component("statisticsSlotMonitoringJob")
public class StatisticsSlotMonitoringJob extends AbstractJob  {

	@Resource
	private DataStatisticsSlotDailyRealtimeService slotDailyRealtimeService;
	
	@Resource
	private DataStatisticsSlotMonitoringService slotMonitoringService;

	@Resource
	private RedisTemplate redisTemplate;
	
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		log.info("********* StatisticsSlotMonitoringJob start *********");
		
		LocalDate todayLocalDate = LocalDate.now();
		String todayStr = LocalDateUtils.formatLocalDateToString(todayLocalDate, LocalDateUtils.DATE_PLAIN_FORMATTER);
		long startTime = LocalDateUtils.getStartSecondsByLocalDate(todayLocalDate);
		long endTime = LocalDateUtils.getEndSecondsByLocalDate(todayLocalDate);
		this.statisticsSlotMonitoringData(startTime, endTime, todayStr);
		
		stopWatch.stop();
		log.info("********* StatisticsSlotMonitoringJob process done use time {} millis *********", stopWatch.getTotalTimeMillis());
	}
	
	/**
	 * 统计当天广告位监控数据
	 */
	private void statisticsSlotMonitoringData(long startTime, long endTime, String todayStr) {
		List<DataStatisticsSlotMonitoring> slotMonitorings = this.slotDailyRealtimeService.getSlotMonitoringStatisticsData(startTime, endTime);
		if(CollectionUtils.isEmpty(slotMonitorings)) {
			log.info("没有查询到广告位监控数据");
			return;
		}
		
		for (DataStatisticsSlotMonitoring slotMonitoring : slotMonitorings) {
			String reqTimeKey = RedisKeyUtil.getSlotRequestTimeMonitoringKey(todayStr, slotMonitoring.getAdSlotId());
			Map<String, String> lastReqTimeMap = this.redisTemplate.HASH.hgetall(reqTimeKey);
			String lastRequestTime = lastReqTimeMap != null ? lastReqTimeMap.get(ReportEventType.REQUEST.getType()) : null;
			String lastFillTime = lastReqTimeMap != null ? lastReqTimeMap.get(ReportEventType.FILL.getType()) : null;
			String lastDisplayTime = lastReqTimeMap != null ? lastReqTimeMap.get(ReportEventType.DISPLAY.getType()) : null;
			String lastClickTime = lastReqTimeMap != null ? lastReqTimeMap.get(ReportEventType.CLICK.getType()) : null;
			slotMonitoring.setLastRequestTime(StringUtils.isNotBlank(lastRequestTime) ? Long.valueOf(lastRequestTime) : 0L);
			slotMonitoring.setLastFillTime(StringUtils.isNotBlank(lastFillTime) ? Long.valueOf(lastFillTime) : 0L);
			slotMonitoring.setLastDisplayTime(StringUtils.isNotBlank(lastDisplayTime) ? Long.valueOf(lastDisplayTime) : 0L);
			slotMonitoring.setLastClickTime(StringUtils.isNotBlank(lastClickTime) ? Long.valueOf(lastClickTime) : 0L);
			slotMonitoring.setDate(startTime);
			boolean ret = this.slotMonitoringService.updateSlotMonitoring(slotMonitoring);
			if(!ret) {
				this.slotMonitoringService.saveSlotMonitoring(slotMonitoring);
			}
		}
	}
	
	
}
