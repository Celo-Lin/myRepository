package com.juan.adx.task.job;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.alibaba.fastjson2.JSON;
import com.juan.adx.task.schedule.AbstractJob;
import com.juan.adx.task.service.AdvertSlotService;
import com.juan.adx.task.service.AppService;
import com.juan.adx.task.service.BudgetService;
import com.juan.adx.task.service.DspPartnerConfigService;
import com.juan.adx.common.cache.RedisKeyExpireTime;
import com.juan.adx.common.cache.RedisKeyUtil;
import com.juan.adx.common.cache.RedisTemplate;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.model.entity.api.AdSlotBudgetSimple;
import com.juan.adx.model.entity.api.AdSlotBudgetWrap;
import com.juan.adx.model.entity.api.AdSlotWrap;
import com.juan.adx.model.entity.manage.DspBudget;
import com.juan.adx.model.entity.manage.DspPartnerConfig;
import com.juan.adx.model.entity.manage.SlotBudget;
import com.juan.adx.model.entity.manage.SspAdvertSlot;
import com.juan.adx.model.entity.manage.SspApp;
import com.juan.adx.model.enums.Status;

import lombok.extern.slf4j.Slf4j;

/**
 * 广告位配置数据刷入缓存（redis）
 * 执行频率：按分钟 （每分钟执行一次）
 */
@Slf4j
@Component("advertSlotDataRefresh")
public class AdvertSlotDataRefresh extends AbstractJob {
	
	@Resource
	private AppService appService;
	
	@Resource
	private AdvertSlotService adSlotService;
	
	@Resource
	private BudgetService budgetService;
	
	@Resource
	private DspPartnerConfigService dspPartnerConfigService;
	
	@Resource
	private RedisTemplate redisTemplate;
	

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		log.info("********* AdvertSlotDataRefresh start *********");
		
		this.refreshSlotData();
		
