package com.juan.adx.task.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.juan.adx.task.mapper.adx.BudgetMapper;
import com.juan.adx.model.entity.manage.DspBudget;
import com.juan.adx.model.enums.Status;

@Service
public class BudgetService {
	
	@Resource
	private BudgetMapper budgetMapper;

	public DspBudget getBudgetById(Integer budgetId) {
		return this.budgetMapper.queryBudgetById(budgetId, Status.VALID.getStatus());
	}
	
	public List<DspBudget> getBudgetList(Integer budgetId, Integer size) {
		return this.budgetMapper.queryBudgetList(budgetId, Status.VALID.getStatus(), size);
	}
	

}
