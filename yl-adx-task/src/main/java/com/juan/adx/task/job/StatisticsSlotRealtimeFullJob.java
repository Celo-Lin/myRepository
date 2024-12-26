package com.juan.adx.task.job;

import java.time.LocalDate;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import com.juan.adx.task.schedule.AbstractJob;
import com.juan.adx.task.service.AdvertSlotService;
import com.juan.adx.task.service.BudgetService;
import com.juan.adx.task.service.DataStatisticsSlotDailyRealtimeService;
import com.juan.adx.common.cache.RedisKeyExpireTime;
import com.juan.adx.common.cache.RedisKeyUtil;
import com.juan.adx.common.cache.RedisTemplate;
import com.juan.adx.common.utils.LocalDateUtils;

/**
 * 统计广告位时段上报事件数据（T+1）
 * 执行频率：按天（每天凌晨执行一次）
 */
@Component("statisticsSlotRealtimeFullJob")
public class StatisticsSlotRealtimeFullJob extends AbstractJob {

	@Resource
	private RedisTemplate redisTemplate;
	
	@Resource
	private AdvertSlotService advertSlotService;
	
	@Resource
	private BudgetService budgetService;
	
	@Resource
	private DataStatisticsSlotDailyRealtimeService slotDailyRealtimeService;
	
	@Resource
	private StatisticsSlotRealtimeJob statisticsSlotRealtimeJob;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		LocalDate yesterDay = LocalDateUtils.minusDays(1);
		String todayStr = LocalDateUtils.formatLocalDateToString(yesterDay, LocalDateUtils.DATE_PLAIN_FORMATTER);
		String key = RedisKeyUtil.getSlotReportDataStatisticsKey(todayStr);
		this.setKeyExpire(key);
		this.statisticsSlotRealtimeJob.statisticsSlotDailyRealtimeData(key);
	}
	
	private void setKeyExpire(String key) {
		Long ttl = this.redisTemplate.KEYS.ttl(key);
		if(ttl != null && ttl.longValue() == -1) {
			this.redisTemplate.KEYS.expired(key, RedisKeyExpireTime.DAY_7);
		}
	}
	

}
