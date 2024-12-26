package com.juan.adx.api.dao;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson2.JSON;
import com.juan.adx.common.cache.RedisKeyUtil;
import com.juan.adx.common.cache.RedisTemplate;
import com.juan.adx.model.entity.api.AdSlotWrap;

@Repository
public class SlotDao {

	@Resource
	private RedisTemplate redisTemplate;
	
	public AdSlotWrap getAdSlotWrapById(Integer slotId) {
		String key = RedisKeyUtil.getAdSlotDataKey(slotId);
		String value = this.redisTemplate.STRINGS.get(key);
		if(StringUtils.isBlank(value)) {
			return null;
		}
		AdSlotWrap adSlotWrap = JSON.parseObject(value, AdSlotWrap.class);
		return adSlotWrap;
	}

}
