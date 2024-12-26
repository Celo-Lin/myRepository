package com.juan.adx.task.job;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import com.juan.adx.task.schedule.AbstractJob;
import com.juan.adx.task.service.DataStatisticsIndexTrendService;
import com.juan.adx.task.service.DataStatisticsSlotDailyRealtimeService;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.model.entity.manage.DataStatisticsTrendIndex;
import com.juan.adx.model.entity.sspmanage.ChannelDataStatisticsTrendIndex;

/**
 * 统计后台首页趋势图数据
 * 执行频率：按天（每天凌晨执行一次，T+1）
 */
@Component("statisticsIndexTrendJob")
public class StatisticsIndexTrendJob extends AbstractJob {

	@Resource
	private DataStatisticsSlotDailyRealtimeService dailyRealtimeService;
	
	@Resource
	private DataStatisticsIndexTrendService indexTrendService;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		LocalDate localDate = LocalDateUtils.minusDays(1);
		this.statisticsIndexTrend(localDate);
		
		this.statisticsIndexTrendSsp(localDate);
	}

	private void statisticsIndexTrendSsp(LocalDate localDate) {
		long startSeconds = LocalDateUtils.getStartSecondsByLocalDate(localDate);
		long endSeconds = LocalDateUtils.getEndSecondsByLocalDate(localDate);
		List<ChannelDataStatisticsTrendIndex> trendIndexs = this.dailyRealtimeService.getIndexTrendSspStatisticsData(startSeconds, endSeconds);
		if(CollectionUtils.isEmpty(trendIndexs)) {
			return;
		}
		for (ChannelDataStatisticsTrendIndex trendIndex : trendIndexs) {
			trendIndex.setDate(startSeconds);
			this.indexTrendService.saveTrendIndexSsp(trendIndex);
		}
	}
	
	private void statisticsIndexTrend(LocalDate localDate) {
		long startSeconds = LocalDateUtils.getStartSecondsByLocalDate(localDate);
		long endSeconds = LocalDateUtils.getEndSecondsByLocalDate(localDate);
		List<DataStatisticsTrendIndex> trendIndexs = this.dailyRealtimeService.getIndexTrendStatisticsData(startSeconds, endSeconds);
		if(CollectionUtils.isEmpty(trendIndexs)) {
			return;
		}
		for (DataStatisticsTrendIndex trendIndex : trendIndexs) {
			trendIndex.setDate(startSeconds);
			this.indexTrendService.saveTrendIndex(trendIndex);
		}
	}
	
	
}
