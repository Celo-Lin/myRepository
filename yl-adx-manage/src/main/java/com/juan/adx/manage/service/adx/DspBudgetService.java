package com.juan.adx.manage.service.adx;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.juan.adx.manage.dao.adx.DspBudgetDao;
import com.juan.adx.common.exception.ExceptionEnum;
import com.juan.adx.common.exception.ServiceRuntimeException;
import com.juan.adx.common.model.PageData;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.common.validator.ParamAssert;
import com.juan.adx.model.dto.manage.DspBudgetDto;
import com.juan.adx.model.dto.manage.DspBudgetOptionDto;
import com.juan.adx.model.entity.manage.DspBudget;
import com.juan.adx.model.enums.Status;
import com.juan.adx.model.form.manage.DspBudgetForm;

@Service
public class DspBudgetService {
	
	@Resource
	private DspBudgetDao dspBudgetDao;
	
	@Resource
	private SlotBudgetService slotBudgetService;
	

	public boolean existBudgetByPartnerId(Integer dspPartnerId) {
		int count = this.dspBudgetDao.countBudgetByPartnerId(dspPartnerId);
		return count > 0;
	}

	public PageData listDspBudget(DspBudgetForm form) {
		PageHelper.startPage(form.getPageNo(), form.getPageSize());
        List<DspBudgetDto> dataList = this.dspBudgetDao.queryDspBudgeList(form);
        PageInfo<DspBudgetDto> pageInfo = new PageInfo<DspBudgetDto>(dataList);
		return new PageData().addPageInfo(pageInfo, dataList);	
	}

	public boolean addDspBudget(DspBudget dspBudget) {
		long nowSeconds = LocalDateUtils.getNowSeconds();
		dspBudget.setStatus(Status.VALID.getStatus());
		dspBudget.setCtime(nowSeconds);
		dspBudget.setUtime(nowSeconds);
		int ret = this.dspBudgetDao.saveDspBudget(dspBudget);
		return ret > 0;
	}

	public DspBudgetDto getDspBudget(Integer id) {
		return this.dspBudgetDao.queryDspBudget(id);
	}

	public boolean updateDspBudget(DspBudget dspBudget) {
		long nowSeconds = LocalDateUtils.getNowSeconds();
		dspBudget.setUtime(nowSeconds);
		int ret = this.dspBudgetDao.updateDspBudget(dspBudget);
		return ret > 0;
	}

	public boolean updateDspBudgetStatus(Integer id, Integer status) {
		Status statusEnum = Status.get(status);
		ParamAssert.isTrue(statusEnum == null, "预算状态无效");
		int ret = this.dspBudgetDao.updateDspBudgetStatus(id, status);
		return ret > 0;
	}

	public boolean deleteDspBudget(Integer id) {
		boolean existMapping = this.slotBudgetService.existMappingByBudgetId(id);
		if(existMapping) {
			throw new ServiceRuntimeException(ExceptionEnum.MUST_DELETE_SSP_SLOT);
		}
		int ret = this.dspBudgetDao.deleteDspBudget(id);
		return ret > 0;
	}

	public List<DspBudgetOptionDto> listDspBudgetOption(Integer dspPartnerId) {
		return this.dspBudgetDao.queryDspBudgetOption(dspPartnerId);
	}

}
