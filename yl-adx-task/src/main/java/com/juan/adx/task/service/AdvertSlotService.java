package com.juan.adx.task.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.juan.adx.task.mapper.adx.SlotBudgetMapper;
import com.juan.adx.task.mapper.adx.SlotMapper;
import com.juan.adx.model.entity.manage.SlotBudget;
import com.juan.adx.model.entity.manage.SspAdvertSlot;

@Service
public class AdvertSlotService {
	
	@Resource
	private SlotMapper slotMapper;
	
	@Resource
	private SlotBudgetMapper slotBudgetMapper;

	public List<SspAdvertSlot> getAllSlots() {
		/*return this.slotMapper.queryAllSlots(Status.VALID.getStatus());*/
		return this.slotMapper.queryAllSlots();
	}
	
	public SspAdvertSlot getSlotById(Integer slotId) {
		return this.slotMapper.querySlotById(slotId);
	}

	public SlotBudget getAdSlotBudgetBySlotIdWithBudgetId(Integer slotId, Integer budgetId) {
		return this.slotBudgetMapper.queryAdSlotBudgetBySlotIdWithBudgetId(slotId, budgetId);
	}

	public List<SlotBudget> getAdSlotBudgetListBySlotId(Integer slotId) {
		return this.slotBudgetMapper.queryAdSlotBudgetListBySlotId(slotId);
	}

}
