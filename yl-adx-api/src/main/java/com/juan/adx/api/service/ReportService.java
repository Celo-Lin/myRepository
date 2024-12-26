package com.juan.adx.api.service;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson2.JSON;
import com.juan.adx.common.cache.RedisKeyExpireTime;
import com.juan.adx.common.cache.RedisKeyUtil;
import com.juan.adx.common.cache.RedisTemplate;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.model.dto.api.RequestLimitDto;
import com.juan.adx.model.entity.api.AdSlotBudgetSimple;
import com.juan.adx.model.entity.api.ReportLinkParam;
import com.juan.adx.model.entity.api.RtbDisplayRecord;
import com.juan.adx.model.enums.CooperationModeSimple;
import com.juan.adx.model.enums.ReportEventType;
import com.juan.adx.model.enums.RequestLimitType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReportService {
	
	private final static String FORMAT = "%s_%s_%d_%d_%d";
	
	@Resource
	private RedisTemplate redisTemplate;
	
	@Resource
	private RequestLimitService requestLimitService;
	

	public void saveReportEvent(ReportEventType eventType, ReportLinkParam reportLinkParam) {
		this.saveReportEvent(eventType, reportLinkParam.getSlotId(), reportLinkParam.getBudgetId(), reportLinkParam.getMod(), reportLinkParam.getTraceId());
	}

	public void saveReportEvent(ReportEventType eventType, Integer slotId, Integer budgetId, Integer mod, Long traceId) {
		
		String todayHourStr = LocalDateUtils.getNowDate(LocalDateUtils.DATE_HOUR_PLAIN_FORMATTER);
		String todayStr = LocalDateUtils.getNowDate(LocalDateUtils.DATE_PLAIN_FORMATTER);
		String key = RedisKeyUtil.getSlotReportDataStatisticsKey(todayStr);
		String field  = null;
		
		String reqTimeKey = RedisKeyUtil.getSlotRequestTimeMonitoringKey(todayStr, slotId);
		String reqTimeField  = null;
		
		String limitKey = RedisKeyUtil.getDspReqLimitTypeKey(todayStr, slotId, budgetId);
		String limitValue = this.redisTemplate.STRINGS.get(limitKey);
		RequestLimitDto requestLimitDto = null;
		if(StringUtils.isNotBlank(limitValue)) {
			AdSlotBudgetSimple slotBudgetSimple = JSON.parseObject(limitValue, AdSlotBudgetSimple.class);
			requestLimitDto = new RequestLimitDto();
			requestLimitDto.setSlotId(slotId);
			requestLimitDto.setBudgetId(budgetId);
			requestLimitDto.setLimitType(slotBudgetSimple.getLimitType());
			requestLimitDto.setMaxRequests(slotBudgetSimple.getMaxRequests());
		}
		
		switch (eventType) {
			case REQUEST:
				this.requestLimitService.requestDspCounter(requestLimitDto, RequestLimitType.REQUEST);
				field = String.format(FORMAT, ReportEventType.REQUEST.getType(), todayHourStr, slotId, budgetId, mod);
				reqTimeField = ReportEventType.REQUEST.getType();
				break;
			case FILL:
				field = String.format(FORMAT, ReportEventType.FILL.getType(), todayHourStr, slotId, budgetId, mod);
				reqTimeField = ReportEventType.FILL.getType();
				break;
			case DISPLAY:
				this.requestLimitService.requestDspCounter(requestLimitDto, RequestLimitType.DISPLAY);
				field = String.format(FORMAT, ReportEventType.DISPLAY.getType(), todayHourStr, slotId, budgetId, mod);
				reqTimeField = ReportEventType.DISPLAY.getType();
				this.saveRtbDisplay(mod, slotId, budgetId, traceId);
				break;
			case CLICK:
				this.requestLimitService.requestDspCounter(requestLimitDto, RequestLimitType.CLICK);
				field = String.format(FORMAT, ReportEventType.CLICK.getType(), todayHourStr, slotId, budgetId, mod);
				reqTimeField = ReportEventType.CLICK.getType();
				break;
			case DOWNLOAD:
				this.requestLimitService.requestDspCounter(requestLimitDto, RequestLimitType.DEEPLINK_DOWNLOAD);
				field = String.format(FORMAT, ReportEventType.DOWNLOAD.getType(), todayHourStr, slotId, budgetId, mod);
				break;
			case INSTALL:
				this.requestLimitService.requestDspCounter(requestLimitDto, RequestLimitType.INSTALLED);
				field = String.format(FORMAT, ReportEventType.INSTALL.getType(), todayHourStr, slotId, budgetId, mod);
				break;
			case DEEPLINK:
				this.requestLimitService.requestDspCounter(requestLimitDto, RequestLimitType.DEEPLINK_DOWNLOAD);
				field = String.format(FORMAT, ReportEventType.DEEPLINK.getType(), todayHourStr, slotId, budgetId, mod);
				break;
			default:
				break;
		}
		//广告事件上报数据统计KEY的过期时间由定时任务刷入DB时设置
		this.redisTemplate.HASH.hincrby(key, field, 1);
		
		//记录广告位最后请求时间
		if(StringUtils.isNotBlank(reqTimeField)) {
			Boolean reqTimeKeyExists = this.redisTemplate.KEYS.exists(reqTimeKey);
			this.redisTemplate.HASH.hset(reqTimeKey, reqTimeField, String.valueOf(LocalDateUtils.getNowSeconds()));
			if(reqTimeKeyExists == null || reqTimeKeyExists.booleanValue() == Boolean.FALSE.booleanValue()) {
				this.redisTemplate.KEYS.expired(limitKey, RedisKeyExpireTime.DAY_1);
			}
		}
		
	}
	
	private void saveRtbDisplay(Integer mod, Integer slotId, Integer budgetId, Long traceId) {
		if(CooperationModeSimple.RTB.getMode() != mod.intValue()) {
			return;
		}
		if(traceId == null || traceId.longValue() <= 0) {
			log.error("RTB 展示事件回传traceId为空，traceId:{}", traceId);
			return;
		}
		RtbDisplayRecord rtbDisplayRecord = new RtbDisplayRecord();
		rtbDisplayRecord.setBudgetId(budgetId);
		rtbDisplayRecord.setSlotId(slotId);
		rtbDisplayRecord.setTraceId(traceId);
		rtbDisplayRecord.setCtime(LocalDateUtils.getNowSeconds());
		String key = RedisKeyUtil.getRtbReportDisplayRecordKey();
		String value = JSON.toJSONString(rtbDisplayRecord);
		this.redisTemplate.LISTS.lpush(key, value);
	}
	
}
