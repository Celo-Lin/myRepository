package com.juan.adx.task.job;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import com.juan.adx.task.schedule.AbstractJob;
import com.juan.adx.task.service.AdvertSlotService;
import com.juan.adx.task.service.BudgetService;
import com.juan.adx.task.service.DataStatisticsSlotDailyRealtimeService;
import com.juan.adx.common.cache.RedisKeyUtil;
import com.juan.adx.common.cache.RedisTemplate;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.model.entity.manage.DataStatisticsSlotRealtime;
import com.juan.adx.model.entity.manage.DspBudget;
import com.juan.adx.model.entity.manage.SlotBudget;
import com.juan.adx.model.entity.manage.SspAdvertSlot;
import com.juan.adx.model.enums.ReportEventType;
import com.juan.adx.model.form.task.SlotStatisticsDataForm;

import lombok.extern.slf4j.Slf4j;

/**
 * 统计广告位时段上报事件数据
 * 执行频率：按分钟（每分钟执行一次）
 */
@Slf4j
@Component("statisticsSlotRealtimeJob")
public class StatisticsSlotRealtimeJob extends AbstractJob {

	@Resource
	private RedisTemplate redisTemplate;
	
	@Resource
	private AdvertSlotService advertSlotService;
	
	@Resource
	private BudgetService budgetService;
	
	@Resource
	private DataStatisticsSlotDailyRealtimeService slotDailyRealtimeService;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String todayStr = LocalDateUtils.getNowDate(LocalDateUtils.DATE_PLAIN_FORMATTER);
		String key = RedisKeyUtil.getSlotReportDataStatisticsKey(todayStr);
		this.statisticsSlotDailyRealtimeData(key);

	}
	
