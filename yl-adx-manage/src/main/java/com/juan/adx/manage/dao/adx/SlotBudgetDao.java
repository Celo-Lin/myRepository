package com.juan.adx.manage.dao.adx;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.juan.adx.manage.mapper.adx.SlotBudgetMapper;
import com.juan.adx.model.dto.manage.SlotBudgetDto;
import com.juan.adx.model.entity.manage.SlotBudget;

@Repository
public class SlotBudgetDao {

	@Resource
	private SlotBudgetMapper slotBudgetMapper;

	public int countMappingByBudgetId(Integer budgetId) {
		return this.slotBudgetMapper.countMappingByBudgetId(budgetId);
	}

	public int countMappingBySlotId(Integer slotId) {
		return this.slotBudgetMapper.countMappingBySlotId(slotId);
	}
	
	public int countMappingBySlotIdAndBudgetId(Integer slotId, Integer budgetId) {
		return this.slotBudgetMapper.countMappingBySlotIdAndBudgetId(slotId, budgetId);
	}

	public List<SlotBudgetDto> querySlotBudgetList(Integer slotId) {
		return this.slotBudgetMapper.querySlotBudgetList(slotId);
	}

	public SlotBudgetDto querySlotBudget(Integer id) {
		return this.slotBudgetMapper.querySlotBudget(id);
	}
	
	public SlotBudget querySlotBudgetByBudgetIdAndSlotId(Integer slotId, Integer budgetId) {
		return this.slotBudgetMapper.querySlotBudgetByBudgetIdAndSlotId(slotId, budgetId);
	}

	public int saveSlotBudget(SlotBudget slotBudget) {
		return this.slotBudgetMapper.saveSlotBudget(slotBudget);
	}

	public int updateSlotBudget(SlotBudget slotBudget) {
		return this.slotBudgetMapper.updateSlotBudget(slotBudget);
	}

	public int deleteSlotBudget(Integer id) {
		return this.slotBudgetMapper.deleteSlotBudget(id);
	}

}
