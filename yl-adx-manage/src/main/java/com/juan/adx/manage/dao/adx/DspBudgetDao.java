package com.juan.adx.manage.dao.adx;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.juan.adx.manage.mapper.adx.DspBudgetMapper;
import com.juan.adx.model.dto.manage.DspBudgetDto;
import com.juan.adx.model.dto.manage.DspBudgetOptionDto;
import com.juan.adx.model.entity.manage.DspBudget;
import com.juan.adx.model.form.manage.DspBudgetForm;

@Repository
public class DspBudgetDao {

	@Resource
	private DspBudgetMapper dspBudgetMapper;

	public int countBudgetByPartnerId(Integer dspPartnerId) {
		return this.dspBudgetMapper.countBudgetByPartnerId(dspPartnerId);
	}

	public List<DspBudgetDto> queryDspBudgeList(DspBudgetForm form) {
		return this.dspBudgetMapper.queryDspBudgeList(form);
	}

	public int saveDspBudget(DspBudget dspBudget) {
		return this.dspBudgetMapper.saveDspBudget(dspBudget);
	}

	public DspBudgetDto queryDspBudget(Integer id) {
		return this.dspBudgetMapper.queryDspBudget(id);
	}

	public int updateDspBudget(DspBudget dspBudget) {
		return this.dspBudgetMapper.updateDspBudget(dspBudget);
	}

	public int updateDspBudgetStatus(Integer id, Integer status) {
		return this.dspBudgetMapper.updateDspBudgetStatus(id, status);
	}

	public int deleteDspBudget(Integer id) {
		return this.dspBudgetMapper.deleteDspBudget(id);
	}

	public List<DspBudgetOptionDto> queryDspBudgetOption(Integer dspPartnerId) {
		return this.dspBudgetMapper.queryDspBudgetOption(dspPartnerId);
	}
}
