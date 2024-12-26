package com.juan.adx.api.service;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;
import com.juan.adx.common.cache.RedisKeyExpireTime;
import com.juan.adx.common.cache.RedisKeyUtil;
import com.juan.adx.common.cache.RedisTemplate;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.model.constants.ApiCommonConstants;
import com.juan.adx.model.dto.api.RequestLimitDto;
import com.juan.adx.model.enums.OsType;
import com.juan.adx.model.enums.RequestLimitType;
import com.juan.adx.model.ssp.common.request.SspReqDeviceId;

@Service
public class RequestLimitService {
	
	@Resource
	private RedisTemplate redisTemplate;

	
	/**
	 * 检查DSP每日最大请求数限制
	 */
	public boolean allowRequestDsp(RequestLimitDto requestLimitDto) {
		Integer maxRequest = requestLimitDto.getMaxRequests();
		Integer slotId = requestLimitDto.getSlotId();
		Integer budgetId = requestLimitDto.getBudgetId();
		if(maxRequest == ApiCommonConstants.DSP_REQUEST_ALWAYS_ALLOW) {
			return true;
		}
		if(maxRequest == ApiCommonConstants.DSP_REQUEST_NOT_ALLOW) {
			return false;
		}
		RequestLimitType limitType = RequestLimitType.get(requestLimitDto.getLimitType());
		if(limitType == null) {
			return false;
		}
		String dateStr = LocalDateUtils.getNowDate(LocalDateUtils.DATE_PLAIN_FORMATTER);
		//检查请求数限制
		String key = RedisKeyUtil.getDspReqMaxKey(dateStr, slotId, budgetId, limitType.getType());
		String value = this.redisTemplate.STRINGS.get(key);
		int count = value != null ? Integer.parseInt(value) : 0;
		return maxRequest.intValue() > count;
	}

	/**
	 * DSP每日最大请求计数器
	 * @param maxRequests	最大请求阀值
	 * @param slotId		广告位ID
	 */
	public void requestDspCounter(RequestLimitDto requestLimitDto, RequestLimitType limitType) {
		if(requestLimitDto == null) {
			return;
		}
		Integer maxRequest = requestLimitDto.getMaxRequests();
		Integer slotId = requestLimitDto.getSlotId();
		Integer budgetId = requestLimitDto.getBudgetId();
		if(maxRequest.intValue() <= ApiCommonConstants.DSP_REQUEST_ALWAYS_ALLOW) {
			return;
		}
		if(!Objects.equal(limitType.getType(), requestLimitDto.getLimitType())) {
			return;
		}
		String dateStr = LocalDateUtils.getNowDate(LocalDateUtils.DATE_PLAIN_FORMATTER);
		String key = RedisKeyUtil.getDspReqMaxKey(dateStr, slotId, budgetId, limitType.getType());
		Long ret = this.redisTemplate.STRINGS.incr(key);
		if(ret != null && ret.longValue() == 1) {
			this.redisTemplate.KEYS.expired(key, RedisKeyExpireTime.DAY_1);
		}
	}
	
	
	/**
	 * 检查DSP单设备每日最大请求数限制
	 */
	public boolean allowRequestDspByDevice(RequestLimitDto requestLimitDto) {
		Integer budgetId = requestLimitDto.getBudgetId();
		Integer deviceMaxRequests = requestLimitDto.getDeviceMaxRequests();
		Integer osType = requestLimitDto.getOsType();
		SspReqDeviceId sspReqDeviceId = requestLimitDto.getSspReqDeviceId();
		if(deviceMaxRequests == ApiCommonConstants.DSP_REQUEST_ALWAYS_ALLOW) {
			return true;
		}
		if(deviceMaxRequests == ApiCommonConstants.DSP_REQUEST_NOT_ALLOW) {
			return false;
		}
		OsType systemType = OsType.get(osType);
		if(systemType != OsType.ANDROID && systemType != OsType.IOS) {
			//非IOS\ANDROID设备时，不做单日设备请求数限制逻辑
			return true;
		}
		String key = this.getDeviceReqMaxRedisKey(sspReqDeviceId, systemType, budgetId);
		if(key == null) {
			return false;
		}
		String value = this.redisTemplate.STRINGS.get(key);
		int count = value != null ? Integer.parseInt(value) : 0;
		return deviceMaxRequests.intValue() > count;
	}
	
	
	/**
	 * DSP单设备每日最大请求计数器
	 * @param budgetId				预算ID
	 * @param deviceMaxRequests		每日单设备最大请求阀值
	 * @param osType				系统类型 @OsType
	 * @param sspReqDeviceId		设备标识
	 */
	public void requestDspByDeviceCounter(RequestLimitDto requestLimitDto) {
		if(requestLimitDto == null) {
			return;
		}
		Integer budgetId = requestLimitDto.getBudgetId();
		Integer deviceMaxRequests = requestLimitDto.getDeviceMaxRequests();
		Integer osType = requestLimitDto.getOsType();
		SspReqDeviceId sspReqDeviceId = requestLimitDto.getSspReqDeviceId();
		if(deviceMaxRequests == ApiCommonConstants.DSP_REQUEST_ALWAYS_ALLOW) {
			return;
		}
		OsType systemType = OsType.get(osType);
		if(systemType != OsType.ANDROID && systemType != OsType.IOS) {
			return;
		}
		String key = this.getDeviceReqMaxRedisKey(sspReqDeviceId, systemType, budgetId);
		if(key == null) {
			return;
		}
		Long ret = this.redisTemplate.STRINGS.incr(key);
		if(ret != null && ret.longValue() == 1) {
			this.redisTemplate.KEYS.expired(key, RedisKeyExpireTime.DAY_1);
		}
	}
	

	private String getDeviceReqMaxRedisKey(SspReqDeviceId sspReqDeviceId, OsType systemType, Integer budgetId) {
		String dateStr = LocalDateUtils.getNowDate(LocalDateUtils.DATE_PLAIN_FORMATTER);
		String deviceIdStr = null;
		if(systemType == OsType.ANDROID) {
			if(StringUtils.isNotBlank(sspReqDeviceId.getImei())) {
				deviceIdStr = sspReqDeviceId.getImei();
			}else if(StringUtils.isNotBlank(sspReqDeviceId.getImeiMd5())) {
				deviceIdStr = sspReqDeviceId.getImeiMd5();
			}else if(StringUtils.isNotBlank(sspReqDeviceId.getOaid())) {
				deviceIdStr = sspReqDeviceId.getOaid();
			}else if(StringUtils.isNotBlank(sspReqDeviceId.getOaidMd5())) {
				deviceIdStr = sspReqDeviceId.getOaidMd5();
			}
		} else if(systemType == OsType.IOS) {
			if(StringUtils.isNotBlank(sspReqDeviceId.getIdfa())) {
				deviceIdStr = sspReqDeviceId.getIdfa();
			}else if(StringUtils.isNotBlank(sspReqDeviceId.getIdfaMd5())) {
				deviceIdStr = sspReqDeviceId.getIdfaMd5();
			}else if(StringUtils.isNotBlank(sspReqDeviceId.getIdfv())) {
				deviceIdStr = sspReqDeviceId.getIdfv();
			}
		}
		return deviceIdStr != null ? RedisKeyUtil.getDspDeviceReqMaxKey(dateStr, budgetId, deviceIdStr) : null;
	}
}