//	private void initRedisData() {
//		String todayStr = LocalDateUtils.getNowDate(LocalDateUtils.DATE_PLAIN_FORMATTER);
//		String key = RedisKeyUtil.getSlotReportDataStatisticsKey(todayStr);
//		this.redisTemplate.HASH.hincrby(key, "request_2023102001_10000601_9_1", 2);
//		this.redisTemplate.HASH.hincrby(key, "fill_2023102001_10000601_9_1", 20);
//		this.redisTemplate.HASH.hincrby(key, "display_2023102001_10000601_9_1", 200);
//		this.redisTemplate.HASH.hincrby(key, "click_2023102001_10000601_9_1", 2000);
//		this.redisTemplate.HASH.hincrby(key, "download_2023102001_10000601_9_1", 20000);
//		this.redisTemplate.HASH.hincrby(key, "install_2023102001_10000601_9_1", 200000);
//		this.redisTemplate.HASH.hincrby(key, "deeplink_2023102001_10000601_9_1", 2000000);
//	}
	
	
	public void statisticsSlotDailyRealtimeData(String redisKey) {
		Map<String, String> reportDataMap = this.redisTemplate.HASH.hgetall(redisKey);
		if(reportDataMap == null || reportDataMap.isEmpty()) {
			log.info("没有待持久化的广告位时段数据统计, keys：{}", redisKey);
			return;
		}
		for (Entry<String, String> item : reportDataMap.entrySet()) {
			String hashKey = item.getKey();
			String hashValue = item.getValue();
			//ReportEventType、todayHourStr、slotId、budgetId、mod
			String[] paramArray = StringUtils.split(hashKey, "_");
			String eventType = paramArray[0];
			String todayHourStr = paramArray[1];
			String slotId = paramArray[2];
			String budgetId = paramArray[3];
			String modStr = paramArray[4];
			Integer mod = Integer.valueOf(modStr);
			
			if(StringUtils.isAnyEmpty(slotId, budgetId) || !StringUtils.isNumeric(slotId) || !StringUtils.isNumeric(budgetId)) {
				log.error("广告位ID或预算ID值不合法, key:{} | field:{} | value:{}", redisKey, hashKey, hashValue);
				continue;
			}
			SspAdvertSlot slot = this.advertSlotService.getSlotById(Integer.valueOf(slotId));
			DspBudget budget = this.budgetService.getBudgetById(Integer.valueOf(budgetId));
			if(slot == null || budget == null) {
				log.error("广告位或预算信息不存在, key:{} | field:{} | value:{}", redisKey, hashKey, hashValue);
				continue;
			}
			DataStatisticsSlotRealtime slotDailyRealtime = this.initStatData(slot, budget, todayHourStr, mod);
			
			ReportEventType eventTypeEnum = ReportEventType.get(eventType);
			switch (eventTypeEnum) {
				case REQUEST:
					slotDailyRealtime.setRequestCount(Integer.valueOf(hashValue));
					break;
				case FILL:
					slotDailyRealtime.setFillCount(Integer.valueOf(hashValue));
					break;
				case DISPLAY:
					slotDailyRealtime.setDisplayCount(Integer.valueOf(hashValue));
					break;
				case CLICK:
					slotDailyRealtime.setClickCount(Integer.valueOf(hashValue));
					break;
				case DOWNLOAD:
					slotDailyRealtime.setDownloadCount(Integer.valueOf(hashValue));
					break;
				case INSTALL:
					slotDailyRealtime.setInstallCount(Integer.valueOf(hashValue));
					break;
				case DEEPLINK:
					slotDailyRealtime.setDeeplinkCount(Integer.valueOf(hashValue));
					break;
				default:
					break;
			}
			if(slotDailyRealtime.getId() != null && slotDailyRealtime.getId().intValue() > 0) {
				this.slotDailyRealtimeService.updateSlotRealtimeStatistics(slotDailyRealtime);
			}else {
				this.slotDailyRealtimeService.saveSlotRealtimeStatistics(slotDailyRealtime);
			}
		}
	}
	
	private DataStatisticsSlotRealtime initStatData(SspAdvertSlot slot, DspBudget budget, String hourStr, Integer mod) {
		LocalDateTime todayHourLocalDateTime = LocalDateUtils.formatStringToLocalDateTime(hourStr, LocalDateUtils.DATE_HOUR_PLAIN_FORMATTER);
		long todayHourSeconds = LocalDateUtils.getSecondsByLocalDateTime(todayHourLocalDateTime);
		
		SlotStatisticsDataForm form = new SlotStatisticsDataForm();
		form.setHourSeconds(todayHourSeconds);
		form.setSlotId(slot.getId());
		form.setBudgetId(budget.getId());
		form.setMod(mod);
		DataStatisticsSlotRealtime slotDailyRealtime = this.slotDailyRealtimeService.getSlotRealtime(form);
		if(slotDailyRealtime != null) {
			return slotDailyRealtime;
		}
		
		SlotBudget slotBudget = this.advertSlotService.getAdSlotBudgetBySlotIdWithBudgetId(slot.getId(), budget.getId());
		Integer maxRequests = slotBudget != null ? slotBudget.getMaxRequests() : 0;
		
		slotDailyRealtime = new DataStatisticsSlotRealtime();
		slotDailyRealtime.setSspPartnerId(slot.getSspPartnerId());
		slotDailyRealtime.setDspPartnerId(budget.getDspPartnerId());
		slotDailyRealtime.setAppId(slot.getAppId());
		slotDailyRealtime.setAdSlotId(slot.getId());
		slotDailyRealtime.setBudgetId(budget.getId());
		slotDailyRealtime.setAdSlotType(slot.getType());
		slotDailyRealtime.setMaxRequests(maxRequests);
		slotDailyRealtime.setRequestCount(0);
		slotDailyRealtime.setFillCount(0);
		slotDailyRealtime.setDisplayCount(0);
		slotDailyRealtime.setClickCount(0);
		slotDailyRealtime.setDownloadCount(0);
		slotDailyRealtime.setInstallCount(0);
		slotDailyRealtime.setDeeplinkCount(0);
		slotDailyRealtime.setSspEstimateIncome(0l);
		slotDailyRealtime.setDspEstimateIncome(0l);
		slotDailyRealtime.setCooperationMode(mod);
		slotDailyRealtime.setDate(todayHourSeconds);
		return slotDailyRealtime;
	}
	
}
