package com.juan.adx.api.service;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.juan.adx.api.dao.SlotDao;
import com.juan.adx.common.validator.ParamAssert;
import com.juan.adx.model.entity.api.AdSlotWrap;

@Service
@CacheConfig(cacheManager = "cacheManager", cacheNames = {"slots"})
public class SlotService {
	
	@Resource
	private SlotDao slotDao;
	
	
	@Cacheable(key = "'slot-' + #id", unless = "#result == null")
	public AdSlotWrap getAdSlotWrapById(Integer id) {
		AdSlotWrap result = this.slotDao.getAdSlotWrapById(id);
		return result;
	}
	
	public AdSlotWrap test(Integer id) {
		ParamAssert.isTrue(id == null, "无效参数");
		return null;
	}
	

}
