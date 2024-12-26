package com.juan.adx.manage.service.adx;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.juan.adx.manage.dao.adx.DspPartnerDao;
import com.juan.adx.common.exception.ExceptionEnum;
import com.juan.adx.common.exception.ServiceRuntimeException;
import com.juan.adx.common.model.PageData;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.model.dto.manage.DspPartnerOptionDto;
import com.juan.adx.model.entity.manage.DspPartner;
import com.juan.adx.model.enums.Status;
import com.juan.adx.model.form.manage.DspPartnerForm;

@Service
public class DspPartnerService {
	
	@Resource
	private DspPartnerDao dspPartnerDao;
	
	@Resource
	private DspBudgetService dspBudgetService;

	
	public PageData listDspPartner(DspPartnerForm form) {
		PageHelper.startPage(form.getPageNo(), form.getPageSize());
        List<DspPartner> dataList = this.dspPartnerDao.queryDspPartnerList(form);
        PageInfo<DspPartner> pageInfo = new PageInfo<DspPartner>(dataList);
		return new PageData().addPageInfo(pageInfo, dataList);	
	}

	public List<DspPartnerOptionDto> listDspPartnerOption(String name) {
		return this.dspPartnerDao.queryDspPartnerOption(name);
	}

	public boolean addDspPartner(DspPartner dspPartner) {
		dspPartner.setStatus(Status.VALID.getStatus());
		long nowSeconds = LocalDateUtils.getNowSeconds();
		dspPartner.setCtime(nowSeconds);
		dspPartner.setUtime(nowSeconds);
		return this.dspPartnerDao.saveDspPartner(dspPartner) > 0;
	}

	public DspPartner getDspPartner(Integer id) {
		return this.dspPartnerDao.queryDspPartner(id);
	}

	public boolean updateDspPartner(DspPartner dspPartner) {
		long nowSeconds = LocalDateUtils.getNowSeconds();
		dspPartner.setUtime(nowSeconds);
		return this.dspPartnerDao.updateDspPartner(dspPartner) > 0;
	}

	public boolean deleteDspPartner(Integer id) {
		boolean existBudgetMapping = this.dspBudgetService.existBudgetByPartnerId(id);
		if(existBudgetMapping) {
			throw new ServiceRuntimeException(ExceptionEnum.MUST_DELETE_DSP_BUDGET);
		}
		return this.dspPartnerDao.deleteDspPartner(id) > 0;
	}

}
