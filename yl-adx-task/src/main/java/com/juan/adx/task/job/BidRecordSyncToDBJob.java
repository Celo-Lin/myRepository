package com.juan.adx.task.job;

import java.time.LocalDate;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson2.JSON;
import com.juan.adx.task.schedule.AbstractJob;
import com.juan.adx.task.service.BidRecordService;
import com.juan.adx.common.cache.RedisKeyExpireTime;
import com.juan.adx.common.cache.RedisKeyUtil;
import com.juan.adx.common.cache.RedisTemplate;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.model.entity.api.BidRecord;

import lombok.extern.slf4j.Slf4j;

/**
 * 将RTB广告的出价记录明细保存到数据库
 * 执行频率：按秒（每30秒，执行一次）
 * 
 * 注意：重启时需要将此任务停止，并确保执行中的线程完毕
 */
@Slf4j
@Component("bidRecordSyncToDBJob")
public class BidRecordSyncToDBJob extends AbstractJob {
	
	@Resource
	private RedisTemplate redisTemplate;
	
	@Resource
	private BidRecordService bidRecordService;
	
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		String todayStr = LocalDateUtils.getNowDate(LocalDateUtils.DATE_PLAIN_FORMATTER);
		String todayKey = RedisKeyUtil.getAdBidRecordKey(todayStr);
		this.setKeyExpire(todayKey);
		this.bidRecordSyncToDB(todayKey);
		
		LocalDate yesterDayLocalDate = LocalDateUtils.minusDays(1);
		String yesterdayStr = LocalDateUtils.formatLocalDateToString(yesterDayLocalDate, LocalDateUtils.DATE_PLAIN_FORMATTER);
		String yesterdayKey = RedisKeyUtil.getAdBidRecordKey(yesterdayStr);
		this.setKeyExpire(yesterdayKey);
		this.bidRecordSyncToDB(yesterdayKey);
	}
	
	private void setKeyExpire(String key) {
		Long ttl = this.redisTemplate.KEYS.ttl(key);
		if(ttl != null && ttl.longValue() == -1) {
			this.redisTemplate.KEYS.expired(key, RedisKeyExpireTime.DAY_7);
		}
	}

	private void bidRecordSyncToDB(String bidRecordKey) {
		while (true) {
			String value = this.redisTemplate.LISTS.rpop(bidRecordKey);
			if(StringUtils.isBlank(value)) {
				break;
			}
			try {
				BidRecord bidRecord = JSON.parseObject(value, BidRecord.class);
				if(bidRecord == null) {
					break;
				}
				boolean ret = this.bidRecordService.saveBidRecord(bidRecord);
				if(!ret) {
					log.warn("出价记录已存在，乎略本条记录， bidRecord: {}", value);
				}
			} catch (Exception e) {
				log.error("保存出价记录异常，bidRecord: {}", value);
			}
			
		}
	}
	
	
	
//	private void initData(String key) {
//		for (int i = 0; i < 10; i++) {
//			BidRecord bidRecord = new BidRecord();
//			bidRecord.setTraceId(100l + i);
//			bidRecord.setSspPartnerId(10);
//			bidRecord.setDspPartnerId(10);
//			bidRecord.setAppId(10);
//			bidRecord.setSlotId(10);
//			bidRecord.setBudgetId(10);
//			bidRecord.setCooperationMode(1);
//			bidRecord.setSspBidPrice(10);
//			bidRecord.setSspReturnPrice(10);
//			bidRecord.setDspBidPrice(10);
//			bidRecord.setDspReturnPrice(100);
//			bidRecord.setReportDisplayStatus(0);
//			bidRecord.setSspFloorPriceSnapshot(10);
//			bidRecord.setDspFloorPriceSnapshot(10);
//			bidRecord.setSspPremiumRateSnapshot(10);
//			bidRecord.setDspFloatingRateSnapshot(10);
//			LocalDateTime dateTime = LocalDateUtils.plusDaysGetLocalDateTime(i);
//			long seconds = LocalDateUtils.parseLocalDateTimeToSeconds(dateTime);
//			bidRecord.setCtime(seconds);
//			this.redisTemplate.LISTS.lpush(key, JSON.toJSONString(bidRecord));
//		}
//	}
	
}
