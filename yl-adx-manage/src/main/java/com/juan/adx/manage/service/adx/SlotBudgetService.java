package com.juan.adx.manage.service.adx;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.juan.adx.manage.dao.adx.SlotBudgetDao;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.model.dto.manage.SlotBudgetDto;
import com.juan.adx.model.entity.manage.SlotBudget;

@Service
public class SlotBudgetService {

	@Resource
	private SlotBudgetDao slotBudgetDao;
	
	public boolean existMappingByBudgetId(Integer budgetId) {
		int count = this.slotBudgetDao.countMappingByBudgetId(budgetId);
		return count > 0;
	}

	public boolean existMappingBySlotId(Integer slotId) {
		int count = this.slotBudgetDao.countMappingBySlotId(slotId);
		return count > 0;
	}
	
	public boolean existMappingBySlotIdAndBudgetId(Integer slotId, Integer budgetId) {
		int count = this.slotBudgetDao.countMappingBySlotIdAndBudgetId(slotId, budgetId);
		return count > 0;
	}

	public List<SlotBudgetDto> listSlotBudgets(Integer slotId) {
		return this.slotBudgetDao.querySlotBudgetList(slotId);
	}

	public SlotBudgetDto getSlotBudget(Integer id) {
		return this.slotBudgetDao.querySlotBudget(id);
	}

	public void addSlotBudget(SlotBudgetDto slotBudgetDto) {
		long nowSeconds = LocalDateUtils.getNowSeconds();
		SlotBudget slotBudget = new SlotBudget();
		slotBudget.setSlotId(slotBudgetDto.getSlotId());
		slotBudget.setBudgetId(slotBudgetDto.getBudgetId());
		slotBudget.setDspAppId(StringUtils.trimToEmpty(slotBudgetDto.getDspAppId()));
		slotBudget.setDspSlotId(StringUtils.trimToEmpty(slotBudgetDto.getDspSlotId()));
		slotBudget.setLimitType(slotBudgetDto.getLimitType());
		slotBudget.setMaxRequests(slotBudgetDto.getMaxRequests());
		slotBudget.setPackageName(StringUtils.trimToEmpty(slotBudgetDto.getPackageName()));
		slotBudget.setSspFloorPrice(slotBudgetDto.getSspFloorPrice());
		slotBudget.setDspFloorPrice(slotBudgetDto.getDspFloorPrice());
		slotBudget.setSspPremiumRate(slotBudgetDto.getSspPremiumRate());
		slotBudget.setDspFloatingRate(slotBudgetDto.getDspFloatingRate());
		slotBudget.setHasRta(slotBudgetDto.getHasRta());
		slotBudget.setRtaPriorityValue(slotBudgetDto.getRtaPriorityValue());
		slotBudget.setRtaSspFloorPrice(slotBudgetDto.getRtaSspFloorPrice());
		slotBudget.setCtime(nowSeconds);
		slotBudget.setUtime(nowSeconds);
		this.addSlotBudget(slotBudget);
	}
	
	public void addSlotBudget(SlotBudget slotBudget) {
		this.slotBudgetDao.saveSlotBudget(slotBudget);
	}

	public void updateSlotBudget(SlotBudgetDto slotBudgetDto) {
		long nowSeconds = LocalDateUtils.getNowSeconds();
		SlotBudget slotBudget = new SlotBudget();
		slotBudget.setId(slotBudgetDto.getId());
		slotBudget.setSlotId(slotBudgetDto.getSlotId());
		slotBudget.setBudgetId(slotBudgetDto.getBudgetId());
		slotBudget.setDspAppId(StringUtils.trimToEmpty(slotBudgetDto.getDspAppId()));
		slotBudget.setDspSlotId(StringUtils.trimToEmpty(slotBudgetDto.getDspSlotId()));
		slotBudget.setLimitType(slotBudgetDto.getLimitType());
		slotBudget.setMaxRequests(slotBudgetDto.getMaxRequests());
		slotBudget.setPackageName(StringUtils.trimToEmpty(slotBudgetDto.getPackageName()));
		slotBudget.setSspFloorPrice(slotBudgetDto.getSspFloorPrice());
		slotBudget.setDspFloorPrice(slotBudgetDto.getDspFloorPrice());
		slotBudget.setSspPremiumRate(slotBudgetDto.getSspPremiumRate());
		slotBudget.setDspFloatingRate(slotBudgetDto.getDspFloatingRate());
		slotBudget.setHasRta(slotBudgetDto.getHasRta());
		slotBudget.setRtaPriorityValue(slotBudgetDto.getRtaPriorityValue());
		slotBudget.setRtaSspFloorPrice(slotBudgetDto.getRtaSspFloorPrice());
		slotBudget.setUtime(nowSeconds);
		this.updateSlotBudget(slotBudget);
	}
	
	public void updateSlotBudget(SlotBudget slotBudget) {
		this.slotBudgetDao.updateSlotBudget(slotBudget);
	}

	public void deleteSlotBudget(Integer id) {
		this.slotBudgetDao.deleteSlotBudget(id);
	}
	
	public SlotBudget querySlotBudgetByBudgetIdAndSlotId(Integer slotId, Integer budgetId) {
		return this.slotBudgetDao.querySlotBudgetByBudgetIdAndSlotId(slotId, budgetId);
	}
}