		stopWatch.stop();
		log.info("********* AdvertSlotDataRefresh process done use time {} millis *********", stopWatch.getTotalTimeMillis());
	}
	
	
	private void refreshSlotData() {
		List<SspAdvertSlot> adSlots = this.adSlotService.getAllSlots();
		if(adSlots == null || adSlots.isEmpty()) {
			log.warn("advert slot list is empty");
			return;
		}
		for (SspAdvertSlot adSlot : adSlots) {
			//查询广告位的应用数据
			SspApp app = this.appService.getAppById(adSlot.getAppId());
			if(app == null) {
				log.warn("advert slot App is empty, slotId:{}", adSlot.getId());
				continue;
			}
			
			//查询广告位关联的预算数据
			List<AdSlotBudgetWrap> slotBudgetWraps = this.getAdBudgets(adSlot.getId());
			if(slotBudgetWraps == null || slotBudgetWraps.isEmpty()) {
				log.warn("advert slot budget is empty, slotId:{}", adSlot.getId());
				continue;
			}
			
			Status status = Status.get(adSlot.getStatus());
			
			AdSlotWrap adSlotWrap = new AdSlotWrap();
			adSlotWrap.setAppName(app.getName());
			adSlotWrap.setAppPackageName(app.getPackageName());
			adSlotWrap.setSystemPlatform(app.getSystemPlatform());
			adSlotWrap.setIndustryId(app.getIndustryId());
			adSlotWrap.setAppStoreId(app.getAppStoreId());

			adSlotWrap.setSlotId(adSlot.getId());
			adSlotWrap.setSlotName(adSlot.getName());
			adSlotWrap.setAppId(adSlot.getAppId());
			adSlotWrap.setSlotType(adSlot.getType());
			adSlotWrap.setSlotCooperationMode(adSlot.getCooperationMode());
			adSlotWrap.setSspBidPrice(adSlot.getSspBidPrice());
			adSlotWrap.setSspPartnerId(app.getSspPartnerId());
			adSlotWrap.setStatus(status == Status.VALID);
			
			adSlotWrap.setAdSlotBudgets(slotBudgetWraps);
			
			String key = RedisKeyUtil.getAdSlotDataKey(adSlot.getId());
			String value = JSON.toJSONString(adSlotWrap);
			this.redisTemplate.STRINGS.setEx(key, RedisKeyExpireTime.DAY_1, value);
			
		}
	}
	
	private List<AdSlotBudgetWrap> getAdBudgets(Integer adSlodId) {
		List<SlotBudget> slotBudgets = this.adSlotService.getAdSlotBudgetListBySlotId(adSlodId);
		if(slotBudgets == null || slotBudgets.isEmpty()) {
			return null;
		}
		List<AdSlotBudgetWrap> slotBudgetWraps = new ArrayList<AdSlotBudgetWrap>();
		for (SlotBudget slotBudget : slotBudgets) {
			//查询预算详情
			DspBudget budget = this.budgetService.getBudgetById(slotBudget.getBudgetId());
			if(budget == null) {
				continue;
			}
			
			//保存请求数限制维度类型
			AdSlotBudgetSimple slotBudgetSimple = new AdSlotBudgetSimple();
			slotBudgetSimple.setSlotId(slotBudget.getSlotId());
			slotBudgetSimple.setBudgetId(slotBudget.getBudgetId());
			slotBudgetSimple.setLimitType(slotBudget.getLimitType());
			slotBudgetSimple.setMaxRequests(slotBudget.getMaxRequests());
			this.saveRequestLimitType(slotBudgetSimple);
			
			//查询DSP详情
			DspPartnerConfig dspPartnerConfig = this.dspPartnerConfigService.getDspPartnerConfig(budget.getDspPartnerId());
			if(dspPartnerConfig == null) {
				continue;
			}
			
			AdSlotBudgetWrap budgetWrap = new AdSlotBudgetWrap();
			budgetWrap.setDspAppId(slotBudget.getDspAppId());
			budgetWrap.setDspSlotId(slotBudget.getDspSlotId());
			budgetWrap.setLimitType(slotBudget.getLimitType());
			budgetWrap.setMaxRequests(slotBudget.getMaxRequests());
			budgetWrap.setPackageName(slotBudget.getPackageName());
			budgetWrap.setSspFloorPrice(slotBudget.getSspFloorPrice());
			budgetWrap.setDspFloorPrice(slotBudget.getDspFloorPrice());
			budgetWrap.setSspPremiumRate(slotBudget.getSspPremiumRate());
			budgetWrap.setDspFloatingRate(slotBudget.getDspFloatingRate());
			budgetWrap.setDspPartnerId(budget.getDspPartnerId());
			budgetWrap.setSlotId(slotBudget.getSlotId());
			budgetWrap.setBudgetId(slotBudget.getBudgetId());
			budgetWrap.setHasRta(slotBudget.getHasRta());
			budgetWrap.setRtaPriorityValue(slotBudget.getRtaPriorityValue());
			budgetWrap.setRtaSspFloorPrice(slotBudget.getRtaSspFloorPrice());
			
			
			budgetWrap.setApiUrl(dspPartnerConfig.getApiUrl());
			budgetWrap.setPinyinName(dspPartnerConfig.getPinyinName());
			budgetWrap.setPriceEncryptKey(dspPartnerConfig.getPriceEncryptKey());
			budgetWrap.setRtaApiSignKey(dspPartnerConfig.getRtaApiSignKey());
			budgetWrap.setRtaApiUrl(dspPartnerConfig.getRtaApiUrl());
			
			
			budgetWrap.setBudgetName(budget.getName());
			budgetWrap.setBudgetTitle(budget.getTitle());
			budgetWrap.setPictureUrl(budget.getPictureUrl());
			budgetWrap.setVideoUrl(budget.getVideoUrl());
			budgetWrap.setDeeplink(budget.getDeeplink());
			budgetWrap.setH5link(budget.getH5link());
			budgetWrap.setDownloadUrl(budget.getDownloadUrl());
			budgetWrap.setDeviceMaxRequests(budget.getDeviceMaxRequests());
			budgetWrap.setBudgetType(budget.getType());
			slotBudgetWraps.add(budgetWrap);
		}
		return slotBudgetWraps;
	}
	
	private void saveRequestLimitType(AdSlotBudgetSimple slotBudgetSimple) {
		//保存请求数限制维度类型
		String dateStr = LocalDateUtils.getNowDate(LocalDateUtils.DATE_PLAIN_FORMATTER);
		String limitKey = RedisKeyUtil.getDspReqLimitTypeKey(dateStr, slotBudgetSimple.getSlotId(), slotBudgetSimple.getBudgetId());
		String value = JSON.toJSONString(slotBudgetSimple);
		this.redisTemplate.STRINGS.setEx(limitKey, RedisKeyExpireTime.DAY_1, value);
	}

}
